package com.example.eshopping.data.repo

import com.example.eshopping.common.ResultState
import com.example.eshopping.common.USER_COLLECTION
import com.example.eshopping.domain.models.CartDataModel
import com.example.eshopping.domain.models.UserData
import com.example.eshopping.domain.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RepoImpl @Inject constructor(
    var firebaseAuth: FirebaseAuth,var firebaseFirestore: FirebaseFirestore
) : Repo
{
    override fun registerUserWithMailAndPassword(
        userData: UserData
    ): Flow<ResultState<String>> = callbackFlow {

        trySend(ResultState.Loading)

        firebaseAuth
            .createUserWithEmailAndPassword(userData.mail, userData.password)
            .addOnCompleteListener { authTask ->

                if (authTask.isSuccessful) {

                    val uid = authTask.result.user?.uid ?: ""

                    firebaseFirestore
                        .collection(USER_COLLECTION)
                        .document(uid)
                        .set(userData)
                        .addOnCompleteListener { fireStoreTask ->

                            if (fireStoreTask.isSuccessful) {
                                trySend(
                                    ResultState.Success(
                                        "User registered successfully"
                                    )
                                )
                            } else {
                                trySend(
                                    ResultState.Error(
                                        fireStoreTask.exception?.localizedMessage
                                            ?: "Firestore error"
                                    )
                                )
                            }
                        }

                } else {
                    trySend(
                        ResultState.Error(
                            authTask.exception?.localizedMessage
                                ?: "Authentication error"
                        )
                    )
                }
            }

        awaitClose {

            close()
        }
    }
}


