package com.b21_cap0148.m_posyandu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.b21_cap0148.m_posyandu.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var fAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        binding.btnDaftar.setOnClickListener({

            val nama = binding.editUsernameRegister.text.toString()
            val email = binding.editEmailRegister.text.toString()
            val nik = binding.editNikRegister.text.toString()
            val password = binding.editPasswordRegister.text.toString()

            daftar(email, password, nama, nik.toInt())
        })
    }

    private fun daftar(email: String, password: String, nama: String, NIK: Int) {
        if(!validateForm()) return

        fAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val uid = fAuth.uid

                    val userData = hashMapOf(
                        "NIK" to NIK,
                        "email" to email,
                        "nama" to nama
                    )

                    fStore.collection("users").document(uid.toString())
                        .set(userData)
                        .addOnSuccessListener {
                            Toast.makeText(baseContext, "Daftar Sukses", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener{}
                    finish()
                }else {
                    Toast.makeText(baseContext, "Daftar Gagal", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val username = binding.editUsernameRegister.text.toString()
        if (TextUtils.isEmpty(username)) {
            binding.editUsernameRegister.error = "Required"
            valid = false
        }else {
            binding.editUsernameRegister.error = null
        }

        val email = binding.editEmailRegister.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.editEmailRegister.error = "Required"
            valid = false
        }else {
            binding.editEmailRegister.error = null
        }

        val NIK = binding.editNikRegister.text.toString()
        if (TextUtils.isEmpty(NIK)) {
            binding.editNikRegister.error = "Required"
            valid = false
        }else {
            binding.editNikRegister.error = null
        }

        val password = binding.editPasswordRegister.text.toString()
        val password2 = binding.editPassword2Register.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.editPasswordRegister.error = "Required"
            valid = false
        } else if(TextUtils.isEmpty(password2)){
            binding.editPassword2Register.error = "Required"
            valid = false
        } else {
            if(password == password2) {
                binding.editPasswordRegister.error = null
                binding.editPassword2Register.error = null
            }else{
                binding.editPassword2Register.error = "Password tidak sama"
            }
        }

        return valid
    }
}