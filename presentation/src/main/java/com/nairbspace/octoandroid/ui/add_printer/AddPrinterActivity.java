package com.nairbspace.octoandroid.ui.add_printer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nairbspace.octoandroid.R;
import com.nairbspace.octoandroid.app.SetupApplication;
import com.nairbspace.octoandroid.model.AddPrinterModel;
import com.nairbspace.octoandroid.ui.BaseActivity;
import com.nairbspace.octoandroid.ui.Presenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class AddPrinterActivity extends BaseActivity<AddPrinterScreen> implements AddPrinterScreen,
        TextView.OnEditorActionListener, DialogInterface.OnClickListener,
        View.OnFocusChangeListener, View.OnClickListener,
        QrDialogFragment.OnFragmentInteractionListener {

    @Inject AddPrinterPresenter mPresenter;

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.add_printer_form) ScrollView mScrollView;
    @BindView(R.id.add_printer_progress) ProgressBar mProgressBar;
    @BindView(R.id.printer_name_edit_text) TextInputEditText mPrinterNameEditText;
    @BindView(R.id.ip_address_edit_text) TextInputEditText mIpAddressEditText;
    @BindView(R.id.port_edit_text) TextInputEditText mPortEditText;
    @BindView(R.id.api_key_edit_text) TextInputEditText mApiKeyEditText;
    @BindView(R.id.ssl_checkbox) CheckBox mSslCheckBox;

    public static Intent newIntent(Context context) {
        return new Intent(context, AddPrinterActivity.class);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SetupApplication.get(this).getAppComponent().inject(this);
        setResult(RESULT_CANCELED);

        setContentView(R.layout.activity_add_printer);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mApiKeyEditText.setOnEditorActionListener(this);
        mIpAddressEditText.setOnFocusChangeListener(this);
        mPortEditText.setOnFocusChangeListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == R.id.add_printer || actionId == EditorInfo.IME_NULL) {
            onAddPrinterButtonClicked();
            return true;
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == mIpAddressEditText) {
            if (hasFocus) {
                mIpAddressEditText.setHint("octopi.local");
            } else {
                mIpAddressEditText.setHint("");
            }
        }

        if (v == mPortEditText) {
            if (hasFocus) {
                if (mSslCheckBox.isChecked()) {
                    mPortEditText.setHint("443");
                } else {
                    mPortEditText.setHint("80");
                }
            } else {
                mPortEditText.setHint("");
            }
        }
    }

    @OnClick(R.id.qr_button)
    public void onQrButtonClicked() {
        showQrDialogFragment();
    }

    @OnClick(R.id.add_printer_button)
    public void onAddPrinterButtonClicked() {
        AddPrinterModel model = AddPrinterModel.builder()
                .accountName(mPrinterNameEditText.getText().toString())
                .ipAddress(mIpAddressEditText.getText().toString())
                .port(mPortEditText.getText().toString())
                .apiKey(mApiKeyEditText.getText().toString())
                .isSslChecked(mSslCheckBox.isChecked())
                .build();
        mPresenter.onAddPrinterClicked(model);
    }

    @Override
    public void showProgress(final Boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mScrollView.setVisibility(show ? View.GONE : View.VISIBLE);
        mScrollView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mScrollView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressBar.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void showQrDialogFragment() {
        DialogFragment df = QrDialogFragment.newInstance();
        df.setCancelable(true);
        df.show(getSupportFragmentManager(), null);
    }

    @Override
    public void showIpAddressError(String error) {
        mIpAddressEditText.setError(error);
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(mScrollView, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Ok", this).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case android.support.design.R.id.snackbar_action:
                Timber.d("This is a snackbar");
                break;
        }
    }

    @Override
    public void showAlertDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.exclamation_triangle)
                .setNegativeButton("Cancel", this)
                .setNeutralButton("Info", this)
                .setPositiveButton("Ok", this)
                .create()
                .show();
    }

    @Override
    public void hideSoftKeyboard(boolean show) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (show) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        } else {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    @Override
    public void navigateToPreviousScreen() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_NEGATIVE: // TODO need to implement info on SSL error
                break;
            case DialogInterface.BUTTON_NEUTRAL:
                break;
            case DialogInterface.BUTTON_POSITIVE:
                mSslCheckBox.setChecked(false);
                onAddPrinterButtonClicked();
                break;
        }
    }

    @Override
    public void onQrSuccess(String apiKey) {
        mApiKeyEditText.setText(apiKey);
    }

    @NonNull
    @Override
    protected Presenter setPresenter() {
        return mPresenter;
    }

    @NonNull
    @Override
    protected AddPrinterScreen setScreen() {
        return this;
    }
}