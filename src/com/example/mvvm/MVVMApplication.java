package com.example.mvvm;

import gueei.binding.Binder;
import android.app.Application;

public class MVVMApplication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        Binder.init(this);
    }  
}
