package com.example.admin.x5webviewdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private X5WebView mWebView;
    private ViewGroup mViewParent;

    private static final String mHomeUrl = "http://app.html5.qq.com/navi/index";
    private static final String TAG = "SdkDemo";
    private ValueCallback<Uri> uploadFile;
    private URL mIntentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getWindow().setFormat( PixelFormat.TRANSLUCENT );

        Intent intent = getIntent();
        if (intent != null) {
            try {
                mIntentUrl = new URL( intent.getData().toString() );
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {

            } catch (Exception e) {
            }
        }
        //
        try {
            if (Integer.parseInt( android.os.Build.VERSION.SDK ) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED );
            }
        } catch (Exception e) {
        }

        setContentView( R.layout.activity_main );
        mViewParent = (ViewGroup) findViewById( R.id.webView1 );

        init();

    }

    private void init() {
        mWebView = new X5WebView( this, null );
        mViewParent.addView( mWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT ) );

        mWebView.setWebViewClient( new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished( view, url );
            }
        } );

        mWebView.setWebChromeClient( new WebChromeClient() {

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm( arg0, arg1, arg2, arg3 );
            }

            View myVideoView;
            View myNormalView;
            IX5WebChromeClient.CustomViewCallback callback;

            /**
             * 全屏播放配置
             */
            @Override
            public void onShowCustomView(View view,
                                         IX5WebChromeClient.CustomViewCallback customViewCallback) {
//                FrameLayout normalView = (FrameLayout) findViewById( R.id.web_filechooser );
//                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
//                viewGroup.removeView( normalView );
//                viewGroup.addView( view );
//                myVideoView = view;
//                myNormalView = normalView;
//                callback = customViewCallback;
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView( myVideoView );
                    viewGroup.addView( myNormalView );
                }
            }

            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                                     JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert( null, arg1, arg2, arg3 );
            }
        } );

        mWebView.setDownloadListener( new com.tencent.smtt.sdk.DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                TbsLog.d( TAG, "url: " + s );
                new AlertDialog.Builder( MainActivity.this )
                        .setTitle( "allow to download？" )
                        .setPositiveButton( "yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Toast.makeText( MainActivity.this,
                                                "fake message: i'll download...", Toast.LENGTH_SHORT ).show();
                                    }
                                } )
                        .setNegativeButton( "no",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(
                                                MainActivity.this,
                                                "fake message: refuse download...",
                                                Toast.LENGTH_SHORT ).show();
                                    }
                                } )
                        .setOnCancelListener(
                                new DialogInterface.OnCancelListener() {

                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(
                                                MainActivity.this,
                                                "fake message: refuse download...",
                                                Toast.LENGTH_SHORT ).show();
                                    }
                                } ).show();
            }
        } );

        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess( true );
        webSetting.setLayoutAlgorithm( WebSettings.LayoutAlgorithm.NARROW_COLUMNS );
        webSetting.setSupportZoom( true );
        webSetting.setBuiltInZoomControls( true );
        webSetting.setUseWideViewPort( true );
        webSetting.setSupportMultipleWindows( false );
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled( true );
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled( true );
        webSetting.setJavaScriptEnabled( true );
        webSetting.setGeolocationEnabled( true );
        webSetting.setAppCacheMaxSize( Long.MAX_VALUE );
        webSetting.setAppCachePath( this.getDir( "appcache", 0 ).getPath() );
        webSetting.setDatabasePath( this.getDir( "databases", 0 ).getPath() );
        webSetting.setGeolocationDatabasePath( this.getDir( "geolocation", 0 )
                .getPath() );
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState( WebSettings.PluginState.ON_DEMAND );
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        long time = System.currentTimeMillis();
        if (mIntentUrl == null) {
            mWebView.loadUrl( mHomeUrl );
        } else {
            mWebView.loadUrl( mIntentUrl.toString() );
        }
        TbsLog.d( "time-cost", "cost time: "
                + (System.currentTimeMillis() - time) );
        CookieSyncManager.createInstance( this );
        CookieSyncManager.getInstance().sync();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else
                return super.onKeyDown( keyCode, event );
        }
        return super.onKeyDown( keyCode, event );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TbsLog.d( TAG, "onActivityResult, requestCode:" + requestCode
                + ",resultCode:" + resultCode );

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue( result );
                        uploadFile = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue( null );
                uploadFile = null;
            }

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent == null || mWebView == null || intent.getData() == null)
            return;
        mWebView.loadUrl( intent.getData().toString() );
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null)
            mWebView.destroy();
        super.onDestroy();
    }
}
