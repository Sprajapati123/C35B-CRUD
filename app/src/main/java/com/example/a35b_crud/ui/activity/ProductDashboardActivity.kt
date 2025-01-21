package com.example.a35b_crud.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a35b_crud.R
import com.example.a35b_crud.adapter.ProductAdapter
import com.example.a35b_crud.databinding.ActivityProductDashboardBinding
import com.example.a35b_crud.repository.ProductRepistoryImpl
import com.example.a35b_crud.viewmodel.ProductViewModel
import java.util.ArrayList

class ProductDashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDashboardBinding

    lateinit var productViewModel: ProductViewModel

    lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProductAdapter(this@ProductDashboardActivity,
            ArrayList())

        var repo = ProductRepistoryImpl()
        productViewModel = ProductViewModel(repo)

        productViewModel.getAllProduct()


        productViewModel.allProducts.observe(this){product->
            product?.let {
                adapter.updateData(it)
            }
          }

        productViewModel.loadingState.observe(this){loading->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{

                binding.progressBar.visibility = View.GONE
            }

        }

        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)



        binding.floatingActionButton2.setOnClickListener {
            var intent = Intent(this@ProductDashboardActivity,
                AddProductActivity::class.java
                )
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}