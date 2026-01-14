package com.example.eshopping.data.repo

import coil3.Uri
import com.example.eshopping.common.ADD_TO_CART
import com.example.eshopping.common.ADD_TO_FAV
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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

/*
"!!" is used to
check this file for any error in image loading due to fireStore Storage
 */


class RepoImpl  @Inject constructor(
    var firebaseAuth: FirebaseAuth,var firebaseFirestore: FirebaseFirestore
) : Repo


{
    override fun registerUserWithMailAndPassword(userData: com.google.firebase.firestore.core.UserData): Flow<ResultState<String>>  =
        callbackFlow{
            trySend(ResultState.Loading)
            firebaseAuth.createUserWithEmailAndPassword(userData.mail,userData.password).addOnCompleteListener {
                if (it.isSuccessful) {

                    firebaseFirestore.collection(USER_COLLECTION)
                        .document(it.result.user?.uid.toString()).set(userData)
                        .addOnCompleteListener {
                            if(it.isSuccessful){



                            trySend(ResultState.Success("User registered successfully"))
                        } else {
                        if (it.exception != null) {
                            trySend(ResultState.Error("User not registered"))
                        }
                    }
                }
                trySend(ResultState.Success("Success in Registered"))
            } else {
                if (it.exception != null) {
                    trySend(ResultState.Error("User not registered"))
                }
            }
            }
            awaitClose {
                close()
            }


        }



    override fun loginUserWithMailAndPassword(userData: com.google.firebase.firestore.core.UserData): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseAuth.signInWithEmailAndPassword(userData.mail, userData.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(ResultState.Success("User logged in successfully"))
                } else {
                    if (it.exception != null) {
                        trySend(ResultState.Error("User not logged in"))
                    }
                }
            }
        awaitClose {
            close()
        }
    }



    override fun getUsrById(uid: Any): Flow<ResultState<UserDataParent>> = callbackFlow{
        trySend(ResultState.Loading)
        firebaseFirestore.collection(USER_COLLECTION).document(uid.toString()).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val data = it.result.toObject(UserData::class.java)!!

                    val userDataParent = UserDataParent(it.result.id, userData = data)
                    trySend(ResultState.Success(userDataParent))




                }else{
                    if(it.exception != null)
                    {
                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                    }
                }

                }

                awaitClose {
                    close()
                }
            }



    override fun updateUserData(userData: UserDataParent): Flow<ResultState<String>> = callbackFlow{

        trySend(ResultState.Loading)
        firebaseFirestore.collection(USER_COLLECTION).document(userData.nodeId).update(userData.userData.toMap())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(ResultState.Success("User updated successfully"))
                } else {
                    if (it.exception != null) {
                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                    }
                }
            }
            awaitClose {

                close()




        }


    }



    override fun userProfileImage(uri: Uri): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        /*
       FirebaseStorage.getInstance().reference().child("userProfileImage/${System.currentTimeMillis()}+${fireBaseAuth.currentUser?.uid}")
            .putFile(uri?: Uri.EMPTY).addOnCompleteListener {

            it.result.storage.downloadUrl.addOnCompleteListener { imageUri ->
                if (imageUri.isSuccessful) {
                    trySend(ResultState.Success(imageUri.result.toString()))
                    }
                    if (imageUri.exception != null) {
                        trySend(ResultState.Error(imageUri.exception?.localizedMessage.toString()))
                    }
                }
            }
            awaitClose {
                close()
                 }
                 */
    }




    override fun getCategoriesInLimit(): Flow<ResultState<List<CategoryDataModel>>>  =  callbackFlow{
        trySend(ResultState.Loading)
        firebaseFirestore.collection("categories").limit(7).get().addOnSuccessListener { querySnapshot ->
            val categories = querySnapshot.documents.mapNotNull {  document ->
                document.toObject(CategoryDataModel::class.java)
            }
            trySend(ResultState.Success(categories))
        }.addOnFailureListener { exception ->
            trySend(ResultState.Error(exception.toString()))
        }
        awaitClose {
            close()

            }



    }



    override fun getProductsInLimited(): Flow<ResultState<List<ProductDataModel>>> = callbackFlow {
     trySend(ResultState.Loading)
        firebaseFirestore.collection("products").limit(10).get().addOnSuccessListener {
            val products = it.documents.mapNotNull { document ->
                document.toObject(ProductDataModel::class.java)?.apply {
                    productId = document.id
                }
            }
            trySend(ResultState.Success(products))

                }.addOnFailureListener{
                    trySend(ResultState.Error(it.toString()))
                }
        awaitClose {
            close()
        }
            }



    override fun getAllProducts(): Flow<ResultState<List<ProductDataModel>>> = callbackFlow{
        trySend(ResultState.Loading)
        firebaseFirestore.collection("products").get().addOnSuccessListener {
            val products = it.documents.mapNotNull { document ->

                document.toObject(ProductDataModel::class.java)?.apply {
                    productId = document.id
                }
            }
            trySend(ResultState.Success(products))

        }.addOnFailureListener{
            trySend(ResultState.Error(it.toString()))
                }
        awaitClose {
            close()
        }


    }



    override fun getProductById(productId: String): Flow<ResultState<ProductDataModel>> = callbackFlow{
        trySend(ResultState.Loading)
        firebaseFirestore.collection(USER_COLLECTION).document(productId).get().addOnSuccessListener {
            val product = it.toObject(ProductDataModel::class.java)
            trySend(ResultState.Success(product!!))

        }.addOnFailureListener{
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }
    }



    override fun addToCart(cartDataModels: CartDataModel): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        firebaseFirestore.collection(ADD_TO_CART).document(firebaseAuth.currentUser!!.uid).collection("UserCart").add(cartDataModels).addOnSuccessListener {
            trySend(ResultState.Success("Added to cart"))

        }.addOnFailureListener{
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }

    }



    override fun addToFav(productDataModels: ProductDataModel): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection("fav").document(firebaseAuth.currentUser!!.uid).collection("UserFav").add(productDataModels).addOnSuccessListener {
            trySend(ResultState.Success("Added to fav"))

        }.addOnFailureListener{
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }

    }



    override fun getAllFav(): Flow<ResultState<List<ProductDataModel>>> = callbackFlow{
        trySend(ResultState.Loading)
        firebaseFirestore.collection("fav").document(firebaseAuth.currentUser!!.uid).collection("UserFav").get().addOnSuccessListener {
            val products = it.documents.mapNotNull { document ->
                document.toObject(ProductDataModel::class.java)
            }
            trySend(ResultState.Success(products))

        }.addOnFailureListener{
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }



    }



    override fun getCart(): Flow<ResultState<List<CartDataModel>>> = callbackFlow{
        trySend(ResultState.Loading)
        firebaseFirestore.collection(ADD_TO_CART).document(firebaseAuth.currentUser!!.uid).collection("UserCart").get().addOnSuccessListener {
            val cart = it.documents.mapNotNull { document ->
                document.toObject(CartDataModel::class.java)?.apply {
                    cartId = document.id
                }
            }
            trySend(ResultState.Success(cart))

        }
        awaitClose {
            close()
        }

    }



    override fun getAllCategories(): Flow<ResultState<List<CategoryDataModel>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection("categories").get().addOnSuccessListener {
            val categories = it.documents.mapNotNull { document ->
                document.toObject(CategoryDataModel::class.java)
            }
            trySend(ResultState.Success(categories))
        }.addOnFailureListener{
            trySend(ResultState.Error(it.toString()))
        }

        awaitClose {
            close()
        }



    }



    override fun getCheckout(productId: String): Flow<ResultState<ProductDataModel>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection("Products").document(productId).get().addOnSuccessListener {
            val product = it.toObject(ProductDataModel::class.java)
            trySend(ResultState.Success(product!!))

        }.addOnFailureListener{
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }


    }



    override fun getBanner(): Flow<ResultState<List<BannerDataModels>>> =  callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection("banners").get().addOnSuccessListener {
            val banners = it.documents.mapNotNull { document ->
                document.toObject(BannerDataModels::class.java)
            }
            trySend(ResultState.Success(banners))
        }.addOnFailureListener{
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }

    }



    override fun getSpecificCategoryItems(categoryName: String): Flow<ResultState<List<ProductDataModel>>> = callbackFlow{
        trySend(ResultState.Loading)
        firebaseFirestore.collection("products").whereEqualTo("category",categoryName).get().addOnSuccessListener {
            val products = it.documents.mapNotNull { document ->
                document.toObject(ProductDataModel::class.java)?.apply {
                    productId = document.id
                }
            }
            trySend(ResultState.Success(products))

        }.addOnFailureListener{
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }

    }



    override fun getAllSuggestedProducts(): Flow<ResultState<List<ProductDataModel>>> = callbackFlow{
        trySend(ResultState.Loading)
        firebaseFirestore.collection(ADD_TO_FAV).document(firebaseAuth.currentUser!!.uid).collection("User_Fav").get().addOnSuccessListener {
            val products = it.documents.mapNotNull { document ->
                document.toObject(ProductDataModel::class.java)
            }
            trySend(ResultState.Success(products))

        }.addOnFailureListener{
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }



    }


}