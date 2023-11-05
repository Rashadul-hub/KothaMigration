//package com.example.kothamigration.activities
//
//import android.content.Context
//import android.os.Handler
//import android.util.Pair
//import android.view.View
//import com.bs.kotha.R
//import java.util.Locale
//
//class LocalAuthActivity : BaseBindableActivity(), OtpBroadcastReceiver.OtpListener {
//    private var mBinding: ActivityLocalAuthBinding? = null
//    private var authViewModel: AuthV2ViewModel? = null
//    private var provider = 0
//    private val DEFAULT_REMAIN_ATTEMPTS_AFTER_FIRST_TRY = 2
//    private val DEFAULT_COUNTRY_CODE = "BD"
//    private val DEFAULT_PHONE_COUNTRY_CODE = "880"
//    private var givenPhoneNumber: String? = null
//    private var resendCountDownTimer: CountDownTimer? = null
//
//    //private CountDownTimer tokenCountDownTimer;
//    //private boolean isTokenActive = false;
//    private var isFromPhoneEntry = true
//    var otpBroadcastReceiver: OtpBroadcastReceiver? = null
//    override val layoutId: Int
//        //private EditText[] editTexts;
//        protected get() = R.layout.activity_local_auth
//    override val statusBarColor: Int
//        get() = R.color.default_background_color
//
//    override fun startUI(savedInstanceState: Bundle?) {
//        mBinding = viewDataBinding as ActivityLocalAuthBinding?
//        authViewModel = ViewModelProvider(this).get(AuthV2ViewModel::class.java)
//        setUpToolbar()
//        //makeEditTextListWithTextWatcher();
//        setupView(true)
//        setupListener()
//        observeData()
//        if (Utils.isNightModeActive(this)) {
//            mBinding.etAuthPhoneNum.setBackground(
//                ContextCompat.getDrawable(
//                    this,
//                    R.drawable.edittext_border2
//                )
//            )
//            mBinding.inputCode.setBackground(
//                ContextCompat.getDrawable(
//                    this,
//                    R.drawable.edittext_border2
//                )
//            )
//        }
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putInt("provider", provider)
//        outState.putBoolean("isFromPhoneEntry", isFromPhoneEntry)
//        outState.putString("givenPhoneNumber", givenPhoneNumber)
//        //outState.putBoolean("isTokenActive", isTokenActive);
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        provider = savedInstanceState.getInt("provider")
//        isFromPhoneEntry = savedInstanceState.getBoolean("isFromPhoneEntry")
//        givenPhoneNumber = savedInstanceState.getString("givenPhoneNumber")
//        //isTokenActive = savedInstanceState.getBoolean("isTokenActive");
//        // update UI components to reflect the restored state
//        setupView(isFromPhoneEntry)
//    }
//
//    @get:DrawableRes
//    protected val homeIndicator: Int
//        protected get() = R.drawable.ic_navigation_back
//
//    private fun setUpToolbar() {
//        setSupportActionBar(mBinding.layoutToolbar.toolbar)
//        if (supportActionBar != null) {
//            supportActionBar!!.setDisplayShowTitleEnabled(false)
//            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_navigation_back)
//            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        }
//        mBinding.layoutToolbar.tvTitle.setText(getString(R.string.signin))
//        mBinding.layoutToolbar.ivMenu.setVisibility(View.GONE)
//    }
//
//    //    need another time
//    //    private void makeEditTextListWithTextWatcher(){
//    //        editTexts = new EditText[]{mBinding.inputCode1, mBinding.inputCode2, mBinding.inputCode3,
//    //                mBinding.inputCode4, mBinding.inputCode5, mBinding.inputCode6};
//    //
//    //        // Add TextWatcher to each EditText
//    //        for (int i = 0; i < editTexts.length; i++) {
//    //            final int currentIndex = i;
//    //            editTexts[i].addTextChangedListener(new TextWatcher() {
//    //                @Override
//    //                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//    //                }
//    //
//    //                @Override
//    //                public void onTextChanged(CharSequence s, int start, int before, int count) {
//    //                }
//    //
//    //                @Override
//    //                public void afterTextChanged(Editable s) {
//    //                    // Move focus to the next EditText when one character is entered
//    //                    if (s.length() == 1) {
//    //                        if (currentIndex < editTexts.length - 1) {
//    //                            editTexts[currentIndex + 1].requestFocus();
//    //                        } else {
//    //                            // If it's the last EditText, hide the keyboard (optional)
//    //                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//    //                            inputMethodManager.hideSoftInputFromWindow(editTexts[currentIndex].getWindowToken(), 0);
//    //                        }
//    //                    }
//    //                }
//    //            });
//    //        }
//    //    }
//    private fun setupView(isFromPhoneEntry: Boolean) {
//        Utils.hideSoftKeyboard(this)
//        this.isFromPhoneEntry = isFromPhoneEntry
//        if (isFromPhoneEntry) {
//            mBinding.title.setText(getString(R.string.one_time_password))
//            mBinding.subTitle.setText(getString(R.string.phone_number_extend))
//            mBinding.buttonForAction.setText(getString(R.string.get_otp))
//            mBinding.otpVerifyPanel.setVisibility(View.GONE)
//            mBinding.phoneNumberPanel.setVisibility(View.VISIBLE)
//            mBinding.noBangladeshiNumber.setVisibility(View.VISIBLE)
//            toggleEnableAuthPhoneNum(true)
//        } else {
//            // Request focus for inputCode1 and show cursor without the keyboard
////            mBinding.inputCode1.requestFocus();
////            mBinding.inputCode1.setCursorVisible(true);
//            mBinding.title.setText(Html.fromHtml(getString(R.string.otp_verification)))
//            mBinding.subTitle.setText(Html.fromHtml(getString(R.string.enter_otp_sent_to)))
//            mBinding.buttonForAction.setText(getString(R.string.confirm))
//            mBinding.otpVerifyPanel.setVisibility(View.VISIBLE)
//            mBinding.phoneNumberPanel.setVisibility(View.VISIBLE)
//            mBinding.noBangladeshiNumber.setVisibility(View.GONE)
//            toggleEnableAuthPhoneNum(false)
//        }
//    }
//
//    private fun setupResend(tryToSetResendInProgressState: Boolean) {
//        if (tryToSetResendInProgressState) {
//            mBinding.resendBtn.setEnabled(false)
//        } else {
//            if (resendCountDownTimer != null) {
//                resendCountDownTimer.cancel()
//            }
//            mBinding.resendBtn.setText(getString(R.string.resend))
//            mBinding.resendBtn.setEnabled(true)
//        }
//    }
//
//    private fun startCountDown() {
//        if (resendCountDownTimer != null) {
//            resendCountDownTimer.cancel()
//        }
//        setupResend(true)
//        resendCountDownTimer = object : CountDownTimer(60000, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                val seconds = millisUntilFinished / 1000
//                val minutes = seconds / 60
//                mBinding.resendBtn.setText(
//                    getString(
//                        R.string.resend_with_timer, String.format(
//                            Locale.getDefault(), "%02d", minutes
//                        ), String.format(Locale.getDefault(), "%02d", seconds)
//                    )
//                )
//            }
//
//            override fun onFinish() {
//                setupResend(false)
//            }
//        }
//        resendCountDownTimer.start()
//    }
//
//    //    private void startTokenTimer() {
//    //        if (tokenCountDownTimer != null) {
//    //            tokenCountDownTimer.cancel();
//    //        }
//    //
//    //        tokenCountDownTimer = new CountDownTimer(4 * 60 * 1000,
//    //                1000) {
//    //            @Override
//    //            public void onTick(long millisUntilFinished) {
//    //                isTokenActive = true;
//    //            }
//    //
//    //            @Override
//    //            public void onFinish() {
//    //                isTokenActive = false;
//    //            }
//    //        };
//    //
//    //        tokenCountDownTimer.start();
//    //    }
//    private fun setupListener() {
//        mBinding.buttonForAction.setOnClickListener { v ->
//            Utils.hideSoftKeyboard(this@LocalAuthActivity)
//            if (isFromPhoneEntry) {
//                if (validatePhoneNum()) {
//                    givenPhoneNumber = "+" + DEFAULT_PHONE_COUNTRY_CODE + phoneNumber
//                    provider = 0
//                    // start sms retriever only for first try
//                    startSmsRetriever()
//                    sendOTP()
//                    processAttemptsInfo()
//                }
//            } else {
//
////                if (mBinding.inputCode1.getText().toString().trim().isEmpty()
////                        || mBinding.inputCode2.getText().toString().trim().isEmpty()
////                        || mBinding.inputCode3.getText().toString().trim().isEmpty()
////                        || mBinding.inputCode4.getText().toString().trim().isEmpty()
////                        || mBinding.inputCode5.getText().toString().trim().isEmpty()
////                        || mBinding.inputCode6.getText().toString().trim().isEmpty()) {
////                    Toaster.showLong(getString(R.string.otp_can_not_empty));
////                    return;
////                }
////
////                String code = mBinding.inputCode1.getText().toString() +
////                        mBinding.inputCode2.getText().toString() +
////                        mBinding.inputCode3.getText().toString() +
////                        mBinding.inputCode4.getText().toString() +
////                        mBinding.inputCode5.getText().toString() +
////                        mBinding.inputCode6.getText().toString();
//                if (mBinding.inputCode.getText().toString().trim().isEmpty()) {
//                    Toaster.showLong(getString(R.string.otp_can_not_empty))
//                    return@setOnClickListener
//                }
//                val code: String = mBinding.inputCode.getText().toString()
//                authViewModel.verifyOTP(
//                    PrefManager.getPreference().getPrivateString(PrefKey.DEVICE_ID),
//                    givenPhoneNumber,
//                    code
//                )
//            }
//        }
//        mBinding.resendBtn.setOnClickListener { v ->
//            toggleEnableAuthPhoneNum(false)
//            mBinding.inputCode.setText("")
//            if (validatePhoneNum()) {
//                provider = provider + 1
//                val attemptsInfo = processAttemptsInfo()
//                if (attemptsInfo.first >= 0) {
//                    sendOTP()
//                } else {
//                    Toaster.showLong(attemptsInfo.second)
//                }
//            }
//        }
//
////        mBinding.etAuthPhoneNum.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
////                givenPhoneNumber = "+" + DEFAULT_PHONE_COUNTRY_CODE + getPhoneNumber();
////                validatePhoneNum();
////            }
////
////            @Override
////            public void afterTextChanged(Editable editable) {
////
////            }
////        });
//        mBinding.noBangladeshiNumber.setOnClickListener(View.OnClickListener {
//            LoginActivity.loginFrom = LoginActivity.LOGIN_FROM.INTERNATIONAL
//            LoginActivity.startThisActivity(this@LocalAuthActivity)
//        })
//        mBinding.subTitle.setOnClickListener(View.OnClickListener {
//            if (!isFromPhoneEntry) {
//                Utils.sendMail(this@LocalAuthActivity, "info@kotha.app")
//            }
//        })
//    }
//
//    private fun toggleEnableAuthPhoneNum(flag: Boolean) {
//        mBinding.etAuthPhoneNum.setEnabled(flag)
//        if (!flag) {
//            mBinding.etAuthPhoneNum.setBackground(
//                ContextCompat.getDrawable(
//                    this,
//                    R.drawable.shape_solid_round_rect_gray_17_opacity
//                )
//            )
//        } else {
//            mBinding.etAuthPhoneNum.setBackground(
//                ContextCompat.getDrawable(
//                    this,
//                    R.drawable.border
//                )
//            )
//        }
//    }
//
//    private fun processAttemptsInfo(): Pair<Int, String> {
//        var attemptsLeft = DEFAULT_REMAIN_ATTEMPTS_AFTER_FIRST_TRY
//        if (provider > 0) {
//            attemptsLeft = DEFAULT_REMAIN_ATTEMPTS_AFTER_FIRST_TRY - provider
//        }
//        var attemptLeftHumanReadable = attemptsLeft
//        if (attemptLeftHumanReadable < 0) {
//            attemptLeftHumanReadable = 0
//        }
//        val attemptsText = java.lang.String.format(
//            Locale.getDefault(),
//            getString(R.string.attempts_left),
//            NumberUtils.getLocaleValue(Locale.getDefault(), attemptLeftHumanReadable)
//        )
//        mBinding.attemptsLeft.setText(attemptsText)
//        return Pair(attemptsLeft, attemptsText)
//    }
//
//    private fun observeData() {
//        authViewModel.getPhoneNoWhitelisted().observe(this) { isWhitelisted ->
//            hideLoader()
//            if (!isWhitelisted) {
//                mBinding.tilAuthPhone.setError(TranslatorEnum.mobile_no_is_not_whitelisted.getCurrentLocaleValue())
//            }
//        }
//        authViewModel.getOtpSendLiveData().observe(this) { success ->
//            hideLoader()
//            if (success == null) return@observe
//            if (!success) {
//                Toaster.showShort(resources.getString(R.string.failed_otp))
//            } else {
//                setupView(false)
//            }
//        }
//        authViewModel.getOtpSendFailureLiveData().observe(this) { throwable ->
//            hideLoader()
//            if (!authViewModel.handleOtpStatusCode(throwable)) {
//                Toaster.showShort(resources.getString(R.string.unable_otp))
//            }
//            if (resendCountDownTimer != null) {
//                resendCountDownTimer.cancel()
//            }
//        }
//        authViewModel.getIs401Error().observe(this) { is401Error ->
//            if (is401Error) {
//                PrefManager.getPreference().setPrivateValue(PrefKey.DEVICE_TOKEN, "")
//                sendOTP()
//            }
//        }
//        authViewModel.getOtpVerificationLiveData().observe(this) { verified ->
//            hideLoader()
//            if (verified != null && verified) {
//                showLoader()
//                onVerifiedOTP()
//            } else {
//                Toaster.showShort(resources.getString(R.string.invalid_otp_code))
//            }
//        }
//        authViewModel.getOtpVerificationFailureLiveData().observe(this) { throwable ->
//            hideLoader()
//            Toaster.showShort(resources.getString(R.string.cant_verify_otp_code))
//        }
//    }
//
//    private fun sendOTP() {
//        if (Utils.internetCheck(this)) {
//            startCountDown()
//            Utils.hideSoftKeyboard(this)
//            if (TextUtils.isEmpty(PrefManager.getPreference().getPrivateString(PrefKey.DEVICE_ID))
//                || TextUtils.isEmpty(
//                    PrefManager.getPreference().getPrivateString(PrefKey.DEVICE_TOKEN)
//                ) //|| !isTokenActive
//            ) {
//
////                if (!isTokenActive) {
////                    startTokenTimer();
////                }
//                PrefManager.getPreference().setPrivateValue(PrefKey.DEVICE_ID, "")
//                sendKothaOTP()
//            } else {
//                sendKothaOTP()
//            }
//        } else {
//            Toaster.showLong(TranslatorEnum.UnknownHostException.getCurrentLocaleValue())
//        }
//    }
//
//    private fun onVerifiedOTP() {
//        PrefManager.getPreference().setPrivateValue(PrefKey.MY_REG_PHONE_NUMBER, givenPhoneNumber)
//        PrefManager.getPreference()
//            .setPrivateValue(PrefKey.KEY_COUNTRY_CODE, DEFAULT_PHONE_COUNTRY_CODE)
//        val intent = Intent()
//        intent.putExtra(LoginActivity.PHONE_NUM, givenPhoneNumber)
//        setResult(Activity.RESULT_OK, intent)
//        PrefManager.getPreference().setPrivateValue(PrefKey.KEY_AUTO_START_RESTORE, true)
//        finish()
//    }
//
//    private val phoneNumber: String
//        //phone number control
//        private get() = if (mBinding != null && !TextUtils.isEmpty(mBinding.etAuthPhoneNum.getText())) {
//            var `val` =
//                if (mBinding.etAuthPhoneNum.getText() == null) "" else mBinding.etAuthPhoneNum.getText()
//                    .toString()
//            if (`val`.length > 1) {
//                try {
//                    val e164Format: String = Utils.getE164FormattedPhoneNumber(
//                        `val`,
//                        DEFAULT_COUNTRY_CODE
//                    )
//                    `val` = `val`.substring(1)
//                    if (e164Format != null && DEFAULT_PHONE_COUNTRY_CODE + `val` == e164Format.replace(
//                            "+",
//                            ""
//                        )
//                    ) {
//                        `val`
//                    } else {
//                        ""
//                    }
//                } catch (numberParseException: NumberParseException) {
//                    ""
//                }
//            } else {
//                ""
//            }
//        } else {
//            ""
//        }
//
//    private fun validatePhoneNum(): Boolean {
//        val primaryFormat = phoneNumber
//        return if (TextUtils.isEmpty(primaryFormat)) {
//            mBinding.tilAuthPhone.setError(getString(R.string.enter_valid_phone_number))
//            Handler(Looper.getMainLooper())
//                .postDelayed(Runnable { mBinding.tilAuthPhone.setError("") }, 3000)
//            false
//        } else {
//            mBinding.tilAuthPhone.setError("")
//            true
//        }
//    }
//
//    private fun showLoader() {
//        mBinding.buttonForAction.setEnabled(false)
//        mBinding.buttonForAction.setBackground(
//            AppCompatResources.getDrawable(
//                this,
//                R.drawable.shape_solid_rect_f0f1f2
//            )
//        )
//        mBinding.progressBar.setVisibility(View.VISIBLE)
//    }
//
//    private fun hideLoader() {
//        mBinding.buttonForAction.setEnabled(true)
//        mBinding.buttonForAction.setBackground(
//            AppCompatResources.getDrawable(
//                this,
//                R.drawable.shape_solid_rect_primary
//            )
//        )
//        mBinding.progressBar.setVisibility(View.GONE)
//    }
//
//    private fun sendKothaOTP() {
//        showLoader()
//        authViewModel.authenticateWithDeviceIdAndSendOtp(givenPhoneNumber, provider)
//    }
//
//    private fun startSmsRetriever() {
//        val client: SmsRetrieverClient = SmsRetriever.getClient(this /* context */)
//        val task: Task<Void> = client.startSmsRetriever()
//        task.addOnSuccessListener { aVoid ->
//            otpBroadcastReceiver = OtpBroadcastReceiver()
//            otpBroadcastReceiver.setOtpListener(this@LocalAuthActivity)
//            // Register the broadcast receiver
//            registerReceiver(otpBroadcastReceiver, IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION))
//        }
//        task.addOnFailureListener(object : OnFailureListener() {
//            fun onFailure(e: Exception) {
//                e.printStackTrace()
//            }
//        })
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        // Unregister the broadcast receiver when the activity is destroyed
//        if (otpBroadcastReceiver != null) {
//            unregisterReceiver(otpBroadcastReceiver)
//        }
//    }
//
//    fun otp(otp: String?) {
//        setupView(false)
//        mBinding.inputCode.setText(otp)
//        mBinding.buttonForAction.performClick()
//    }
//
//    fun timeout() {}
//    fun wrongFormat() {}
//
//    companion object {
//        fun startThisActivity(context: Context) {
//            val intent = Intent(context, LocalAuthActivity::class.java)
//            context.startActivity(intent)
//        }
//    }
//}