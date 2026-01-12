package com.example.eshopping.domain.models

import androidx.compose.runtime.mutableStateMapOf

data class UserData(
    val firstName: String= "",
    val lastName: String = " ",
    val password: String = "",
    val profileImage: String = "",
    val mail: String = "",
    val address: String = "",
    val mobile: String =""
)
{
    fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()

        firstName?.let { map["firstName"] = it }
        lastName?.let { map["lastName"] = it }
        password?.let { map["password"] = it }
        profileImage?.let { map["profileImage"] = it }
        mail?.let { map["mail"] = it }
        address?.let { map["address"] = it }
        mobile?.let { map["mobile"] = it }

        return map
    }

}
data class UserDataParent(
    val nodeId: String = "",
    val userData: UserData = UserData()
)

// we created USER dataparent  data class to let storage straight up access (UserData)instead of adding values manually
