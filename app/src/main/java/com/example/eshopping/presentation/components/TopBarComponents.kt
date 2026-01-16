package com.example.eshopping.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eshopping.R
import com.example.eshopping.presentation.navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),

        title = {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Hello,",
                    color = Color.Black,
                    fontSize = 14.sp,
                    lineHeight = 16.sp
                )
                Text(
                    text = "Himanshu",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 16.sp
                )
            }
        },

        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.ProfileScreen)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Profile"
                )
            }
        },

        actions = {

            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Notification",
                    modifier = Modifier.size(22.dp)
                )
            }

            IconButton(
                onClick = {
                    navController.navigate(Routes.CartScreen)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Cart",
                    modifier = Modifier.size(22.dp)
                )
            }
        },

        scrollBehavior = scrollBehavior
    )
}