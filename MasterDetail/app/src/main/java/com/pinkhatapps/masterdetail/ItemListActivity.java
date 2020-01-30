package com.pinkhatapps.masterdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/*
  This is the list activity (Master)
  Details will be displayed in ItemDetailsActivity
 */
public class ItemListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    String json;
    public static List<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }

        json = readRawFileIntoString(R.raw.photos);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Photo>>() {
        }.getType();
        photos = gson.fromJson(json, type);

        RecyclerView recyclerView = findViewById(R.id.item_list);
        recyclerView.setAdapter(new PhotosAdapter(this, photos, mTwoPane));
    }

    public static class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder>{
        private final ItemListActivity mParentActivity;
        private final List<Photo> mValues;
        private final boolean mTwoPane;

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Photo item = (Photo) view.getTag();
                //if two fragments will be inside the same activity side-by-side
                //then replace the detail area with the info of selected item
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, ""+item.id);//pass item id
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    //we must keep a handle to parent activity so that we can
                    //change fragments inside it
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    //if two fragments will be inside different activities
                    //then start the other activity while passing the item's id
                    //whose details will be displayed in that other activity
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, ""+item.id); //pass item id
                    context.startActivity(intent);
                }
            }
        };

        PhotosAdapter(ItemListActivity parent, List<Photo> items, boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
            return new PhotosAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.mIdView.setText("Photo #"+mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).title);
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView =  view.findViewById(R.id.id_text);
                mContentView =  view.findViewById(R.id.content);
            }
        }
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

    public static Photo getPhotoById(String id){
        for(int i=0;i<photos.size();i++){
            if(photos.get(i).id == Integer.valueOf(id)){
                return photos.get(i);
            }
        }
        return null;
    }
}
