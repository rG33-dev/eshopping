package com.example.eshopping.domain.models

import androidx.compose.runtime.mutableStateMapOf

data class UserData(
    val firstName: String? = null,
    val lastName: String? = null,
    val password: String? = null,
    val profileImage: String? = null,
    val mail: String? = null,
    val address: String? = null,
    val mobile: String? = null
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

// we created USERdataparent  data class to let storage straight up access (UserData)instead of adding values manually
