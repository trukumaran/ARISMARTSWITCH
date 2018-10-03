package com.ari_smart_switch.arismartswitch;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.CompoundButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editTextHeroId, editTextName;
    private Switch sw1,sw2;

    ProgressBar progressBar;
    ListView listView;
    //Button buttonAddUpdate, buttonAddUpdate2;
    SeekBar seekBar;

    List<Hero> heroList;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




       // buttonAddUpdate = (Button) findViewById(R.id.buttonAddUpdate);
       // buttonAddUpdate2 = (Button) findViewById(R.id.buttonAddUpdate2);
        sw1 = (Switch)findViewById(R.id.switch1);
        sw2 = (Switch)findViewById(R.id.switch2);

        seekBar = (SeekBar) findViewById(R.id.seekBar);



        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewHeroes);

        heroList = new ArrayList<>();


       /* buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateHero("1","On");

            }
        });

        buttonAddUpdate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateHero("2","On");

            }
        }); */



        /*sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    updateHero("1","On");
                }else{
                    updateHero("1","Off");
                }
            }
        });

        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    updateHero("2","On");
                }else{
                    updateHero("2","Off");
                }
            }
        });*/



        sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (sw1.isChecked()) {
                    updateHero("1","D1on");
                } else {
                    updateHero("1","D1off");
                }
            }
        });

        sw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (sw2.isChecked()) {
                    updateHero("2","D2on");
                } else {
                    updateHero("2","D2off");
                }
            }
        });




        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                updateHero("3",String.valueOf(progressChangedValue));

                //Toast.makeText(MainActivity.this, "Fan Speed is :" + progressChangedValue,Toast.LENGTH_SHORT).show();
            }
        });



        readHeroes();
    }




    private void readHeroes() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_HEROES, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void updateHero(String id, String status) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("status", status);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_HERO, params, CODE_POST_REQUEST);
        request.execute();


        isUpdating = false;
    }


    private void deleteHero(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_HERO + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshHeroList(JSONArray devices) throws JSONException {
        heroList.clear();

        for (int i = 0; i < devices.length(); i++) {
            JSONObject obj = devices.getJSONObject(i);

            if(i==0){
                Log.d("post_execution", "after toast"+obj.getString("status"));
                if(obj.getString("status") == "D1on"){
                    sw1.setChecked(true);
                    Log.d("post_execution", "after toast on");
                }if(obj.getString("status") == "D1off"){
                    Log.d("post_execution", "after toast off");
                    sw1.setChecked(false);
                }
            }

            if(i==1){
                if(obj.getString("status") == "D2on"){
                    //Log.d("light2","light 2 on");
                    sw2.setChecked(true);
                }
                if(obj.getString("status") == "D2off"){
                    //Log.d("light2","light 2 on");
                    sw2.setChecked(false);
                }
            }

            heroList.add(new Hero(
                    obj.getInt("id"),
                    obj.getString("status"),
                    obj.getString("label"),
                    obj.getString("cur")
            ));
        }

        HeroAdapter adapter = new HeroAdapter(heroList);
        listView.setAdapter(adapter);
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
                    //Log.d("post_execution", "after toast");
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

    class HeroAdapter extends ArrayAdapter<Hero> {
        List<Hero> heroList;

        public HeroAdapter(List<Hero> heroList) {
            super(MainActivity.this, R.layout.layout_hero_list, heroList);
            this.heroList = heroList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_hero_list, null, true);

            TextView textViewDeviceLabel = listViewItem.findViewById(R.id.textViewDeviceLabel);
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);
            TextView textViewCur = listViewItem.findViewById(R.id.textViewCur);



            //TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Hero hero = heroList.get(position);

            textViewName.setText(hero.getName());
            textViewDeviceLabel.setText(hero.getDevLabel());
            textViewCur.setText(hero.getCur());



            /*textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Delete " + hero.getName())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteHero(hero.getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });*/

            return listViewItem;
        }
    }
}