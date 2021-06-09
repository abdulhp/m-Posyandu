package com.b21_cap0148.m_posyandu

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.b21_cap0148.m_posyandu.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var fAuth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fAuth = FirebaseAuth.getInstance()
        if(fAuth.currentUser == null) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }

        val sharedPref = baseContext.getSharedPreferences("userDetail", MODE_PRIVATE)

        binding.textUsername.text = sharedPref.getString("nama", "")

        binding.btnDataAnak.setOnClickListener(this)
        binding.btnInputPengukuran.setOnClickListener(this)
        binding.btnProfil.setOnClickListener(this)
        binding.btnAgendaPosyandu.setOnClickListener(this)
        binding.btnRiwayatPengukuran.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnDataAnak.id -> {
                startActivity(Intent(this@MainActivity, DataAnakActivity::class.java))
            }
            binding.btnInputPengukuran.id -> {
                startActivity(Intent(this@MainActivity, InputPengukuranActivity::class.java))
            }
        }
    }
}