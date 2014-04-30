package com.example.mvvm.converter;

import android.graphics.Color;
import gueei.binding.IObservable;
import gueei.binding.Converter;

public class WEBCOLOR extends Converter<String> {
    
    public WEBCOLOR(IObservable<?>[] dependents){
      super(String.class, dependents);
    }
    
    @Override
    public String calculateValue(Object... args) throws Exception {
      String value = "";
      if (args != null && 0 < args.length && args[0] != null) {
        int color = (Integer) args[0];
        value = String.format("#%02x%02x%02x",
            Color.red(color), Color.green(color), Color.blue(color));
      }
      return value;
    }
  }