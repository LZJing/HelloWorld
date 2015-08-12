package com.buaa.lizha.helloworld;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


public class MainActivity extends Activity {

    ImageView mImageView = null;

    Bitmap bmp = null;

    String imageUrl = "http://pic.nipic.com/2007-11-09/2007119122519868_2.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.image);
        //testLoadImage();
        //testDisplayImage();
        testLoadImageSync();


    }

    //使用回调
    public void testLoadImage(){

        ImageLoader.getInstance().loadImage(imageUrl, new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                mImageView.setImageBitmap(loadedImage);
            }
        });
    }

    //直接加载
    public void testDisplayImage(){
        ImageLoader.getInstance().displayImage(imageUrl,mImageView);
    }

    //同步加载方式，该方式应该自己建立子线程使用，在主线程中是Android不允许的。
    public void testLoadImageSync(){

        Thread thread =new Thread(new Runnable(){
            @Override
            public void run() {
                bmp = ImageLoader.getInstance().loadImageSync(imageUrl);
                Log.v("Tag", "getBitMap 第二版");
                Message message = new Message();
                message.what =1;
                handler.sendMessage(message);
            }
        });
        thread.start();
    }
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            Log.v("Tag","getInHandler");
            switch (msg.what){
                case 1:
                    Log.v("Tag","case1");
                    mImageView.setImageBitmap(bmp);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };

}
