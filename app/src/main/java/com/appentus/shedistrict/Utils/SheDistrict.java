package com.appentus.shedistrict.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.appentus.shedistrict.network.ApiController;
import com.appentus.shedistrict.network.ResponseListener;
import com.google.firebase.FirebaseApp;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import io.embrace.android.embracesdk.Embrace;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

public class SheDistrict extends Application implements Application.ActivityLifecycleCallbacks , LifecycleObserver{


    public static  SheDistrict sheDistrict;
    public static  Activity activity;
    private static Context baseContextApp;
    private static Location userCurrentlocation;
    public static Context context;
    ApiController apiController;
    @Override
    public void onCreate() {
        super.onCreate();
        sheDistrict = this;
        baseContextApp = this;
        context=this;

        //apiController = new ApiController(this, this);

        Embrace.getInstance().start(this);

    TwitterConfig config = new TwitterConfig.Builder(this)
            .logger(new DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(new TwitterAuthConfig("h29m1bC0y28m4BDqw3Qfwb2GO","DqvM505kI7jbj6poeu7qZqoMOJk0XG3OrMJJrrqvMBNShW8u5L"))
            .debug(true)
            .build();
        Twitter.initialize(config);


        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
        registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        onAppBackgrounded();
        onAppForegrounded();
    }


    public static Location getLocation() {
        return userCurrentlocation;
    }

    public static void setLocation(Location location) {
        SheDistrict.userCurrentlocation= location;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        new Utility().setStatusBar(activity);
        this.activity=activity;

        new GPSTrackerFus(activity, new GPSTrackerFus.LocationInterface() {
            @Override
            public void locationReceived(Location location) {
                userCurrentlocation=location;

            }
        });
        onAppDidEnterBackground();
    }



    public static  SheDistrict getInstance(){
        return sheDistrict;
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {


    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {


    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppBackgrounded() {
        Log.d("MyApp", "App in background");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppForegrounded() {
        Log.d("MyApp", "App in foreground");
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    public static SheDistrict getSheDistrict() {
        return sheDistrict;
    }

    public static void setSheDistrict(SheDistrict sheDistrict) {
        SheDistrict.sheDistrict = sheDistrict;
    }

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        SheDistrict.activity = activity;
    }

    public static Context getBaseContextApp() {
        return baseContextApp;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppDidEnterForeground() {
        Log.e("sfjsdfjd","djshfvhdvfd");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppDidEnterBackground() {
        Log.e("fkfkgbk","djshfvhdvfd");
    }

   /* public  String encrypt(String password, String clef){
        RSAPublicKey key = new RSAPublicKey() {
            @Override
            public BigInteger getPublicExponent() {
                return null;
            }
            @Override
            public String getAlgorithm() {
                return null;
            }
            @Override
            public String getFormat() {
                return null;
            }
            @Override
            public byte[] getEncoded() {
                return new byte[0];
            }
            @Override
            public BigInteger getModulus() {
                return null;
            }
        };

        try

        {
            byte[] keyBytes = DatatypeConverter.parseBase64Binary(clef);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            try {
                key = (RSAPublicKey) kf.generatePublic(spec);
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,key);
            return new String(Base64.getEncoder().encode(cipher.doFinal(password.getBytes("UTF8"))));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }*/
}
