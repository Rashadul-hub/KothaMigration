package com.bs.kotha.framework.base


import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import androidx.databinding.DataBindingUtil
import java.util.Date
import java.util.concurrent.Callable
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsetsController
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.bs.kotha.KothaApp
import com.bs.kotha.application.chat.view.communityutils.CommunityHelper
import com.bs.kotha.application.feed.view.activity.NewPostActivity
import com.bs.kotha.application.navigation.activity.DashboardActivity
import com.bs.kotha.application.profile.view.dialog.BottomSheetProfileSelectionDialog
import com.bs.kotha.application.profile.view.dialog.ConfirmationBottomSheetDialogFragment
import com.bs.kotha.application.registration.activity.AuthenticationActivity
import com.bs.kotha.application.registration.activity.OnBoardActivity
import com.bs.kotha.application.registration.viewmodel.AuthV2ViewModel
import com.bs.kotha.application.voip.viewmodel.VOIPCallViewModel
import com.bs.kotha.framework.communitychat.CommunityApp
import com.bs.kotha.framework.database.controller.RealmProfileController
import com.bs.kotha.framework.database.model.RealmProfile
import com.bs.kotha.framework.preference.PrefKey
import com.bs.kotha.framework.preference.PrefManager
import com.bs.kotha.framework.rest.listeners.ApiCallEventListener
import com.bs.kotha.framework.socket.connection.KothaConnectionManager
import com.bs.kotha.framework.socket.listeners.ConnectionErrorEventListener
import com.bs.kotha.framework.socket.listeners.UserAccountEventListener
import com.bs.kotha.framework.socket.listeners.VOIPCallPacketListener
import com.bs.kotha.framework.socket.model.webrtc.AnswerSDP
import com.bs.kotha.framework.socket.model.webrtc.CancelCall
import com.bs.kotha.framework.socket.model.webrtc.IceCandidates
import com.bs.kotha.framework.socket.model.webrtc.MediaSwitch
import com.bs.kotha.framework.socket.model.webrtc.NewCallReq
import com.bs.kotha.framework.socket.model.webrtc.OfferSDP
import com.bs.kotha.framework.socket.model.webrtc.RingingCall
import com.bs.kotha.framework.util.BottomSheetUtils
import com.bs.kotha.framework.util.Utils
import com.bs.kotha.framework.util.ui.DialogUtil
import com.bs.kotha.framework.util.ui.ServiceNavigator
import com.example.kothamigration.R
import com.example.languagecontrolmodule.LanguageUtils
import com.example.languagecontrolmodule.TranslatorEnum
import com.module.payment.helper.PaymentLauncher

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


/**
 * Abstract class. All common activity related task happens here.
 */
abstract class BaseBindableActivity : AppCompatActivity(), View.OnClickListener,
    VOIPCallPacketListener.VOIPCallEventListener,
    UserAccountEventListener.UserAccountRemoteEventListener,
    UserAccountEventListener.UserFeedCreateBanListener,
    UserAccountEventListener.UserFeedCreateLimitReachListener,
    ConnectionErrorEventListener.OnConnectionErrorEventListener {
    private var mViewDataBinding: ViewDataBinding? = null
    private var connectionDisposable: Disposable? = null
    protected abstract val layoutId: Int
    var isActivityResumed = false
        private set
    protected var authVM: AuthV2ViewModel? = null
    protected var callViewModel: VOIPCallViewModel? = null
    protected var progressLoaderDialog: AlertDialog? = null
    private var numberOfChecks = 0
    private val handler = Handler()


    private val trackOngoingTokenRefreshProcess: Runnable = object : Runnable {
        override fun run() {
            if (numberOfChecks < MAX_CHECKS) {
                // Check the realm variable here
                val refreshStarted: Boolean = PrefManager.getPreference()
                    .getPrivateBoolean(PrefKey.KEY_IS_TOKEN_REFRESH_PROCESS_ALREADY_STARTED, false)
                if (!refreshStarted) {
                    KothaConnectionManager.getInstance().disconnectInternal()
                    KothaConnectionManager.getInstance().connect()
                } else {
                    numberOfChecks++
                    handler.postDelayed(this, CHECK_INTERVAL_MS)
                }
            }
        }
    }


    private val apiUnauthorizedErrorListener: ApiCallEventListener.ApiUnauthorizedErrorListener =
        ApiCallEventListener.ApiUnauthorizedErrorListener { url -> refreshToken() }

    protected fun shouldShowProfileSelection(): String? {
        return null
    }

    protected override fun attachBaseContext(newBase: Context) {
        if (TextUtils.isEmpty(LanguageUtils.getCurrentLangCode())) {
            KothaApp.getInstance().initLanguage()
        }
        val context: Context = LanguageUtils.changeLang(newBase, LanguageUtils.getCurrentLangCode())
        super.attachBaseContext(context)
    }

    @CallSuper
    protected override fun onCreate(savedInstanceState: Bundle?) {
        checkAndSwitchDarkMode()
        super.onCreate(savedInstanceState)
        setWindowFlags()
        val layoutId = layoutId
        if (layoutId > toolbarId) {
            updateLayoutView(layoutId)
            setStatusBarColor()
            val toolbarId: Int = toolbarId
            if (toolbarId > Companion.toolbarId) {
                val toolbar: Toolbar = findViewById<Toolbar>(toolbarId)
                if (toolbar != null) {
                    setSupportActionBar(toolbar)
                }
                setTitle("")
                val actionBar: ActionBar = getSupportActionBar()
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true)
                    actionBar.setDisplayShowHomeEnabled(true)
                }
            }
        }
        authVM = ViewModelProvider(this).get(AuthV2ViewModel::class.java)
        callViewModel = ViewModelProvider(this).get(VOIPCallViewModel::class.java)
        if (!RealmProfileController.isBusinessProfileSelected()) {
            CommunityApp.saveUserToken(
                CommunityHelper.getUserId(),
                CommunityHelper.getIrcToken(),
                CommunityHelper.getIrcId(),
                CommunityHelper.getUserName(),
                CommunityHelper.getUserAvatar()
            )
            PaymentLauncher.setToken(
                PrefManager.getPreference().getPrivateString(PrefKey.ACCESS_TOKEN)
            )
        }
        val targetProfileId = shouldShowProfileSelection()
        if (!TextUtils.isEmpty(targetProfileId)) {
            BottomSheetProfileSelectionDialog.showDialog(
                getSupportFragmentManager(),
                targetProfileId,
                true
            )
        } else {
            setRefreshingAccessTokenObserver()
            startUI(savedInstanceState)
            //            KothaConnectionManager.getInstance().getConnectionSateMutableLiveData().observe(this, connectionState -> {
//                if (connectionState != null && connectionState == KothaConnection.ConnectionState.CONNECTED) {
//                    VOIPCallPacketListener.getInstance().addEventListeners(BaseBindableActivity.this);
//                } else {
//                    VOIPCallPacketListener.getInstance().removeEventListeners(BaseBindableActivity.this);
//                }
//            });
        }
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        showForwardTransition()
    }

    protected override fun onResume() {
        super.onResume()
        isActivityResumed = true
        startConnecting()
        UserAccountEventListener.getListener().addRemoteEventListener(this)
        UserAccountEventListener.getListener().addFeedCreateBanListener(this)
        UserAccountEventListener.getListener().addFeedCreateLimitReachListener(this)
        ConnectionErrorEventListener.getListener().addConnectionErrorEventListener(this)
        if (!(this is SplashActivity
                    || this is LoginActivity)
        ) {
            checkAndRefreshToken()
            ApiCallEventListener.getListener().addUnauthorizedListener(apiUnauthorizedErrorListener)
            val realmProfile: RealmProfile = RealmProfileController.getSelectedProfile()
            if (KothaApp.getInstance()
                    .isBanned() || realmProfile != null && realmProfile.isBanned()
            ) {
                UserAccountEventListener.getListener()
                    .onProcessAction(UserAccountEventListener.AccountActionReason.BANNED)
            }
        }
    }

    protected override fun onPause() {
        super.onPause()
        isActivityResumed = false
        UserAccountEventListener.getListener().removeRemoteEventListener(this)
        UserAccountEventListener.getListener().removeFeedCreateBanListener(this)
        UserAccountEventListener.getListener().removeFeedCreateLimitReachListener(this)
        ConnectionErrorEventListener.getListener().removeConnectionErrorEventListener(this)
        ApiCallEventListener.getListener().removeUnauthorizedListener(apiUnauthorizedErrorListener)
    }

    private fun checkAndRefreshToken() {
        val currentDate = Date(System.currentTimeMillis())
        val dateLong: Long =
            PrefManager.getPreference().getPrivateLong(PrefKey.KEY_TOKEN_GETTING_TIME)
        val fromDate = Date(dateLong)
        val days: Long = Utils.getDifferenceDays(fromDate, currentDate)
        val shouldRefreshToken = PrefManager.getPreference()
            .getBoolean(PrefKey.SHOULD_REFRESH_TOKEN, false) || dateLong == 0L
        if (days > 6 || PrefManager.getPreference().isAnyTokenMissing() || shouldRefreshToken) {
            refreshToken()
        }
    }

    private fun refreshToken() {
        authVM.refreshTokenKothaServicesV2()
    }

    private fun startConnecting() {
        if (KothaConnectionManager.getInstance().canNotStartConnecting()) return
        disposeConnectionDisposable()
        connectionDisposable = Single.fromCallable(connectionCallable)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Boolean?>() {
                fun onSuccess(@NonNull aBoolean: Boolean?) {}
                fun onError(@NonNull e: Throwable?) {}
            })
    }

    private fun disposeConnectionDisposable() {
        if (connectionDisposable != null && !connectionDisposable.isDisposed()) {
            connectionDisposable.dispose()
        }
    }

    private val connectionCallable: Callable<Boolean>
        private get() = Callable<Boolean> {
            KothaConnectionManager.getInstance().connect()
            KothaConnectionManager.getInstance().setReadersEnabled(true)
            true
        }

    protected fun setWindowFlags() {}
    private fun showForwardTransition() {
        if (this is SplashActivity
            || this is LoginActivity
            || this is OnBoardActivity
        ) {
            return
        }
        Utils.showForwardTransition(this)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        showForwardTransition()
    }

    val statusBarColor: Int
        /**
         * child class can override this to change status bar color
         *
         * @return - color resource ID
         */
        get() = R.color.default_background_color

    /**
     * will set status bar color accordingly
     */
    @Suppress("deprecation")
    private fun setStatusBarColor() {
        if (mViewDataBinding == null) return
        val statusBarColor = statusBarColor
        if (statusBarColor > 0) {
            val container: View = mViewDataBinding.getRoot()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val controller: WindowInsetsController? = container.windowInsetsController
                if (controller != null) controller.setSystemBarsAppearance(
                    if (statusBarColor != R.color.white) 0 else WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS or WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS or WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                )
            } else {
                var flags = container.systemUiVisibility
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    flags = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                    }
                    flags =
                        if (statusBarColor != R.color.white) flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                container.systemUiVisibility = flags
            }
            getWindow().setStatusBarColor(ContextCompat.getColor(this, statusBarColor))
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, statusBarColor))
        }
    }

    private fun updateLayoutView(layoutId: Int) {
        Log.i(TAG, "updateLayoutView: $layoutId")
        try {
            mViewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (mViewDataBinding == null) {
            setContentView(layoutId)
        }
    }

    /**
     * Child class have to implement this method. This method run on onStart lifecycle
     *
     * @param savedInstanceState -
     */
    protected abstract fun startUI(savedInstanceState: Bundle?)

    /**
     * Child class have to implement this method. This method run on onDestroy lifecycle
     */
    protected fun stopUI() {}
    protected val viewDataBinding: ViewDataBinding?
        /**
         * Return current viewDataBinding
         *
         * @return -
         */
        protected get() = mViewDataBinding

    override fun onClick(view: View) {}
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    protected override fun onDestroy() {
        super.onDestroy()
        disposeConnectionDisposable()
        Utils.hideSoftKeyboard(this)
        stopUI()
        VOIPCallPacketListener.getInstance().removeEventListeners(this)
    }

    /**
     * To set title on toolbar
     *
     * @param title string value
     */
    protected fun setTitle(title: String?) {
        val actionBar: ActionBar = getSupportActionBar()
        if (actionBar != null) {
            actionBar.setTitle(title)
        }
    }

    /**
     * To set sub title on toolbar
     *
     * @param subtitle string value
     */
    fun setSubtitle(subtitle: String?) {
        val actionBar: ActionBar = getSupportActionBar()
        if (actionBar != null) {
            actionBar.setSubtitle(subtitle)
        }
    }

    /**
     * To set click listener on any view, You can pass multiple view at a time
     *
     * @param views View as params
     */
    protected fun setClickListener(vararg views: View) {
        for (view in views) {
            view.setOnClickListener(this)
        }
    }

    override fun onBackPressed() {
        if (Utils.hideSoftKeyboard(this)) {
            return
        }
        try {
            super.onBackPressed()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        if (this is SplashActivity
            || this is LoginActivity
            || this is OnBoardActivity
            || this is DashboardActivity
        ) {
            return
        }
        Utils.showBackwardTransition(this)
    }

    /**
     * Register observer for listening whether an access token is missing or encountered 401 error
     */
    private fun setRefreshingAccessTokenObserver() {
        authVM.getAllLoginDataSuccessLiveData().observe(this) { success ->
            Log.d(TAG, "refresh token success:: $success")
            if (success) {
                if (!KothaConnectionManager.getInstance().isConnected()) {
                    KothaConnectionManager.getInstance().disconnectInternal()
                    KothaConnectionManager.getInstance().connect()
                }
            }
        }
    }

    // Receiver got new call request
    fun onNewCallReq(newCallReq: NewCallReq?) {
        if (this is SplashActivity
            || this is LoginActivity
            || this is AuthenticationActivity
            || this is OnBoardActivity
        ) {
            return
        }
        /*
        Log.d("Firebase", "is call running: " + KothaApp.getInstance().getCallManager().isCallRunning());
        if (!KothaApp.getInstance().getCallManager().isCallRunning()) {
            Intent intent = new Intent(getApplicationContext(), IncomingCallService.class);
            intent.setAction(IncomingCallService.ACTION_START_INCOMING_CALL);
            intent.putExtra(IntentKeyCall.FROM_ID, jsonObject.optString("from"));
            intent.putExtra(IntentKeyCall.UNIQUE_CALL_ID, jsonObject.optString("uniqueCallID"));

            intent.putExtra(IntentKeyCall.IS_VIDEO_CALL, jsonObject.optBoolean("hasVideo", false));
            intent.putExtra(IntentKeyCall.CALLER_NAME, jsonObject.optString("content"));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        } else {
            DevLog.d(TAG, "\t>> another incoming call id from socket: " + jsonObject.optString("uniqueCallID") + ", own call id: " + KothaApp.getInstance().getCallManager().getRunningCallId());
            if (!TextUtils.equals(jsonObject.optString("uniqueCallID"), KothaApp.getInstance().getCallManager().getRunningCallId())) {
                PacketSender.getInstance().sendCallRejectPacket(RealmProfileController.getUserProfileId(), jsonObject.optString("from"), jsonObject.optString("uniqueCallID"));
            }
        }
        */

//        runOnUiThread(() -> {
//            String uniqueCallID = jsonObject.optString("uniqueCallID");
//            String fromId = jsonObject.optString("from");
//            String hasVideo = jsonObject.optString("hasVideo");
//            DevLog.d("VOIPCall", "isCallRunning: " + KothaApp.getInstance().getCallManager().isCallRunning() + ", runningCallId: " + KothaApp.getInstance().getCallManager().getRunningCallId(), true);
//            if (!KothaApp.getInstance().getCallManager().isCallRunning() &&
//                    !KothaApp.getInstance().getCallManager().isRunningCallVerificationRequested() &&
//                    !KothaApp.getInstance().isCellularCallRunning()) {
//                PacketSender.getInstance().sendCallVerificationReq(RealmProfileController.getUserProfileId(), fromId, SUB_TYPE_VERIFICATION_REQ, uniqueCallID, hasVideo);
//            } else if (KothaApp.getInstance().isCellularCallRunning()) {
//                PacketSender.getInstance().sendCallRejectPacket(RealmProfileController.getUserProfileId(), fromId, uniqueCallID);
//            } else {
//                if (!TextUtils.equals(uniqueCallID, KothaApp.getInstance().getCallManager().getRunningCallId())) {
//                    PacketSender.getInstance().sendCallRejectPacket(RealmProfileController.getUserProfileId(), fromId, uniqueCallID);
//                }
//            }
//        });
    }

    fun onReceiveOfferSDP(offerSDP: OfferSDP?) {}
    fun onReceiveAnswerSDP(answerSDP: AnswerSDP?) {}
    fun onReceiveIceCandidates(iceCandidates: IceCandidates?) {}
    fun onCallRinging(ringingCall: RingingCall?) {}
    fun onCallCancel(cancelCall: CancelCall?) {}
    fun onRemoteSwitchVideo(mediaSwitch: MediaSwitch?) {}
    fun onUserDeleted() {
        ServiceNavigator.cancelContactSyncService(this)
        ServiceNavigator.cancelChatFileSyncService(this)
        val confirmationDialog = ConfirmationBottomSheetDialogFragment()
        confirmationDialog.setCancelable(true)
        confirmationDialog.setTitle("Account Deleted")
        confirmationDialog.setSubtitle("Your account has been deleted")
        confirmationDialog.setAcceptButtonText("Ok")
        confirmationDialog.setCanceledOnTouchOutside(false)
        confirmationDialog.setCancelable(false)
        confirmationDialog.setHideAllButtons(false)
        confirmationDialog.setHideDeniedOnly(true)
        confirmationDialog.setListener(object : OnActionSelectedListener() {
            fun onAccepted() {
                confirmationDialog.dismissAllowingStateLoss()
                val intent = Intent(this@BaseBindableActivity, SplashActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }

            fun onDenied() {}
        })
        confirmationDialog.show(getSupportFragmentManager(), confirmationDialog.getTag())
    }

    fun onUserBanned() {
        val confirmationDialog = ConfirmationBottomSheetDialogFragment()
        confirmationDialog.setCancelable(true)
        confirmationDialog.setTitle(TranslatorEnum.account_banned_title.getCurrentLocaleValue())
        confirmationDialog.setSubtitle(TranslatorEnum.account_banned_desc.getCurrentLocaleValue())
        confirmationDialog.setAcceptButtonText(TranslatorEnum.ok.getCurrentLocaleValue())
        confirmationDialog.setCanceledOnTouchOutside(false)
        confirmationDialog.setCancelable(false)
        confirmationDialog.setHideAllButtons(false)
        confirmationDialog.setHideDeniedOnly(true)
        confirmationDialog.setListener(object : OnActionSelectedListener() {
            fun onAccepted() {
                confirmationDialog.dismissAllowingStateLoss()
                KothaApp.getInstance().setBanned(false)
                val intent = Intent(this@BaseBindableActivity, SplashActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }

            fun onDenied() {}
        })
        confirmationDialog.show(getSupportFragmentManager(), confirmationDialog.getTag())
    }

    fun onPostCreateBanned() {
        BottomSheetUtils.showForcedDialog(getSupportFragmentManager(),
            getResources().getString(R.string.post_is_forbidden),
            getResources().getString(R.string.post_is_forbidden),
            getResources().getString(R.string.done),
            object : OnActionSelectedListener() {
                fun onAccepted() {
                    if (this@BaseBindableActivity is NewPostActivity) {
                        finish()
                    }
                }

                fun onDenied() {}
            })
    }

    fun onPostCreateLimitReached() {
        BottomSheetUtils.showForcedDialog(getSupportFragmentManager(),
            getResources().getString(R.string.daily_post_limit_reached_title),
            getResources().getString(R.string.daily_post_limit_reached_msg),
            getResources().getString(R.string.done),
            object : OnActionSelectedListener() {
                fun onAccepted() {
                    if (this@BaseBindableActivity is NewPostActivity) {
                        finish()
                    }
                }

                fun onDenied() {}
            })
    }

    fun onConnectionErrorEventListener(isUnauthorized: Boolean) {
        if (isUnauthorized) {
            if (!PrefManager.getPreference()
                    .getPrivateBoolean(PrefKey.KEY_IS_TOKEN_REFRESH_PROCESS_ALREADY_STARTED, false)
            ) {
                authVM.refreshTokenKothaServicesV2()
            } else {
                // Start the checking process when
                handler.postDelayed(trackOngoingTokenRefreshProcess, CHECK_INTERVAL_MS)
            }
        }
    }

    protected fun checkAndSwitchDarkMode() {
        val darkModeOverrideSystemSettings: Boolean = PrefManager.getPreference()
            .getBoolean(getString(R.string.key_app_dark_mode_override_system_settings), false)
        if (darkModeOverrideSystemSettings) {
            val darkModeEnabled: Boolean =
                PrefManager.getPreference().getBoolean(getString(R.string.key_app_dark_mode), false)
            val currentMode: Int = AppCompatDelegate.getDefaultNightMode()
            val targetMode: Int =
                if (darkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            if (currentMode != targetMode) {
                AppCompatDelegate.setDefaultNightMode(targetMode)
            }
        } else {
            val isSystemNightModeActive: Boolean = Utils.isNightModeActive(this)
            PrefManager.getPreference().setPrivateValueAsync(
                getString(R.string.key_app_dark_mode),
                isSystemNightModeActive
            )
            PrefManager.getPreference().setPrivateValueAsync(
                getString(R.string.key_app_dark_mode_override_system_settings),
                false
            )
            val currentMode: Int = AppCompatDelegate.getDefaultNightMode()
            val targetMode: Int
            targetMode = if (isSystemNightModeActive) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            if (currentMode != targetMode) {
                AppCompatDelegate.setDefaultNightMode(targetMode)
            }
        }
    }

    protected fun showUIBlockedProgressLoader() {
        progressLoaderDialog = DialogUtil.showLoaderAlert(this)
    }

    protected fun hideUIBlockedProgressLoader() {
        DialogUtil.hideLoaderAlert(progressLoaderDialog)
    }

    companion object {
        private const val TAG = "BaseBindableActivity"
        protected val toolbarId = 0
            /**
             * Child class can(optional) override this method. On this method you will pass the toolbar id of current layout
             *
             * @return -
             */
            protected get() = Companion.field
        private const val MAX_CHECKS = 5
        private const val CHECK_INTERVAL_MS: Long = 2000 // 2 second
    }
}