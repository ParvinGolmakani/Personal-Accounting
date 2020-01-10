package com.pg.personalaccounting.core.utils

import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseApplication

object AppPreferences {

    fun saveTheme(theme: Int) {
        BaseApplication.preferencesEditor.putInt("theme", theme)
        BaseApplication.preferencesEditor.apply()
    }

    fun getTheme(): Int {
        return BaseApplication.preferences.getInt("theme", R.style.LightTheme)
    }
    fun saveName(name:String){
        BaseApplication.preferencesEditor.putString("name", name)
        BaseApplication.preferencesEditor.apply()
    }
    fun getName(): String{
        return BaseApplication.preferences.getString("name", "Account Name" )!!
    }
}
