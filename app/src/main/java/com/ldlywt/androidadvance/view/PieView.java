package com.ldlywt.androidadvance.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ldlywt.androidadvance.bean.PieData;

import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : wutao
 *     time   : 2018/11/15
 *     desc   : 扇饼图
 * </pre>
 */
public class PieView extends View {

    // 颜色表(注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    // 饼状图初始绘制角度
    private float mStartAngle = 0;
    // 数据
    private ArrayList<PieData> mData;
    // 宽高
    private int mWidth, mHeight;
    // 画笔
    private Paint mPaint = new Paint();

    private PieListener mPieListener;

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    public void setStartAngle(int startAngle) {
        mStartAngle = startAngle;
        invalidate();
    }

    // 设置数据
    public void setData(ArrayList<PieData> mData) {
        this.mData = mData;
        initData(mData);
        invalidate();   // 刷新
    }

    private void initData(ArrayList<PieData> mData) {
        if (mData == null || mData.size() == 0) {
            return;
        }
        float sumValue = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pieData = mData.get(i);
            sumValue += pieData.getValue();
            int j = i % mColors.length;
            pieData.setColor(mColors[j]);
        }

        for (int i = 0; i < mData.size(); i++) {
            PieData pieData = mData.get(i);
            float percentage = pieData.getValue() / sumValue;   // 百分比
            float angle = percentage * 360;                 // 对应的角度
            pieData.setPercentage(percentage);
            pieData.setAngle(angle);
            Log.i("angle", "" + pieData.getAngle());
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                return true;
            case MotionEvent.ACTION_UP:
                if (mPieListener != null) {
                    mPieListener.onItemClick(333333333333l);
                }
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == mData) {
            return;
        }
        float currentStartAngle = mStartAngle;   // 当前起始角度
        canvas.translate(mWidth / 2, mHeight / 2);   // 将画布坐标原点移动到中心位置
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);  // 饼状图半径
        RectF rectF = new RectF(-r, -r, r, r);
        for (int i = 0; i < mData.size(); i++) {
            PieData pieData = mData.get(i);
            mPaint.setColor(pieData.getColor());
            canvas.drawArc(rectF, currentStartAngle, pieData.getAngle(), true, mPaint);
            currentStartAngle += pieData.getAngle();
        }
    }

    public void setOnPieListener(PieListener listener) {
        mPieListener = listener;
    }

    public interface PieListener {
        void onItemClick(float value);
    }

}
