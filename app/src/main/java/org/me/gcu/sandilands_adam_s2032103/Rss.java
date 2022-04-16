// Adam Sandilands s2032103

package org.me.gcu.sandilands_adam_s2032103;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class Rss extends AsyncTask<String, String, ArrayList<DataModel>> {


    private WeakReference<RecyclerView> RecyclerView;
    private WeakReference<ProgressBar> ProgressBar;
    private ArrayList<DataModel> DataList;
    private String result = "";
    private String userInput = "";
    private String handlerSelection = "";




    Rss(RecyclerView recyclerView, ProgressBar progressBar, ArrayList<DataModel> DataList, TextView errorMessage) {
        RecyclerView = new WeakReference<>(recyclerView);
        ProgressBar = new WeakReference<>(progressBar);


        this.DataList = DataList;
    }


    @Override
    protected ArrayList doInBackground(String... strings) {
        URL aurl;
        URLConnection yc;
        BufferedReader in = null;
        String inputLine = "";

        if (strings.length > 1) {
            String date = strings[1];
            userInput = strings[1];
            handlerSelection = strings[2];

        } else {

            userInput = "";
        }

        try {


            aurl = new URL(strings[0]); // first string from input arg
            yc = aurl.openConnection();
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                result = result + inputLine;


            }
            in.close();
        }
        catch (IOException ae)
        {

        }

        ArrayList<DataModel> newTrafficData = new ArrayList<DataModel>();
        XMLParser trafficXMLParser = new XMLParser();
        try {
            newTrafficData = trafficXMLParser.parse(result);

            if (!userInput.isEmpty()) {

                 if (handlerSelection.equals("r")) {
                    newTrafficData = filterByRoad(userInput, newTrafficData);
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newTrafficData;
    }


    /*
    public String getPubDateFormatted() {
        try {
            if (pubDate != null) {
                Date date = dateInFormat.parse(pubDate);
                String pubDateFormatted = dateOutFormat.format(date);
                return pubDateFormatted;
            }
            else {
                return "No date in RSS feed";
            }
        }
        catch (ParseException e) {
            return "";
        }
    }
*/
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ProgressBar.get().setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(ArrayList newTrafficData) {
        super.onPostExecute(newTrafficData);
        String parsedString = "";

        this.DataList.clear();
        this.DataList.addAll(newTrafficData);

        RecyclerView.get().getAdapter().notifyDataSetChanged();
        ProgressBar.get().setVisibility(View.GONE);

    }

    public ArrayList<DataModel> filterByDate(Calendar date, ArrayList list) {
        ArrayList<DataModel> filteredTrafficDataList = new ArrayList<>();
        Iterator<DataModel> dataModelIterator = list.iterator();
        while (dataModelIterator.hasNext()) {
            DataModel trafficDataModel = dataModelIterator.next();

            if (date.compareTo(trafficDataModel.getStartDate()) >= 0 &&
                    date.compareTo(trafficDataModel.getEndDate()) <= 0) {
                filteredTrafficDataList.add(trafficDataModel);
            }
        }
        return filteredTrafficDataList;
    }

    public ArrayList<DataModel> filterByRoad(String road, ArrayList list) {
        ArrayList<DataModel> filteredTrafficDataList = new ArrayList<>();
        Iterator<DataModel> dataModelIterator = list.iterator();
        while (dataModelIterator.hasNext()) {
            DataModel trafficDataModel = dataModelIterator.next();
            try {
                if (trafficDataModel.getTitle().contains(road)) {
                    filteredTrafficDataList.add(trafficDataModel);
                }
            } catch (Exception e) {

            }
        }
        return filteredTrafficDataList;
    }
}
