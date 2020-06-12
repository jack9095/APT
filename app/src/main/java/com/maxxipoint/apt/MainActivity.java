package com.maxxipoint.apt;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.mobile.inject.ViewBinder;
import com.mobile.inject_annotation.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 调用自己定义的 ViewBinder
        ViewBinder.bind(this);
        tv.setText("Hi,ViewBinder!");
    }
}
