package com.ldlywt.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ColorInt;

/**
 * <pre>
 *     author : wutao
 *     time   : 2019/01/07
 *     desc   :
 * </pre>
 */
public class ChartView extends View {

    //xy坐标轴颜色
    private int xyLineColor = 0xffe2e2e2;
    //xy坐标轴宽度
    private float xyLineWidth = dpToPx(1);
    //xy坐标轴文字颜色
    private int xyTextColor = 0xff7e7e7e;
    //xy坐标轴文字大小
    private int xyTextSize = spToPx(12);
    //折线图中折线的颜色
    private int lineColorOne = 0xff02bbb7;
    private int lineColorTwo = 0xffed1c24;
    //虚线颜色
    private int dashLineColor = Color.GRAY;
    //x轴各个坐标点水平间距
    private int interval = dpToPx(50);
    //是否在ACTION_UP时，根据速度进行自滑动，没有要求，建议关闭，过于占用GPU
    private boolean isScroll = false;
    //绘制XY轴坐标对应的画笔
    private Paint xyPaint;
    //绘制XY轴的文本对应的画笔
    private Paint xyTextPaint;
    //画折线对应的画笔
    private Paint linePaint;
    //虚线画笔
    private Paint mDashPaint;
    private int width;
    private int height;
    //x轴的原点坐标
    private int xOri;
    //y轴的原点坐标
    private int yOri;
    //第一个点X的坐标
    private float xInit;
    //第一个点对应的最大X坐标
    private float maxXInit;
    //第一个点对应的最小X坐标
    private float minXInit;
    //点击的点对应的X轴的第几个点，默认0
    private int selectIndex = 0;
    //X轴刻度文本对应的最大矩形，为了选中时，在x轴文本画的框框大小一致
    private Rect xValueRect;
    //速度检测器
    private VelocityTracker velocityTracker;
    private float startX;
    //是否正在滑动
    private boolean isScrolling = false;
    //是否隐藏Y轴
    private boolean isHideY;
    private boolean isHideX;
    private boolean mDrawDottedT = true;
    //如果隐藏Y轴时，X轴第一个刻度距离X轴起点的的距离
    private int hideYStartLengthX = dpToPx(10);
    private int textBoxPosition = 1;
    private Path mDashPath;
    private float mUpLimitY = 240;
    private int mSector = 4;
    private OnChartLineValueListener mLineValueListener;

    private DecimalFormat mDecimalFormat = new DecimalFormat("#0.0");

    private List<Units> mUnitList1 = new ArrayList<>();
    private List<Units> mUnitList2 = new ArrayList<>();
    private int mDataSize;

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
        initPaint();
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.chartView, defStyleAttr, 0);
        lineColorOne = array.getColor(R.styleable.chartView_lineColorOne, lineColorOne);
        lineColorTwo = array.getColor(R.styleable.chartView_lineColorTwo, lineColorTwo);
        xyLineColor = array.getColor(R.styleable.chartView_xyLineColor, xyLineColor);
        xyTextColor = array.getColor(R.styleable.chartView_xyTextColor, xyTextColor);
        dashLineColor = array.getColor(R.styleable.chartView_dashLineColor, dashLineColor);

        xyLineWidth = array.getDimension(R.styleable.chartView_xyLineWidth, xyLineWidth);
        interval = (int) array.getDimension(R.styleable.chartView_interval, interval);
        xyTextSize = (int) array.getDimension(R.styleable.chartView_xyTextSize, xyTextSize);

        isHideX = array.getBoolean(R.styleable.chartView_isHideX, false);
        isHideY = array.getBoolean(R.styleable.chartView_isHideY, false);
        isScroll = array.getBoolean(R.styleable.chartView_isScroll, false);

        textBoxPosition = array.getInt(R.styleable.chartView_textBoxPosition, 0);
        array.recycle();

    }

    private void initPaint() {
        xyPaint = new Paint();
        xyPaint.setAntiAlias(true);
        xyPaint.setStrokeWidth(xyLineWidth);
        xyPaint.setStrokeCap(Paint.Cap.ROUND);
        xyPaint.setColor(xyLineColor);

        xyTextPaint = new Paint();
        xyTextPaint.setAntiAlias(true);
        xyTextPaint.setTextSize(xyTextSize);
        xyTextPaint.setStrokeCap(Paint.Cap.ROUND);
        xyTextPaint.setColor(xyTextColor);
        xyTextPaint.setStyle(Paint.Style.STROKE);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(xyLineWidth);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setColor(lineColorOne);
        linePaint.setStyle(Paint.Style.STROKE);

        mDashPaint = new Paint();
        mDashPaint.setAntiAlias(true);
        mDashPaint.setStrokeWidth(xyLineWidth);
        mDashPaint.setStrokeCap(Paint.Cap.ROUND);
        mDashPaint.setColor(Color.GRAY);
        mDashPaint.setStyle(Paint.Style.STROKE);
        mDashPaint.setPathEffect(new DashPathEffect(new float[]{15, 5}, 0));
        mDashPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isScrolling)
            return super.onTouchEvent(event);
        this.getParent().requestDisallowInterceptTouchEvent(true);//当该view获得点击事件，就请求父控件不拦截事件
        obtainVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (interval * mDataSize > width - xOri) {//当期的宽度不足以呈现全部数据
                    float dis = event.getX() - startX;
                    startX = event.getX();
                    if (xInit + dis < minXInit) {//第一个点的坐标+滑动的距离 < 最小X坐标，滑到最左侧了
                        xInit = minXInit;
                    } else if (xInit + dis > maxXInit) {//第一个点的坐标+滑动的距离 > 第一个点的最大值，滑动最右侧了
                        xInit = maxXInit;
                    } else {
                        xInit = xInit + dis;
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                clickAction(event);
                scrollAfterActionUp();
                this.getParent().requestDisallowInterceptTouchEvent(false);
                recycleVelocityTracker();
                break;
            case MotionEvent.ACTION_CANCEL:
                this.getParent().requestDisallowInterceptTouchEvent(false);
                recycleVelocityTracker();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isHideX) {
            drawX(canvas);
        }
        if (!isHideY) {
            drawY(canvas);
        }
        drawBrokenLineAndPoint(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            //这里需要确定几个基本点，只有确定了xy轴原点坐标，第一个点的X坐标值及其最大最小值
            width = getWidth();
            height = getHeight();
            //Y轴文本最大宽度
            float textYWidth = getTextBounds("000", xyTextPaint).width();
            for (int i = 0; i < mDataSize; i++) {//求取y轴文本最大的宽度
                float temp = getTextBounds(mUnitList1.get(0).y + "", xyTextPaint).width();
                if (temp > textYWidth)
                    textYWidth = temp;
            }
            int dp2 = dpToPx(2);
            int dp3 = dpToPx(3);
            xOri = (int) (dp2 + textYWidth + dp2 + xyLineWidth);//dp2是y轴文本距离左边，以及距离y轴的距离
            //X轴文本最大高度
            xValueRect = getTextBounds("000", xyTextPaint);
            float textXHeight = xValueRect.height();
            for (int i = 0; i < mDataSize; i++) {//求取x轴文本最大的高度
                Rect rect = getTextBounds(mUnitList1.get(0).x + "", xyTextPaint);
                if (rect.height() > textXHeight)
                    textXHeight = rect.height();
                if (rect.width() > xValueRect.width())
                    xValueRect = rect;
            }
            yOri = (int) (height - dp2 - textXHeight - dp3 - xyLineWidth);//dp3是x轴文本距离底边，dp2是x轴文本距离x轴的距离
            if (isHideY) {
                xInit = hideYStartLengthX + xOri;
            } else {
                xInit = interval + xOri;
            }
            minXInit = width - (width - xOri) * 0.1f - interval * (mDataSize - 1);//减去0.1f是因为最后一个X周刻度距离右边的长度为X轴可见长度的10%
            maxXInit = xInit;
//            Log.i("wutao", "xInit: " + xInit + "  minXInit: " + minXInit + "  maxXInit: " + maxXInit);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 绘制X轴坐标
     */
    private void drawX(Canvas canvas) {
        int length = dpToPx(4);//刻度的长度;
        //绘制X轴坐标
        canvas.drawLine(xOri, yOri + xyLineWidth / 2, width, yOri + xyLineWidth / 2, xyPaint);
        //绘制x轴箭头
        xyPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        //整个X轴的长度
        float xLength = xInit + interval * (mDataSize - 1) + (width - xOri) * 0.1f;
        if (xLength < width)
            xLength = width;
        path.moveTo(xLength - dpToPx(12), yOri + xyLineWidth / 2 - dpToPx(5));
        path.lineTo(xLength - xyLineWidth / 2, yOri + xyLineWidth / 2);
        path.lineTo(xLength - dpToPx(12), yOri + xyLineWidth / 2 + dpToPx(5));
        canvas.drawPath(path, xyPaint);
        //绘制x轴刻度
        for (int i = 0; i < mDataSize; i++) {
            float x = xInit + interval * i;
            if (x >= xOri) {//只绘制从原点开始的区域
                xyTextPaint.setColor(xyTextColor);
                canvas.drawLine(x, yOri, x, yOri - length, xyPaint);
                //绘制X轴文本
                String text = mUnitList1.get(i).x;
                Rect rect = getTextBounds(text, xyTextPaint);
                if (i == selectIndex - 1) {
                    xyTextPaint.setColor(lineColorOne);
                    canvas.drawText(text, 0, text.length(), x - rect.width() / 2, yOri + xyLineWidth + dpToPx(2) + rect.height(), xyTextPaint);
                    canvas.drawRoundRect(x - xValueRect.width() / 2 - dpToPx(3), yOri + xyLineWidth + dpToPx(1), x + xValueRect.width() / 2 + dpToPx(3), yOri + xyLineWidth + dpToPx(2) + xValueRect.height() + dpToPx(2), dpToPx(2), dpToPx(2), xyTextPaint);

                } else {
                    canvas.drawText(text, 0, text.length(), x - rect.width() / 2, yOri + xyLineWidth + dpToPx(2) + rect.height(), xyTextPaint);
                }
            }
        }
    }

    private float mYScaleSelector;

    /**
     * 绘制折线和折线交点处对应的点
     */
    private void drawBrokenLineAndPoint(Canvas canvas) {
        if (mDataSize <= 0)
            return;
        //重新开一个图层
        int layerId = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        drawBrokenLine(canvas, mUnitList1, lineColorOne);
        drawBrokenLine(canvas, mUnitList2, lineColorTwo);
        drawBrokenPoint(canvas, mUnitList1, lineColorOne);
        mDrawDottedT = false;
        drawBrokenPoint(canvas, mUnitList2, lineColorTwo);

        mDrawDottedT = true;
        // 将折线超出x轴坐标的部分截取掉
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.WHITE);
        linePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        RectF rectF = new RectF(0, 0, xOri, height);
        canvas.drawRect(rectF, linePaint);
        linePaint.setXfermode(null);
        //保存图层
        canvas.restoreToCount(layerId);
    }

    /**
     * 绘制Y轴坐标
     */
    private void drawY(Canvas canvas) {
        int length = dpToPx(4);//刻度的长度
        //绘制Y坐标轴
        canvas.drawLine(xOri - xyLineWidth / 2, 0, xOri - xyLineWidth / 2, yOri, xyPaint);
        //绘制y轴箭头
        xyPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(xOri - xyLineWidth / 2 - dpToPx(5), dpToPx(12));
        path.lineTo(xOri - xyLineWidth / 2, xyLineWidth / 2);
        path.lineTo(xOri - xyLineWidth / 2 + dpToPx(5), dpToPx(12));
        canvas.drawPath(path, xyPaint);
        //绘制y轴刻度
        int yLength = (int) (yOri * (1 - 0.1f) / mSector);//y轴上面空出10%,计算出y轴刻度间距
        for (int i = 0, n = mSector + 1; i < n; i++) {
            //绘制Y轴刻度
            canvas.drawLine(xOri, yOri - yLength * i + xyLineWidth / 2, xOri + length, yOri - yLength * i + xyLineWidth / 2, xyPaint);
            xyTextPaint.setColor(xyTextColor);
            //绘制Y轴文本
            String text = String.valueOf(i * mUpLimitY / mSector);
            Rect rect = getTextBounds(text, xyTextPaint);
            canvas.drawText(text, 0, text.length(), xOri - xyLineWidth - dpToPx(2) - rect.width(), yOri - yLength * i + rect.height() / 2, xyTextPaint);
        }
    }

    /**
     * 绘制折线
     */
    private void drawBrokenLine(Canvas canvas, List<Units> list, @ColorInt int color) {
        if (list.size() <= 0) {
            return;
        }
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(color);
        //绘制折线
        Path path = new Path();
        float x = xInit + interval * 0;
        float y = yOri - yOri * (1 - 0.1f) * (list.get(0).y) / mUpLimitY;
        path.moveTo(x, y);
        for (int i = 1; i < mDataSize; i++) {
            x = xInit + interval * i;
            y = yOri - yOri * (1 - 0.1f) * (list.get(i).y) / mUpLimitY;
            path.lineTo(x, y);
        }
        canvas.drawPath(path, linePaint);
    }

    /**
     * 绘制显示Y值的浮动框:在上面
     */
    private void drawTopFloatTextBox(Canvas canvas, float x, float y, float text) {
        int dp6 = dpToPx(6);
        int dp18 = dpToPx(18);
        //p1
        Path path = new Path();
        path.moveTo(x, y);
        //p2
        path.lineTo(x - dp6, y - dp6);
        //p3
        path.lineTo(x - dp18, y - dp6);
        //p4
        path.lineTo(x - dp18, y - dp6 - dp18);
        //p5
        path.lineTo(x + dp18, y - dp6 - dp18);
        //p6
        path.lineTo(x + dp18, y - dp6);
        //p7
        path.lineTo(x + dp6, y - dp6);
        //p1
        path.lineTo(x, y);
        canvas.drawPath(path, linePaint);
        linePaint.setColor(Color.WHITE);
        linePaint.setTextSize(spToPx(14));
        Rect rect = getTextBounds(text + "", linePaint);
        canvas.drawText(text + "", x - rect.width() / 2, y - dp6 - (dp18 - rect.height()) / 2, linePaint);
    }

    /**
     * 绘制折线对应的点
     */
    private void drawBrokenPoint(Canvas canvas, List<Units> list, @ColorInt int color) {
        float dp2 = dpToPx(3);
        float dp4 = dpToPx(4);
        float dp7 = dpToPx(7);
        //绘制节点对应的原点
        for (int i = 0; i < mDataSize; i++) {
            float x = xInit + interval * i;
            float y = yOri - yOri * (1 - 0.1f) * (list.get(i).y) / mUpLimitY;
            Log.i("Point", "drawBrokenPoint: " + y + " / " + yOri * (1 - 0.1f) * (mUpLimitY - list.get(i).y) / mUpLimitY + " / " + list.get(i).y + "  -- " + yOri);
            //绘制选中的点
            if (i == selectIndex - 1) {
                linePaint.setStyle(Paint.Style.FILL);
                linePaint.setColor(0xffd0f3f2);
                canvas.drawCircle(x, y, dp7, linePaint);
                linePaint.setColor(color);
                canvas.drawCircle(x, y, dp4, linePaint);
                if (textBoxPosition == 0) {
                    drawTopFloatTextBox(canvas, x, y - dp7, list.get(i).y);
                } else {
                    drawRightFloatTextBox(canvas, x + dp7, y, list.get(i).y, checkOffset(i));
                }
                //添加虚线
                if (mDrawDottedT) {
                    if (mLineValueListener != null) {
                        mLineValueListener.onChartValue(mUnitList1.get(selectIndex - 1).y, mUnitList2.get(selectIndex - 1).y);
                    }
                    drawDashLine(canvas, xInit + interval * (selectIndex - 1), yOri);
                }
            }
            //绘制普通的节点
            linePaint.setStyle(Paint.Style.FILL);
            linePaint.setColor(Color.WHITE);
            canvas.drawCircle(x, y, dp2, linePaint);
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setColor(color);
            canvas.drawCircle(x, y, dp2, linePaint);

        }
    }

    /**
     * 绘制显示Y值的浮动框:在右边
     */
    private void drawRightFloatTextBox(Canvas canvas, float x, float y, float text, float offset) {
        int dp6 = dpToPx(6);
        int dp18 = dpToPx(18);
        int dp7 = dpToPx(7);
        int dp9 = dpToPx(9);
        //p1
        Path path = new Path();
        if (offset != 0) {
            if (Math.abs(offset) < dpToPx(36)) {
                if (mDrawDottedT) {
                    if (offset > 0) {
                        y -= dp7 + dp18;
                    } else {
                        y += dp7 + dp18;
                    }
                } else {
                    if (offset > 0) {
                        y += dp7 + dp18;
                    } else {
                        y -= dp7 + dp18;
                    }
                }
            }
        } else {
            if (mDrawDottedT) {
                y -= dp7 + dp18;
            } else {
                y += dp7 + dp18;
            }
        }
        path.moveTo(x, y);
        //p2
        path.lineTo(x + dp6, y - dp6);
        //p3
        path.lineTo(x + dp6, y - dp18);
        //p4
        path.lineTo(x + (dp18 + dp6) * 2, y - dp18);
        //p5
        path.lineTo(x + (dp18 + dp6) * 2, y + dp18);
        //p6
        path.lineTo(x + dp6, y + dp18);
        //p7
        path.lineTo(x + dp6, y + dp6);
        //p1
//        path.lineTo(x, y);
        path.close();
        canvas.drawPath(path, linePaint);
        linePaint.setColor(Color.WHITE);
        linePaint.setTextSize(spToPx(14));
        String formatText = mDecimalFormat.format(text);
        Rect rect = getTextBounds(formatText, linePaint);
        canvas.drawText(formatText, x + dp9 + dp18 - rect.width() / 2, y + rect.height() / 2, linePaint)
        ;
    }

    private void drawDashLine(Canvas canvas, float x, int yOri) {
        canvas.save();
        mDashPath.reset();
        mDashPath.moveTo(x, 0);
        mDashPath.lineTo(x, yOri);
        canvas.drawPath(mDashPath, mDashPaint);
        canvas.restore();
    }

    /**
     * sp转化为px
     */
    private int spToPx(int sp) {
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (scaledDensity * sp + 0.5f * (sp >= 0 ? 1 : -1));
    }

    /**
     * 获取速度跟踪器
     */
    private void obtainVelocityTracker(MotionEvent event) {
        if (!isScroll)
            return;
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

    /**
     * 点击X轴坐标或者折线节点
     */
    private void clickAction(MotionEvent event) {
        int halfInterval = dpToPx(interval >> 1);
        float eventX = event.getX();
        float eventY = event.getY();
        for (int i = 0; i < mDataSize; i++) {
            //节点
            float x = xInit + interval * i;
//            float y = yOri - yOri * (1 - 0.1f) * value.get(xValue.get(i)) / yValue.get(mDataSize - 1);
            if (eventX >= x - halfInterval && eventX <= x + halfInterval
//                    && eventY >= y - dp8 && eventY <= y + dp8
                    && selectIndex != i + 1) {//每个节点周围8dp都是可点击区域
                selectIndex = i + 1;
                invalidate();
                return;
            }
            //X轴刻度
            String text = mUnitList1.get(i).x;
            Rect rect = getTextBounds(text, xyTextPaint);
            x = xInit + interval * i;
//            y = yOri + xyLineWidth + dpToPx(2);
            if (eventX >= x - rect.width() / 2 - halfInterval && eventX <= x + rect.width() + halfInterval / 2
//                    && eventY >= y - dp8 && eventY <= y + rect.height() + dp8
                    && selectIndex != i + 1) {
                selectIndex = i + 1;
                invalidate();
                return;
            }
        }
    }

    /**
     * 手指抬起后的滑动处理
     */
    private void scrollAfterActionUp() {
        if (!isScroll)
            return;
        final float velocity = getVelocity();
        float scrollLength = maxXInit - minXInit;
        if (Math.abs(velocity) < 10000)//10000是一个速度临界值，如果速度达到10000，最大可以滑动(maxXInit - minXInit)
            scrollLength = (maxXInit - minXInit) * Math.abs(velocity) / 10000;
        ValueAnimator animator = ValueAnimator.ofFloat(0, scrollLength);
        animator.setDuration((long) (scrollLength / (maxXInit - minXInit) * 1000));//时间最大为1000毫秒，此处使用比例进行换算
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                if (velocity < 0 && xInit > minXInit) {//向左滑动
                    if (xInit - value <= minXInit)
                        xInit = minXInit;
                    else
                        xInit = xInit - value;
                } else if (velocity > 0 && xInit < maxXInit) {//向右滑动
                    if (xInit + value >= maxXInit)
                        xInit = maxXInit;
                    else
                        xInit = xInit + value;
                }
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isScrolling = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isScrolling = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                isScrolling = false;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();

    }

    /**
     * 回收速度跟踪器
     */
    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    /**
     * dp转化成为px
     *
     * @param dp
     * @return
     */
    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }

    /**
     * 获取丈量文本的矩形
     */
    private Rect getTextBounds(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }

    /**
     * 获取速度
     */
    private float getVelocity() {
        if (velocityTracker != null) {
            velocityTracker.computeCurrentVelocity(1000);
            return velocityTracker.getXVelocity();
        }
        return 0;
    }

    /**
     * 向右滑动
     */
    public void slide(float scrollLength, long duration, final boolean isLeft) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, scrollLength);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                if (isLeft) {//向左滑动
                    if (xInit - value <= minXInit) {
                        xInit = minXInit;
                    } else {
                        xInit = xInit - value;
                    }
                } else {//向右滑动
                    if (xInit + value >= maxXInit) {
                        xInit = maxXInit;
                    } else {
                        xInit = xInit + value;
                    }
                }
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isScrolling = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isScrolling = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                isScrolling = false;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();

    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
        invalidate();
    }

    public void setValue(List<Units> list1, List<Units> list2) {
        mUnitList1 = list1;
        mUnitList2 = list2;
        mDataSize = Math.max(list1.size(), list2.size());
        invalidate();
    }

    public void addValue(ChartView.Units value1, ChartView.Units value2) {
        mUnitList1.add(value1);
        mUnitList2.add(value2);
        mDataSize = Math.max(mUnitList1.size(), mUnitList2.size());
//        mRefreshLayout = true;
        minXInit = width - (width - xOri) * 0.1f - interval * (mDataSize - 1);//减去0.1f是因为最后一个X周刻度距离右边的长度为X轴可见长度的10%
        slide(interval, 200, true);
    }

    public String getLastXValue() {
        return mUnitList1.get(mUnitList1.size() - 1).x;
    }

    private float checkOffset(int index) {
        float lineOneY = yOri - yOri * (1 - 0.1f) * mUnitList1.get(index).y / mUpLimitY;
        float lineTweY = yOri - yOri * (1 - 0.1f) * mUnitList2.get(index).y / mUpLimitY;
        return (lineTweY - lineOneY);
    }

    public void setYLimit(float maxY, int sector) {
        this.mUpLimitY = maxY;
        this.mSector = sector;
    }

    public void setChartLineValueListener(OnChartLineValueListener lineValueListener) {
        this.mLineValueListener = lineValueListener;
    }

    public interface OnChartLineValueListener {

        void onChartValue(float lineOneValue, float lineTweValue);
    }

    public static class Units {
        public float y;
        String x;

        public Units(String x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}