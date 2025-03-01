package com.example.a35b_crud.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a35b_crud.Calculations
import com.example.a35b_crud.R
import com.example.a35b_crud.databinding.ActivityCalculationBinding

class CalculationActivity : AppCompatActivity() {
    lateinit var binding: ActivityCalculationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCalculationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalulate.setOnClickListener {
            var calculations = Calculations()
            var a = binding.firstValue.text.toString().toInt()
            var b = binding.secondValue.text.toString().toInt()
            var result = calculations.add(a,b)

            binding.displaySum.text = result.toString()

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}