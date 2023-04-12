package com.example.image_database

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.image_database.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dbHelper = dbHelperClass(this)

        binding.CallerBtn.setOnClickListener { startActivity(Intent(this, Activity_Caller::class.java)) }
        binding.ResouseBtn.setOnClickListener { startActivity(Intent(this, Resoucre_Save::class.java)) }




        val imageByte = dbHelper.getData()

        if (imageByte!= null){
            binding.Image.setImageBitmap(byteArrayToBitmap(imageByte))
        }
        else{
            binding.Image.setImageResource(R.drawable.photo)
        }



    }

    fun byteArrayToBitmap(byteArray: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}