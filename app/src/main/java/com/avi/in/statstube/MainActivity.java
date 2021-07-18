package com.avi.in.statstube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class MainActivity extends AppCompatActivity {


    androidx.appcompat.widget.SearchView searchView;

    ChannelAdapter adapter;


    TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyView = findViewById(R.id.empty_text_view);

        searchView = findViewById(R.id.search_view);

        //To show the search view in full
        searchView.setIconified(false);

        searchView.setQueryHint("Search Here");



        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                emptyView.setVisibility(View.GONE);

                RecyclerView rv = findViewById(R.id.recycler_view);

                ArrayList<ChannelInfo> finalList = getFinalChannelList(query);
                Log.v("MAIN_ACTIVITY : ","LIST CREATED SUCCESSFULLY");

                adapter = new ChannelAdapter(finalList);

                rv.setAdapter(adapter);

                rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));



                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }


    public ArrayList<ChannelInfo> getFinalChannelList(String searchQuery){

        ArrayList<ChannelInfo> partialList = new ArrayList<>();

        QueryUtils queryUtils = new QueryUtils(MainActivity.this,searchQuery);

        queryUtils.fetchYouTubeData(partialList);

        return partialList;
    }

}