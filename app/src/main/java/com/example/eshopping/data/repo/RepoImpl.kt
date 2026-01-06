package com.example.eshopping.data.repo

import coil3.Uri
import com.example.eshopping.common.ResultState
import com.example.eshopping.common.USER_COLLECTION
import com.example.eshopping.domain.models.BannerDataModels
import com.example.eshopping.domain.models.CartDataModel
import com.example.eshopping.domain.models.CategoryDataModel
import com.example.eshopping.domain.models.ProductDataModel
import com.example.eshopping.domain.models.UserData
import com.example.eshopping.domain.models.UserDataParent
import com.example.eshopping.domain.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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
            .createUserWithEmailAndPassword(userData.mail.toString(), userData.password.toString())
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

    override fun loginUserWithMailAndPassword(userData: UserData): Flow<ResultState<Any>> = callbackFlow() {
        trySend(ResultState.Loading)
        firebaseAuth.signInWithEmailAndPassword(userData.mail.toString(),
            userData.password.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful){
                trySend(ResultState.Success("Login Successful"))
            }else{
                trySend(ResultState.Error(it.exception?.localizedMessage?:"Login Error"))
            }
        }
        awaitClose {
            close()
        }

    }

    override fun getUsrById(uid: Any): Flow<ResultState<Any>>  = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection(USER_COLLECTION).document(uid.toString()).get().addOnCompleteListener {
            if (it.isSuccessful){
                val data = it.result.toObject(UserDataParent::class.java) !!
                val userDataParent = UserDataParent(it.result.id, userData = data.userData)

                trySend(ResultState.Success(userDataParent))
            }else{
                if(it.exception != null){
                    trySend(ResultState.Error(it.exception!!.localizedMessage?:"Error"))

                }
            }
        }
        awaitClose {
            close()
        }
2

    }

    override fun updateUserData(UserDataParent: UserDataParent): Flow<ResultState<Any>> = callbackFlow{
        trySend(ResultState.Loading)
        firebaseFirestore.collection(USER_COLLECTION).document(UserDataParent.nodeId).update(
            UserDataParent.userData.toMap()).addOnCompleteListener {
            if (it.isSuccessful){
                trySend(ResultState.Success("Updated"))
            }else{
                if(it.exception != null){
                    trySend(ResultState.Error(it.exception!!.localizedMessage?:"Error"))

                }
            }
        }
        awaitClose {
            close()
        }


    }

    override fun userProfileImage(uri: Uri): Flow<ResultState<Any>> = callbackFlow {
//        trySend(FirebaseStorage.getInstance().reference.child("users/${uri.lastPathSegment}").putFile(uri).addOnCompleteListener {
//            if (it.isSuccessful){
//                trySend(ResultState.Success"))
//    }
//        )else{
//            if(it.exception != null){
//                trySend(ResultState.Error(it.exception!!.localizedMessage}
//        }
//        }
//        awaitClose {
//            close()
//        }
        /*
        Need Firebase storage access and then create an object in it to access the code
         */

    }


    override fun getCategoriesInLimited(): Flow<ResultState<List<CategoryDataModel>>>  = callbackFlow{
        trySend(ResultState.Loading)
        firebaseFirestore.collection("categories").limit(7).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val data = it.result.toObjects(CategoryDataModel::class.java)
                trySend(ResultState.Success(data))
            } else {
                if (it.exception != null) {
                    trySend(ResultState.Error(it.exception!!.localizedMessage ?: "Error"))
                }

            }
        }

    }

    override fun getProductsInLimited(): Flow<ResultState<List<ProductDataModel>>> =  callbackFlow{
        trySend(ResultState.Loading)
       firebaseFirestore.collection("Products").limit(10).get().addOnCompleteListener {
//           val products = it.documents.mapNotNull {
//               it.toObject(ProductDataModel::class.java)
//               productId = documents.id
//           }
       }


       }


    override fun getAllProducts(): Flow<ResultState<List<ProductDataModel>>> {
        TODO("Not yet implemented")
    }

    override fun getProductById(productId: String): Flow<ResultState<ProductDataModel>> {
        TODO("Not yet implemented")
    }

    override fun addToCart(cartDataModels: CartDataModel): Flow<ResultState<String>> {
        TODO("Not yet implemented")
    }

    override fun addToFav(productDataModels: ProductDataModel): Flow<ResultState<String>> {
        TODO("Not yet implemented")
    }

    override fun getAllFav(): Flow<ResultState<List<ProductDataModel>>> {
        TODO("Not yet implemented")
    }

    override fun getCart(): Flow<ResultState<List<CartDataModel>>> {
        TODO("Not yet implemented")
    }

    override fun getAllCategories(): Flow<ResultState<List<CategoryDataModel>>> {
        TODO("Not yet implemented")
    }

    override fun getCheckout(productId: String): Flow<ResultState<ProductDataModel>> {
        TODO("Not yet implemented")
    }

    override fun getBanner(): Flow<ResultState<List<BannerDataModels>>> {
        TODO("Not yet implemented")
    }

    override fun getSpecificCategoryItems(categoryName: String): Flow<ResultState<List<ProductDataModel>>> {
        TODO("Not yet implemented")
    }

    override fun getAllSuggestedProducts(): Flow<ResultState<List<ProductDataModel>>> {
        TODO("Not yet implemented")
    }
}


