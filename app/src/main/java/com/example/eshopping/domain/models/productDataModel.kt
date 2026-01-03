package com.example.eshopping.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class ProductDataModel(
   var name:String = "",
   var productId:String = "",
   var finalPrice  : String = "",
   var price : String = "",
   var image : String = "",
   var date:Long = System.currentTimeMillis(),
   var createdBy  : String = "",
   var description : String = "",
    var availableUnits : Int = 0,


)
