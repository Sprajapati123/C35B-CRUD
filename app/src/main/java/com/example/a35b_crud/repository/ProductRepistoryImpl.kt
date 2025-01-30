package com.example.a35b_crud.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.a35b_crud.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.InputStream
import java.util.concurrent.Executors

class ProductRepistoryImpl : ProductRepository {

    val database: FirebaseDatabase =
        FirebaseDatabase.getInstance()

    val reference: DatabaseReference = database.reference.child("products")

    override fun addProduct(productModel: ProductModel, callback: (Boolean, String) -> Unit) {
        var id = reference.push().key.toString()
        productModel.productId = id
        reference.child(id).setValue(productModel)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Product Added succesfully")
                } else {
                    callback(false, "${it.exception?.message}")
                }
            }
    }


    override fun updateProduct(
        productId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(productId).updateChildren(data)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Product Updated succesfully")
                } else {
                    callback(false, "${it.exception?.message}")
                }
            }
    }

    override fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit) {
        reference.child(productId).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Product deleted succesfully")
                } else {
                    callback(false, "${it.exception?.message}")
                }
            }
    }

    override fun getProductById(
        productId: String,
        callback: (ProductModel?, Boolean, String) -> Unit
    ) {
        reference.child(productId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var model = snapshot.getValue(ProductModel::class.java)
                        callback(model, true, "Data fetched")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null, false, error.message.toString())
                }

            })
    }

    override fun getAllProduct(callback: (List<ProductModel>?, Boolean, String) -> Unit) {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var products = mutableListOf<ProductModel>()
                if (snapshot.exists()) {
                    for (eachProduct in snapshot.children) {
                        var model = eachProduct.getValue(ProductModel::class.java)
                        if (model != null) {
                            products.add(model)
                        }
                    }
                    callback(products, true, "fetched")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message.toString())
            }
        })
    }

    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dvztiygb9",
            "api_key" to "755714538743444",
            "api_secret" to "GjdgSzpbKnK-DWASwytXK_8yZu8"
        )
    )

    override fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
                var fileName = getFileNameFromUri(context, imageUri)

                fileName = fileName?.substringBeforeLast(".") ?: "uploaded_image"

                val response = cloudinary.uploader().upload(
                    inputStream, ObjectUtils.asMap(
                        "public_id", fileName,
                        "resource_type", "image"
                    )
                )

                var imageUrl = response["url"] as String?

                imageUrl = imageUrl?.replace("http://", "https://")

                Handler(Looper.getMainLooper()).post {
                    callback(imageUrl)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    callback(null)
                }
            }
        }
    }

    override fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var fileName: String? = null
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }


}