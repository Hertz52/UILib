package com.zhongdihang.uilib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


public class MyVerticalTrackView extends View {
    public MyVerticalTrackView(Context context) {
        this(context, null);
    }

    public MyVerticalTrackView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyVerticalTrackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private Paint mCirclePaint;

    private Paint mLinePaint;

    private Bitmap mBitmap;

    private int colorAccent;
    private int colorDim;

    private void init() {
        colorAccent = Color.parseColor("#1989FA");
        colorDim = Color.parseColor("#DDDEE4");

        mCirclePaint = new Paint();
        mCirclePaint.setColor(colorDim);
        mCirclePaint.setAntiAlias(true);

        mLinePaint = new Paint();
        mLinePaint.setColor(colorAccent);
        mLinePaint.setStrokeWidth(dp2px(1));


        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_ok);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int centerX = getWidth() / 2;

        final int paddingTop = dp2px(14);

        final int circleRadius = dp2px(4);

        final int imgSideLen = dp2px(24);

        final int circleCenterX = centerX;
        final int circleCenterY = paddingTop + imgSideLen / 2;

        int lineStartY = 0;
        int lineEndY = getHeight();

        switch (mPosition) {
            case POSITION_START: {
                lineStartY = circleCenterY;
                break;
            }

            case POSITION_END: {
                lineEndY = circleCenterY;
                break;
            }
        }

        // line
        switch (mStatus) {
            case STATUS_ARRIVED:
            case STATUS_NOT_REACHED: {
                mLinePaint.setColor(mStatus == STATUS_NOT_REACHED ? colorDim : colorAccent);
                canvas.drawLine(centerX, lineStartY, centerX, lineEndY, mLinePaint);
                break;
            }

            case STATUS_IN_PROCESS: {
                mLinePaint.setColor(colorAccent);
                canvas.drawLine(centerX, lineStartY, centerX, circleCenterY, mLinePaint);

                mLinePaint.setColor(colorDim);
                canvas.drawLine(centerX, circleCenterY, centerX, lineEndY, mLinePaint);
                break;
            }
        }

        switch (mStatus) {
            case STATUS_ARRIVED:
            case STATUS_IN_PROCESS: {
                // bitmap
                @SuppressLint("DrawAllocation")
                Rect dst = new Rect(circleCenterX - imgSideLen / 2,
                        circleCenterY - imgSideLen / 2,
                        circleCenterX + imgSideLen / 2,
                        circleCenterY + imgSideLen / 2);

                canvas.drawBitmap(mBitmap, null, dst, null);
                break;
            }

            case STATUS_NOT_REACHED: {
                // circle
                canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, mCirclePaint);
                break;
            }
        }
    }

    public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static final int POSITION_START = 0x10;
    public static final int POSITION_MIDDLE = 0x20;
    public static final int POSITION_END = 0x30;

    private int mPosition = POSITION_START;

    public static final int STATUS_ARRIVED = 0;
    public static final int STATUS_IN_PROCESS = 1;
    public static final int STATUS_NOT_REACHED = 2;

    private int mStatus = STATUS_ARRIVED;

    public void setProgress(int position, int status) {
        mPosition = position;
        mStatus = status;

        invalidate();
    }

}
