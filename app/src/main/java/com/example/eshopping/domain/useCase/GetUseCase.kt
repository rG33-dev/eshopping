package com.example.eshopping.domain.useCase

import com.example.eshopping.common.ResultState
import com.example.eshopping.domain.models.CartDataModel
import com.example.eshopping.domain.models.ProductDataModel
import com.example.eshopping.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartUseCase@Inject constructor(private val repo : Repo) {

    fun getCart(): Flow<ResultState<List<CartDataModel>>>
    {
        return repo.getCart()
    }



}