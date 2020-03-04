package com.ldlywt.androidadvance.view.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wutao
 */
public class TimelineItemDecoration extends RecyclerView.ItemDecoration {

    private List<Units> mUnitsList = new ArrayList<>();
    // 写右边字的画笔(具体信息)
    private Paint mPaint;
    // 日期字的画笔( 时间 + 日期)
    private Paint mDatePaint;
    private Paint mTimePaint;
    // 左 上偏移长度
    private int mLeftOffset = 50;
    private int mTopOffset = 50;
    // 图标
//    private Bitmap mIcon;
    // 轴点半径
    private int mCircleRadius = 10;

    // 在构造函数里进行绘制的初始化，如画笔属性设置等
    public TimelineItemDecoration(Context context) {
        initPaint();
        // 获取图标资源
//         mIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
    }

    private void initPaint() {
        // 轴点画笔(红色)
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        // 左边时间文本画笔(蓝色)
        // 此处设置了两只分别设置 时分 & 年月
        mDatePaint = new Paint();
        mDatePaint.setColor(Color.GREEN);
        mDatePaint.setTextSize(28);
        mTimePaint = new Paint();
        mTimePaint.setTextSize(22);
        mTimePaint.setColor(Color.BLUE);
    }

    public void setDataList(List<Units> dataList) {
        mUnitsList.clear();
        mUnitsList = dataList;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            View lastChild;
            float lastCenterY = 0f;
            if (i > 0) {
                lastChild = parent.getChildAt(i - 1);
                lastCenterY = lastChild.getTop() - mTopOffset + (mTopOffset + lastChild.getHeight()) / 2;
            }
            float centerX = child.getLeft() - mLeftOffset / 3;
            float centerY = child.getTop() - mTopOffset + (mTopOffset + child.getHeight()) / 2;
            // 通过Canvas绘制角标
//             c.drawBitmap(mIcon,centerX - mCircleRadius ,centerY - mCircleRadius,mPaint);
            int index = parent.getChildAdapterPosition(child);
            Log.i("wutao", "index: " + index);
            // 设置文本起始坐标
            float textX = centerX + mCircleRadius / 2 + 10;
            float textY = centerY;
            if (index == 0 || !(mUnitsList.get(index - 1).date).equals(mUnitsList.get(index).date)) {
                c.drawText(mUnitsList.get(index).date, textX, textY, mDatePaint);
                c.drawText(mUnitsList.get(index).time, textX, textY + 20, mTimePaint);
                c.drawCircle(centerX, centerY, mCircleRadius, mPaint);
            } else {
                c.drawText(mUnitsList.get(index).time, textX, textY, mTimePaint);
            }
            if (i > 0) {
                c.drawLine(centerX, lastCenterY, centerX, centerY, mPaint);
            }
            if (i == childCount - 1) {
                c.drawLine(centerX, lastCenterY, centerX, child.getBottom(), mPaint);
            }
        }
    }

    // 作用：设置ItemView 左 & 上偏移长度
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // 设置ItemView的左 & mLeftOffset px & mTopOffset,即此为onDraw()可绘制的区域
        outRect.set(mLeftOffset, mTopOffset, 0, 0);

    }

    public static class Units {
        public String date;
        public String time;

        public Units(String date, String time) {
            this.date = date;
            this.time = time;
        }
    }
}


