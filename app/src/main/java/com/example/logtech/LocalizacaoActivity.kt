package com.example.logtech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.logtech.databinding.ActivityLocalizacaoBinding

class LocalizacaoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocalizacaoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalizacaoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}