package com.pinkhatapps.recyclerexample;

import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
                tvTitle.setText(item.title);
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
