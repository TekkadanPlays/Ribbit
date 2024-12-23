package com.example.ribbit

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object AccountManager {
    private var onSignInSuccess: ((String) -> Unit)? = null
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    private lateinit var context: Context

    fun setSignInLauncher(launcher: ActivityResultLauncher<Intent>) {
        signInLauncher = launcher
    }

    fun setContext(context: Context) {
        this.context = context
    }

    fun startSignInFlow() {
        // Logic to start the sign-in flow
    }

    fun handleSignInResponse(data: Intent?) {
        // Logic to handle sign-in response
    }

    fun setOnSignInSuccess(onSuccess: (String) -> Unit) {
        onSignInSuccess = onSuccess
    }

    @Composable
    fun SignInScreen() {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Sign in with Nostr",
                modifier = Modifier
                    .clickable { startSignInFlow() }
                    .padding(16.dp)
            )
            Button(onClick = { enterDebugMode() }) {
                Text("Enter Debug Mode")
            }
        }
    }

    private fun enterDebugMode() {
        val mockNpub = "npub17pysfuwqhynfqqu7dsd37v3g4wag6larzac6kk5lkzepr9y6xmaqgsyerc"
        Log.d("AccountManager", "Entering debug mode with npub: $mockNpub")
        onSignInSuccess?.invoke(mockNpub)
        (context as? MainActivity)?.enterDebugMode(mockNpub)
    }
}
