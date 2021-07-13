package com.example.android.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    static ArrayList<String> places = new ArrayList<String>();
    static ArrayList<LatLng> locations = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.android.memorableplaces",Context.MODE_PRIVATE);

        try {
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize(new ArrayList<String>())));

                latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lats",ObjectSerializer.serialize(new ArrayList<String>())));
                longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lons",ObjectSerializer.serialize(new ArrayList<String>())));

                if (places.size()>0 && longitudes.size()>0  && latitudes.size()>0)
                    if (latitudes.size()==longitudes.size() && longitudes.size() == places.size())
                    {
                        for(int i = 0 ; i<latitudes.size();i++){
                            locations.add(new LatLng(Double.parseDouble(latitudes.get(i)),Double.parseDouble(longitudes.get(i))));
                        }
                    }else {
                        locations.add(new LatLng(0,0));
                        places.add("Add a new place.....");
                    }

        } catch (Exception e) {
            e.printStackTrace();
        }


        ListView listView = (ListView) findViewById(R.id.listview);


        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,places);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(MainActivity.this, Integer.toString(position), Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this,MapsActivity.class);
                    intent.putExtra("pos",position);
                    startActivity(intent);

            }
        });




    }
}
