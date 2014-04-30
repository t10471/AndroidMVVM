package com.example.mvvm.model;

import java.util.Collection;

import com.example.mvvm.view.BallView;

import android.graphics.Point;
import android.view.View;

import gueei.binding.CollectionChangedEventArg;
import gueei.binding.CollectionObserver;
import gueei.binding.Command;
import gueei.binding.IObservableCollection;
import gueei.binding.Observable;
import gueei.binding.collections.ArrayListObservable;
import gueei.binding.labs.EventAggregator;
import gueei.binding.observables.IntegerObservable;
import gueei.binding.observables.ObjectObservable;

public class MainViewModel {
    
    public static final String ADD_BALL = "ADD_BALL";
    public static final String REMOVE_BALL = "REMOVE_BALL";
    private final EventAggregator mEventAggregator;
    
    public MainViewModel(EventAggregator aggregator) {
        mEventAggregator = aggregator;
    }
    
    public final ArrayListObservable<BallViewModel> balls = new ArrayListObservable<BallViewModel>(BallViewModel.class);
    public final IntegerObservable count = new IntegerObservable(balls.size());
    public final Observable<Point> touchPoint = new Observable<Point>(Point.class);
    public final ObjectObservable clickedBall = new ObjectObservable();
    private final CollectionObserver ballCountObserver = new CollectionObserver() {
        @Override
        public void onCollectionChanged(IObservableCollection<?> iObservableCollection, CollectionChangedEventArg collectionChangedEventArg, Collection<Object> objects) {
            count.set(iObservableCollection.size());
        }
    };
    
    private int idCounter = 0;
    public final Command stageClickedCommand = new Command() {
        @Override
        public void Invoke(View view, Object... objects) {
            Point touched = touchPoint.get();
            BallViewModel ball = new BallViewModel(idCounter++, touched.x, touched.y);
            balls.add(ball);
            mEventAggregator.publish(ADD_BALL, ball, null);
        }
    };
    
    public final Command ballClickedCommand = new Command() {
        @Override
        public void Invoke(View view, Object... objects) {
            BallViewModel ball = null;
            if (view instanceof BallView) {
                ball = ((BallView)view).getViewModel();
            } else {
                ball = (BallViewModel)clickedBall.get();
            }
            balls.remove(ball);
            mEventAggregator.publish(REMOVE_BALL, ball, null);
        }
    };
    
    {
        // インスタンスイニシャライザ
        balls.subscribe(ballCountObserver);
    }
    
}
