package com.example.roxniv.bitlive;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.example.roxniv.bitlive.GetPrice;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import java.io.IOException;

public class LivePriceUpdate implements Runnable {
    String price = "";
    String price_pre = "";
    final TextView main_price;
    final TextView bit_info;
    GraphView graph;
    private LineGraphSeries<DataPoint> mSeries;
    private int lastX=0;
    private double price_last=0;
    private double price_now=0;


    LivePriceUpdate(Activity main_activity) {
        graph = main_activity.findViewById(R.id.graph);
        main_price = main_activity.findViewById(R.id.main_price);
        bit_info = main_activity.findViewById(R.id.bit_info);


        //Init Graph
        graph.setTitle("Live BTC");
        graph.setTitleTextSize(45);
        graph.setTitleColor(main_activity.getResources().getColor(android.R.color.holo_red_dark));
        graph.getGridLabelRenderer().setGridColor(Color.rgb(33, 150, 243));
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.rgb(33, 150, 243));
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.rgb(33, 150, 243));
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(20);
        graph.getViewport().setMinY(4);
        //graph.getViewport().setMaxYAxisSize(8000.00);
        //graph.getViewport().setMaxY(8000.00);
        graph.getGridLabelRenderer().setLabelVerticalWidth(100);
    }
        @Override
        public void run() {
            //Init Line Series
            mSeries = new LineGraphSeries<DataPoint>();
            mSeries.setDrawDataPoints(true);
            mSeries.setDrawBackground(true);
            mSeries.setColor(Color.RED);
            mSeries.setThickness(3);
            mSeries.setBackgroundColor(0x40FFFFFF);
            graph.addSeries(mSeries);


            while (true) {
                try {
                    Log.d("db",Thread.currentThread().getName().toString());
                    price = new GetPrice().getINR();

                    //Ui cahnge Goes here
                    main_price.setText("" + price + " INR");
                    //Log.d("db","Price:"+price+" :: PricePre:"+price_pre);

                    if(!price.equals(price_pre)) {
                        price_now = new GetPrice().getINRfloat();
                        UpdateLiveGraph(price_now);
                        //Updating Cahnge
                        if(price_last != 0.0){
                            if((price_now-price_last)>0) {
                                bit_info.setText(String.format("+%.2f", (price_now - price_last)));
                            }
                            else {
                                bit_info.setText(String.format("%.2f", (price_now - price_last)));
                            }
                        }
                        Log.d("up","Graph Udated :"+!price.equals(price_pre)+" change:"+String.format("%.2f",(price_now-price_last)));
                    }
                    price_last = price_now;
                    price_pre = price;
                    Thread.sleep(3000);
                    Log.d("db","Slept 3000 milisec");


                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                } catch (JSONException e) {
                    e.printStackTrace();
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }


        }

    void UpdateLiveGraph(double p){
        double y = p/100.00;
        mSeries.appendData(new DataPoint(lastX++, y), false, 20);
    }
}
