package com.pinkhatapps.retrofitexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Query;

public class MainActivity extends AppCompatActivity {
    private static final String API_URL = "https://api.ipify.org/";
    TextView txtResult;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_refresh:
                getResults();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        txtResult = findViewById(R.id.txtResult);

        Button btn = findViewById(R.id.btnGetResults);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResults();
            }
        });
    }

    private void getResults() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(API_URL).build();
        ApiInterface api = adapter.create(ApiInterface.class);
        api.getIP(new Callback<ApiResponse>() {
            @Override
            public void success(ApiResponse apiResponse, Response response) {
                Toast.makeText(MainActivity.this, "Response status: " + response.getStatus(), Toast.LENGTH_LONG).show();
                txtResult.setText(String.format("Your IP is: %s", apiResponse.ip));
            }

            @Override
            public void failure(RetrofitError error) {
                String errMsg = error == null ? "n/a" : error.getMessage();
                Log.d("ERROR", errMsg);
                Toast.makeText(MainActivity.this, String.format("No response. Error: %s", errMsg), Toast.LENGTH_LONG).show();
            }
        });
    }

    interface ApiInterface {
        @Headers({"Accept: application/json"})
        @GET("/?format=json")
        void getIP(Callback<ApiResponse> callback);
    }
    class ApiResponse {
        public String ip;
    }
}

