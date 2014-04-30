package com.example.mvvm.view;

import java.util.Collection;

import com.example.mvvm.model.BallViewModel;

import gueei.binding.IObservable;
import gueei.binding.Observer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class BallView extends View {
    private BallViewModel mViewModel;
    private Paint mPaint = new Paint();
    private RectF mDrawRect;
    
    public BallView(Context context) {
        super(context);
    }
    
    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public BallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(mDrawRect, mPaint);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mDrawRect = new RectF(0, 0, w, h);
    }
    
    public void setViewModel(BallViewModel viewModel) {
        mViewModel = viewModel;
        setupObservers(viewModel);
    }
    
    public BallViewModel getViewModel() {
        return mViewModel;
    }
    
    private void setupObservers(BallViewModel viewModel) {
        viewModel.color.subscribe(mColorObserver);
        viewModel.color.notifyChanged();
        viewModel.size.subscribe(mRectObserver);
        viewModel.x.subscribe(mRectObserver);
        viewModel.y.subscribe(mRectObserver);
        viewModel.size.notifyChanged();
    }
    
    private final Observer mColorObserver = new Observer() {
        @Override
        public void onPropertyChanged(IObservable<?> iObservable, Collection<Object> objects) {
            int color = (Integer)iObservable.get();
            Paint paint = new Paint();
            paint.setColor(color);
            mPaint = paint;
            postInvalidate();
        }
    };
    
    private final Observer mRectObserver = new Observer() {
        @Override
        public void onPropertyChanged(IObservable<?> iObservable, Collection<Object> objects) {
            int size = mViewModel.size.get();
            int x = mViewModel.x.get() - size / 2;
            int y = mViewModel.y.get() - size / 2;
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, size);
            params.leftMargin = x;
            params.topMargin = y;
            setLayoutParams(params);
            postInvalidate();
        }
    };
}