package com.example.image_database

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.example.image_database.databinding.ActivityResoucreSaveBinding
import java.io.ByteArrayOutputStream

class Resoucre_Save : AppCompatActivity() {
    private lateinit var binding : ActivityResoucreSaveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResoucreSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dbHelper = dbHelperClass(this)

         binding.resouse.setImageResource(R.drawable.photo)


        binding.savebutton.setOnClickListener {
            dbHelper.updateData(drawableToByteArray(R.drawable.photo,this))
        }
    }

    fun drawableToByteArray(drawableResId: Int, context: Context): ByteArray? {
        val drawable = ContextCompat.getDrawable(context, drawableResId)
        val bitmap = Bitmap.createBitmap(drawable?.intrinsicWidth ?: 0, drawable?.intrinsicHeight ?: 0, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable?.setBounds(0, 0, canvas.width, canvas.height)
        drawable?.draw(canvas)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
}