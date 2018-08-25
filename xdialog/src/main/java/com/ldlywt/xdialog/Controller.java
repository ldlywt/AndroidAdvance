package com.ldlywt.xdialog;

import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;

import java.io.Serializable;

/**
 * <pre>
 *     author : wutao
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class Controller<A extends BaseAdapter> implements Serializable, Parcelable {
    private FragmentManager fragmentManager;
    private int layoutRes;
    private int height;
    private int width;
    private float dimAmount;
    private int gravity;
    private String tag;
    private int[] ids;
    private boolean isCancelableOutside;
    private OnViewClickListener onViewClickListener;
    private A adapter;
    private int orientation;
    private int dialogAnimationRes;
    private View dialogView;
    private DialogInterface.OnDismissListener onDismissListener;


    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public int getLayoutRes() {
        return layoutRes;
    }

    public void setLayoutRes(int layoutRes) {
        this.layoutRes = layoutRes;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getDimAmount() {
        return dimAmount;
    }

    public void setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public String getTag() {
        return tag == null ? "" : tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }

    public boolean isCancelableOutside() {
        return isCancelableOutside;
    }

    public void setCancelableOutside(boolean cancelableOutside) {
        isCancelableOutside = cancelableOutside;
    }

    public OnViewClickListener getOnViewClickListener() {
        return onViewClickListener;
    }

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    public A getAdapter() {
        return adapter;
    }

    public void setAdapter(A adapter) {
        this.adapter = adapter;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getDialogAnimationRes() {
        return dialogAnimationRes;
    }

    public void setDialogAnimationRes(int dialogAnimationRes) {
        this.dialogAnimationRes = dialogAnimationRes;
    }

    public View getDialogView() {
        return dialogView;
    }

    public void setDialogView(View dialogView) {
        this.dialogView = dialogView;
    }

    public DialogInterface.OnDismissListener getOnDismissListener() {
        return onDismissListener;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    //-----------------序列化---------------------
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.layoutRes);
        dest.writeInt(this.height);
        dest.writeInt(this.width);
        dest.writeFloat(this.dimAmount);
        dest.writeInt(this.gravity);
        dest.writeString(this.tag);
        dest.writeIntArray(this.ids);
        dest.writeByte(this.isCancelableOutside ? (byte) 1 : (byte) 0);
        dest.writeInt(this.orientation);
        dest.writeInt(this.dialogAnimationRes);
    }

    public Controller() {
    }

    protected Controller(Parcel in) {
        this.fragmentManager = in.readParcelable(FragmentManager.class.getClassLoader());
        this.layoutRes = in.readInt();
        this.height = in.readInt();
        this.width = in.readInt();
        this.dimAmount = in.readFloat();
        this.gravity = in.readInt();
        this.tag = in.readString();
        this.ids = in.createIntArray();
        this.isCancelableOutside = in.readByte() != 0;
        this.orientation = in.readInt();
        this.dialogAnimationRes = in.readInt();
        this.dialogView = in.readParcelable(View.class.getClassLoader());
        this.onDismissListener = in.readParcelable(DialogInterface.OnDismissListener.class.getClassLoader());
    }

    public static final Parcelable.Creator<Controller> CREATOR = new Parcelable.Creator<Controller>() {
        @Override
        public Controller createFromParcel(Parcel source) {
            return new Controller(source);
        }

        @Override
        public Controller[] newArray(int size) {
            return new Controller[size];
        }
    };

    //-----------------序列化结束-----------------------


    public static class Params<A extends BaseAdapter> {
        public FragmentManager mFragmentManager;
        public int mLayoutRes;
        public int mWidth;
        public int mHeight;
        public float mDimAmount = 0.2f;
        public int mGravity = Gravity.CENTER;
        public String mTag = "TDialog";
        public int[] ids;
        public boolean mIsCancelableOutside = true;
        public OnViewClickListener mOnViewClickListener;
        public int mDialogAnimationRes = 0;//弹窗动画
        public int listLayoutRes;
        public int orientation = LinearLayoutManager.VERTICAL;//默认RecyclerView的列表方向为垂直方向
        public View mDialogView;//直接使用传入进来的View,而不需要通过解析Xml
        public DialogInterface.OnDismissListener mOnDismissListener;

        public void apply(Controller tController) {
            tController.fragmentManager = mFragmentManager;
            if (mLayoutRes > 0) {
                tController.layoutRes = mLayoutRes;
            }
            if (mDialogView != null) {
                tController.dialogView = mDialogView;
            }
            if (mWidth > 0) {
                tController.width = mWidth;
            }
            if (mHeight > 0) {
                tController.height = mHeight;
            }
            tController.dimAmount = mDimAmount;
            tController.gravity = mGravity;
            tController.tag = mTag;
            if (ids != null) {
                tController.ids = ids;
            }
            tController.isCancelableOutside = mIsCancelableOutside;
            tController.onViewClickListener = mOnViewClickListener;
            tController.onDismissListener = mOnDismissListener;
            tController.dialogAnimationRes = mDialogAnimationRes;
            if (tController.getLayoutRes() <= 0 && tController.getDialogView() == null) {
                throw new IllegalArgumentException("请先调用setLayoutRes()方法设置弹窗所需的xml布局!");
            }
            //如果宽高都没有设置,则默认给弹窗提供宽度为600
            if (tController.width <= 0 && tController.height <= 0) {
                tController.width = 600;
            }
        }
    }

}
