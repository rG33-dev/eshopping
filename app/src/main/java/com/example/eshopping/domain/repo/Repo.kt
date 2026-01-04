package com.example.eshopping.domain.repo

import coil3.Uri
import com.example.eshopping.common.ResultState
import com.example.eshopping.domain.models.BannerDataModels
import com.example.eshopping.domain.models.CartDataModel
import com.example.eshopping.domain.models.CategoryDataModel
import com.example.eshopping.domain.models.ProductDataModel
import com.example.eshopping.domain.models.UserData
import com.example.eshopping.domain.models.UserDataParent
import kotlinx.coroutines.flow.Flow

interface Repo {
    fun registerUserWithMailAndPassword(userData: UserData): Flow<ResultState<Any>>
    fun loginUserWithMailAndPassword(userData: UserData): Flow<ResultState<Any>>
    fun getUsrById(uid:Any): Flow<ResultState<Any>>
    fun updateUserData(userData: UserDataParent): Flow<ResultState<Any>>
    fun userProfileImage(uri: Uri):Flow<ResultState<Any>>
    fun getCategoriesInLimited(): Flow<ResultState<List<CategoryDataModel>>>


    fun getProductsInLimited(): Flow<ResultState<List<ProductDataModel>>>

    fun getAllProducts(): Flow<ResultState<List<ProductDataModel>>>

    fun getProductById(productId: String): Flow<ResultState<ProductDataModel>>

    fun addToCart(cartDataModels: CartDataModel): Flow<ResultState<String>>

    fun addToFav(productDataModels: ProductDataModel): Flow<ResultState<String>>

    fun getAllFav(): Flow<ResultState<List<ProductDataModel>>>

    fun getCart(): Flow<ResultState<List<CartDataModel>>>

    fun getAllCategories(): Flow<ResultState<List<CategoryDataModel>>>

    fun getCheckout(productId: String): Flow<ResultState<ProductDataModel>>

    fun getBanner(): Flow<ResultState<List<BannerDataModels>>>

    fun getSpecificCategoryItems(categoryName: String): Flow<ResultState<List<ProductDataModel>>>

    fun getAllSuggestedProducts(): Flow<ResultState<List<ProductDataModel>>>
}