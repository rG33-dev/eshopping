package com.example.eshopping.domain.models

import androidx.compose.runtime.mutableStateMapOf

data class UserData(

val firstName:String = "",
val lastName :  String  = " ",
val password :  String = "",
val profileImage :  String = "",

val mail:  String  = "",
val mobile :  Int = 0,

val address :  String = ""

){
    fun toMap() : Map<Any, Any>{
        val map = mutableStateMapOf<Any, Any>()
        map["firstName"] = firstName
        map["lastName"] = lastName
        map["password"] = password
        map["profileImage"] = profileImage
        map["mail"] = mail
        map["address"] = address
        map["mobile"] = mobile



        return map
    }
}
data class UserDataParent(
    val nodeId :  String = "",
    val userData : UserData = UserData()
)
// we created USERdataparent  data class to let storage straight up access (UserData)instead of adding values manually
