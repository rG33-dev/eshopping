package com.example.eshopping.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshopping.common.HomeScreenState
import com.example.eshopping.common.ResultState
import com.example.eshopping.domain.models.CartDataModel
import com.example.eshopping.domain.models.CategoryDataModel
import com.example.eshopping.domain.models.ProductDataModel
import com.example.eshopping.domain.models.UserDataParent
import com.example.eshopping.domain.useCase.AddToCartUseCase
import com.example.eshopping.domain.useCase.AddToFavUseCase
import com.example.eshopping.domain.useCase.CreateUserUseCase
import com.example.eshopping.domain.useCase.GetAllCategoryUseCase
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
import com.example.eshopping.domain.useCase.getCheckOutUseCase
import com.example.eshopping.domain.useCase.getProductById
import com.example.eshopping.domain.useCase.getProductsInLimitUseCase
import com.example.eshopping.domain.useCase.getSpecificCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingAppViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val getUsrByIdUseCase: GetUserUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val userProfileImageUseCase: UserProfileImageUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val addToFavUseCase: AddToFavUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val getCheckoutUseCase: getCheckOutUseCase, // Corrected Type
    private val getAllFavUseCase: GetAllFavUseCase,
    private val getCategoriesInLimitedUseCase: getCategoryInLimit,
    private val getProductsInLimitedUseCase: getProductsInLimitUseCase,
    private val getAllProductsUseCase: GetAllProductUseCase,
    private val getProductByIdUseCase: getProductById,
    private val getBannerUseCase: GetBannerUseCase,
    private val getSpecificCategoryItemsUseCase: getSpecificCategoryUseCase,
    private val getAllSuggestedProductsUseCase: getAllSuggestedProductUseCase,
    private val getAllCategoryUseCase: GetAllCategoryUseCase
) : ViewModel() {

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

    private val _getSpecificCategory = MutableStateFlow(GetSpecificCategoryItemsState())
    val getSpecificCategory = _getSpecificCategory.asStateFlow()


    private val _getAllSuggestedProductsState = MutableStateFlow(GetAllSuggestedProductsState())
    val getAllSuggestedProductsState = _getAllSuggestedProductsState.asStateFlow()

    private val homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreen = homeScreenState.asStateFlow()


    fun getSpecificCategoryItems(categoryName: String) {

        viewModelScope.launch {
            getSpecificCategoryItemsUseCase.getSpecificCategory(categoryName).collect {


                when (it) {
                    is ResultState.Success -> {
                        _getSpecificCategoryItemsState.value =
                            getSpecificCategoryItemsState.value.copy(
                                userData = it.data,
                                isLoading = false,
                                errorMessage = null
                            )

                    }

                    is ResultState.Error -> {
                        _getSpecificCategoryItemsState.value =
                            _getSpecificCategoryItemsState.value.copy(
                                isLoading = false,
                                errorMessage = it.message
                            )

                    }

                    is ResultState.Loading -> {
                        _getSpecificCategoryItemsState.value =
                            _getSpecificCategoryItemsState.value.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                    }
                }
            }
        }
    }


    fun getAllSuggestedProducts(suggestedProduct: String) {

        viewModelScope.launch {
            getAllSuggestedProductsUseCase.getAllSuggested().collect {
                when (it) {
                    is ResultState.Success -> {
                        _getAllSuggestedProductsState.value =
                            _getAllSuggestedProductsState.value.copy(
                                userData = it.data,
                                isLoading = false,
                                errorMessage = null
                            )

                    }

                    is ResultState.Error -> {
                        _getAllSuggestedProductsState.value =
                            _getAllSuggestedProductsState.value.copy(
                                isLoading = false,
                                errorMessage = it.message
                            )
                    }


                    else -> {}
                }

            }

        }
    }


    fun getCheckout(productId: String) {
        viewModelScope.launch {
            getCheckoutUseCase.getCheckOutUseCase(productId).collect {
                when (it) {
                    is ResultState.Success -> {
                        _getCheckoutState.value = _getCheckoutState.value.copy(
                            userData = it.data,
                            isLoading = false,
                            errorMessage = null
                        )
                    }

                    is ResultState.Error -> {
                        _getCheckoutState.value = _getCheckoutState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getCheckoutState.value = _getCheckoutState.value.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    fun getAllCategories() {
        viewModelScope.launch {
            getAllCategoryUseCase.getAllCategoriesUseCase().collect {
                when (it) {
                    is ResultState.Error<*> -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Success<*> -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            userData = it.data as List<CategoryDataModel>,
                            isLoading = false,
                            errorMessage = null
                        )

                    }

                    is ResultState.Loading -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = true
                        )

                    }
                }
            }
        }
    }


    fun getCart() {
        viewModelScope.launch {
            getCartUseCase.getCart().collect {
                when (it) {
                    is ResultState.Success -> {
                        _getCartState.value = _getCartState.value.copy(
                            userData = it.data,
                            isLoading = false,
                            errorMessage = null
                        )
                    }

                    is ResultState.Error -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = true
                        )

                    }


                }
            }

        }
    }


    fun getAllFav() {
        viewModelScope.launch {
            getAllFavUseCase.getALlFav().collect {
                when (it) {
                    is ResultState.Success -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            userData = it.data,
                            isLoading = false,
                            errorMessage = null
                        )
                    }

                    is ResultState.Error -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = true
                        )
                    }


                }
            }

        }


    }


    fun addToFav(productDataModel: ProductDataModel) {
        viewModelScope.launch {
            addToFavUseCase.addToFav(productDataModel).collect {
                when (it) {
                    is ResultState.Success -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            userData = it.data as String?,
                            isLoading = false,
                            errorMessage = null
                        )


                    }

                    is ResultState.Error -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )


                    }

                    is ResultState.Loading -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = true
                        )
                    }


                }

            }


        }
    }


    fun getProductById(productId: String) {
        viewModelScope.launch {
            getProductByIdUseCase.getProductById(productId).collect {
                when (it) {
                    is ResultState.Success -> {
                        _getProductByIdState.value = _getProductByIdState.value.copy(
                            userData = it.data,
                            isLoading = false,
                            errorMessage = null
                        )
                    }

                    is ResultState.Error -> {
                        _getProductByIdState.value = _getProductByIdState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _getProductByIdState.value = _getProductByIdState.value.copy(
                            isLoading = true
                        )
                    }



                }

            }
        }
    }


    fun addToCart(cartDataModel: CartDataModel) {
        viewModelScope.launch {
            addToCartUseCase.addToCart(cartDataModel).collect {
                when (it) {
                    is ResultState.Success -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            userData = it.data as String?,
                            isLoading = false,
                            errorMessage = null
                        )
                    }

                    is ResultState.Error -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = false,
                            errorMessage = it.message)



                    }
                    is ResultState.Loading -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = true,
                            errorMessage = null,


                        )

                    }

                }

            }
        }

        }




}



data class ProfileScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: UserDataParent? = null
)

data class SignUpScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: UserDataParent? = null
)

data class LoginScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: UserDataParent? = null

)

data class UpDateScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class UploadUserImageScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class AddToCartState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class GetProductByIdState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: ProductDataModel? = null
)

data class AddToFavState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class GetAllFavState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductDataModel>? = null

)

data class GetAllProductsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductDataModel>? = emptyList()

)

data class GetCartState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<CartDataModel>? = emptyList()


)

data class GetAllCategoriesState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<CategoryDataModel>? = emptyList()


)

data class GetCheckoutState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: ProductDataModel? = null


)

data class GetBannerState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductDataModel>? = emptyList()


)

data class GetSpecificCategoryItemsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductDataModel>? = emptyList()


)

data class GetAllSuggestedProductsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductDataModel>? = emptyList()


)

