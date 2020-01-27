package com.pinkhatapps.swipetorefresh;

import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvTitle;
    String json;
    List<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        tvTitle = findViewById(R.id.txtTitle);

        json = readRawFileIntoString(R.raw.photos);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Photo>>() {
        }.getType();
        photos = gson.fromJson(json, type);

        swipeRefreshLayout = findViewById(R.id.simpleSwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Random random = new Random();
                int index = random.nextInt(10);
                Photo item = photos.get(index);
                String title = String.format(Locale.getDefault(), "Item #%d : %s", item.id , item.title);
                tvTitle.setText(title);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private String readRawFileIntoString(@RawRes int resourceId) {
        StringBuilder sb = new StringBuilder();
        InputStream is = getResources().openRawResource(resourceId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = null;
        try {
            do {
                line = reader.readLine();
                if (line != null) {
                    sb.append(line);
                }
            } while (line != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
