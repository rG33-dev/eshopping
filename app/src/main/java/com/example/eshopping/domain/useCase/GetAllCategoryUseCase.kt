package com.example.eshopping.domain.useCase

import com.example.eshopping.common.ResultState
import com.example.eshopping.domain.models.CartDataModel
import com.example.eshopping.domain.models.CategoryDataModel
import com.example.eshopping.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategoryUseCase @Inject constructor(private val repo: Repo) {

    fun getAllCategoriesUseCase(): Flow<ResultState<List<CategoryDataModel>>> {
        return repo.getAllCategories()
    }
}
