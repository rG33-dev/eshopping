package com.example.eshopping.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eshopping.R
import com.example.eshopping.presentation.navigation.Routes

@Composable
fun SearchBarComponentForHomeScreen(
    navController: NavController
) {
    var query by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                navController.navigate(Routes.SearchScreen)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            tint = Color.Gray,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Voice Search",
            tint = Color.Gray,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Camera Search",
            tint = Color.Gray,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}
