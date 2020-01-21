package com.pg.personalaccounting.core.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseApplication
import java.io.ByteArrayOutputStream


object AppPreferences {


    // Theme Management
    fun saveTheme(theme: Int) {
        BaseApplication.preferencesEditor.putInt("theme", theme)
        BaseApplication.preferencesEditor.apply()
    }

    fun getTheme(): Int {
        return BaseApplication.preferences.getInt("theme", R.style.LightTheme)
    }

    // User Name
    fun saveName(name: String) {
        BaseApplication.preferencesEditor.putString("name", name)
        BaseApplication.preferencesEditor.apply()
    }

    fun getName(): String {
        return BaseApplication.preferences.getString("name", "Account Name")!!
    }


    // Save and Get image
    fun saveImage(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        val imageEncoded: String = Base64.encodeToString(b, Base64.DEFAULT)
        BaseApplication.preferencesEditor.putString("image", imageEncoded)
        BaseApplication.preferencesEditor.apply()
    }

    fun getImage(): Bitmap? {
        val input = BaseApplication.preferences.getString("image", "")
        val decodedByte: ByteArray = Base64.decode(input, 0)
        return BitmapFactory
            .decodeByteArray(decodedByte, 0, decodedByte.size);
    }
}
