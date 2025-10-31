package uk.ac.tees.mad.planty.presentation.HIltViewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


import javax.inject.Inject


@HiltViewModel
class AuthViewmodel @Inject constructor(): ViewModel() {


    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    fun logoutUser() {

        auth.signOut()

    }



    fun signUp(
        email: String,
        password: String,
        name: String,
        onResult: (String, Boolean) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val userId = user?.uid

                        if (userId != null) {
                            val userInfo = PostUserInfo(
                                profileImageUrl = "",
                                name = name,
                                email = email,
                                uid = userId,
                                passkey = password,
                            )

                            firestore.collection("user").document(userId).set(userInfo)
                                .addOnSuccessListener {
                                    onResult("Signup successful", true)
                                }.addOnFailureListener { exception ->
                                    auth.currentUser?.delete()
                                    onResult("Failed to save user info", false)
                                }
                        } else {
                            onResult("User ID not found", false)
                        }
                    } else {
                        val errorMessage = when (task.exception) {
                            is FirebaseAuthUserCollisionException -> "This email is already registered"
                            is FirebaseAuthWeakPasswordException -> "Password is too weak"
                            else -> task.exception?.localizedMessage ?: "Signup failed"
                        }
                        onResult("mine $errorMessage ", false)
                    }
                }
            } catch (e: Exception) {
                onResult("Unexpected error: ${e.localizedMessage}", false)
            }
        }
    }


}



data class PostUserInfo(
    val profileImageUrl: String,
    val name: String,
    val email: String,
    val uid: String,
    val passkey: String,
    )

data class GetUserInfo(
    val profileImageUrl: String = "",
    val name: String = "",
    val email: String = "",
    val uid: String = "",
    val passkey: String = "",
    )