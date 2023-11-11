package com.bs.kotha.framework.base

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.bs.kotha.R
import com.example.kothamigration.R

class NotificationPermissionActivity : BaseBindableActivity() {
    private val TAG = "NotificationPermissionActivity"
    private var mBinding: ActivityNotificationPermissionBinding? = null
    private var multiplePermissionsContract: RequestMultiplePermissions? = null
    private var multiplePermissionLauncher: ActivityResultLauncher<Array<String>>? = null
    private val permissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    private var alertDialog: AlertDialog? = null
    override val layoutId: Int
        protected get() = R.layout.activity_notification_permission

    override fun startUI(savedInstanceState: Bundle?) {
        mBinding = viewDataBinding as ActivityNotificationPermissionBinding?
        multiplePermissionsContract = ActivityResultContracts.RequestMultiplePermissions()
        multiplePermissionLauncher = registerForActivityResult<Array<String>, Map<String, Boolean>>(
            multiplePermissionsContract,
            ActivityResultCallback<Map<String, Boolean>> { isGranted: Map<String?, Boolean?> ->
                if (isGranted.containsValue(false)) {
                    nextDesireActivity()
                } else {
                    nextDesireActivity()
                }
            })
        setupListener()
    }

    public override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= 24 && NotificationUtils.areNotificationPermissionEnable()) {
            nextDesireActivity()
        }
    }

    private fun setupListener() {
        mBinding.tvImIn.setOnClickListener { v ->
            if (Build.VERSION.SDK_INT >= 33) {
                if (!letsCheckPermissions()) {
                    multiplePermissionLauncher.launch(permissions)
                }
            } else {
                if (Build.VERSION.SDK_INT >= 24) {
                    if (!NotificationUtils.areNotificationPermissionEnable()) {
                        DialogUtil.goToAppSettingsDialog(this@NotificationPermissionActivity)
                    } else {
                        nextDesireActivity()
                    }
                } else {
                    nextDesireActivity()
                }
            }
        }
        mBinding.tvSkip.setOnClickListener { v -> nextDesireActivity() }
    }

    private fun letsCheckPermissions(): Boolean {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            return ContextCompat.checkSelfPermission(NotificationPermissionActivity.this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
//        } else {
        return hasPermissions(permissions)
        //}
    }

    private fun hasPermissions(permissions: Array<String>?): Boolean {
        if (permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        this@NotificationPermissionActivity,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
            return true
        }
        return false
    }

    private fun nextDesireActivity() {
        showLoader()
        if (!PrefManager.getPreference()
                .getPrivateBoolean(PrefKey.FLAG_ONBOARD_PROFILE_UPDATED, false)
        ) {
            OnBoardActivity.startThisActivity(this@NotificationPermissionActivity, false)
        } else {
            Navigator.startDashboardActivity(this@NotificationPermissionActivity, false)
        }
        finish()
    }

    private fun showLoader() {
        if (!isFinishing && alertDialog == null || !alertDialog!!.isShowing) {
            alertDialog = DialogUtil.showLoaderAlert(this)
        }
    }

    private fun hideLoader() {
        DialogUtil.hideLoaderAlert(alertDialog)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    companion object {
        fun startThisActivity(context: Context) {
            val intent = Intent(context, NotificationPermissionActivity::class.java)
            context.startActivity(intent)
        }
    }
}