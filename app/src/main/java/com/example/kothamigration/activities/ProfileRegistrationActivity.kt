/*
package com.bs.kotha.application.registration.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bs.kotha.R;
import com.bs.kotha.application.navigation.activity.DashboardActivity;
import com.bs.kotha.application.registration.viewmodel.ProfileRegistrationViewModel;
import com.bs.kotha.databinding.ActivityProfileRegistrationBinding;
import com.bs.kotha.framework.base.BaseBindableActivity;
import com.bs.kotha.framework.preference.PrefKey;
import com.bs.kotha.framework.preference.PrefManager;
import com.bs.kotha.framework.rest.model.feed.post.feedpost.FeedLogin;
import com.bs.kotha.framework.util.Logger;
import com.bs.kotha.framework.util.PermissionUtil;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import com.google.firebase.iid.FirebaseInstanceId;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

*/
/*
 * ****************************************************************************
 * * Created by:
 * * Name : Mimo Saha
 * * Email : mmimosaha@gmail.com
 * ****************************************************************************
 */
/*


public class ProfileRegistrationActivity extends BaseBindableActivity implements
        ProfileRegistrationViewModel.ProfileRegistrationCallback, ContactSyncHelper.ContactSyncCallback  {

    private static final String TAG = "ProfileRegistrationActi";
    private ActivityProfileRegistrationBinding activityProfileRegistrationBinding;
    private ProfileRegistrationViewModel profileRegistrationViewModel;

    private final int PERMISSION_REQUEST_CODE = 102, CAPTURE_PHOTO = 103, SELECT_PHOTO = 104,
            PERMISSION_OVERLAY = 105;

    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, ProfileRegistrationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile_registration;
    }

    @Override
    protected void startUI(Bundle savedInstanceState) {

        activityProfileRegistrationBinding = (ActivityProfileRegistrationBinding)
                getViewDataBinding();

        profileRegistrationViewModel = getViewModel();
        profileRegistrationViewModel.setProfileRegistrationCallback(this);
        //getPushTokenListener();
        checkPermission();
//        overlayPermission();
        setClickListener(activityProfileRegistrationBinding.imageChange);

        profileRegistrationViewModel.getPushTokenSendCompleteLiveData().observe(this, isPush -> {
            Runnable runnable = () -> {
                feedLoginWithAccessToken();
                PrefManager.getPreference().setPrivateValue(PrefKey.FLAG_REG_COMPLETE, true);

                if (pd != null) {
                    pd.dismiss();
                }

                DashboardActivity.startThisActivity(ProfileRegistrationActivity.this, true);
                finish();
            };

            runOnUiThread(runnable);
        });
    }

    private void syncRegistration(boolean isContactPermission) {

        String regPhoneNumber = PrefManager.getPreference().getPrivateString(PrefKey.MY_REG_PHONE_NUMBER);
        Logger.logi(TAG, "syncRegistration: number=" + regPhoneNumber);
        if (TextUtils.isEmpty(regPhoneNumber)) {
            userIsRegistered(isContactPermission);
        } else {
            syncProcessStart(regPhoneNumber, isContactPermission);
        }
    }

    private void syncProcessStart(String regPhoneNumber, boolean isContactPermission) {

        Thread thread = new Thread(() -> RegistrationHelper.getInstance()
                .setPhoneNum(regPhoneNumber).startProcess(isContactPermission, this));

        thread.setDaemon(true);
        thread.start();
    }

    private void userIsRegistered(boolean isContactPermission) {

        AccessToken accessToken = AccountKit.getCurrentAccessToken();
        Logger.logd(TAG, "userIsRegistered: accessToken=" + accessToken);
        if (accessToken == null)
            return;

        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {

                PhoneNumber phoneNumber = account.getPhoneNumber();
                if (phoneNumber != null) {
                    String regPhoneNumber = phoneNumber.toString();
                    if (PermissionUtil.getInstance().isAllowed(Manifest.permission.READ_CONTACTS)) {
                        syncProcessStart(regPhoneNumber, isContactPermission);
                    }
                }
            }

            @Override
            public void onError(final AccountKitError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setup_information, menu);
        return true;
    }

    private ProgressDialog pd;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_done:

                String name = activityProfileRegistrationBinding
                        .editFullName.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    getMaterialDialog().show();
                    return true;
                }


                pd = new ProgressDialog(this);
                pd.setMessage("loading");
                pd.setCancelable(false);
                pd.show();

                profileRegistrationViewModel.submitDone(name);
//                DashboardActivity.startThisActivity(this);
                PrefManager.getPreference().setPrivateValue(PrefKey.KEY_FULL_NAME,
                        activityProfileRegistrationBinding
                                .editFullName.getText().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private MaterialDialog mMaterialDialog;

    private MaterialDialog getMaterialDialog() {
        if (mMaterialDialog == null) {
            mMaterialDialog = new MaterialDialog(this)
                    .setTitle("Error")
                    .setMessage("Please fill all the fields.These \nFields are mandatory")
                    .setPositiveButton("OK", v -> mMaterialDialog.dismiss())
                    .setNegativeButton("CANCEL", v -> mMaterialDialog.dismiss());
        }
        return mMaterialDialog;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.image_change:
                chooseImage();
                break;
        }
    }

    private void checkPermission() {

        if (!PermissionUtil.getInstance().isAllowed(Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS)) {

            PermissionUtil.getInstance().request(this, PERMISSION_REQUEST_CODE,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS);
        } else {
            syncRegistration(true);
        }
    }

    private void overlayPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                myIntent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(myIntent, PERMISSION_OVERLAY);
            } else {
                checkPermission();
            }
        } else {
            checkPermission();
        }
    }

    private void chooseImage() {
        ArrayAdapter<String> arrayAdapter
                = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        arrayAdapter.add(getString(R.string.userinfo_select_camera));
        arrayAdapter.add(getString(R.string.userinfo_select_gallery));

        ListView listView = new ListView(this);
        listView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
        listView.setDividerHeight(0);
        listView.setAdapter(arrayAdapter);

        MaterialDialog alert = new MaterialDialog(this).setContentView(listView);

        alert.setPositiveButton(getString(R.string.alert_dialog_cancel_message), v -> alert.dismiss());
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 0) {

                alert.dismiss();
                checkPermissions(CAPTURE_PHOTO, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);


            } else {

                alert.dismiss();
                checkPermissions(SELECT_PHOTO, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);


            }
        });

        alert.show();
    }

    private void checkPermissions(int requestCode, String... permissions) {
        Dexter.withActivity(this)
                .withPermissions(permissions)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            switch (requestCode) {
                                case CAPTURE_PHOTO:
                                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(i, CAPTURE_PHOTO);
                                    break;
                                case SELECT_PHOTO:
                                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                    photoPickerIntent.setType("image/ *");
                                    startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                                    break;
                                default:
                                    break;

                            }
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (PermissionUtil.getInstance().isAllowed(Manifest.permission.READ_CONTACTS)) {
                    syncRegistration(true);
                } else {
                    syncRegistration(false);
                }
                break;

            case PERMISSION_OVERLAY:
                overlayPermission();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CAPTURE_PHOTO:
                if (resultCode == RESULT_OK) {
                    profileRegistrationViewModel.getUserImage(data, true);
                }

                break;

            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    profileRegistrationViewModel.getUserImage(data, false);
                }
                break;
        }
    }

    @Override
    public void setUserImage(Bitmap bitmap) {
        if (bitmap == null)
            return;

        activityProfileRegistrationBinding.profilePhoto.setImageBitmap(bitmap);
    }

    @Override
    public void registrationDone() {

        String userToken = FirebaseInstanceId.getInstance().getToken();
        profileRegistrationViewModel.sendRegistrationToServer(userToken);

    }


    private ProfileRegistrationViewModel getViewModel() {
        return ViewModelProviders.of(this).get(ProfileRegistrationViewModel.class);
    }

    @Override
    public void syncStatus(boolean isDone) {
        PrefManager.getPreference().setPrivateValue(PrefKey.KEY_IS_CONTACT_IMPORTED, true);
        profileRegistrationViewModel.isSyncDone = isDone;
        profileRegistrationViewModel.uploadInfoStart();
    }

  */
/*  private void getPushTokenListener() {
        profileRegistrationViewModel.getPushTokenSendCompleteLiveData().observe(this, isTokenSend -> {
            if (isTokenSend) {

            }
        });
    }*/
/*


    private void feedLoginWithAccessToken() {
        String userId = "_" + PrefManager.getPreference().
                getPrivateString(PrefKey.KEY_USER_ID);
        FeedLogin feedLogin = new FeedLogin();
        feedLogin.setId(userId);
        profileRegistrationViewModel.loginWithAccessToken(feedLogin);

    }

}
*/