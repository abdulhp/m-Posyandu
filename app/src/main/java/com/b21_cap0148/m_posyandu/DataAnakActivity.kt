package com.b21_cap0148.m_posyandu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.b21_cap0148.m_posyandu.databinding.ActivityDataAnakBinding

class DataAnakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataAnakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnTambahAnak.setOnClickListener{
            var i = Intent(this@DataAnakActivity, EditAnakActivity::class.java)
            i.putExtra("editMode", false)
            startActivity(i)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}