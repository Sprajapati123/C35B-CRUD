package com.example.a35b_crud.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a35b_crud.R
import com.example.a35b_crud.databinding.ActivityAddProductBinding
import com.example.a35b_crud.model.ProductModel
import com.example.a35b_crud.repository.ProductRepistoryImpl
import com.example.a35b_crud.utils.LoadingUtils
import com.example.a35b_crud.viewmodel.ProductViewModel

class AddProductActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddProductBinding

    lateinit var productViewModel: ProductViewModel

    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        var repo = ProductRepistoryImpl()
        productViewModel = ProductViewModel(repo)

        binding.btnAddProduct.setOnClickListener {
            loadingUtils.show()
            var productName = binding.editProductName.text.toString()
            var productPrice = binding.editProductprice.text.toString().toInt()
            var productDesc = binding.editProductDesc.text.toString()

            var model = ProductModel("",
                productName,
                productDesc, productPrice)

            productViewModel.addProduct(model){
                success,message->
                if(success){
                    loadingUtils.dismiss()
                    Toast.makeText(this@AddProductActivity,
                        message,Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    loadingUtils.dismiss()
                    Toast.makeText(this@AddProductActivity,
                        message,Toast.LENGTH_LONG).show()

                }
            }

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}