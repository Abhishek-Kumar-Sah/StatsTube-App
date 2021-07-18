package com.avi.in.statstube;



import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private Context context;


    private final String firstQueryPart1 = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&type=channel&maxResults=50&order=videoCount&q=";
    private final String queryKey = "&key=AIzaSyCFG5jN8dBwDZsd_6X546xFc8IrtDNr5gc";

    //private final String queryKey = "&key=AIzaSyAUxV9DZRnON7nFpMmozYdJG5-UzCoYNC4";
    private String secondQueryPart1 = "https://www.googleapis.com/youtube/v3/channels?part=contentDetails%2Cstatistics&id=";
    private String inputSearchQuery;

    public QueryUtils(Context context, String query) {
        this.inputSearchQuery = query.trim();
        this.context = context;
    }


    public void fetchYouTubeData(ArrayList<ChannelInfo> channelInfo) {

        // Instantiate the RequestQueue.
        RequestQueue queue1 = Volley.newRequestQueue(context);
        String firstQueryURL = firstQueryPart1 + inputSearchQuery + queryKey;


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, firstQueryURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray items = response.getJSONArray("items");

                    for (int i =0; i < items.length(); i++){

                        JSONObject item = items.getJSONObject(i);
                        JSONObject details = item.getJSONObject("snippet");
                        String channelID = details.getString("channelId");
                        String channelTile = details.getString("channelTitle");

                        JSONObject thumbnails = details.getJSONObject("thumbnails");
                        JSONObject image = thumbnails.getJSONObject("high");
                        String imageURL = image.getString("url");

                        channelInfo.add(new ChannelInfo(channelTile,channelID,imageURL));
                    }
                    fetchEachChannelData(channelInfo);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(LOG_TAG,"ERROR RECEIVING DATA FROM API");
            }
        });

        // Add the request to the RequestQueue.
        queue1.add(jsonArrayRequest);
    }



    public void fetchEachChannelData(ArrayList<ChannelInfo> partialChannelList){


        Log.v("RECEIVED PARTIAL_CHANNEL_LIST OF SIZE : ",String.valueOf(partialChannelList.size()));


        if (partialChannelList.isEmpty())
            return;


        for(int i =0; i < partialChannelList.size(); i++) {

            //Setup query url for each channel
            ChannelInfo currentChannel = partialChannelList.get(i);
            String channelID = currentChannel.getChannelID();
            String detailsQueryURL = secondQueryPart1 + channelID + queryKey;


            RequestQueue detailsRequestQueue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, detailsQueryURL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray items = response.getJSONArray("items");
                        JSONObject stats = items.getJSONObject(0).getJSONObject("statistics");
                        long viewCount = stats.getLong("viewCount");
                        long subscriberCount = stats.getLong("subscriberCount");
                        long videoCount = stats.getLong("videoCount");


                        currentChannel.setViewCount(viewCount);
                        currentChannel.setSubsCount(subscriberCount);
                        currentChannel.setVideosCount(videoCount);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.v(LOG_TAG,"ERROR RECEIVING RESPONSE FOR INDIVIDUAL CHANNELS");
                }
            });


            detailsRequestQueue.add(jsonObjectRequest);
        }



        Toast.makeText(context.getApplicationContext(), "Data Fetched Successfully. Go Back To See Results", Toast.LENGTH_SHORT).show();
    }
}
