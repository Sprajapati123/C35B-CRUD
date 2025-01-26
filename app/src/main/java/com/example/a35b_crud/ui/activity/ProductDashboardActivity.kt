package com.example.a35b_crud.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var productId = adapter.getProductId(viewHolder.adapterPosition)

                productViewModel.deleteProduct(productId){
                    success,message->
                    if(success){
                        Toast.makeText(this@ProductDashboardActivity,
                            message,Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@ProductDashboardActivity,message,Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }).attachToRecyclerView(binding.recycler)



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