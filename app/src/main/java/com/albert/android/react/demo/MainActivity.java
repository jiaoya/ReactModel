package com.albert.android.react.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.albert.android.react.module.FileUtils;
import com.albert.android.react.module.RnCoreActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    SimpleDraweeView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.fresco_view);
        imageView.setImageURI("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2133031131,1997928181&fm=26&gp=0.jpg");

//        File file = new File(getFilesDir(), "index.js");
//        String path = this.getFilesDir().getAbsolutePath();
//        Log.e("paht", path + "---" + file.getAbsolutePath());
//
//
//        try {
//            File file1 = FileUtils.getAsseets(this, "index.android.bundle");
//            if (file1.exists()) {
//                Log.e("paht", path + "---" + file1.getFreeSpace());
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, RnCoreActivity.class));
            }
        });

        Button button = findViewById(R.id.btnTest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    FileUtils.dsdsd(MainActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
