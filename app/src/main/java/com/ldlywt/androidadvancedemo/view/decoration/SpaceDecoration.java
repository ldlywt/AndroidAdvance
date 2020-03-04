package com.ldlywt.androidadvancedemo.view.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * <pre>
 *     author : wutao
 *     time   : 2018/11/29
 *     desc   : 空白分割线，适合所有的LayoutManager
 * </pre>
 */
public class SpaceDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private boolean mPaddingEdgeSide = true;
    private boolean mPaddingStart = true;


    public SpaceDecoration(int space) {
        this.space = space;
    }

    /**
     * 是否为左右2边添加padding.默认true.
     */
    public void setPaddingEdgeSide(boolean mPaddingEdgeSide) {
        this.mPaddingEdgeSide = mPaddingEdgeSide;
    }

    /**
     * 是否在给第一行的item添加上padding(不包含header).默认true.
     */
    public void setPaddingStart(boolean mPaddingStart) {
        this.mPaddingStart = mPaddingStart;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int spanCount = 0;
        int orientation = 0;
        int spanIndex = 0;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            spanIndex = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
        } else if (layoutManager instanceof GridLayoutManager) {
            orientation = ((GridLayoutManager) layoutManager).getOrientation();
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            spanIndex = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
        } else if (layoutManager instanceof LinearLayoutManager) {
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            spanCount = 1;
            spanIndex = 0;
        }

        if (orientation == RecyclerView.VERTICAL) {
            setVertical(outRect, parent, position, spanCount, spanIndex);
        } else {
            setHorizontal(outRect, parent, position, spanCount, spanIndex);
        }
    }

    private void setVertical(Rect outRect, RecyclerView parent, int position, int spanCount, int spanIndex) {
        float expectedWidth = (float) (parent.getWidth() - space * (spanCount + (mPaddingEdgeSide ? 1 : -1))) / spanCount;
        float originWidth = (float) parent.getWidth() / spanCount;
        float expectedX = (mPaddingEdgeSide ? space : 0) + (expectedWidth + space) * spanIndex;
        float originX = originWidth * spanIndex;
        outRect.left = (int) (expectedX - originX);
        outRect.right = (int) (originWidth - outRect.left - expectedWidth);
        if (position < spanCount && mPaddingStart) {
            outRect.top = space;
        }
        outRect.bottom = space;
    }

    private void setHorizontal(Rect outRect, RecyclerView parent, int position, int spanCount, int spanIndex) {
        float expectedHeight = (float) (parent.getHeight() - space * (spanCount + (mPaddingEdgeSide ? 1 : -1))) / spanCount;
        float originHeight = (float) parent.getHeight() / spanCount;
        float expectedY = (mPaddingEdgeSide ? space : 0) + (expectedHeight + space) * spanIndex;
        float originY = originHeight * spanIndex;
        outRect.bottom = (int) (expectedY - originY);
        outRect.top = (int) (originHeight - outRect.bottom - expectedHeight);
        if (position < spanCount && mPaddingStart) {
            outRect.left = space;
        }
        outRect.right = space;
    }

}