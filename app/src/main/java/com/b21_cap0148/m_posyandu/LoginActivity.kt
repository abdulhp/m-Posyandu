package com.b21_cap0148.m_posyandu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.b21_cap0148.m_posyandu.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var fAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        binding.btnLogin.setOnClickListener({
            Login(binding.editEmailLogin.text.toString(), binding.editPasswordLogin.text.toString())
        })

        binding.tvRegisterLogin.setOnClickListener({
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        })
    }

    private fun Login(email: String, password: String) {
        if(!validateForm()) return

        fAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val uid = fAuth.uid
                    val docRef = fStore.collection("users").document(uid.toString())
                    docRef.get()
                        .addOnCompleteListener{
                            val document = it.result
                            val sharedPref = baseContext.getSharedPreferences("userDetail", MODE_PRIVATE)
                            with(sharedPref.edit()) {
                                putString("NIK", document!!.get("NIK").toString())
                                putString("nama", document!!.get("nama").toString())
                                putString("email", document!!.get("email").toString())
                                apply()
                            }
                        }

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(baseContext, "email atau password salah", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val username = binding.editEmailLogin.text.toString()
        if (TextUtils.isEmpty(username)) {
            binding.editEmailLogin.error = "Required"
            valid = false
        }else {
            binding.editEmailLogin.error = null
        }

        val password = binding.editPasswordLogin.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.editPasswordLogin.error = "Required"
            valid = false
        }else {
            binding.editPasswordLogin.error = null
        }

        return valid
    }
}