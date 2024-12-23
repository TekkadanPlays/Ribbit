package com.example.ribbit

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(npub: String) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var showCopiedToClipboardSnackbar by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile picture
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.White, shape = MaterialTheme.shapes.small),
                contentAlignment = Alignment.Center
            ) {
                Text("?", fontSize = 60.sp, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Username
            Text("Mock User", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            // NPub
            Text(
                npub,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .clickable {
                        coroutineScope.launch {
                            copyToClipboard(context, npub)
                            showCopiedToClipboardSnackbar = true
                            delay(2000) // Show the snackbar for 2 seconds
                            showCopiedToClipboardSnackbar = false
                        }
                    }
            )
        }

        if (showCopiedToClipboardSnackbar) {
            Snackbar(
                modifier = Modifier.align(Alignment.BottomCenter),
                content = { Text("NPub copied to clipboard") }
            )
        }
    }
}

private fun copyToClipboard(context: Context, text: String) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("NPub", text)
    clipboardManager.setPrimaryClip(clipData)
    Toast.makeText(context, "NPub copied to clipboard", Toast.LENGTH_SHORT).show()
}
