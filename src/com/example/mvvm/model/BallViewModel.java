package com.example.mvvm.model;

import java.util.Random;

import android.graphics.Color;

import gueei.binding.observables.IntegerObservable;

public class BallViewModel {
    
    public final IntegerObservable id = new IntegerObservable();
    public final IntegerObservable color = new IntegerObservable();
    public final IntegerObservable x = new IntegerObservable();
    public final IntegerObservable y = new IntegerObservable();
    public final IntegerObservable size = new IntegerObservable();
    
    public BallViewModel(int id, int x, int y) {
        Random random = new Random();
        this.id.set(id);
        color.set(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        size.set(30 + random.nextInt(140));
        this.x.set(x);
        this.y.set(y);
    }
}