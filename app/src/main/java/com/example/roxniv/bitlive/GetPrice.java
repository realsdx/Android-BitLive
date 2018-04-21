package com.example.roxniv.bitlive;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;
import org.json.JSONException;


public class GetPrice {
    String api_url = "https://api.coindesk.com/v1/bpi/currentprice/INR.json";
    URL url_obj;
    HttpURLConnection url_con;
    BufferedReader data;
    String json_raw="";
    String line="";
    JSONObject json_obj=null;

    JSONObject getJSONData() throws IOException{
        try {
            url_obj = new URL(api_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        url_con = (HttpURLConnection) url_obj.openConnection();
        data = new BufferedReader(new InputStreamReader(url_con.getInputStream()));

        while ((line=data.readLine())!=null){
            json_raw+=line;
        }
        //Catch Json exception
        try {
            json_obj = new JSONObject(json_raw);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return json_obj;


    }

    String getINR() throws IOException, JSONException {
        JSONObject data= getJSONData();
        String INR = data.getJSONObject("bpi").getJSONObject("INR").getString("rate");
        //String USD = data.getJSONObject("bpi").getJSONObject("USD").getString("rate");
        return INR;
    }

    double getINRfloat() throws IOException, JSONException {
        JSONObject data= getJSONData();
        double INRfloat = Double.parseDouble(data.getJSONObject("bpi").getJSONObject("INR").getString("rate_float"));
        return INRfloat;
    }

    String getUSD() throws IOException, JSONException {
        JSONObject data= getJSONData();
        //String INR = data.getJSONObject("bpi").getJSONObject("INR").getString("rate");
        String USD = data.getJSONObject("bpi").getJSONObject("USD").getString("rate");
        return  USD;
    }
}