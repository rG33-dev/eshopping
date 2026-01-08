package com.example.eshopping.data.repo

import coil3.Uri
import coil3.util.CoilUtils.result
import com.example.eshopping.common.ADD_TO_CART
import com.example.eshopping.common.ADD_TO_FAV
import com.example.eshopping.common.PRODUCT_COLLECTION
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
import kotlinx.coroutines.channels.produce
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

    override fun getProductsInLimited(): Flow<ResultState<List<ProductDataModel>>> =  callbackFlow {  // documents error
        trySend(ResultState.Loading)
        firebaseFirestore.collection("Products").limit(10).get().addOnCompleteListener {
       val products = it.result.mapNotNull {
              it.toObject(ProductDataModel::class.java)


        }
     }

   }


    override fun getAllProducts(): Flow<ResultState<List<ProductDataModel>>> {
        TODO("Not yet implemented")
    }

    override fun getProductById(productId: String): Flow<ResultState<ProductDataModel>>  =  callbackFlow{
    trySend(ResultState.Loading)
        firebaseFirestore.collection(PRODUCT_COLLECTION).document(productId).get().addOnCompleteListener {
            val product = it.result.toObject(ProductDataModel::class.java)
            trySend(ResultState.Success(product!!))}.addOnCompleteListener {  //!! used to avoid null pointer exception, if no product on backend app will crash
                trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }


    }

    override fun addToCart(cartDataModels: CartDataModel): Flow<ResultState<String>> = callbackFlow{
       trySend(ResultState.Loading)
        firebaseFirestore.collection(ADD_TO_CART).document(firebaseAuth.currentUser!!.uid).collection("user_Cart").add(cartDataModels).addOnCompleteListener {
            if (it.isSuccessful) {
                trySend(ResultState.Success("Added to cart"))


            }else {
                if (it.exception != null) {
                    trySend(ResultState.Error(it.exception!!.localizedMessage ?: "Error"))

            }

        }

        }
        awaitClose {
            close()
        }



    }

    override fun addToFav(productDataModels: ProductDataModel): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection(ADD_TO_FAV).document(firebaseAuth.currentUser!!.uid).collection("User_Fav").add(productDataModels).addOnSuccessListener {
            trySend(ResultState.Success("Added to Fav"))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.localizedMessage?:"Error"))
        }

            awaitClose {
            close()
        }
    }

    override fun getAllFav(): Flow<ResultState<List<ProductDataModel>>> = callbackFlow{
        trySend(ResultState.Loading)
        firebaseFirestore.collection(ADD_TO_FAV).document(firebaseAuth.currentUser!!.uid).collection("User_Fav").get().addOnCompleteListener {
           val fav =  it.result.mapNotNull {result ->
              result.toObject(ProductDataModel::class.java)

          }
            trySend(ResultState.Success(fav))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.localizedMessage?:"Error"))
        }
        awaitClose {
            close()
        }


    }

    override fun getCart(): Flow<ResultState<List<CartDataModel>>> = callbackFlow { // need to check for documents error
        trySend(ResultState.Loading)
        firebaseFirestore.collection(ADD_TO_CART).document(firebaseAuth.currentUser!!.uid).collection("user_Cart").get().addOnCompleteListener {
            val cart = it.result.toObjects(CartDataModel::class.java)
            trySend(ResultState.Success(cart))
            }.addOnFailureListener {
            trySend(ResultState.Error(it.localizedMessage?:"Error"))


        }
        awaitClose {
            close()
        }


    }


    override fun getCheckout(productId: String): Flow<ResultState<ProductDataModel>>  =  callbackFlow{
        trySend(ResultState.Loading)
        firebaseFirestore.collection("Products").document(productId).get().addOnCompleteListener {
            val product = it.result.toObject(ProductDataModel::class.java)
            trySend(ResultState.Success(product!!))
        }.addOnFailureListener{
            trySend(ResultState.Error(it.localizedMessage?:"Error"))
        }
        awaitClose {
            close()
        }
    }

    override fun getBanner(): Flow<ResultState<List<BannerDataModels>>>  =  callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection("banner").get().addOnCompleteListener {
            val banner = it.result.toObjects(BannerDataModels::class.java)
            trySend(ResultState.Success(banner))
        }.addOnFailureListener{
            trySend(ResultState.Error(it.localizedMessage?:"Error"))
        }
        awaitClose {
            close()
        }

    }

    override fun getSpecificCategoryItems(categoryName: String): Flow<ResultState<List<ProductDataModel>>> = callbackFlow { // check for documents error
        trySend(ResultState.Loading)

        firebaseFirestore.collection("Products").whereEqualTo("category", categoryName).get().addOnCompleteListener {
            val products = it.result.mapNotNull  { result ->
                result.toObject(
                (ProductDataModel::class.java))?.apply { productId = result.id }
            }
            trySend(ResultState.Success(products))

        }.addOnFailureListener {
            trySend(ResultState.Error(it.localizedMessage?:"Error"))
        }
        awaitClose {
            close() }
    }

    override fun getAllSuggestedProducts(): Flow<ResultState<List<ProductDataModel>>>  =  callbackFlow{
         trySend(ResultState.Loading)
        firebaseFirestore.collection("Products").limit(10).get().addOnCompleteListener {
            val fav = it.result.toObjects(ProductDataModel::class.java)
            trySend(ResultState.Success(fav))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.localizedMessage?:"Error"))
        }
        awaitClose {
            close()
        }
    }
    override fun getAllCategories(): Flow<ResultState<List<CategoryDataModel>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection("categories").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val categories = task.result.toObjects(CategoryDataModel::class.java)
                trySend(ResultState.Success(categories))
            } else {
                trySend(ResultState.Error(task.exception?.localizedMessage ?: "Unknown Error"))
            }
        }
        awaitClose { close() }
    }
}


