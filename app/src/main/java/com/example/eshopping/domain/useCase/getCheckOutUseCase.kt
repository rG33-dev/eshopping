package com.example.eshopping.domain.useCase

import com.example.eshopping.common.ResultState
import com.example.eshopping.domain.models.ProductDataModel
import com.example.eshopping.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getCheckOutUseCase @Inject constructor(private val repo : Repo){

    fun getCheckOutUseCase(productId : String) : Flow<ResultState<ProductDataModel>> {

        return repo.getCheckout(productId)
}

}
