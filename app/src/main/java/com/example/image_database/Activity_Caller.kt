package com.example.image_database

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.constraint.motion.Debug
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.image_database.databinding.ActivityCallerBinding
import java.io.ByteArrayOutputStream

class Activity_Caller : AppCompatActivity() {
    private lateinit var binding: ActivityCallerBinding
    private var pickImage = 100
    private lateinit var imageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dbHelper = dbHelperClass(this)

        binding.pickBtn.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }


        binding.saveBtn.setOnClickListener {
            if (imageUri!=null){
                try {
                    val result = dbHelper.updateData(uriToByteArray(imageUri,this@Activity_Caller))
                    Log.d("DataResult", result.toString())
                }catch (e :java.lang.Exception){
                    Log.d("DataResult", e.toString()) } } }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            if (data != null) {
                imageUri = data.data!!
            }
            binding.imageView.setImageURI(imageUri)
        }
    }

    fun uriToByteArray(uri: Uri, context: Context): ByteArray? {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val outputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len: Int
        while (inputStream?.read(buffer).also { len = it!! } != -1) {
            outputStream.write(buffer, 0, len)
        }
        return outputStream.toByteArray()
    }

}