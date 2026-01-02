package com.example.eshopping.presentation

import android.R.attr.text
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eshopping.presentation.Utils.CustomTextField


@Preview(showBackground = true)
@Composable
fun LoginScreen() {


    val context =LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Login",
            fontSize = 24.sp,
            style = TextStyle(fontWeight = FontWeight.Bold)
        )

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Enter Mail",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Enter Password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = "Forgot Password?", modifier = Modifier
                .align(Alignment.End)
                .clickable(onClick = {
                    ""
                })
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Button(onClick = {
            if (email.isNotBlank() && password.isNotBlank()){  //Verify the user details from database



                Toast.makeText(context,"Logged in" , Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"Wrong credentials" , Toast.LENGTH_SHORT).show()
            }
        },
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            shape = RoundedCornerShape(8.dp),

            colors = ButtonDefaults.buttonColors(colorResource(android.R.color.holo_purple))
        )
        {
            Text(" Sign In",
                )

        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text ="Already a user?")
            TextButton(onClick = {}) {
                Text(text="SignUp",color=colorResource(id= com.example.eshopping.R.color.purple_500))
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
                painter = painterResource(id = com.example.eshopping.R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(text = "Login with Google")

        }


    }




}



