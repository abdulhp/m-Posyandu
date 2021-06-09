package com.b21_cap0148.m_posyandu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.b21_cap0148.m_posyandu.databinding.ActivityInputPengukuranBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class InputPengukuranActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputPengukuranBinding
    private lateinit var fStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputPengukuranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fStore = FirebaseFirestore.getInstance()
        val sharedPrefs = baseContext.getSharedPreferences("userDetail", MODE_PRIVATE)
        val parentId = sharedPrefs.getString("uid", "")

        binding.btnSimpanUkur.setOnClickListener {
            val dataUkur = hashMapOf(
                "NIK" to binding.editNikUkur.text.toString(),
                "nama" to binding.editNamaUkur.text.toString(),
                "parentId" to parentId,
                "beratBadan" to binding.editBeratUkur.text.toString(),
                "tinggiBadan" to binding.editTinggiUkur.text.toString(),
                "tanggalUkur" to Timestamp.now(),
                "output" to "Normal"
            )

            simpan(dataUkur)
        }
    }

    private fun simpan(data: Any) {
        fStore.collection("pengukuran")
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(baseContext, "Input Sukses", Toast.LENGTH_SHORT).show()
                val result = it.id
                val i = Intent(this@InputPengukuranActivity, HasilPengukuranActivity::class.java)
                i.putExtra("dataUkurTerakhir", result)
                startActivity(i)
                finish()
            }
            .addOnFailureListener{
                Toast.makeText(baseContext, "Input Gagal", Toast.LENGTH_SHORT).show()
            }
    }
}