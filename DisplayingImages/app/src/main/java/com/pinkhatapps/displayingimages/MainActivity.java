package com.pinkhatapps.displayingimages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity {

    int id=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Displaying Images");
        getSupportActionBar().setSubtitle("with Glide");
        //getSupportActionBar().setLogo(R.drawable.ic_foreground);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_my_image_asset);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /*
    Check out these for learning more about Glide:
    https://bumptech.github.io/glide/doc/getting-started.html
    https://bumptech.github.io/glide/doc/placeholders.html
    https://bumptech.github.io/glide/doc/options.html
    https://bumptech.github.io/glide/ref/samples.html

    Glide + animations
    https://futurestud.io/tutorials/glide-custom-animations-with-animate
    */
    public void RefreshImage(View view) {

        String url = String.format("https://picsum.photos/id/%d/200/300", id++);

        ImageView imageView = findViewById(R.id.ivBottom);
        Glide.with(view.getContext())
                .load(url)
                /*.transition(GenericTransitionOptions.with(R.transition.zoom_in))*/
                .transition(withCrossFade())
                .thumbnail(0.25f)
                .placeholder(R.drawable.ic_insert_photo_black_12dp)
                .error(R.drawable.image_error)
                .into(imageView);
    }
}
