//package com.bs.kotha.framework.base
//
//import android.content.Context
//import android.view.View
//import android.view.animation.Animation
//import android.view.animation.AnimationUtils
//import com.bs.kotha.R
//import android.content.Intent
//import android.os.Build
//import android.os.Bundle
//import android.text.SpannableString
//import android.text.Spanned
//import android.text.TextUtils
//import android.text.method.LinkMovementMethod
//import android.text.style.ForegroundColorSpan
//import android.text.style.StyleSpan
//import androidx.annotation.IntDef
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.ViewModelProvider
//import com.bs.kotha.R
//import com.bs.kotha.application.contact.viewmodel.BlockUserViewModel
//import com.bs.kotha.application.profile.viewmodel.FollowersViewModel
//import com.bs.kotha.databinding.ActivityLoginBinding
//import com.bs.kotha.framework.DevLog
//import com.bs.kotha.framework.base.BaseKothaInitialActivity
//import com.bs.kotha.framework.notification.NotificationUtils
//import com.bs.kotha.framework.preference.PrefKey
//import com.bs.kotha.framework.preference.PrefManager
//import com.bs.kotha.framework.util.KothaFontSetting
//import com.bs.kotha.framework.util.ui.Navigator
//import com.bs.kotha.framework.view_utils.PlainClickableSpan
//import java.lang.annotation.Retention
//
//
//class LoginActivity : BaseKothaInitialActivity(), View.OnClickListener {
//    @IntDef([LOGIN_FROM.LOCAL, LOGIN_FROM.INTERNATIONAL])
//    @Retention(AnnotationRetention.SOURCE)
//    annotation class LOGIN_FROM {
//        companion object {
//            var LOCAL = 1
//            var INTERNATIONAL = 2
//        }
//    }
//
//    private val TAG = "LoginActivity"
//    private var loginBinding: ActivityLoginBinding? = null
//    private var followersVM: FollowersViewModel? = null
//    private var blockUserVM: BlockUserViewModel? = null
//    private var onOffAnimation: Animation? = null
//    private var isShowingLoadingAnim = false
//    protected val layoutId: Int
//        protected get() = R.layout.activity_login
//
//    protected fun startUI(savedInstanceState: Bundle?) {
//        onOffAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_on_off)
//        onOffAnimation.setRepeatCount(Animation.INFINITE)
//        loginBinding = getViewDataBinding() as ActivityLoginBinding?
//        setupViewModels()
//        setupViews()
//        observeData()
//        initiateLoginProcess()
//    }
//
//    val statusBarColor: Int
//        get() = R.color.default_background_color
//
//    private fun observeData() {
//        authVM.getAllLoginDataSuccessLiveData().observe(this) { success ->
//            DevLog.d(TAG, "AllLoginDataSucccess: $success", true)
//            if (!isShowingLoadingAnim) showLoginProgress()
//            followersVM.loadBiFollowerList()
//        }
//        followersVM.getLoadBiFollowersLiveData().observe(this) { success ->
//            DevLog.d(TAG, "LoadBiFollowers: $success", true)
//            if (!isShowingLoadingAnim) showLoginProgress()
//            if (Build.VERSION.SDK_INT >= 24 && !NotificationUtils.areNotificationPermissionEnable()) {
//                NotificationPermissionActivity.startThisActivity(this)
//            } else {
//                if (!PrefManager.getPreference()
//                        .getPrivateBoolean(PrefKey.FLAG_ONBOARD_PROFILE_UPDATED, false)
//                ) {
//                    Navigator.startOnBoardActivity(this, true)
//                } else {
//                    Navigator.startDashboardActivity(this, false)
//                }
//            }
//            finish()
//        }
//        authVM.getPhoneNoWhitelisted().observe(this) { success ->
//            if (success) {
//                startOTPLogin()
//            }
//        }
//    }
//
//    private fun showLoginProgress() {
//        runOnUiThread {
//            loginBinding.tvPrivacyPolicyMsg.setVisibility(View.GONE)
//            loginBinding.tvLoginWithPhoneNumber.setVisibility(View.GONE)
//            loginBinding.ivKothaLogo.startAnimation(onOffAnimation)
//            isShowingLoadingAnim = true
//        }
//    }
//
//    private fun hideLoginProgress() {
//        runOnUiThread {
//            loginBinding.ivKothaLogo.clearAnimation()
//            onOffAnimation!!.cancel()
//            onOffAnimation!!.reset()
//            loginBinding.tvPrivacyPolicyMsg.setVisibility(View.VISIBLE)
//            loginBinding.tvLoginWithPhoneNumber.setVisibility(View.VISIBLE)
//            isShowingLoadingAnim = false
//            if (loginFrom == LOGIN_FROM.INTERNATIONAL) {
//                startOTPLogin()
//            }
//        }
//    }
//
//    override fun onClick(view: View) {
//        super.onClick(view)
//        when (view.id) {
//            R.id.tvLoginWithPhoneNumber -> startOTPLogin()
//        }
//    }
//
//    private fun initiateLoginProcess() {
//        //showLoginProgress();
//        //authVM.loadAccountKitData();
//        if (authVM.isUserSignedIn()) {
//            authVM.startLogin()
//        } else {
//            hideLoginProgress()
//        }
//    }
//
//    private fun startOTPLogin() {
//        if (loginFrom == LOGIN_FROM.LOCAL) {
//            val intent = Intent(this, LocalAuthActivity::class.java)
//            startActivityForResult(intent, AUTHENTICATION_REQUEST_CODE)
//        } else if (loginFrom == LOGIN_FROM.INTERNATIONAL) {
//            val intent = Intent(this, AuthenticationActivity::class.java)
//            startActivityForResult(intent, AUTHENTICATION_REQUEST_CODE)
//        }
//
//        //Back to default
//        loginFrom = LOGIN_FROM.LOCAL
//    }
//
//    /*private void initiatePhoneLogin() {
//        Intent intent = new Intent(getApplicationContext(), AccountKitActivity.class);
//        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder = new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE, AccountKitActivity.ResponseType.TOKEN);
//        configurationBuilder.setFacebookNotificationsEnabled(true);
////        configurationBuilder.setVoiceCallbackNotificationsEnabled(true);
//        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
//        startActivityForResult(intent, APP_REQUEST_CODE);
//    }*/
//    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            AUTHENTICATION_REQUEST_CODE -> if (data != null) {
//                if (!TextUtils.isEmpty(data.getStringExtra(PHONE_NUM))) {
//                    showLoginProgress()
//                    authVM.startLogin()
//                } else {
//                    hideLoginProgress()
//                }
//            } else {
//                hideLoginProgress()
//            }
//        }
//    }
//
//    protected fun onDestroy() {
//        super.onDestroy()
//        loginBinding.ivKothaLogo.clearAnimation()
//        onOffAnimation!!.cancel()
//        onOffAnimation!!.reset()
//    }
//
//    private fun setupViewModels() {
//        followersVM = ViewModelProvider(this).get(FollowersViewModel::class.java)
//        blockUserVM = ViewModelProvider(this).get(BlockUserViewModel::class.java)
//    }
//
//    private fun setupViews() {
//        loginBinding.tvPrivacyPolicyMsg.setMovementMethod(LinkMovementMethod.getInstance())
//        loginBinding.tvPrivacyPolicyMsg.setOnLongClickListener { v -> true }
//        loginBinding.tvPrivacyPolicyMsg.setText(formattedPrivacyPolicyMsg)
//    }
//
//    private val formattedPrivacyPolicyMsg: SpannableString
//        private get() {
//            val privacyPolicyMessage: String = getString(R.string.privacy_policy_msg)
//            val length_start_privacy: Int =
//                privacyPolicyMessage.indexOf(getString(R.string.privacy_policy))
//            val length_end_privacy: Int =
//                length_start_privacy + getString(R.string.privacy_policy).length()
//            val length_start_terms: Int =
//                privacyPolicyMessage.indexOf(getString(R.string.terms_of_service))
//            val length_end_terms: Int =
//                length_start_terms + getString(R.string.terms_of_service).length()
//            val ss = SpannableString(getString(R.string.privacy_policy_msg))
//            try {
//                ss.setSpan(
//                    ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)),
//                    length_start_privacy,
//                    length_end_privacy,
//                    Spanned.SPAN_INCLUSIVE_INCLUSIVE
//                )
//                ss.setSpan(
//                    StyleSpan(KothaFontSetting.getInstance().getBoldTypeFace().getStyle()),
//                    length_start_privacy,
//                    length_end_privacy,
//                    Spanned.SPAN_INCLUSIVE_INCLUSIVE
//                )
//                ss.setSpan(
//                    PlainClickableSpan(this, "https://kotha.app/policy.html"),
//                    length_start_privacy,
//                    length_end_privacy,
//                    Spanned.SPAN_INCLUSIVE_INCLUSIVE
//                )
//                ss.setSpan(
//                    ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)),
//                    length_start_terms,
//                    length_end_terms,
//                    Spanned.SPAN_INCLUSIVE_INCLUSIVE
//                )
//                ss.setSpan(
//                    StyleSpan(KothaFontSetting.getInstance().getBoldTypeFace().getStyle()),
//                    length_start_terms,
//                    length_end_terms,
//                    Spanned.SPAN_INCLUSIVE_INCLUSIVE
//                )
//                ss.setSpan(
//                    PlainClickableSpan(this, "https://kotha.app/policy.html"),
//                    length_start_terms,
//                    length_end_terms,
//                    Spanned.SPAN_INCLUSIVE_INCLUSIVE
//                )
//            } catch (indexOutOfBoundsException: IndexOutOfBoundsException) {
//                indexOutOfBoundsException.printStackTrace()
//            }
//            return ss
//        }
//
//    companion object {
//        var loginFrom = LOGIN_FROM.LOCAL
//        fun startThisActivity(context: Context) {
//            val intent = Intent(context, LoginActivity::class.java)
//            context.startActivity(intent)
//        }
//
//        const val AUTHENTICATION_REQUEST_CODE = 223
//        const val PHONE_NUM = "auth_phone_num_kotha"
//    }
//}