package com.liu.heart;

import android.animation.Animator;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Random;

public class MainActivity extends Activity {
    private TitanicTextView textView;
    private FloatHeartView fhv;
    private ImageView iv;
    Titanic titanic;
    MediaPlayer mediaPlayer;
    ConstraintLayout parent;
    MyHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new MyHandler(this);
        parent = findViewById(R.id.parent);
        fhv = findViewById(R.id.fhv);
        iv = findViewById(R.id.tagIv);
        handler.postAtTime(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        },300);
        parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    handler.obtainMessage(1,event).sendToTarget();
                }
                return true;
            }
        });
        textView = findViewById(R.id.titanic);
        titanic = new Titanic();
        mediaPlayer = MediaPlayer.create(this,R.raw.breathless);
        initMusic();
        titanic.setAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        titanic.start(textView);
    }
    public void initMusic(){
        if (mediaPlayer != null){
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();

        }
    }

    /**
     * 开始动画
     * @param event
     */
//    public void startHeart(MotionEvent event){
//
//    }
    class MyHandler extends Handler{
        WeakReference<MainActivity> activityWeakReference;
        MyHandler(MainActivity mainActivity){
            this.activityWeakReference = new WeakReference<>(mainActivity);
        }
        @Override
        public void handleMessage(Message msg) {
//            MotionEvent event = (MotionEvent) msg.obj;
            MainActivity activity = activityWeakReference.get();
            activity.fhv.addFloatHeart(activity.iv,getResId());
            handler.sendEmptyMessageDelayed(0,300);
            super.handleMessage(msg);
        }
    }
    /***
     * 随机获取图片
     * @return
     */
    public int getResId() {
        int min = 1;
        int max = 6;
        Random random = new Random();
        int num = random.nextInt(max) % (max - min + 1) + min;
        switch (num) {
            case 1:
                return R.mipmap.heart1;
            case 2:
                return R.mipmap.heart2;
            case 3:
                return R.mipmap.heart3;
            case 4:
                return R.mipmap.heart4;
            case 5:
                return R.mipmap.heart5;
            case 6:
                return R.mipmap.heart6;
        }
        return R.mipmap.heart1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
    }
}
