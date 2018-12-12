package com.ari_smart_switch.arismartswitch;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ProgressBar progressBar;
    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        showDevices();
    }


    private void showDevices() {
        /*PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_SHOW_DEVICES, null, CODE_GET_REQUEST);
        request.execute();*/
        HashMap<String, String> params = new HashMap<>();
        params.put("custId", "3");
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_SHOW_DEVICES, params, CODE_POST_REQUEST);
        request.execute();

    }

    private void updateHero(String sn, String status) {
        HashMap<String, String> params = new HashMap<>();
        params.put("sn", sn);
        params.put("status", status);
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_DEVICE, params, CODE_POST_REQUEST);
        request.execute();
        isUpdating = false;
    }




    private void refreshHeroList(final JSONArray devices) throws JSONException {
        LinearLayout layout = (LinearLayout)findViewById(R.id.button_list);
        layout.removeAllViews();
        final Switch tv[] = new Switch[devices.length()];
        final TextView textv[] = new TextView[devices.length()];
        for(int i=0;i<devices.length();i++)
        {

            tv[i] = new Switch(this);
            textv[i] = new TextView(this);
            textv[i].setTextSize(20);

            String label = devices.getJSONObject(i).getString("label");
            String status = devices.getJSONObject(i).getString("status");
            tv[i].setText(status);
            textv[i].setText(label);
            textv[i].setTextColor(Color.parseColor("#000000"));
            tv[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            //tv[i].setGravity(Gravity.CENTER_HORIZONTAL);


            if (tv[i].getText().toString().contains("on")) {
                tv[i].setBackgroundColor(Color.parseColor("#BCF8A7"));
            }else{
                tv[i].setBackgroundColor(Color.parseColor("#FB9C9C"));
            }



            final int finalI = i;
            final String sn = devices.getJSONObject(i).getString("sn");
            tv[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (tv[finalI].getText().toString().contains("on")) {
                        //tv[finalI].setBackgroundColor(Color.parseColor("#BCF8A7"));
                        updateHero(sn,"off");
                    } else {
                        updateHero(sn,"on");
                    }

                }
            });
            layout.addView(textv[i]);
            layout.addView(tv[i]);


        }

    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshHeroList(object.getJSONArray("devices"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }


}