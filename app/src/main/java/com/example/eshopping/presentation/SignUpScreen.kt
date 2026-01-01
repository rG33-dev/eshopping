package com.example.eshopping.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eshopping.presentation.Utils.CustomTextField
import java.time.format.TextStyle
import androidx.compose.material3.Icon





@Preview(showBackground = true)
@Composable
fun SignUp(){




    val context = LocalContext.current
    var firstName by remember{ mutableStateOf("") }
    val lastName by remember{ mutableStateOf("") }
    val password by remember{ mutableStateOf("") }
    val confirmPassword by remember{ mutableStateOf("") }
    val contact by remember{ mutableStateOf("") }


    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = "Join Us" ,
        fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp).align(Alignment.Start)

        )


        CustomTextField(
            value = "firstName",
            onValueChange = {firstName = it},
            label = "First Name",
           // leadingIcon = Icons.Default.Person,
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)


        )









    }























}

