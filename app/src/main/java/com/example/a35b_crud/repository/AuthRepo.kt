package com.example.a35b_crud.repository

interface AuthRepo {

    fun login(email:String,password:String,callback:(Boolean,String) -> Unit)

}