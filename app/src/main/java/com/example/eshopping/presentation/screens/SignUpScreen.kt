package com.example.eshopping.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eshopping.presentation.Utils.CustomTextField
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.eshopping.R


@Preview(showBackground = true)
@Composable
fun SignUp() {


    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "Join Us",

            fontSize = 24.sp,
            //android.R.style,make this text bold
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.Start)

        )


        CustomTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = "First Name",
            //leadingIcon = Icon(painter = painterResource(id = R.drawable.ic_launcher_background)), //this icon will appear before name
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        CustomTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = "Last Name",
            //leadingIcon = Icon(painter = painterResource(id = R.drawable.ic_launcher_background)), //this icon will appear before name
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )


        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            //leadingIcon = Icon(painter = painterResource(id = R.drawable.ic_launcher_background)), //this icon will appear before name
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            visualTransformation = PasswordVisualTransformation(),//use to hide password
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)//use to hide password
        )
        CustomTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirm Password",
            //leadingIcon = Icon(painter = painterResource(id = R.drawable.ic_launcher_background)), //this icon will appear before name
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        CustomTextField(
            value = contact,
            onValueChange = { contact = it },
            label = "Mobile",
            //leadingIcon = Icon(painter = painterResource(id = R.drawable.ic_launcher_background)), //this icon will appear before name
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Mail",
            //leadingIcon = Icon(painter = painterResource(id = R.drawable.ic_launcher_background)), //this icon will appear before name
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Button(
            onClick = {
                if (firstName.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && email.isNotEmpty()) {
                    if (password == confirmPassword) {
                        Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Password Mismatch", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Please Fill Required Details", Toast.LENGTH_SHORT)
                        .show()
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_500))
        ) {
            Text(text = "Sign Up", color = colorResource(id = R.color.white))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text ="Already a user?")
            TextButton(onClick = {}) {
                Text(text="Login",color=colorResource(id=R.color.purple_500))
            }


        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f))

            Text(text = "or", modifier = Modifier.padding(horizontal = 8.dp))


            HorizontalDivider(modifier = Modifier.weight(1f))


        }
        OutlinedButton(
            onClick = {},

            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp)

        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(text = "Login with Google")

        }


    }


}

