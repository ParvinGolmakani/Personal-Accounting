package com.pg.personalaccounting.view.setting

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Handler
import android.provider.MediaStore
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseFragment
import com.pg.personalaccounting.core.interfaces.NameInterface
import com.pg.personalaccounting.core.utils.AppConstants
import com.pg.personalaccounting.core.utils.AppPreferences
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SettingFragment : BaseFragment(R.layout.fragment_setting), NameInterface {


    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: BaseFragment? = null

        fun getInstance(): BaseFragment {
            if (INSTANCE == null) {
                INSTANCE = SettingFragment()
            }
            return INSTANCE as BaseFragment
        }
    }


    override fun afterLoadView() {
        editName()
        loadImage()
        setThemeName()
        // On click events
        mView.share.setOnClickListener {
            shareApp()
        }
        mView.contactUs.setOnClickListener {
            contactUs()
        }
        mView.aboutUs.setOnClickListener {
            val aboutUs = AboutUsDialog(context!!)
            aboutUs.show()
        }
        mView.themeButton.setOnClickListener {
            changeTheme()
        }
        mView.nameTV.setOnClickListener {
            val dialog = NameDialog(this.context!!, this)
            dialog.show()

        }
        mView.profileIV.setOnClickListener {
            imageOpenHandler()
        }
    }

    private fun setThemeName() {
        if (AppPreferences.getTheme() == R.style.DarkTheme) {
            themeButton.text = "Light"
        } else {
            themeButton.text = "Dark"
        }
    }

    private fun editName() {
        nameTV.text = AppPreferences.getName()
    }

    private fun shareApp() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "I use the Personal Account app and I suggest you use it too"
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun contactUs() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:09357773373")
        startActivity(intent)
    }

    private fun changeTheme() {
        if (AppPreferences.getTheme() == R.style.LightTheme) {
            AppPreferences.saveTheme(R.style.DarkTheme)
        } else {
            AppPreferences.saveTheme(R.style.LightTheme)
        }

        restartApplication()
    }

    override fun changeName(name: String) {
        AppPreferences.saveName(name)
        nameTV.text = AppPreferences.getName()
    }

    private fun imageOpenHandler() {
        var permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                openImageSelector()
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
            }
        }

        TedPermission.with(context)
            .setPermissionListener(permissionListener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .check()
    }

    private fun loadImage() {
        GlobalScope.launch {
            val bitmap = AppPreferences.getImage()
            withContext(Dispatchers.Main) {
                if (bitmap != null) {
                    try {
                        profileIV.setImageBitmap(AppPreferences.getImage())
                    } catch (e: Exception) {
                    }
                } else {
                    profileIV.setImageResource(R.drawable.user)
                }
            }
        }
    }

    private fun openImageSelector() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, AppConstants.PICK_PHOTO_FOR_AVATAR)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstants.PICK_PHOTO_FOR_AVATAR) {
                val returnUri: Uri = data?.data!!
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, returnUri)
                GlobalScope.launch {
                    AppPreferences.saveImage(bitmap)
                    withContext(Dispatchers.Main) {
                        loadImage()
                    }
                }
            }

        }
    }

    private fun restartApplication() {
        // Show wait dialog
        val dialog = ProgressDialog(context)
        dialog.setMessage("Changing theme ...")
        dialog.show()
        // dialog shown

        // Restarting application
        Handler().postDelayed({
            val packageManager: PackageManager = context!!.packageManager
            val intent = packageManager.getLaunchIntentForPackage(context!!.packageName)
            val componentName = intent!!.component
            val mainIntent = Intent.makeRestartActivityTask(componentName)
            context!!.startActivity(mainIntent)
            Runtime.getRuntime().exit(0)
        }, 1000)

    }
}