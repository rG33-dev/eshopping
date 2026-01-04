package com.example.eshopping.domain.useCase

import coil3.Uri
import com.example.eshopping.common.ResultState
import com.example.eshopping.domain.models.UserDataParent
import com.example.eshopping.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserProfileImageUseCase@Inject constructor(private val repo : Repo) {

    fun updateUserData(uri: Uri): Flow<ResultState<Any>> {
        return repo.userProfileImage(uri)
    }


}