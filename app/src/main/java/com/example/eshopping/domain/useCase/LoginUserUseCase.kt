package com.example.eshopping.domain.useCase

import com.example.eshopping.common.ResultState
import com.example.eshopping.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUserUseCase@Inject constructor(private val repo : Repo) {

    fun loginUser(userData: com.google.firebase.firestore.core.UserData): Flow<ResultState<Any>>
    {
        return repo.loginUserWithMailAndPassword(userData)

    }



}