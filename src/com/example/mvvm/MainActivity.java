package com.example.mvvm;

import com.example.mvvm.model.BallViewModel;
import com.example.mvvm.model.MainViewModel;
import com.example.mvvm.view.BallView;

import gueei.binding.labs.EventAggregator;
import gueei.binding.labs.EventSubscriber;
import gueei.binding.v30.app.BindingActivityV30;
import android.os.Bundle;
import android.graphics.Point;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends BindingActivityV30 {
    private MainViewModel mViewModel;
    private SparseArray<BallView> mBallViews = new SparseArray<BallView>();
    private ViewGroup mStage;
    
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new MainViewModel(getEventAggregator());
        setAndBindRootView(R.layout.activity_main, mViewModel);
        mStage = (ViewGroup)findViewById(R.id.stage);
        
        mStage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mViewModel.touchPoint.set(new Point((int)event.getX(), (int)event.getY()));
                }
                return false;
            }
        });
    }
    
    private EventAggregator getEventAggregator() {
        EventAggregator aggregator = EventAggregator.getInstance(getApplicationContext());
        
        aggregator.subscribe(MainViewModel.ADD_BALL, new EventSubscriber() {
            @Override
            public void onEventTriggered(String s, Object o, Bundle bundle) {
                BallViewModel ball = (BallViewModel)o;
                BallView view = new BallView(getApplicationContext());
                view.setViewModel(ball);
                view.setOnClickListener(mOnBallClickListener);
                mStage.addView(view);
                mBallViews.append(ball.id.get(), view);
            }
        });
        
        aggregator.subscribe(MainViewModel.REMOVE_BALL, new EventSubscriber() {
            @Override
            public void onEventTriggered(String s, Object o, Bundle bundle) {
                BallViewModel ball = (BallViewModel)o;
                BallView view = mBallViews.get(ball.id.get());
                mStage.removeView(view);
                mBallViews.delete(ball.id.get());
            }
        });
        
        return aggregator;
    }
    
    private View.OnClickListener mOnBallClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mViewModel.ballClickedCommand.InvokeCommand(v);
        }
    };
}
