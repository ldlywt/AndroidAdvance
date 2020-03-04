package com.ldlywt.androidadvancedemo.view.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import com.ldlywt.androidadvancedemo.utils.PinyinUtils;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *     author : wutao
 *     time   : 2018/11/30
 *     desc   : RecyclerView的侧边字母提示
 * </pre>
 */
public class AbcDecoration extends RecyclerView.ItemDecoration {

    List<String> mList;
    private Context mContext;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mTextSize = 20;
    private int mTextColor = Color.RED;
    private int mLeftPadding = 40;//距离左边偏移量

    public AbcDecoration(Context context, List<String> list) {
        mContext = context;
        mList = list;
        mPaint.setTextSize(sp2px(mTextSize));
        mPaint.setColor(mTextColor);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (!(layoutManager instanceof LinearLayoutManager))
            return;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            int position = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition() + i;
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            float left = 0;
            float top = child.getTop() - params.topMargin;
            float right = child.getRight() - params.rightMargin;
            float bottom = child.getBottom() + params.bottomMargin;
            float width = right - left;
            float height = bottom - (bottom - top) / 2;
            //当前名字拼音的第一个字母
            String letter = PinyinUtils.getPinyin(mList.get(position)).charAt(0) + "";
            if (position == 0) {
                drawLetter(letter, width, height, c, parent);
            } else {
                String preLetter = PinyinUtils.getPinyin(mList.get(position - 1)).charAt(0) + "";
                if (!letter.equalsIgnoreCase(preLetter)) {
                    drawLetter(letter, width, height, c, parent);
                }
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(dip2px(mLeftPadding), 0, 0, 0);
    }

    private int dip2px(int dip) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    private void drawLetter(String letter, float width, float height, Canvas c, RecyclerView parent) {
        float fontLength = getFontLength(mPaint, letter);
        float fontHeight = getFontHeight(mPaint);
        float tx = (width - fontLength) / 2;
        float ty = height - fontHeight / 2 + getFontLeading(mPaint);
        c.drawText(letter, tx, ty, mPaint);
    }

    /**
     * 返回指定笔和指定字符串的长度
     */
    private float getFontLength(Paint paint, String str) {
        return paint.measureText(str);
    }

    /**
     * 返回指定笔的文字高度
     */
    private float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    /**
     * 返回指定笔离文字顶部的基准距离
     */
    private float getFontLeading(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.leading - fm.ascent;
    }

    public int sp2px(int sp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, mContext.getResources().getDisplayMetrics()) + 0.5f);
    }
}
