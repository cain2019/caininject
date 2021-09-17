package com.cain.inject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.core.inject.InjectLayout;
import com.core.inject.InjectManager;
import com.core.inject.InjectView;
import com.core.inject.OnClick;

@InjectLayout(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private  String TAG = this.getClass().getSimpleName();

    @InjectView(R.id.button)
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.inject(this);
    }


    @OnClick(R.id.button)
    public void setOnClick(View v) {
        Log.e(TAG,"setOnClickListener");
    }

}
