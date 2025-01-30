package com.example.a35b_crud.ui.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a35b_crud.R
import com.example.a35b_crud.databinding.ActivityAddProductBinding
import com.example.a35b_crud.model.ProductModel
import com.example.a35b_crud.repository.ProductRepistoryImpl
import com.example.a35b_crud.utils.ImageUtils
import com.example.a35b_crud.utils.LoadingUtils
import com.example.a35b_crud.viewmodel.ProductViewModel
import com.squareup.picasso.Picasso

class AddProductActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddProductBinding

    lateinit var productViewModel: ProductViewModel

    lateinit var loadingUtils: LoadingUtils

    lateinit var imageUtils: ImageUtils

    var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUtils = ImageUtils(this)

        loadingUtils = LoadingUtils(this)

        var repo = ProductRepistoryImpl()
        productViewModel = ProductViewModel(repo)

        imageUtils.registerActivity { url ->
            url.let { it ->
                imageUri = it
                Picasso.get().load(it).into(binding.imageBrowse)
            }
        }
        binding.browseImage.setOnClickListener {
            imageUtils.launchGallery(this)
        }
        binding.btnAddProduct.setOnClickListener {
            uploadImage()

        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun uploadImage() {
        imageUri?.let { uri ->
            productViewModel.uploadImage(this, uri) { imageUrl ->
                Log.d("checpoirs", imageUrl.toString())
                if (imageUrl != null) {
                    addProduct(imageUrl)
                } else {
                    Log.e("Upload Error", "Failed to upload image to Cloudinary")
                }
            }
        }
    }

    private fun addProduct(url: String) {
        var productName = binding.editProductName.text.toString()
        var productPrice = binding.editProductprice.text.toString().toInt()
        var productDesc = binding.editProductDesc.text.toString()

        var model = ProductModel(
            "",
            productName,
            productDesc, productPrice, url
        )

        productViewModel.addProduct(model) { success, message ->
            if (success) {
                Toast.makeText(
                    this@AddProductActivity,
                    message, Toast.LENGTH_LONG
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this@AddProductActivity,
                    message, Toast.LENGTH_LONG
                ).show()

            }
        }
    }
}