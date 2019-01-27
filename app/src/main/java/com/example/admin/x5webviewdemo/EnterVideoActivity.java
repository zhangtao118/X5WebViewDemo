package com.example.admin.x5webviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tencent.smtt.sdk.TbsVideo;


//        public static boolean canUseTbsPlayer(Context context)
//        //判断当前Tbs播放器是否已经可以使用。
//        public static void openVideo(Context context, String videoUrl)
//        //直接调用播放接口，传入视频流的url
//        public static void openVideo(Context context, String videoUrl, Bundle extraData)
//extraData对象是根据定制需要传入约定的信息，没有需要可以传如null
//extraData可以传入key: "screenMode", 值: 102, 来控制默认的播放UI
//类似: extraData.putInt("screenMode", 102); 来实现默认全屏+控制栏等UI

public class EnterVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_enter_video );

        findViewById( R.id.button ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extraData=new Bundle(  );
                extraData.putInt("screenMode", 102);
                if(TbsVideo.canUseTbsPlayer( EnterVideoActivity.this )) {
                    String url="http://kamaoguanjia.llyzf.cn/kamao/credit_card_tutorial.mp4";
                    TbsVideo.openVideo( EnterVideoActivity.this, url, extraData );
                }
            }
        } );

    }
}
