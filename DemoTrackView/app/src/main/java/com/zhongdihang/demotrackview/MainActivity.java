package com.zhongdihang.demotrackview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTrackView = findViewById(R.id.track_view);
    }

    MyVerticalTrackView mTrackView;

    // TODO: 2020/3/11
    public void click1(View v) {
        mTrackView.setProgress(MyVerticalTrackView.POSITION_START, 0);
    }

    public void click2(View v) {
        mTrackView.setProgress(MyVerticalTrackView.POSITION_MIDDLE, 0);
    }

    public void click3(View v) {
        mTrackView.setProgress(MyVerticalTrackView.POSITION_END, 0);
    }
}

