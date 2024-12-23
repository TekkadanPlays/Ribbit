package com.example.ribbit

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ribbit.ui.theme.RibbitTheme
import androidx.compose.material3.DrawerState // Import DrawerState if you plan to use it
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState // Import rememberDrawerState if you plan to use it
import androidx.compose.material3.ExperimentalMaterial3Api // Import if you are using experimental APIs
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember // Import remember if you plan to use it
import androidx.compose.runtime.setValue

// Import the DrawerContent composable
import com.example.ribbit.DrawerContent

class MainActivity : ComponentActivity() {
    private var npub: String? = null
    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            AccountManager.handleSignInResponse(result.data)
        }
    }
    private var showProfileScreen by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AccountManager.setSignInLauncher(signInLauncher)
        AccountManager.setContext(this)
        refreshUI()

        AccountManager.setOnSignInSuccess { npub ->
            this.npub = npub
            refreshUI()
        }
    }

    private fun refreshUI() {
        setContent {
            RibbitTheme {
                if (npub != null) {
                    FeedScreen(npub!!) {
                        showProfileScreen = true
                    }
                } else {
                    AccountManager.SignInScreen()
                }
                if (showProfileScreen) {
                    ProfileScreen(npub!!)
                }
            }
        }
    }

    fun enterDebugMode(npub: String) {
        this.npub = npub
        refreshUI()
    }
}
