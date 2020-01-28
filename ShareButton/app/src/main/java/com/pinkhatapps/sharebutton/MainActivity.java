package com.pinkhatapps.sharebutton;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*
* https://developer.android.com/training/sharing/send
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnShare = findViewById(R.id.btnShare);
        final TextView tvQuote = findViewById(R.id.textView);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quote = tvQuote.getText().toString();
                String shareContent = quote + " - shared via " + getString(R.string.app_name);

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                //sendIntent.setPackage("com.whatsapp"); //we can specify the share target as well
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
                try {
                    //startActivity(sendIntent); //it is ok to not create a chooser
                    Intent shareIntent = Intent.createChooser(sendIntent, null); //but chooser look nicer
                    startActivity(shareIntent);
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this,"Whatsapp have not been installed.", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
                }

            }
        });
    }
}
