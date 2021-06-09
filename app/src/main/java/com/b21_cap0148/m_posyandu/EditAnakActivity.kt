package com.b21_cap0148.m_posyandu

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.b21_cap0148.m_posyandu.databinding.ActivityEditAnakBinding
import com.google.firebase.firestore.FirebaseFirestore

class EditAnakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditAnakBinding
    private lateinit var fStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fStore = FirebaseFirestore.getInstance()

        val editMode = intent.getBooleanExtra("editMode", false)
        val sharedPrefs = baseContext.getSharedPreferences("userDetail", MODE_PRIVATE)
        val uid = sharedPrefs.getString("uid", "")

        binding.btnSimpanAnak.setOnClickListener {
            if(editMode) {

            }else {
                val dataAnak = hashMapOf(
                    "NIK" to binding.editNikAnak.text.toString(),
                    "parentId" to uid,
                    "nama" to binding.editNameAnak.text.toString(),
                    "jenisKelamin" to binding.editGenderAnak.text.toString(),
                    "tglLahir" to binding.editTglAnak.text.toString(),
                    "alamat" to binding.editAlamatAnak.text.toString()
                )

                tambahAnak(dataAnak)
            }
        }
    }

    private fun tambahAnak(dataAnak: Any) {
        fStore.collection("childrens")
            .add(dataAnak)
            .addOnSuccessListener {
                Toast.makeText(baseContext, "Input Sukses", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@EditAnakActivity, DataAnakActivity::class.java))
                finish()
            }
            .addOnFailureListener{
                Toast.makeText(baseContext, "Input Gagal", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}