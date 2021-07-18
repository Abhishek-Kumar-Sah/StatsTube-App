package com.avi.in.statstube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder>{

    ArrayList<ChannelInfo> finalList;

    public ChannelAdapter(ArrayList<ChannelInfo> list){
        finalList = list;
    }


    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View newView = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_list_item,parent,false);

        ViewHolder holder = new ViewHolder(newView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChannelAdapter.ViewHolder holder, int position) {

        bind(holder,position);

    }

    @Override
    public int getItemCount() {
        return finalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView channelTitleView;
        TextView subscriberCountView;
        TextView videoCountView;
        TextView viewCountView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.list_item_image_view);
            channelTitleView = itemView.findViewById(R.id.list_item_channel_name_view);
            subscriberCountView = itemView.findViewById(R.id.list_item_subscriber_count_view);
            videoCountView = itemView.findViewById(R.id.list_item_total_videos_text_view);
            viewCountView = itemView.findViewById(R.id.list_item_views_count_text_view);
        }
    }

    public void bind(ChannelAdapter.ViewHolder holder, int position){

        ChannelInfo currentChannel = finalList.get(position);
        String imageUrl = currentChannel.getImageURL();
        String channelName = currentChannel.getTitle();
        Long subsCount = currentChannel.getSubsCount();
        Long vdoCount = currentChannel.getVideosCount();
        Long viewsCount = currentChannel.getViewCount();

        int radius = 50, margin = 20;
        Glide.with(holder.imageView.getContext())
                .load(imageUrl)
                .override(900,750)
                .into(holder.imageView);

        holder.channelTitleView.setText(channelName);
        holder.subscriberCountView.setText(String.valueOf(subsCount));
        holder.videoCountView.setText(String.valueOf(vdoCount));
        holder.viewCountView.setText(String.valueOf(viewsCount));
    }

}
