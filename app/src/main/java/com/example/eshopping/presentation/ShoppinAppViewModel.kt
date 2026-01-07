package com.example.eshopping.presentation

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eshopping.common.HomeScreenState
import com.example.eshopping.domain.models.CartDataModel
import com.example.eshopping.domain.models.CategoryDataModel
import com.example.eshopping.domain.models.ProductDataModel
import com.example.eshopping.domain.models.UserDataParent
import com.example.eshopping.domain.useCase.AddToCartuseCase
import com.example.eshopping.domain.useCase.AddToFavuseCase
import com.example.eshopping.domain.useCase.CreateUseruseCase
import com.example.eshopping.domain.useCase.GetAllFavUseCase
import com.example.eshopping.domain.useCase.GetAllProductUseCase
import com.example.eshopping.domain.useCase.GetBannerUseCase
import com.example.eshopping.domain.useCase.GetCartUseCase
import com.example.eshopping.domain.useCase.GetUserUseCase
import com.example.eshopping.domain.useCase.LoginUserUseCase
import com.example.eshopping.domain.useCase.UpdateUserDataUseCase
import com.example.eshopping.domain.useCase.UserProfileImageUseCase
import com.example.eshopping.domain.useCase.getAllSuggestedProductUseCase
import com.example.eshopping.domain.useCase.getCategoryInLimit
import com.example.eshopping.domain.useCase.getProductById
import com.example.eshopping.domain.useCase.getProductsInLimitUseCase
import com.example.eshopping.domain.useCase.getSpecificCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingAppViewModel @Inject constructor(
    private val createUserUseCase: CreateUseruseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val getUsrByIdUseCase: GetUserUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val userProfileImageUseCase: UserProfileImageUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartuseCase,
    private val addToFavUseCase: AddToFavuseCase,
    private val getAllFavUseCase: GetAllFavUseCase,
    private val getCategoriesInLimitedUseCase: getCategoryInLimit,
    private val getProductsInLimitedUseCase: getProductsInLimitUseCase,
    private val getAllProductsUseCase: GetAllProductUseCase,
    private val getProductByIdUseCase: getProductById,
    private val getCheckoutUseCase: GetCheckoutState,
    private val getBannerUseCase: GetBannerUseCase,
    private val getSpecificCategoryItemsUseCase: getSpecificCategoryUseCase,
    private val getAllSuggestedProductsUseCase: getAllSuggestedProductUseCase
): ViewModel() {

    private val _signUpScreenState = MutableStateFlow(SignUpScreenState())
    val signUpScreenState = _signUpScreenState.asStateFlow()

    private val _loginScreenState = MutableStateFlow(LoginScreenState())
    val loginScreenState = _loginScreenState.asStateFlow()
    private val _profileScreenState = MutableStateFlow(ProfileScreenState())
    val profileScreenState = _profileScreenState.asStateFlow()

    private val _updateScreenState = MutableStateFlow(UpDateScreenState())
    val updateScreenState = _updateScreenState.asStateFlow()

    private val _uploadUserImageScreenState = MutableStateFlow(UploadUserImageScreenState())
    val uploadUserImageScreenState = _uploadUserImageScreenState.asStateFlow()


    private val _addToCartState = MutableStateFlow(AddToCartState())
    val addToCartState = _addToCartState.asStateFlow()

    private val _getProductByIdState = MutableStateFlow(GetProductByIdState())
    val getProductByIdState = _getProductByIdState.asStateFlow()


    private val _addToFavState = MutableStateFlow(AddToFavState())
    val addToFavState = _addToFavState.asStateFlow()

    private val _getAllFavState = MutableStateFlow(GetAllFavState())
    val getAllFavState = _getAllFavState.asStateFlow()


    private val _getAllProductsState = MutableStateFlow(GetAllProductsState())
    val getAllProductsState = _getAllProductsState.asStateFlow()


    private val _getCartState = MutableStateFlow(GetCartState())
    val getCartState = _getCartState.asStateFlow()

    private val _getAllCategoriesState = MutableStateFlow(GetAllCategoriesState())
    val getAllCategoriesState = _getAllCategoriesState.asStateFlow()

    private val _getCheckoutState = MutableStateFlow(GetCheckoutState())
    val getCheckoutState = _getCheckoutState.asStateFlow()

    private val _getBannerState = MutableStateFlow(GetBannerState())
    val getBannerState = _getBannerState.asStateFlow()


    private val _getSpecificCategoryItemsState = MutableStateFlow(GetSpecificCategoryItemsState())
    val getSpecificCategoryItemsState = _getSpecificCategoryItemsState.asStateFlow()


    private val _getAllSuggestedProductsState = MutableStateFlow(GetAllSuggestedProductsState())
    val getAllSuggestedProductsState = _getAllSuggestedProductsState.asStateFlow()

    private val homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreen = homeScreenState.asStateFlow()


    fun getSpecificCategoryItems(categoryName: String) {

        viewModelScope.launch {
            getSpecificCategoryItemsUseCase.getSpecificCategory(categoryName).collect {
                when (it) {
                    is com.example.eshopping.common.ResultState.Success -> {
                        _getSpecificCategoryItemsState.value =
                            GetSpecificCategoryItemsState(userData = it.data)

                    }

                    else -> {
                        _getSpecificCategoryItemsState.value =
                            GetSpecificCategoryItemsState(errorMessage = it.toString())
                    }
                }
            }
        }
    }
}
















data class ProfileScreenState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val  userData : UserDataParent? = null
)

data class SignUpScreenState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val  userData : UserDataParent? = null
)

data class LoginScreenState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val userData : UserDataParent? = null

 )

data class UpDateScreenState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val  userData : String? = null
)

data class UploadUserImageScreenState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val  userData : String? = null)

data class AddToCartState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val  userData : String? = null
)

data class GetProductByIdState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val  userData : ProductDataModel? = null
)
data class AddToFavState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val  userData : String? = null
)
data class GetAllFavState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val  userData : List<ProductDataModel>? = null

)

data class GetAllProductsState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val  userData : List<ProductDataModel>? = emptyList()

)
data class GetCartState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val  userData : List<CartDataModel>? = emptyList()


)

data class GetAllCategoriesState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val  userData : List<CategoryDataModel>? = emptyList()


)
data class GetCheckoutState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val  userData : ProductDataModel? = null


)
data class GetBannerState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val  userData : List<ProductDataModel>? = emptyList()


)
data class GetSpecificCategoryItemsState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val  userData : List<ProductDataModel>? = emptyList()


)
data class GetAllSuggestedProductsState(
    val isLoading : Boolean = false,
    val errorMessage :  String? =  null,
    val  userData : List<ProductDataModel>? = emptyList()


)



