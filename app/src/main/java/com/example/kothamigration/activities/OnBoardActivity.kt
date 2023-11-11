package com.bs.kotha.framework.base

import android.Manifest
import android.R
import android.content.Context
import android.net.Uri
import android.util.Pair
import android.view.MenuItem
import android.view.View
import com.bs.kotha.BuildConfig
import java.io.File
import java.util.concurrent.TimeUnit

class OnBoardActivity : BaseKothaInitialActivity(), View.OnClickListener,
    UserProfileBottomSheetActionCallback {
    //private MutableLiveData<Boolean> registrationLiveData;
    //private UserProfileViewModel mViewModel;
    private var registrationViewModel: PersonalProfileRegistrationViewModel? = null
    private var s3UploadViewModel: S3UploadViewModel? = null
    private var mBinding: ActivityOnBoardPageBinding? = null

    //protected MenuItem menuItemDone;
    protected var namePublisher: PublishSubject<String>? = null
    private var userProfileBottomSheet: UserProfileBottomSheetDialogFragment? = null

    //protected RealmProfile clonedProfile;
    private var cameraIntentImageUri: Uri? = null
    private var proPic: String? = null
    private var isDoneClicked = false
    private var isFetchedFromServer = false
    protected val layoutId: Int
        protected get() = R.layout.activity_on_board_page
    private val nameChangeObserver: Observer<String> = object : Observer<String?>() {
        fun onSubscribe(d: Disposable) {}
        fun onNext(s: String) {
            if (!validateName(s)) {
                setErrorMessage(getResources().getString(R.string.name_should_not_be_empty))
            } else {
                setErrorMessage("")
            }
        }

        fun onError(e: Throwable) {
            e.printStackTrace()
        }

        fun onComplete() {}
    }

    private fun fetchUserProfileInfo() {
        setProgressGroupVisibility(true)
        registrationViewModel.getUserProfileInfoFromServer()
    }

    private fun setProgressGroupVisibility(uploading: Boolean) {
        if (isDoneClicked && !uploading) return
        mBinding.groupProgress.setVisibility(if (uploading) View.VISIBLE else View.GONE)
    }

    protected fun setUpToolbar() {
        setSupportActionBar(mBinding.toolbar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true)
        }
        ExtensionsKt.setInputAdjustResize(this, mBinding.getRoot(), true)
    }

    private fun bindNameEditorChangeListener() {
        mBinding.tvProfileName.addTextChangedListener(nameWatcher)
        mBinding.tvProfileName.setFilters(
            arrayOf<InputFilter>(
                TextOnlyInputFilter(true, true),
                LengthFilter(70)
            )
        )
        Utils.showSoftKeyboard(mBinding.tvProfileName)
    }

    private fun setupListeners() {
        mBinding.ivDone.setOnClickListener(this)
    }

    protected fun startUI(savedInstanceState: Bundle?) {
        mBinding = getViewDataBinding() as ActivityOnBoardPageBinding?
        //mViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);
        registrationViewModel =
            ViewModelProvider(this).get(PersonalProfileRegistrationViewModel::class.java)
        s3UploadViewModel = ViewModelProvider(this).get(S3UploadViewModel::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBinding.ivDone.setElevation(Utils.convertDpToPx(this, 5))
        }
        observeData()
        setUpToolbar()
        setupListeners()
        bindNameEditorChangeListener()
        if (PrefManager.getPreference()
                .getPrivateBoolean(PrefKey.FLAG_REG_COMPLETE, false) && PrefManager.getPreference()
                .getPrivateBoolean(PrefKey.FLAG_ONBOARD_PROFILE_UPDATED, false)
        ) {
            Navigator.startDashboardActivity(this, true)
            finish()
            return
        }
        val selectedProfile: RealmProfile = RealmProfileController.getSelectedProfile()
        if (selectedProfile != null && selectedProfile.isBusiness()) {
            Navigator.startDashboardActivity(this, true)
            finish()
            return
        }
        if (getIntent().getBooleanExtra(KEY_IS_FIRST_LAUNCH, false)) {
            //ContactSyncHelper.getInstance().checkAndStartContactSyncOperation(this, true, true);
            ServiceNavigator.startContactSyncServiceWithPermission(
                getSupportFragmentManager(),
                this,
                ContactSyncService.ContactSyncType.FULL_SYNC
            )
        }
    }

    private fun onUploadedToS3(s3UploadDataBooleanPair: Pair<S3UploadData, Boolean>) {
        setProgressGroupVisibility(false)
        val s3UploadData: S3UploadData? = s3UploadDataBooleanPair.first
        val isSuccess = s3UploadDataBooleanPair.second
        if (s3UploadData != null && isSuccess) {
            if (s3UploadData.getMediaUris().size() > 0) {
                //clonedProfile.setImageUrl(s3UploadData.getMediaUris().get(0).getUrl());
                proPic = s3UploadData.getMediaUris().get(0).getUrl()
                showProfileImage(proPic)
            }
        } else {
            Toaster.showShort(getString(R.string.upload_failed))
        }
    }

    private fun updateView(profile: RealmProfile?) {

        //clonedProfile = profile;
        if (!isDoneClicked) {
            Utils.showSoftKeyboard(mBinding.tvProfileName)
        }
        if (profile == null || !profile.isValid()) {
            return
        }
        showProfileImage(profile.getImageUrl())
        if (!TextUtils.isEmpty(profile.getName())) {
            mBinding.tvProfileName.setText(profile.getName())
            mBinding.tvProfileName.setSelection(mBinding.tvProfileName.getText().length())
        }
        mBinding.tvProfileName.requestFocus()
    }

    private fun showProfileImage(profileImage: String?) {
        GlideUtils.setImageFromUrl(
            mBinding.civUserProfileImage,
            profileImage,
            R.drawable.ic_social_user_avatar_placeholder_circle
        )
    }

    private fun observeData() {
        registrationViewModel.getUpdatingStatusMutableLiveData()
            .observe(this) { uploading: Boolean -> setProgressGroupVisibility(uploading) }
        registrationViewModel.getProfileChangeMutableLiveData().observe(this) { realmProfile ->
            if (realmProfile == null) return@observe
            if (!isDoneClicked) {
                setProgressGroupVisibility(false)
            }
            if (!isFetchedFromServer) {
                isFetchedFromServer = true
                fetchUserProfileInfo()
            }
            updateView(realmProfile)
        }
        registrationViewModel.getRegistrationCompleteLiveData().observe(this) { success ->
            if (success != null) {
                isDoneClicked = success
                if (success) {
                    Navigator.startDashboardActivity(this, true)
                    finish()
                } else {
                    setProgressGroupVisibility(false)
                }
            }
        }
        s3UploadViewModel.getS3UploadDataLiveData()
            .observe(this) { s3UploadDataBooleanPair: Pair<S3UploadData, Boolean> ->
                onUploadedToS3(s3UploadDataBooleanPair)
            }

//        mViewModel.loadUserProfile();
        registrationViewModel.loadUserProfile()
    }

    private val nameWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (namePublisher == null) {
                namePublisher = PublishSubject.create()
                namePublisher.debounce(300, TimeUnit.MILLISECONDS)
                    .distinctUntilChanged()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(nameChangeObserver)
            } else {
                namePublisher.onNext(s.toString())
            }
            if (!TextUtils.isEmpty(s)) {
                mBinding.tvProfileName.setSelection(mBinding.tvProfileName.getSelectionEnd())
            }
        }
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.home) {
            onBackPressed()
            return true
        } else if (itemId == R.id.menuItemSave) {
            if (!validateName(mBinding.tvProfileName.getText())) {
                setErrorMessage(getResources().getString(R.string.name_should_not_be_empty))
            } else {
                Utils.hideSoftKeyboard(this)
                setErrorMessage("")
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View) {
        super.onClick(view)
        val id = view.id
        if (id == R.id.civUserProfileImage || id == R.id.tvChangeProPic) {
            Utils.hideSoftKeyboard(this)
            showBottomSheet()
        } else if (id == R.id.ivDone) {
            if (TextUtils.isEmpty(mBinding.tvProfileName.getText())) {
                Toaster.showShort(getResources().getString(R.string.name_should_not_be_empty))
            } else {
                Utils.hideSoftKeyboard(this)
                doneActionUponSelection()
            }
        }
    }

    private fun doneActionUponSelection() {
        isDoneClicked = true
        val name: String = mBinding.tvProfileName.getText().toString()
        if (validateName(name)) {
            if (validHttpsUrl(proPic)) {
                registrationViewModel.updateNameProPic(name, proPic)
            } else {
                registrationViewModel.updateName(name)
            }
        } else {
            setErrorMessage(getResources().getString(R.string.name_should_not_be_empty))
        }
    }

    private fun validHttpsUrl(url: String?): Boolean {
        return !TextUtils.isEmpty(url) && url!!.startsWith("https")
    }

    fun setErrorMessage(msg: String?) {
        if (mBinding != null) {
            mBinding.tilUserName.setError(msg)
            mBinding.tilUserName.setErrorEnabled(!TextUtils.isEmpty(msg))
        }
    }

    private fun validateName(value: String?): Boolean {
        return value != null && !TextUtils.isEmpty(value.trim { it <= ' ' })
    }

    private fun validateName(value: Editable?): Boolean {
        return value != null && !TextUtils.isEmpty(value.toString().trim { it <= ' ' })
    }

    private fun showBottomSheet() {
        val typedArray: TypedArray =
            getResources().obtainTypedArray(R.array.pick_photo_action_icon_list_32dp)
        val actionIcons = IntArray(typedArray.length())
        for (i in 0 until typedArray.length()) {
            actionIcons[i] = typedArray.getResourceId(i, -1)
        }
        typedArray.recycle()
        val actionTitle: Array<String> =
            getResources().getStringArray(R.array.pick_photo_action_list)
        userProfileBottomSheet = UserProfileBottomSheetDialogFragment()
        userProfileBottomSheet.setCancelable(true)
        userProfileBottomSheet.setViewContents(actionTitle, actionIcons)
        userProfileBottomSheet.setActionCallback(this)
        userProfileBottomSheet.show(getSupportFragmentManager(), userProfileBottomSheet.getTag())
    }

    fun onBottomSheetActionClicked(action: String) {
        if (action.equals(getString(R.string.from_camera), ignoreCase = true)) {
            userProfileBottomSheet.dismissAllowingStateLoss()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                checkPermissions(
                    REQUEST_CODE_CAMERA,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
                )
            } else {
                checkPermissions(
                    REQUEST_CODE_CAMERA,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        } else if (action.equals(getString(R.string.from_images), ignoreCase = true)) {
            userProfileBottomSheet.dismissAllowingStateLoss()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                checkPermissions(
                    REQUEST_CODE_GALLERY,
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
                )
            } else {
                checkPermissions(
                    REQUEST_CODE_GALLERY,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
        }
    }

    private fun checkPermissions(requestCode: Int, vararg permissions: String) {
        Dexter.withContext(this)
            .withPermissions(permissions)
            .withListener(object : MultiplePermissionsListener() {
                fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        dispatchPhotoIntent(requestCode)
                    }
                }

                fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }

    private fun dispatchPhotoIntent(code: Int) {
        if (code == REQUEST_CODE_CAMERA) {
            val imageCaptureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (imageCaptureIntent.resolveActivity(getPackageManager()) != null) {
                val imageFile: File = DirectoryManager.createTempImageFile()
                if (imageFile != null) {
                    cameraIntentImageUri = FileProvider.getUriForFile(
                        this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        imageFile
                    )
                    imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraIntentImageUri)
                    startActivityForResult(imageCaptureIntent, REQUEST_CODE_CAMERA)
                }
            }
        } else if (code == REQUEST_CODE_GALLERY) {
            BottomSheetUtils.openGalleryBS(getSupportFragmentManager(), { galleryItems ->
                if (galleryItems != null && !galleryItems.isEmpty()) {
                    val selectedImageUri: Uri = galleryItems.get(0).getFileUri()
                    setProgressGroupVisibility(true)
                    val uploadSrc = S3UploadSrc()
                    uploadSrc.setSrc(registrationViewModel.getClonedProfile())
                    uploadSrc.setFolder(S3UploadViewModel.Folder.USER)
                    val imageUrls: MutableList<String> = ArrayList()
                    imageUrls.add(
                        ImageUtil.getImageUtil().getFilePathFromUri(this, selectedImageUri)
                    )
                    uploadSrc.setMediaUris(imageUrls)
                    s3UploadViewModel.uploadToS3(uploadSrc)
                }
            }, true, 1, true, false)
        }
    }

    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_CAMERA -> if (cameraIntentImageUri != null && resultCode == RESULT_OK) {
                setProgressGroupVisibility(true)
                val uploadSrc = S3UploadSrc()
                uploadSrc.setSrc(registrationViewModel.getClonedProfile())
                uploadSrc.setFolder(S3UploadViewModel.Folder.USER)
                val imageUrls: MutableList<String> = ArrayList()
                imageUrls.add(
                    ImageUtil.getImageUtil().getFilePathFromUri(this, cameraIntentImageUri)
                )
                uploadSrc.setMediaUris(imageUrls)
                s3UploadViewModel.uploadToS3(uploadSrc)
            }

            REQUEST_CODE_GALLERY -> if (resultCode == RESULT_OK && data != null) {
                val images: List<Uri> = Matisse.obtainResult(data)
                if (registrationViewModel.getClonedProfile() != null && images != null && images.size > 0) {
                    val selectedImageUri: Uri = Matisse.obtainResult(data).get(0)
                    setProgressGroupVisibility(true)
                    val uploadSrc = S3UploadSrc()
                    uploadSrc.setSrc(registrationViewModel.getClonedProfile())
                    uploadSrc.setFolder(S3UploadViewModel.Folder.USER)
                    val imageUrls: MutableList<String> = ArrayList()
                    imageUrls.add(
                        ImageUtil.getImageUtil().getFilePathFromUri(this, selectedImageUri)
                    )
                    uploadSrc.setMediaUris(imageUrls)
                    s3UploadViewModel.uploadToS3(uploadSrc)
                }
                break
            }
        }
    }

    companion object {
        const val KEY_IS_FIRST_LAUNCH = "KEY_IS_FIRST_LAUNCH"
        private const val REQUEST_CODE_CAMERA = 11
        private const val REQUEST_CODE_GALLERY = 22
        fun startThisActivity(context: Context, isLaunchFirst: Boolean) {
            val intent = Intent(context, OnBoardActivity::class.java)
            intent.putExtra(KEY_IS_FIRST_LAUNCH, isLaunchFirst)
            context.startActivity(intent)
        }
    }
}