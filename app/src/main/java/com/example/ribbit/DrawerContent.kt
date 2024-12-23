// DrawerContent.kt
package com.example.ribbit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(drawerState: DrawerState, onProfileClicked: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(320.dp)
            .background(Color(0xFF333333))
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .padding(8.dp)
                .background(Color.White, shape = MaterialTheme.shapes.small),
            contentAlignment = Alignment.Center
        ) {
            Text("?", fontSize = 40.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Profile", modifier = Modifier
            .clickable {
                onProfileClicked()
                coroutineScope.launch {
                    drawerState.close()
                }
            }
            .padding(vertical = 8.dp)
            .fillMaxWidth(), color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Settings", modifier = Modifier
            .clickable { /* Navigate to Settings */ }
            .padding(vertical = 8.dp)
            .fillMaxWidth(), color = Color.White)
    }
}
