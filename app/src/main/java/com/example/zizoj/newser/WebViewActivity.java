package com.example.zizoj.newser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class WebViewActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        ImageView Backpresd = (ImageView)findViewById(R.id.Goback);

        progressBar = (ProgressBar)findViewById(R.id.WebviewpProgress);


        Backpresd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(WebViewActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


      String Url = String.valueOf(getIntent().getSerializableExtra("NewsUrl"));

        WebView webView = (WebView)findViewById(R.id.News_Web_View);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                setTitle("Loading...");

            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                setTitle(view.getTitle());

            }
        });
        webView.loadUrl(Url);



    }
}
