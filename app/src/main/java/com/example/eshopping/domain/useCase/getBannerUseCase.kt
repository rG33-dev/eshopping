package com.example.eshopping.domain.useCase

import com.example.eshopping.common.ResultState
import com.example.eshopping.domain.models.BannerDataModels
import com.example.eshopping.domain.models.CartDataModel
import com.example.eshopping.domain.models.ProductDataModel
import com.example.eshopping.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBannerUseCase @Inject constructor(private val repo : Repo) {

    var banner: Flow<ResultState<List<BannerDataModels>>> = TODO("initialize me")

    fun getBannerUseCase(): Flow<ResultState<List<BannerDataModels>>>
    {
        return repo.getBanner()

    }



}