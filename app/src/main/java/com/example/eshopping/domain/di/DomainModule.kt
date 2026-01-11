package com.example.eshopping.domain.di

import com.example.eshopping.data.repo.RepoImpl
import com.example.eshopping.domain.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
   @Provides
  fun provideRepo(firebaseAuth: FirebaseAuth, firebaseFirestore: FirebaseFirestore): Repo {

       return RepoImpl(firebaseFirestore, firebaseAuth)

  }




}
/*

 */