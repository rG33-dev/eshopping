package com.example.eshopping.domain.useCase

import com.example.eshopping.common.ResultState
import com.example.eshopping.domain.models.UserDataParent
import com.example.eshopping.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserDataUseCase @Inject constructor(private val repo : Repo) {

    fun updateUserData(userDataParent: UserDataParent): Flow<ResultState<String>>
    {
        return repo.updateUserData(userDataParent)
    }



}