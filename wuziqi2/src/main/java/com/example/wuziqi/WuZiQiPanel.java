package com.example.wuziqi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.x;
import static android.R.attr.y;

/**
 * Created by gsh on 2017/6/8.
 */

public class WuZiQiPanel extends View {
    private int mPanelWidth;
    private float mLineHeigth;
    private int MAX_LINE = 10;
    private Paint paint = new Paint();
    private float ratioPieceOfLineHight = 3 * 1.0f / 4;
    private Bitmap mWhtieP;
    private Bitmap mBlackP;
    private ArrayList<Point> mWhiteArray = new ArrayList<>();
    private ArrayList<Point> mBlackArray = new ArrayList<>();
    private boolean isWhite = true;
    private boolean isGameOver;
    private boolean isWhiteWinner;

    public WuZiQiPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();


    }

    private void init() {
        paint.setColor(0X88000000);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        mWhtieP = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mBlackP = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = Math.min(widthSize, heightSize);
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth = w;
        mLineHeigth = mPanelWidth * 1.0f / MAX_LINE;
        int PSize = (int) (mLineHeigth * ratioPieceOfLineHight);
        mWhtieP = Bitmap.createScaledBitmap(mWhtieP, PSize, PSize, false);
        mBlackP = Bitmap.createScaledBitmap(mBlackP, PSize, PSize, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawPiece(canvas);
        checkGameOver();
    }

    private void checkGameOver() {
        boolean whiteWin = checkFiveInLine(mWhiteArray);
        boolean blackWin = checkFiveInLine(mBlackArray);
        if (whiteWin || blackWin) {
            isGameOver = true;
            isWhiteWinner = whiteWin;
            String text;
            if (isWhiteWinner) {
                text = "白胜";
            } else {
                text = "黑胜";
            }
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkFiveInLine(ArrayList<Point> points) {
        for (Point p : points) {
            int x = p.x;
            int y = p.y;
            boolean win = checkHorzontalWin(x, y, points);
            if(win) return true;
            win = checkVetialWin(x, y, points);
            if(win) return true;
            win = checkLeftDiagonalWin(x, y, points);
            if(win) return true;
            win = checkRightDiagonalWin(x, y, points);
            if(win) return true;

        }
        return false;
    }

    private boolean checkHorzontalWin(int x, int y, ArrayList<Point> points) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (points.contains(new Point(x + i, y))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        for (int i = 1; i < 5; i++) {
            if (points.contains(new Point(x - i, y))) {
                count++;
            } else {
                break;
            }


            if (count == 5) {
                return true;
            }
        }
        return false;

    }
    private boolean checkVetialWin(int x, int y, ArrayList<Point> points) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (points.contains(new Point(x , y+i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        for (int i = 1; i < 5; i++) {
            if (points.contains(new Point(x , y-i))) {
                count++;
            } else {
                break;
            }


            if (count == 5) {
                return true;
            }
        }
        return false;

    }
    private boolean checkLeftDiagonalWin(int x, int y, ArrayList<Point> points) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (points.contains(new Point(x + i, y-i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        for (int i = 1; i < 5; i++) {
            if (points.contains(new Point(x - i, y+i))) {
                count++;
            } else {
                break;
            }


            if (count == 5) {
                return true;
            }
        }
        return false;

    }
    private boolean checkRightDiagonalWin(int x, int y, ArrayList<Point> points) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (points.contains(new Point(x + i, y+i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        for (int i = 1; i < 5; i++) {
            if (points.contains(new Point(x - i, y-i))) {
                count++;
            } else {
                break;
            }


            if (count == 5) {
                return true;
            }
        }
        return false;

    }


    private void drawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeigth;

        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);
            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, paint);
            canvas.drawLine(y, startX, y, endX, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (isGameOver) {
            return false;
        }
        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = getVaildPoint(x, y);
            if (mWhiteArray.contains(p) || mBlackArray.contains(p)) {
                return false;
            }
            if (isWhite) {
                mWhiteArray.add(p);
            } else {
                mBlackArray.add(p);
            }
            invalidate();
            isWhite = !isWhite;

        }
        return true;
    }

    private Point getVaildPoint(int x, int y) {
        return new Point((int) (x / mLineHeigth), (int) (y / mLineHeigth));
    }

    private void drawPiece(Canvas canvas) {
        int n1 = mWhiteArray.size();
        int n2 = mBlackArray.size();
        for (int i = 0; i < n1; i++) {
            Point whitePoint = mWhiteArray.get(i);
            canvas.drawBitmap(mWhtieP, (whitePoint.x + (1 - ratioPieceOfLineHight) / 2) * mLineHeigth,
                    (whitePoint.y + (1 - ratioPieceOfLineHight) / 2) * mLineHeigth, null);
        }
        for (int i = 0; i < n2; i++) {
            Point blackPoint = mBlackArray.get(i);
            canvas.drawBitmap(mBlackP, (blackPoint.x + (1 - ratioPieceOfLineHight) / 2) * mLineHeigth,
                    (blackPoint.y + (1 - ratioPieceOfLineHight) / 2) * mLineHeigth, null);
        }
    }
    private static final String INSTANCE="instance";
    private static final String INSTANCE_GAME_OVER="instance_game_over";
    private static final String INSTANCE_WHITE_ARRAY="instance_white_array";
    private static final String INSTANCE_BLACK_ARRAY="instance_black_array";
    private static final String INSTANCE_ISWHITE="instance_ISWHITE";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle =new Bundle();
        bundle.putParcelable(INSTANCE,super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER,isGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY,mWhiteArray);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY,mBlackArray);
        bundle.putBoolean(INSTANCE_ISWHITE,isWhite);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof  Bundle){
            Bundle bundle= (Bundle) state;
            isGameOver=bundle.getBoolean(INSTANCE_GAME_OVER);
            mBlackArray=bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
            mWhiteArray=bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
           // isWhite=bundle.getBoolean(INSTANCE_ISWHITE);
        }
        super.onRestoreInstanceState(state);
    }
    public void reStart(){
        mWhiteArray.clear();
        mBlackArray.clear();
        isGameOver=false;
        isWhiteWinner=false;
        invalidate();
    }
}
