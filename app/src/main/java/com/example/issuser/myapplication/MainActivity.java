package com.example.issuser.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import github.nisrulz.screenshott.ScreenShott;

public class MainActivity extends Activity {

    private LinearLayout activityMain;
    private Button button;
    private ImageView imageView;
    static int count = 0;
    private Canvas canvas;
    Bitmap bitmap_view = null;
    Bitmap bitmap_tmp = null;
    List<Shape> shapeList = new ArrayList<Shape>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        activityMain = (LinearLayout) findViewById(R.id.activity_main);
        imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            int startX;
            int startY;
            int endX;
            int endY;
            Paint paint = new Paint();
            Path path = new Path();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                paint.setColor(Color.BLUE);
                if(bitmap_view == null)
                    return true;
//                Shape shape = null;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        endX = (int) event.getX();
                        endY = (int) event.getY();
                        //1.重置页面
                        resetCanvas();
                        //2.画出list中保存的操作
                        drawFromList(canvas,paint,shapeList);
                        //3.画出本次拖动状态的操作
                         Shape shape = new LineShape(startX,startY,endX,endY);
//                        canvas.drawLine(startX,startY,endX,endY,paint);
                        shape.draw(canvas,paint);
                        imageView.setImageBitmap(bitmap_view);
                        break;
                    case MotionEvent.ACTION_UP:
                        endX = (int) event.getX();
                        endY = (int) event.getY();
                        //1.重置画布
                        resetCanvas();
                        //2.将新的操作加入list
                         Shape shape1 = new LineShape(startX,startY,endX,endY);
                        shapeList.add(shape1);
                        //3.画出list中保存的操作
                        drawFromList(canvas,paint,shapeList);
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
    }

    public void onClick(View view){
        Toast.makeText(this,count+"",Toast.LENGTH_SHORT).show();

//        Bitmap bitmap_view = ScreenShott.getInstance().takeScreenShotOfView(view);
//
//// RootView
//        Bitmap bitmap_rootview = ScreenShott.getInstance().takeScreenShotOfRootView(view);
//
//// Just the View without any constraints
//        Bitmap bitmap_hiddenview = ScreenShott.getInstance().takeScreenShotOfJustView(view);

        if(count >=2 )
            count =0;
        switch (count){
            case 0:
                bitmap_view = ScreenShott.getInstance().takeScreenShotOfView(activityMain);
                break;
            case 1:
                bitmap_view = ScreenShott.getInstance().takeScreenShotOfView(activityMain);
                break;
            default:
                break;
        }
        bitmap_tmp = bitmap_view.copy(Bitmap.Config.ARGB_8888,true);
        imageView.setImageBitmap(bitmap_view);
        canvas = new Canvas(bitmap_view);
        count ++;
        shapeList.clear();
    }

    public Bitmap takeScreenShotOfView(View v) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);

        // creates immutable clone
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false); // clear drawing cache
        return b;
    }

    /*
    * 重置画布
    *
    * */
    public void resetCanvas(){
//        bitmap_view = ScreenShott.getInstance().takeScreenShotOfView(activityMain);
        bitmap_view =  bitmap_tmp.copy(Bitmap.Config.ARGB_8888,true);
        imageView.setImageBitmap(bitmap_view);
        canvas = new Canvas(bitmap_view);
    }


    /*
    画出list中保存的操作
     */
    public void drawFromList(Canvas canvas,Paint paint,List<Shape> list){
        for (Shape shape : list){
            shape.draw(canvas,paint);
        }
    }

    /*
    *
    * 撤销功能
    *
    * */
    public void undo(){
        if(shapeList != null && shapeList.size() > 0){
            //删除最后一次操作
            shapeList.remove(shapeList.size() - 1);
        }
    }
}
