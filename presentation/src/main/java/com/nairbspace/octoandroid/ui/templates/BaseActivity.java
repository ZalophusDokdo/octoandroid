package com.nairbspace.octoandroid.ui.templates;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nairbspace.octoandroid.data.net.NetworkChecker;
import com.nairbspace.octoandroid.receiver.ActiveNetworkReceiver;
import com.nairbspace.octoandroid.receiver.ActiveNetworkReceiver.ActiveNetworkListener;
import com.nairbspace.octoandroid.ui.Navigator;

import javax.inject.Inject;

public abstract class BaseActivity<T> extends AppCompatActivity implements ActiveNetworkListener {

    @Inject Navigator mNavigator;
    @Inject NetworkChecker mNetworkChecker;
    private ActiveNetworkReceiver mActiveNetworkReceiver;

    @NonNull protected abstract Presenter setPresenter();

    @NonNull protected abstract T setScreen();

    @Override
    protected void onStart() {
        super.onStart();
        setPresenter().onStart();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setPresenter().onInitialize(setScreen());
        mActiveNetworkReceiver = new ActiveNetworkReceiver(this, mNetworkChecker);
    }

    protected void onResume() {
        super.onResume();
        setPresenter().onResume();
        if (mActiveNetworkReceiver != null) {
            registerReceiver(mActiveNetworkReceiver, mActiveNetworkReceiver.getIntentFilter());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        setPresenter().onPause();
        if (mActiveNetworkReceiver != null) unregisterReceiver(mActiveNetworkReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        setPresenter().onStop();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        setPresenter().onDestroy(setScreen());
        if (mActiveNetworkReceiver != null) mActiveNetworkReceiver = null;
    }

    @Override
    public void networkNowActive() {
        setPresenter().networkNowActiveReceived();
    }

    @Override
    public void networkNowInactive() {
        setPresenter().networkNowInactiveReceived();
    }

    public Navigator getNavigator() {
        return mNavigator;
    }
}
