package com.example.a35b_crud.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.a35b_crud.model.ProductModel
import com.example.a35b_crud.repository.ProductRepository

class ProductViewModel(val repository: ProductRepository) {
    fun addProduct(productModel: ProductModel,
                   callback:(Boolean,String) -> Unit
    ){
        repository.addProduct(productModel,callback)
    }

    fun updateProduct(productId:String,
                      data: MutableMap<String,Any>,
                      callback: (Boolean, String) -> Unit){
        repository.updateProduct(productId, data, callback)
    }

    fun deleteProduct(productId:String,
                      callback: (Boolean, String) -> Unit){
        repository.deleteProduct(productId, callback)
    }

    var _products = MutableLiveData<ProductModel?>()
    var products = MutableLiveData<ProductModel?>()
        get() = _products

    var _allProducts = MutableLiveData<List<ProductModel>?>()
    var allProducts = MutableLiveData<List<ProductModel>?>()
        get() = _allProducts

    fun getProductById(productId:String){
        repository.getProductById(productId){
            product,success,message->
            if(success){
                _products.value = product
            }
        }
    }

    var _loadingState = MutableLiveData<Boolean>()
    var loadingState = MutableLiveData<Boolean>()
        get() = _loadingState

    fun getAllProduct(){
        _loadingState.value = true
        repository.getAllProduct{
            products, success, message ->
            if(success){
                _allProducts.value = products
                _loadingState.value = false
            }
        }
    }
}