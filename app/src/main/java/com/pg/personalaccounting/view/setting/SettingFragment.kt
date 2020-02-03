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

    private var loadingDialog: ProgressDialog? = null

    override fun afterLoadView() {

        editName()

        // load user profile image
        loadImage()

        // set change theme button
        setThemeName()

        // On click events
        share.setOnClickListener {
            shareApp()
        }
        contactUs.setOnClickListener {
            contactUs()
        }
        aboutUs.setOnClickListener {
            val aboutUs = AboutUsDialog(context!!)
            aboutUs.show()
        }
        themeButton.setOnClickListener {
            changeTheme()
        }
        nameTV.setOnClickListener {
            val dialog = NameDialog(this.context!!, this)
            dialog.show()

        }
        profileIV.setOnClickListener {
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

        // set name in ui
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

        //check permission
        val permissionListener = object : PermissionListener {
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
        showLoading()
        GlobalScope.launch {
            val bitmap = AppPreferences.getImage()
            withContext(Dispatchers.Main) {
                hideLoading()
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

        // open gallery for pick up image

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, AppConstants.PICK_PHOTO_FOR_AVATAR)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // image selected

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstants.PICK_PHOTO_FOR_AVATAR) {

                showLoading()

                //save image bitmap to local database and show in ui

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
        showLoading()
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

    private fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = ProgressDialog(context)
            loadingDialog!!.setMessage("Please wait ...")
        }
        loadingDialog!!.show()

    }

    private fun hideLoading() {
        loadingDialog!!.hide()
    }
}