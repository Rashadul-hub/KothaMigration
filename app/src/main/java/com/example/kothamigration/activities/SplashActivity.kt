package com.bs.kotha.framework.base


import androidx.core.splashscreen.SplashScreen
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.core.splashscreen.SplashScreen
import com.bs.kotha.BuildConfig
import com.bs.kotha.KothaApp
import com.bs.kotha.framework.base.BaseKothaInitialActivity
import com.bs.kotha.framework.firebase.FCMHandler
import com.bs.kotha.framework.notification.NotificationUtils
import com.bs.kotha.framework.preference.PrefKey
import com.bs.kotha.framework.preference.PrefManager
import com.bs.kotha.framework.util.ui.Navigator
import com.example.languagecontrolmodule.LanguageConstants


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseKothaInitialActivity() {
    protected val layoutId: Int
        protected get() = 0

    protected fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen: SplashScreen = SplashScreen.installSplashScreen(this)
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }

        // Add a callback that's called when the splash screen is animating to
        // the app content.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getSplashScreen().setOnExitAnimationListener { splashScreenView ->
                val slideUp: ObjectAnimator = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_Y,
                    0f,
                    -splashScreenView.getHeight()
                )
                slideUp.setInterpolator(AnticipateInterpolator())
                slideUp.setDuration(200L)

                // Call SplashScreenView.remove at the end of the custom animation.
                slideUp.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        splashScreenView.remove()
                    }
                })

                // Run this animation.
                slideUp.start()
            }
        }
    }

    protected fun startUI(savedInstanceState: Bundle?) {}
    protected fun onStart() {
        super.onStart()
        runOnUiThread(FCMHandler::enableFCM)
        Handler(Looper.getMainLooper()).post(Runnable { startTargetActivityIntent() })
    }

    private fun startTargetActivityIntent() {
        // LUC: 20200301
        if (!PrefManager.getPreference().isKeyStayed(LanguageConstants.KEY_APP_LANGUAGE)) {
            Log.i(TAG, "startTargetActivityIntent: Language Select")
            if (BuildConfig.isSupportMultiLanguage) {
                LanguageSelectActivity.startThisActivity(this)
            } else {
                PrefManager.getPreference()
                    .putString(LanguageConstants.KEY_APP_LANGUAGE, LanguageConstants.ENGLISH)
                KothaApp.getInstance().initLanguage()
                LoginActivity.startThisActivity(this)
            }
        } else if (!authVM.hasPhoneNum() || PrefManager.getPreference().isAnyTokenMissing()) {
            Log.i(TAG, "startTargetActivityIntent: Login")
            LoginActivity.startThisActivity(this)
        } else if (Build.VERSION.SDK_INT >= 24 && !NotificationUtils.areNotificationPermissionEnable()) {
            NotificationPermissionActivity.startThisActivity(this)
        } else if (!PrefManager.getPreference()
                .getPrivateBoolean(PrefKey.FLAG_ONBOARD_PROFILE_UPDATED, false)
        ) {
            Log.i(TAG, "startTargetActivityIntent: On Board")
            OnBoardActivity.startThisActivity(this, false)
        } else if (KothaApp.getInstance().getCallManager().getVoipCallStateData()
                .isCallRunning() && KothaApp.getInstance().getCallPendingIntent() != null
        ) {
            Log.i(TAG, "startTargetActivityIntent: Audio call activity")
            try {
                KothaApp.getInstance().getCallPendingIntent().send()
            } catch (e: CanceledException) {
                e.printStackTrace()
            }
        } else if (KothaApp.getInstance().getCallManager().getVoipCallStateData()
                .isCallRunning() && KothaApp.getInstance().getIncomingCallPendingIntent() != null
        ) {
            Log.i(TAG, "startTargetActivityIntent: Incoming call activity")
            try {
                KothaApp.getInstance().getIncomingCallPendingIntent().send()
            } catch (e: CanceledException) {
                e.printStackTrace()
            }
        } else {
            Log.i(TAG, "startTargetActivityIntent: Dash Board")
            Navigator.startDashboardActivity(this, false)
        }
        finish()
    }

    fun onBackPressed() {
        super.onBackPressed()
    }

    companion object {
        private const val TAG = "SplashActivity"
    }
}