package com.burak.healven.helpful;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.burak.healven.Login;
import com.burak.healven.R;
import com.burak.healven.additional_pages.Forsale;
import com.burak.healven.additional_pages.Others;
import com.burak.healven.additional_pages.Socialmedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class helperFunctions {

    public static boolean menuClickOptions(int id, Activity con){
        // Handle item selection
        switch (id){
            case R.id.logout_actbar:
                con.finish();
                con.startActivity(new Intent(con, Login.class));
                Toast.makeText(con, "Logout", Toast.LENGTH_LONG).show();
                break;
            case R.id.settings_actbar:
                Toast.makeText(con, "Settings pressed", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    public static void onMainMenuItemPressed(Context con, int id) {
        switch (id){
            case 0:
                con.startActivity(new Intent(con, Forsale.class));
                break;
            case 1:
                con.startActivity(new Intent(con, Socialmedia.class));
                break;
            case 2:
                con.startActivity(new Intent(con, Others.class));
                break;
        }
    }

        public static void onForSaleItemPressed(Context con, int id){
        Toast.makeText(con, "Item with id " +id+ " pressed", Toast.LENGTH_LONG ).show();

    }

    public static void onSocialMediaItemPressed(Context con, int id){
        Toast.makeText(con, "Item with id " +id+ " pressed", Toast.LENGTH_LONG ).show();

    }


    public static void exitOnBackButton(final Activity act){
        final AlertDialog.Builder exit = new AlertDialog.Builder(act);
        exit.setTitle("Exit");
        exit.setMessage("Do you really want to exit?");
        exit.setNegativeButton("No", null);
        exit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              act.finish();
              act.finishAffinity();
            }
        });
        exit.show();
    }

    public static void onDislikeButtonPressed(Context mContext, int position) {
        Toast.makeText(mContext, "Disliked Item with id " +position+ " pressed", Toast.LENGTH_LONG ).show();
    }



    public static SwipeRefreshLayout.OnRefreshListener refreshListener(final Context con, final SwipeRefreshLayout swipeRefreshLayout) {

        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(con, "Refreshed", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        };

    }

    public static boolean addItemForSale(String toString, String toString1, String toString2, String toString3) {
        return true;
    }

    public static boolean addItemSocialMedia(String toString, String toString1) {
        return  true;
    }

    public static boolean sendJSONToServer(String jsonObject) throws Exception {
        URL url = new URL("https://healvenapi20191029100941.azurewebsites.net/api/Users");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try{
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(httpURLConnection.getOutputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
            outputStreamWriter.write(jsonObject);

        }finally {
            httpURLConnection.disconnect();

        }
        return httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED;
    }


    public static JSONObject getJSONFromServer(final String mail) throws IOException, JSONException {
        URL url = new URL("https://healvenapi20191029100941.azurewebsites.net/api/Users?mail=" + mail);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        StringBuilder jsonObject = new StringBuilder();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setReadTimeout(3000 /* milliseconds */ );
        httpURLConnection.setConnectTimeout(5000 /* milliseconds */ );

                try {
                    if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                        int c;

                        while((c = inputStreamReader.read()) != -1){
                            jsonObject.append((char)c);
                        }
                        inputStreamReader.close();
                    }
                        httpURLConnection.disconnect();
                } catch (IOException e) {
                   e.printStackTrace();
                }

        if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            return new JSONObject(jsonObject.toString());
        }else{
            return null;
        }

    }

}
