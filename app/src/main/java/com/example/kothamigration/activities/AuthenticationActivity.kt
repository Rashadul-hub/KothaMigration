//package com.bs.kotha.framework.base
//
//import android.os.Build
//import android.os.CountDownTimer
//import android.view.View
//import androidx.fragment.app.Fragment
//import com.example.kothamigration.R
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment
//import java.util.Locale
//import java.util.Objects
//import java.util.concurrent.TimeUnit
//
//class AuthenticationActivity : BaseBindableActivity(), AuthPhoneEntryListener,
//    AuthCountryCodeSelectionAdapter.OnItemSelectedListener, AuthOTPListener {
//    private var mBinding: ActivityAuthenticationLandingBinding? = null
//    private var authViewModel: AuthV2ViewModel? = null
//    private var authPhoneEntryFragment: AuthPhoneEntryFragment? = null
//    private var authPhoneOtpFragment: AuthPhoneOtpFragment? = null
//    private var allCountryCodes: MutableList<CountryCodeModel> = ArrayList<CountryCodeModel>()
//    private var countrySelectionDialog: BottomSheetDialogFragment? = null
//    private var prevSelectedCountry = -1
//    private var firebaseVerificationId: String? = null
//
//    //private boolean verifyKothaOTP = false;
//    private var phoneNum: String? = null
//    private var countryCode: String? = null
//    private var smsProvider: String = Constants.SMS_PROVIDER_FIREBASE
//    private var resendCountDownTimer: CountDownTimer? = null
//    private var tokenCountDownTimer: CountDownTimer? = null
//    private var isTokenActive = false
//    private var verificationInProgress = false
//    private var options: PhoneAuthOptions? = null
//    override val layoutId: Int
//        protected get() = R.layout.activity_authentication_landing
//    override val statusBarColor: Int
//        get() = R.color.default_background_color
//
//    override fun startUI(savedInstanceState: Bundle?) {
//        mBinding = viewDataBinding as ActivityAuthenticationLandingBinding?
//        authViewModel = ViewModelProvider(this).get(AuthV2ViewModel::class.java)
//        setUpToolbar()
//        setupInitialFragments()
//        setupObservers()
//        DevLog.d(TAG, "Firebase getCurrentUser: " + FirebaseAuth.getInstance().getCurrentUser())
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            FirebaseAuth.getInstance().signOut()
//        }
//
//        //load country codes
//        loadAllCountryCodes()
//
//        //start phone entry fragment
//        startFragment(authPhoneEntryFragment, authPhoneEntryFragment.getTag())
//        showCountrySelectionDialog()
//    }
//
//    private fun setUpToolbar() {
//        setSupportActionBar(mBinding.layoutToolbar.toolbar)
//        if (supportActionBar != null) {
//            supportActionBar!!.setDisplayShowTitleEnabled(false)
//            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_navigation_back)
//            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mBinding.layoutToolbar.toolbar.setElevation(Utils.convertDpToPx(this, 0))
//        }
//        mBinding.layoutToolbar.tvTitle.setText("")
//        mBinding.layoutToolbar.ivMenu.setVisibility(View.GONE)
//    }
//
//    private fun setupInitialFragments() {
//        authPhoneEntryFragment = AuthPhoneEntryFragment()
//        authPhoneEntryFragment.setListener(this)
//        authPhoneOtpFragment = AuthPhoneOtpFragment()
//        authPhoneOtpFragment.setListener(this)
//    }
//
//    private fun setupObservers() {
//        authViewModel.getAuthenticateDeviceAdvanceLiveData().observe(this) { objectList ->
//            hideLoader()
//            val phoneNum = objectList.get(0) as String
//            val countryCode = objectList.get(1) as String
//            smsProvider = objectList.get(2)
//            startTokenTimer((4 * 60 * 1000).toLong())
//            controlSentOTP(
//                countryCode, phoneNum //, useFirebase()
//            )
//        }
//        authViewModel.getAuthenticateDeviceFailureLiveData().observe(this) { throwable ->
//            hideLoader()
//            Toaster.showShort(getString(R.string.unable_to_register_device))
//            if (authPhoneEntryFragment != null) {
//                startFragment(authPhoneEntryFragment, authPhoneEntryFragment.getTag())
//            }
//        }
//        authViewModel.getPhoneNoWhitelisted().observe(this) { isWhitelisted ->
//            hideLoader()
//            if (!isWhitelisted) {
//                if (authPhoneEntryFragment != null) {
//                    startFragment(authPhoneEntryFragment, authPhoneEntryFragment.getTag())
//                    authPhoneEntryFragment.setErrorMessage(TranslatorEnum.mobile_no_is_not_whitelisted.getCurrentLocaleValue())
//                }
//            }
//        }
//        authViewModel.getOtpSendLiveData().observe(this) { success ->
//            hideLoader()
//            if (success == null) return@observe
//            if (!success) {
//                Toaster.showShort(getString(R.string.failed_to_send_otp))
//            } else {
//                /*if (authPhoneOtpFragment != null) {
//                    authPhoneOtpFragment.setPhoneNum(phoneNum);
//                    startFragment(authPhoneOtpFragment, authPhoneOtpFragment.getTag());
//                }*/
//            }
//        }
//        authViewModel.getOtpVerificationLiveData().observe(this) { verified ->
//            hideLoader()
//            if (verified != null && verified) {
//                onVerifiedOTP(countryCode, phoneNum)
//            } else {
//                if (authPhoneOtpFragment != null) {
//                    authPhoneOtpFragment.setError("Invalid OTP code")
//                }
//            }
//        }
//        authViewModel.getOtpSendFailureLiveData().observe(this) { throwable ->
//            hideLoader()
//            if (!authViewModel.handleOtpStatusCode(throwable)) {
//                Toaster.showShort(getString(R.string.unable_to_send_otp))
//            }
//            if (resendCountDownTimer != null) {
//                resendCountDownTimer.cancel()
//            }
//            if (authPhoneOtpFragment != null) {
//                authPhoneOtpFragment.setTimer(resources.getString(R.string.resend))
//                authPhoneOtpFragment.resendToggle(true)
//            }
//        }
//        authViewModel.getOtpVerificationFailureLiveData().observe(this) { throwable ->
//            hideLoader()
//            Toaster.showShort(getString(R.string.cant_verify_otp))
//        }
//    }
//
//    private fun startFragment(fragment: Fragment, tag: String) {
//        val transaction = supportFragmentManager.beginTransaction()
//        if (supportFragmentManager.fragments.size > 0) {
//            transaction.setCustomAnimations(
//                R.anim.fragment_slide_in_right,
//                R.anim.fragment_slide_out_left,
//                R.anim.fragment_slide_in_left,
//                R.anim.fragment_slide_out_right
//            )
//        }
//        transaction.replace(R.id.flPhoneAuthContainer, fragment, tag)
//        if (supportFragmentManager.fragments.size > 0) {
//            transaction.addToBackStack(tag)
//        }
//        transaction.commit()
//    }
//
//    private fun loadAllCountryCodes() {
//        showLoader()
//        allCountryCodes = Utils.readCountryCodeJSONAsList(this, true)
//        for (index in allCountryCodes.indices) {
//            val countryCodeModel: CountryCodeModel = allCountryCodes[index]
//            if (TextUtils.equals(Utils.getCallingCodes(countryCodeModel), REMOVE_COUNTRY_CODE)) {
//                allCountryCodes.remove(countryCodeModel)
//            }
//        }
//        DevLog.d(TAG, "country code size: " + allCountryCodes.size)
//        var indx = 0
//        for (countryCodeModel in allCountryCodes) {
//            if (TextUtils.equals(Utils.getCallingCodes(countryCodeModel), DEFAULT_COUNTRY_CODE)) {
//                prevSelectedCountry = indx
//                countryCodeModel.setSelected(true)
//                if (authPhoneEntryFragment != null) {
//                    authPhoneEntryFragment.setSelectedCountryCodeModel(countryCodeModel)
//                }
//                break
//            }
//            indx++
//        }
//        hideLoader()
//    }
//
//    fun onPhoneNumberGiven(countryCode: String, phone: String) {
//        DevLog.d(TAG, "given phone num: $phone", true)
//        phoneNum = phone
//        this.countryCode = countryCode
//        Utils.hideSoftKeyboard(this)
//        BottomSheetUtils.showConfirmationDialog(
//            supportFragmentManager, String.format(getString(R.string.otp_send_popup_title), phone),
//            getString(R.string.otp_send_popup_desc),
//            getString(R.string.confirm),
//            getString(R.string.cancel),
//            true,
//            true,
//            object : OnActionSelectedListener() {
//                fun onAccepted() {
//                    sendOTP(
//                        countryCode, phone //, useFirebase()
//                    )
//                }
//
//                fun onDenied() {}
//            })
//    }
//
//    override fun onClick(view: View) {
//        super.onClick(view)
//        when (view.id) {
//            R.id.tvAuthPhoneCountryCode -> showCountrySelectionDialog()
//        }
//    }
//
//    private fun showLoader() {
//        if (mBinding != null) {
//            mBinding.viewLoader.getRoot().setVisibility(View.VISIBLE)
//        }
//    }
//
//    private fun hideLoader() {
//        if (mBinding != null) {
//            mBinding.viewLoader.getRoot().setVisibility(View.GONE)
//        }
//    }
//
//    private fun showCountrySelectionDialog() {
//        Utils.hideSoftKeyboard(this)
//        val countryCodeSelectionAdapter = AuthCountryCodeSelectionAdapter()
//        countryCodeSelectionAdapter.setCountryCodeModelList(allCountryCodes)
//        countryCodeSelectionAdapter.setListener(this)
//        countrySelectionDialog = BottomSheetUtils.showListWithBottomSheet(
//            supportFragmentManager,
//            countryCodeSelectionAdapter,
//            resources.getString(R.string.choose_country),
//            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false),
//            false,
//            true,
//            null
//        )
//    }
//
//    private fun useFirebase(): Boolean {
//        return TextUtils.equals(smsProvider, Constants.SMS_PROVIDER_FIREBASE)
//    }
//
//    private fun sendOTP(
//        countryCode: String, phoneNum: String //, boolean useFirebase
//    ) {
//        if (Utils.internetCheck(this)) {
//            startCountDown()
//            Utils.hideSoftKeyboard(this)
//            if (TextUtils.isEmpty(PrefManager.getPreference().getPrivateString(PrefKey.DEVICE_ID))
//                || TextUtils.isEmpty(
//                    PrefManager.getPreference().getPrivateString(PrefKey.DEVICE_TOKEN)
//                )
//                || !isTokenActive
//            ) {
//                showLoader()
//                authViewModel.authenticateDeviceId(
//                    PrefManager.getPreference().getPrivateString(PrefKey.DEVICE_ID),
//                    phoneNum,
//                    countryCode
//                )
//            } else {
//                controlSentOTP(
//                    countryCode, phoneNum //, useFirebase
//                )
//            }
//        } else {
//            Toaster.showLong(TranslatorEnum.UnknownHostException.getCurrentLocaleValue())
//        }
//    }
//
//    private fun controlSentOTP(
//        countryCode: String, phoneNum: String //, boolean useFirebase
//    ) {
//
//        //if (useFirebase) {
//        sendFirebaseOTP(countryCode, phoneNum)
//        if (authPhoneOtpFragment != null) {
//            authPhoneOtpFragment.setPhoneNum(phoneNum)
//            authPhoneOtpFragment.setCountryCode(countryCode)
//        }
//
//        //}
////        else {
////            sendKothaOTP();
////        }
//        if (authPhoneOtpFragment != null) {
//            authPhoneOtpFragment.setPhoneNum(phoneNum)
//            authPhoneOtpFragment.setCountryCode(countryCode)
//            startFragment(authPhoneOtpFragment, authPhoneOtpFragment.getTag())
//        }
//    }
//
//    private fun startCountDown() {
//        if (resendCountDownTimer != null) {
//            resendCountDownTimer.cancel()
//        }
//        resendCountDownTimer =
//            object : CountDownTimer(RESEND_COUNT_DOWN_TIMER, RESEND_COUNT_DOWN_INTERVAL_TIMER) {
//                override fun onTick(millisUntilFinished: Long) {
//                    val seconds = millisUntilFinished / 1000
//                    val minutes = seconds / 60
//                    if (authPhoneOtpFragment != null) {
//                        authPhoneOtpFragment.setTimer(
//                            resources.getString(
//                                R.string.resend_with_timer, String.format(
//                                    Locale.getDefault(), "%02d", minutes
//                                ), String.format(Locale.getDefault(), "%02d", seconds)
//                            )
//                        )
//                    }
//                }
//
//                override fun onFinish() {
//                    if (authPhoneOtpFragment != null) {
//                        authPhoneOtpFragment.setTimer(resources.getString(R.string.resend))
//                        authPhoneOtpFragment.resendToggle(true)
//                    }
//                }
//            }
//        resendCountDownTimer.start()
//        if (authPhoneOtpFragment != null) {
//            authPhoneOtpFragment.resendToggle(false)
//        }
//    }
//
//    private fun startTokenTimer(durationInMillis: Long) {
//        if (tokenCountDownTimer != null) {
//            tokenCountDownTimer.cancel()
//        }
//        tokenCountDownTimer = object : CountDownTimer(durationInMillis, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                isTokenActive = true
//            }
//
//            override fun onFinish() {
//                isTokenActive = false
//            }
//        }
//        tokenCountDownTimer.start()
//    }
//
//    //    private void sendKothaOTP(){
//    //        verifyKothaOTP = true;
//    //        showLoader();
//    //        authViewModel.sendOTP(PrefManager.getPreference().getPrivateString(PrefKey.DEVICE_ID), phoneNum);
//    //    }
//    private fun sendFirebaseOTP(countryCode: String, phoneNum: String) {
//
//        //verifyKothaOTP = false;
//        showLoader()
//        val callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
//            object : OnVerificationStateChangedCallbacks() {
//                fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
//                    hideLoader()
//                    if (authPhoneOtpFragment != null) {
//                        authPhoneOtpFragment.setOTP(phoneAuthCredential.getSmsCode())
//                    }
//                    showLoader()
//                    verifyFirebaseOtp(phoneAuthCredential, countryCode, phoneNum)
//
//                    // Clear the flag
//                    verificationInProgress = false
//                }
//
//                fun onVerificationFailed(e: FirebaseException) {
//                    e.printStackTrace()
//                    FirebaseCrashlytics.getInstance().recordException(e)
//                    hideLoader()
//                    if (e is FirebaseTooManyRequestsException) {
//                        Toaster.showShort(getString(R.string.please_try_again))
//                        if (resendCountDownTimer != null) {
//                            resendCountDownTimer.cancel()
//                        }
//                        if (authPhoneOtpFragment != null) {
//                            authPhoneOtpFragment.setTimer(resources.getString(R.string.resend))
//                            authPhoneOtpFragment.resendToggle(true)
//                        }
//                    } else {
//                        if (!TextUtils.isEmpty(e.getLocalizedMessage()) && Objects.requireNonNull(e.getLocalizedMessage())
//                                .contains("The format of the phone number provided is incorrect")
//                        ) {
//                            authPhoneEntryFragment.setErrorMessage("Please enter valid phone number")
//                        } else {
//                            Toaster.showShort(e.getMessage())
//                        }
//                    }
//                    e.printStackTrace()
//
//                    // Clear the flag
//                    verificationInProgress = false
//                }
//
//                fun onCodeSent(
//                    s: String,
//                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
//                ) {
//                    super.onCodeSent(s, forceResendingToken)
//                    hideLoader()
//                    firebaseVerificationId = s
//                }
//
//                fun onCodeAutoRetrievalTimeOut(s: String?) {
//                    super.onCodeAutoRetrievalTimeOut(s)
//                    hideLoader()
//                }
//            }
//        if (!verificationInProgress) {
//            verificationInProgress = true
//            options = PhoneAuthOptions.newBuilder()
//                .setPhoneNumber(phoneNum) // Phone number to verify
//                .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
//                .setActivity(this) // Activity (for callback binding)
//                .setCallbacks(callbacks)
//                .build()
//            PhoneAuthProvider.verifyPhoneNumber(options)
//        }
//    }
//
//    //    private void verifyKothaOtp(String phoneNum, String otp) {
//    //
//    //        showLoader();
//    //
//    //        authViewModel.verifyOTP(PrefManager.getPreference().getPrivateString(PrefKey.DEVICE_ID), phoneNum, otp);
//    //
//    //    }
//    private fun verifyFirebaseOtp(
//        credential: PhoneAuthCredential,
//        countryCode: String,
//        phoneNum: String
//    ) {
//        FirebaseAuth.getInstance().signInWithCredential(credential)
//            .addOnCompleteListener { task ->
//                hideLoader()
//                if (task.isSuccessful()) {
//                    Toaster.showShort(getString(R.string.otp_verified))
//                    onVerifiedOTP(countryCode, phoneNum)
//                } else if (task.isCanceled()) {
//                    Toaster.showShort(getString(R.string.canceled))
//                } else if (task.isComplete()) {
//                    //Toaster.showShort("Complete");
//                }
//            }
//            .addOnCanceledListener {
//                hideLoader()
//                Toaster.showShort(getString(R.string.canceled))
//            }
//            .addOnFailureListener { e ->
//                hideLoader()
//                e.printStackTrace()
//            }
//    }
//
//    fun onCountryItemSelected(countryCodeModel: CountryCodeModel?, pos: Int) {
//        DevLog.d(TAG, "prevSelectedCountry: $prevSelectedCountry, pos: $pos", true)
//        if (countrySelectionDialog != null) {
//            countrySelectionDialog.dismissAllowingStateLoss()
//        }
//        if (authPhoneEntryFragment != null) {
//            authPhoneEntryFragment.setSelectedCountryCodeModel(countryCodeModel)
//        }
//        if (prevSelectedCountry != -1 && prevSelectedCountry != pos) {
//            allCountryCodes[prevSelectedCountry].setSelected(false)
//        }
//        prevSelectedCountry = pos
//        allCountryCodes[pos].setSelected(true)
//    }
//
//    private fun onVerifiedOTP(countryCode: String?, phoneNum: String?) {
//        PrefManager.getPreference().setPrivateValue(PrefKey.MY_REG_PHONE_NUMBER, phoneNum)
//        PrefManager.getPreference().setPrivateValue(PrefKey.KEY_COUNTRY_CODE, countryCode)
//        val intent = Intent()
//        intent.putExtra(LoginActivity.PHONE_NUM, phoneNum)
//        setResult(Activity.RESULT_OK, intent)
//        PrefManager.getPreference().setPrivateValue(PrefKey.KEY_AUTO_START_RESTORE, true)
//        finish()
//    }
//
//    fun onChangePhoneNum() {
//        onBackPressed()
//    }
//
//    fun onOtpResend(countryCode: String, phoneNum: String) {
//        sendOTP(
//            countryCode, phoneNum //, false
//        )
//    }
//
//    fun onOtpVerify(countryCode: String, phoneNum: String, otp: String?) {
//        Utils.hideSoftKeyboard(this)
//
////        if (verifyKothaOTP) {
////            verifyKothaOtp(phoneNum, otp);
////        } else {
//        try {
//            val phoneAuthCredential: PhoneAuthCredential =
//                PhoneAuthProvider.getCredential(firebaseVerificationId, otp)
//            verifyFirebaseOtp(phoneAuthCredential, countryCode, phoneNum)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Toaster.showShort(getString(R.string.unable_to_verify_otp))
//            if (resendCountDownTimer != null) {
//                resendCountDownTimer.cancel()
//            }
//            if (authPhoneOtpFragment != null) {
//                authPhoneOtpFragment.setTimer(resources.getString(R.string.resend))
//                authPhoneOtpFragment.resendToggle(true)
//            }
//        }
//        //}
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//
//        // Save the flag
//        outState.putBoolean("verificationInProgress", verificationInProgress)
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//
//        // Restore the flag
//        verificationInProgress = savedInstanceState.getBoolean("verificationInProgress")
//    }
//
//    override fun onStart() {
//        super.onStart()
//
//        // If verification is already in progress, call verifyPhoneNumber again
//        if (verificationInProgress && options != null) {
//            PhoneAuthProvider.verifyPhoneNumber(options)
//        }
//    }
//
//    companion object {
//        private const val TAG = "AuthenticationActivity"
//        private const val REMOVE_COUNTRY_CODE = "880"
//        private const val DEFAULT_COUNTRY_CODE = "91"
//        private const val RESEND_COUNT_DOWN_TIMER: Long = 60000
//        private const val RESEND_COUNT_DOWN_INTERVAL_TIMER: Long = 1000
//    }
//}