/*
 * Copyright (c) 2016, Shanghai YUEWEN Information Technology Co., Ltd.
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *  Neither the name of Shanghai YUEWEN Information Technology Co., Ltd. nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY SHANGHAI YUEWEN INFORMATION TECHNOLOGY CO., LTD. AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package com.jerry.baselib.common.flow;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wengyiming on 2017/7/21.
 */

public class FloatMenuView extends View {

    public static final int STATUS_LEFT = 3;//展开左边菜单
    public static final int STATUS_RIGHT = 4;//展开右边菜单

    private int mStatus = STATUS_RIGHT;//默认右边

    private Paint mPaint;//画笔
    private int mBackgroundColor = 0x00FFFFFF;//默认背景颜色 完全透明的白色

    private int mMenuBackgroundColor = -1;//菜单的背景颜色

    private RectF mBgRect;//菜单的背景矩阵
    private int mItemWidth = dip2px(50);//菜单项的宽度
    private int mItemHeight = dip2px(50);//菜单项的高度
    private int mItemLeft = 0;//菜单项左边的默认偏移值，这里是0
    private int mCorner = dip2px(2);//菜单背景的圆角多出的宽度


    private int mRadius = dip2px(4);//红点消息半径
    private final int mRedPointRadiuWithNoNum = dip2px(3);//红点圆半径

    private int mFontSizePointNum = sp2px(10);//红点消息数字的文字大小

    private int mFontSizeTitle = sp2px(12);//菜单项的title的文字大小
    private float mFirstItemTop;//菜单项的最小y值，上面起始那条线
    private boolean mDrawNum = false;//是否绘制数字，false则只绘制红点
    private boolean cicleBg = false;//菜单项背景是否绘制成圆形，false则绘制矩阵

    private List<FloatItem> mItemList = new ArrayList<>();//菜单项的内容
    private List<RectF> mItemRectList = new ArrayList<>();//菜单项所占用位置的记录，用于判断点击事件

    private SimpleMenuClickListener mOnMenuClickListener;//菜单项的点击事件回调

    private ObjectAnimator mAlphaAnim;//消失关闭动画的透明值

    //设置菜单内容集合
    public void setItemList(List<FloatItem> itemList) {
        mItemList = itemList;
    }

    //设置是否绘制红点数字
    public void drawNum(boolean drawNum) {
        mDrawNum = drawNum;
    }

    //设置是否绘制圆形菜单，否则矩阵
    public void setCicleBg(boolean cicleBg) {
        this.cicleBg = cicleBg;
    }

    //用于标记所依赖的view的screen的坐标，实际view的坐标是window坐标，所以这里后面会减去状态栏的高度


    //设置菜单的背景颜色
    public void setMenuBackgroundColor(int mMenuBackgroundColor) {
        this.mMenuBackgroundColor = mMenuBackgroundColor;
    }

    //设置这个view（整个屏幕）的背景，这里默认透明
    public void setBgColor(int backgroundColor) {
        this.mBackgroundColor = backgroundColor;
    }


    //下面开始的注释我写不动了，看不懂的话请自行领悟吧
    public FloatMenuView(Context context) {
        super(context);
    }

    public FloatMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FloatMenuView(Context baseContext, int status) {
        super(baseContext);
        mStatus = status;
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        mBgRect = new RectF(0, 0, screenWidth, screenHeight);
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mItemWidth * mItemList.size(), mItemHeight);
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(sp2px(12));

        mAlphaAnim = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0f);
        mAlphaAnim.setDuration(50);
        mAlphaAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(final Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mOnMenuClickListener != null) {
                    removeView();
                    mOnMenuClickListener.dismiss();
                }
            }

            @Override
            public void onAnimationCancel(final Animator animation) {

            }

            @Override
            public void onAnimationRepeat(final Animator animation) {

            }
        });

        mFirstItemTop = 0;
        if (mStatus == STATUS_LEFT) {
            mItemLeft = 0;
        } else {
            mItemLeft = 0;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (mStatus) {
            case STATUS_LEFT:
                drawBackground(canvas);
                drawFloatLeftItem(canvas);
                break;
            case STATUS_RIGHT:
                drawBackground(canvas);
                drawFloatLeftItem(canvas);
                break;
            default:
                break;
        }
        super.onDraw(canvas);
    }

    private void drawBackground(Canvas canvas) {
        mPaint.setColor(mBackgroundColor);
        canvas.drawRect(mBgRect, mPaint);

    }

    private void drawFloatLeftItem(Canvas canvas) {
        mItemRectList.clear();
        for (int i = 0; i < mItemList.size(); i++) {
            mPaint.setColor(mMenuBackgroundColor);
            mItemRectList.add(new RectF(mItemLeft + i * mItemWidth, mFirstItemTop, mItemLeft + mItemWidth + i * mItemWidth, mFirstItemTop + mItemHeight));
            mPaint.setColor(mItemList.get(i).bgColor);
            drawIconTitleDot(canvas, i);
        }
    }


    private void drawIconTitleDot(Canvas canvas, int position) {
        FloatItem floatItem = mItemList.get(position);

        if (floatItem.icon != null) {
            float centryX = mItemLeft + mItemWidth / 2 + (mItemWidth) * position;//计算每一个item的中心点x的坐标值
            float centryY = mFirstItemTop + mItemHeight / 2;//计算每一个item的中心点的y坐标值

            float left = centryX - mItemWidth / 4;//计算icon的左坐标值 中心点往左移宽度的四分之一
            float right = centryX + mItemWidth / 4;

            float iconH = mItemHeight * 0.5f;//计算出icon的宽度 = icon的高度

            float textH = getTextHeight(floatItem.getTitle(), mPaint);
            float paddH = (mItemHeight - iconH - textH - mRadius) / 2;//总高度减去文字的高度，减去icon高度，再除以2就是上下的间距剩余

            float top = centryY - mItemHeight / 2 + paddH;//计算icon的上坐标值
            float bottom = top + iconH;//剩下的高度空间用于画文字

            //画icon
            mPaint.setColor(floatItem.titleColor);
            canvas.drawBitmap(floatItem.icon, null, new RectF(left, top, right, bottom), mPaint);
            mPaint.setColor(floatItem.titleColor);
            mPaint.setTextSize(mFontSizeTitle);
            //画menu title
            canvas.drawText(floatItem.title, centryX - getTextWidth(floatItem.getTitle(), mPaint) / 2,
                centryY + iconH / 2 + getTextHeight(floatItem.getTitle(), mPaint) / 2, mPaint);
        }
    }


    public void startAnim() {
        if (mItemList.size() == 0) {
            return;
        }
        invalidate();
    }


    public void dismiss() {
        if (!mAlphaAnim.isRunning()) {
            mAlphaAnim.start();
        }
    }

    private void removeView() {
        ViewGroup vg = (ViewGroup) this.getParent();
        if (vg != null) {
            vg.removeView(this);
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        if (visibility == GONE) {
            if (mOnMenuClickListener != null) {
                mOnMenuClickListener.dismiss();
            }
        }
        super.onWindowVisibilityChanged(visibility);


    }

    public void setOnMenuClickListener(SimpleMenuClickListener onMenuClickListener) {
        this.mOnMenuClickListener = onMenuClickListener;
    }

    public static class SimpleMenuClickListener implements OnMenuClickListener {

        @Override
        public void onItemClick(int position, String title) {

        }

        @Override
        public void dismiss() {

        }
    }

    private interface OnMenuClickListener {

        void onItemClick(int position, String title);

        void dismiss();
    }

    private long touchIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < mItemRectList.size(); i++) {
                    if (isPointInRect(new PointF(event.getX(), event.getY()), mItemRectList.get(i))) {
                        touchIndex = i;
                        return true;
                    }
                }
                dismiss();
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < mItemRectList.size(); i++) {
                    if (mOnMenuClickListener != null && isPointInRect(new PointF(event.getX(), event.getY()), mItemRectList.get(i))
                        && touchIndex == i) {
                        mOnMenuClickListener.onItemClick(i, mItemList.get(i).title);
                        return true;
                    }
                }
                touchIndex = -1;
                break;
            default:
                break;
        }
        return false;
    }

    private boolean isPointInRect(PointF pointF, RectF targetRect) {
        return pointF.x >= targetRect.left && pointF.x <= targetRect.right && pointF.y >= targetRect.top && pointF.y <= targetRect.bottom;
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private float getTextHeight(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height() / 1.1f;
    }

    private float getTextWidth(String text, Paint paint) {
        return paint.measureText(text);
    }
}
