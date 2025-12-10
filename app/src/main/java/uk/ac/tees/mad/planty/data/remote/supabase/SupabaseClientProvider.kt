package uk.ac.tees.mad.planty.data.remote.supabase

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.storage.Storage

object SupabaseClientProvider {
    val client =
        createSupabaseClient(
            supabaseUrl = "https://dfrypkzyrudscklkvxfo.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRmcnlwa3p5cnVkc2NrbGt2eGZvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQ1NzE3MTEsImV4cCI6MjA4MDE0NzcxMX0.pMVcvJBcSgdcrOxLGlaVDTeGaRrN0-WJKmFTIolCt14"
        ) {
            install(GoTrue.Companion)
            install(Storage.Companion)
        }


}
