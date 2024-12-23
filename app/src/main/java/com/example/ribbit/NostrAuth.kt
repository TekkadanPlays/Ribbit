package com.example.ribbit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.google.gson.Gson // Import Gson

data class Event(
    val id: String,
    val pubKey: String,
    val createdAt: Long,
    val kind: Int,
    val tags: Array<Array<String>>,
    val content: String,
    var sig: String = ""
)

object NostrAuth {
    private var onSignInSuccess: ((String) -> Unit)? = null
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    private lateinit var context: Context

    fun setSignInLauncher(launcher: ActivityResultLauncher<Intent>) {
        signInLauncher = launcher
    }

    fun setContext(context: Context) {
        this.context = context
    }

    fun sendSigningRequest(event: Event) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("nostrsigner:get_public_key"))
        intent.`package` = "com.greenart7c3.nostrsigner"

        // Log the event details
        Log.d("NostrAuth", "Sending signing request for event: $event")

        val permissions = listOf(
            Permission("sign_event", 22242),
            Permission("nip04_encrypt"),
            Permission("nip04_decrypt"),
            Permission("nip44_encrypt"),
            Permission("nip44_decrypt"),
            Permission("decrypt_zap_event")
        )
        intent.putExtra("permissions", permissions.toJson())
        intent.putExtra("type", "get_public_key")
        intent.putExtra("event", event.toJson()) // Convert event to JSON

        signInLauncher.launch(intent)
    }

    fun handleSignInResponse(data: Intent?) {
        val npub = data?.data?.getQueryParameter("signature")
        if (npub != null) {
            Log.d("NostrAuth", "Received npub: $npub")
            onSignInSuccess?.invoke(npub)
        } else {
            Log.e("NostrAuth", "Failed to retrieve npub from response")
        }
    }

    fun setOnSignInSuccess(onSuccess: (String) -> Unit) {
        onSignInSuccess = onSuccess
    }

    private fun List<Permission>.toJson(): String {
        return this.joinToString(separator = ",") { permission ->
            val versionPart = permission.version?.let { ":$it" } ?: ""
            "{\"${permission.name}\"$versionPart}"
        }
    }

    private fun Event.toJson(): String {
        // Convert the Event object to a JSON string
        return Gson().toJson(this) // Use Gson or any other JSON library
    }
}

data class Permission(
    val name: String,
    val version: Int? = null
)
