package com.example.eshopping.common

import com.example.eshopping.domain.models.BannerDataModels
import com.example.eshopping.domain.models.ProductDataModel

data class HomeScreenState(
    val isLoading : Boolean = true,
    val errorMessage : String ? = null,
    val categories :List<ProductDataModel>? = null,
    val products: List<ProductDataModel>?=null,
    val banners: List<BannerDataModels> ? = null

) {
}