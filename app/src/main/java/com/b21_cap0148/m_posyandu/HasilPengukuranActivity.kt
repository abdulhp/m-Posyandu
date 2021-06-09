package com.b21_cap0148.m_posyandu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.b21_cap0148.m_posyandu.databinding.ActivityHasilPengukuranBinding
import com.google.firebase.firestore.FirebaseFirestore

class HasilPengukuranActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHasilPengukuranBinding
    private lateinit var fStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilPengukuranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fStore = FirebaseFirestore.getInstance()

        val docRef = fStore.collection("pengukuran").document(intent.getStringExtra("dataUkurTerakhir").toString())
        docRef.get()
            .addOnCompleteListener{
                val document = it.result
                binding.tvNikHasil.text = document!!.getString("NIK")
                binding.tvNamaHasil.text = document!!.getString("nama")
                binding.tvBeratHasil.text = document!!.getString("beratBadan") + " Kg"
                binding.tvTinggiHasil.text = document!!.getString("tinggiBadan") + " Cm"
                binding.tvOutputHasil.text = document!!.getString("output")
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}