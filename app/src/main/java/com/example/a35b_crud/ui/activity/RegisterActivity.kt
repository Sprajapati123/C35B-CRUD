package com.example.a35b_crud.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a35b_crud.R
import com.example.a35b_crud.databinding.ActivityRegisterBinding
import com.example.a35b_crud.model.UserModel
import com.example.a35b_crud.repository.UserRepository
import com.example.a35b_crud.repository.UserRepositoryImpl
import com.example.a35b_crud.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userRepository = UserRepositoryImpl()

        userViewModel = UserViewModel(userRepository)


        binding.signUp.setOnClickListener {
            var email: String = binding.registerEmail.text.toString()
            var password: String = binding.registerPassword.text.toString()
            var fName: String = binding.registerFname.text.toString()
            var lName: String = binding.registerLName.text.toString()
            var address: String = binding.registerAddress.text.toString()
            var contact: String = binding.registerContact.text.toString()

            userViewModel.signup(email,password){
                success,message,userId ->
                val userModel = UserModel(
                        userId,
                        email, fName, lName, address, contact
                    )
                userViewModel.addUserToDatabase(userId,userModel){
                    success,message ->
                    if(success){
                        Toast.makeText(this@RegisterActivity
                            ,message,Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@RegisterActivity
                            ,message,Toast.LENGTH_SHORT).show()
                    }
                }
            }

//            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
//                if (it.isSuccessful) {
//
//                    val userId = auth.currentUser?.uid
//
//                    val userModel = UserModel(
//                        userId.toString(),
//                        email, fName, lName, address, contact
//                    )
//
//                    reference.child(userId.toString()).setValue(userModel)
//                        .addOnCompleteListener {
//                            if (it.isSuccessful) {
//                                Toast.makeText(
//                                    this@RegisterActivity,
//                                    "REGISTRATION SUCCESS", Toast.LENGTH_SHORT
//                                ).show()
//                            } else {
//                                Toast.makeText(
//                                    this@RegisterActivity,
//                                    it.exception?.message, Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
//
//
//                } else {
//                    Toast.makeText(
//                        this@RegisterActivity,
//                        it.exception?.message, Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}