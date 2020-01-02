package com.pg.personalaccounting.core.bases

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pg.personalaccounting.core.utils.AppPreferences

abstract class BaseActivity(private val viewID: Int) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(AppPreferences.getTheme())
        setContentView(viewID)
        afterLoadView()

    }

    abstract fun afterLoadView()

    fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}