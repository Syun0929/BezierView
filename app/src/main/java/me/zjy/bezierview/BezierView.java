package me.zjy.bezierview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BezierView extends View {

    private Paint bezierPaint;
    private float ctrlPointX;
    private float ctrlPointY;
    private float startPointX;
    private float startPointY;
    private float endPointX;
    private float endPointY;
    private float rangePoint;
    private Path path = new Path();

    public BezierView(Context context) {
        super(context);
    }

    public BezierView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        bezierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bezierPaint.setStyle(Paint.Style.FILL);
        bezierPaint.setColor(Color.BLUE);

        startPointX = 0f;
        startPointY = 0f;
        endPointX = 0f;
        endPointY = 0f;
        initCtrlPoint();
        updateRangePoint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        endPointX = width;
        initCtrlPoint();
        updateRangePoint();
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        path.moveTo(startPointX,startPointY);
        path.quadTo(ctrlPointX,ctrlPointY,endPointX,endPointY);
        canvas.drawPath(path,bezierPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                if(event.getX()<rangePoint+100 && event.getX()>rangePoint-100) {//控制可以触摸移动的范围，可以自定义修改
//                    ctrlPointX = event.getX();
                    ctrlPointY = event.getY();//目前只让控制点上下移动
                    invalidate();
                    return true;
                }
        }
        return true;
    }

    private void initCtrlPoint(){
        ctrlPointX = (startPointX+endPointX)/2;
        ctrlPointY = ctrlPointX/2;
    }

    public void setBezierColor(int color){
        this.bezierPaint.setColor(color);
    }

    public float getCtrlPointX() {
        return ctrlPointX;
    }

    public void setCtrlPointX(float ctrlPointX) {
        this.ctrlPointX = ctrlPointX;
    }

    public float getCtrlPointY() {
        return ctrlPointY;
    }

    public void setCtrlPointY(float ctrlPointY) {
        this.ctrlPointY = ctrlPointY;
    }

    public float getStartPointX() {
        return startPointX;
    }

    public void setStartPointX(float startPointX) {
        this.startPointX = startPointX;
        updateRangePoint();
    }

    public float getStartPointY() {
        return startPointY;
    }

    public void setStartPointY(float startPointY) {
        this.startPointY = startPointY;
    }

    public float getEndPointX() {
        return endPointX;
    }

    public void setEndPointX(float endPointX) {
        this.endPointX = endPointX;
        updateRangePoint();
    }

    public float getEndPointY() {
        return endPointY;
    }

    public void setEndPointY(float endPointY) {
        this.endPointY = endPointY;
    }

    public float getRangePoint() {
        return rangePoint;
    }

    void updateRangePoint(){
        this.rangePoint = (getStartPointX()+getEndPointX())/2;
    }

}
