package com.example.a35b_crud.repository

import android.content.Context
import android.net.Uri
import com.example.a35b_crud.model.ProductModel

interface ProductRepository {

//    {
//        "success":true
//        "message": "Product added successfully"
//    }

    fun addProduct(productModel: ProductModel,
                   callback:(Boolean,String) -> Unit
                   )

    fun updateProduct(productId:String,
                      data: MutableMap<String,Any>,
                      callback: (Boolean, String) -> Unit)

    fun deleteProduct(productId:String,
                      callback: (Boolean, String) -> Unit)

    fun getProductById(productId:String,
                       callback: (ProductModel?, Boolean,
                                  String) -> Unit)

    fun getAllProduct(callback:
                          (List<ProductModel>?,Boolean,
                           String) -> Unit)

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit)

    fun getFileNameFromUri(context: Context, uri: Uri): String?

}