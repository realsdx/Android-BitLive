package com.example.roxniv.bitlive;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.roxniv.bitlive.LivePriceUpdate;

public class MainActivity extends AppCompatActivity {
    Button start_update;
    Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        final Thread update_th = new Thread(new LivePriceUpdate());
        start_update = (Button)findViewById(R.id.button);


        start_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandlerThread ht = new HandlerThread("ht");
                ht.start();
                mHandler = new Handler(ht.getLooper());
//                mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new LivePriceUpdate(MainActivity.this));
                start_update.setEnabled(false);

            }
        });




    }

}
