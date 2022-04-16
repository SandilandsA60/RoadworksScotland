// Adam Sandilands s2032103
package org.me.gcu.sandilands_adam_s2032103;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;



public class DataActivity extends AppCompatActivity  {

    Intent intent;
    DataModel DataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);


        TextView title = findViewById(R.id.traffic_title_display);
        TextView description = findViewById(R.id.traffic_description_display);
        TextView startDate = findViewById(R.id.startDate_display);

        TextView endDate = findViewById(R.id.endDate_display);
        TextView link = findViewById(R.id.link_display);
        TextView rss = findViewById(R.id.coordinates_displau);

        TextView pubDate = findViewById(R.id.published_display);
        TextView roadworks = findViewById(R.id.dayNo_display);

        intent = getIntent();
        DataModel = intent.getParcelableExtra("TRAFFIC_DATA");

        title.setText(DataModel.getTitle());
        Log.v("TrafficActivity", DataModel.getTitle());
        description.setText(DataModel.getDescription());

        link.setText(DataModel.getLink());
        rss.setText(DataModel.getRss());

        pubDate.setText(DataModel.getPubDate());
        startDate.setText(DataModel.getStartDateAsString());
        endDate.setText(DataModel.getEndDateAsString());
        roadworks.setText(DataModel.getRoadworksLength()+"");
    }
}
