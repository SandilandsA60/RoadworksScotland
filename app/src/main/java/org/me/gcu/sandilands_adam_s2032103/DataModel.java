// Adam Sandilands s2032103
package org.me.gcu.sandilands_adam_s2032103;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class DataModel implements Parcelable {
    private Calendar startDate;
    private Calendar endDate;
    private String title;
    private String description;
    private String link;
    private String rss;
    private String pubDate;
    private String startDateAsString;
    private String endDateAsString;
    private long roadworksLength;

    protected DataModel(Parcel in) {
        title = in.readString();
        description = in.readString();
        link = in.readString();
        rss = in.readString();
        pubDate = in.readString();
        startDateAsString = in.readString();
        endDateAsString = in.readString();
        roadworksLength = in.readLong();
    }

    public DataModel() {
    }

    public static final Creator<DataModel> CREATOR = new Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.link);
        dest.writeString(this.rss);
        dest.writeString(this.pubDate);
        dest.writeString(this.startDateAsString);
        dest.writeString(this.endDateAsString);
        dest.writeLong(this.roadworksLength);
    }



    public void setStartDateAsString(String startDateAsString) {
        this.startDateAsString = startDateAsString;
    }

    public String getEndDateAsString() {
        return endDateAsString;
    }

    public void setEndDateAsString(String endDateAsString) {
        this.endDateAsString = endDateAsString;
    }

    public long getRoadworksLength() {

        return roadworksLength;
    }

    public void setRoadworksLength(long roadworksLength) {

        this.roadworksLength = roadworksLength;
    }

    public String getStartDateAsString() {

        return startDateAsString;
    }

    public String getTitle() {
        return title;
    }

    public void setStartDate(Calendar startDate) {

        this.startDate = startDate;
    }

    public String getRss() {

        return rss;
    }


    public void setGeorss(String georss) {

        this.rss = georss;
    }

    public String getPubDate() {

        return pubDate;
    }

    public Calendar getEndDate() {

        return endDate;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }



    public void setEndDate(Calendar endDate) {

        this.endDate = endDate;
    }

    public String getLink() {

        return link;
    }

    public void setLink(String link) {

        this.link = link;
    }

    public void setPubDate(String pubDate) {

        this.pubDate = pubDate;
    }

    public Calendar getStartDate() {

        return startDate;
    }

/*
    @Override
    public String toString() {

        return titile + '\''+"\n\n" + description ;


    }


*/

}
