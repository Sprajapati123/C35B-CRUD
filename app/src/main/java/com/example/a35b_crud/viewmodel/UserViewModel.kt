package com.example.a35b_crud.viewmodel

import com.example.a35b_crud.model.UserModel
import com.example.a35b_crud.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(val repo: UserRepository) {
    fun login(email:String,password:String,callback:(Boolean,String) -> Unit){
        repo.login(email,password, callback)
    }

    fun signup(email: String,password: String,callback: (Boolean,String,String) -> Unit){
        repo.signup(email, password, callback)
    }

    fun forgetPassword(email: String,callback: (Boolean, String) -> Unit){
        repo.forgetPassword(email, callback)
    }

    fun addUserToDatabase(userId:String, userModel: UserModel,
                          callback: (Boolean, String) -> Unit){
        repo.addUserToDatabase(userId, userModel, callback)
    }

    fun getCurrentUser() : FirebaseUser?{
        return repo.getCurrentUser()
    }
}