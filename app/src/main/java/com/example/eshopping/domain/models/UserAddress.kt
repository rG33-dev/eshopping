package com.example.eshopping.domain.models

data class UserAddress(
    val firstName:String = "",
    val lastName :  String  = " ",
    val pinCode : Int = 0 ,
    val city :  String = "",
    val country :  String  = "",
    val mobile :  Int = 0,
    val state  :  String = "",
    val address :  String = ""

)
