package com.example.eshopping.presentation.Utils

import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.retain.LocalRetainedValuesStoreProvider
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.strictmode.FragmentStrictMode
import androidx.navigation.ActivityNavigatorExtras


@Composable
fun NotificationPermissionRequest(){

    val context = LocalContext.current

    val  reqPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()

    ) {

        isGranted : Boolean ->
        if (isGranted){
            Toast.makeText(context,"Permission Granted", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context,"Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    //check for android 13 and if permission is already granted tiramisu is name for android 13
    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU)  // whats this??
    {
        val permissionStatus = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.POST_NOTIFICATIONS
        )

        if (permissionStatus != PackageManager.PERMISSION_DENIED) {
            //REq the permission

            LaunchedEffect(Unit) {
                reqPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }

        }
    }





}
