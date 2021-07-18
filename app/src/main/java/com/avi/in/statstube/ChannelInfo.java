package com.avi.in.statstube;

public class ChannelInfo {

    String title ;
    String channelID;
    String imageURL;

    long viewCount=0;
    long subsCount=0;
    long videosCount=0;

    public String getTitle() {
        return title;
    }

    public String getChannelID() {
        return channelID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public long getSubsCount() {
        return subsCount;
    }

    public void setSubsCount(long subsCount) {
        this.subsCount = subsCount;
    }

    public long getVideosCount() {
        return videosCount;
    }

    public void setVideosCount(long videosCount) {
        this.videosCount = videosCount;
    }

    public ChannelInfo(String title, String channelID, String imageURL){

        this.title = title;
        this.channelID = channelID;
        this.imageURL = imageURL;
    }
}
