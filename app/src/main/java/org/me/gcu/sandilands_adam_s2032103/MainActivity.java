// Adam Sandilands s2032103
/*

References: These videos were used for inspiration.

Shehab, M, 30/10/20, Using XML Pull Parser to parse XLM data : https://www.youtube.com/watch?v=i7aGM8uy2T0&ab_channel=MohamedShehab
yoursTruly, 19/8/19, Pull to Refresh in Recycler View | Android : https://www.youtube.com/watch?v=Ffa0Mtd21_M&ab_channel=yoursTRULY
HOW To, 13/12/19, Progress Bar in Android Studio : https://www.youtube.com/watch?v=HNd8Cu3Wa_U&ab_channel=HOWTO
San S, Splash Screen Android studio : https://www.youtube.com/watch?v=Q0gRqbtFLcw&t=81s&ab_channel=Stevdza-San
*/

package org.me.gcu.sandilands_adam_s2032103;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Button find_Feed;
    private RadioButton filter_by_Date;
    private RadioButton filter_by_Road;
    private RadioButton filter_by_Roadwork;
    private RadioButton filter_by_Planned;
    private RadioButton filter_by_Incident;
    private RadioButton no_filter;
    private TextView display_Error;

    final String Roadworks_Current_Url = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    final String Roadworks_Planned_Url = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    final String Incidents_Current_Url = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";

    private TextView userInput;
    private ArrayList<DataModel> scotland_Traffic= new ArrayList<DataModel>();
    private RecyclerView RecyclerView;
    private org.me.gcu.sandilands_adam_s2032103.Adapter Adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar Progress_Bar;
    private Parcelable ListState;
    private String dateString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Attempt to change the method of retaining the API.
        //Planned downloadXML1 = new Planned();
        //downloadXML1.execute("https://trafficscotland.org/rss/feeds/plannedroadworks.aspx");

        //Incidences downloadXML2 = new Incidences();
        //downloadXML2.execute("https://trafficscotland.org/rss/feeds/currentincidents.aspx");

        //Roadworks downloadXML3 = new Roadworks();
        //downloadXML3.execute("https://trafficscotland.org/rss/feeds/roadworks.aspx");


        find_Feed = findViewById(R.id.find_rss_feed);
        filter_by_Date = findViewById(R.id.find_by_date);
        filter_by_Road = findViewById(R.id.find_by_road);
        no_filter = findViewById(R.id.no_filter);
        display_Error = findViewById(R.id.error);
        filter_by_Roadwork = findViewById(R.id.roadworks_feed);
        filter_by_Planned = findViewById(R.id.planned_feed);
        filter_by_Incident = findViewById(R.id.incedents_feed);
        Progress_Bar = findViewById(R.id.progressBar);
        userInput = findViewById(R.id.User_Input);

        if (savedInstanceState != null) {

            boolean noneState = savedInstanceState.getBoolean("noneState");
            if (noneState) {
                userInput.setEnabled(false);
            } else {
                userInput.setEnabled(true);
            }


            ListState = savedInstanceState.getParcelable("traffic_data_state");
            scotland_Traffic = savedInstanceState.getParcelableArrayList("traffic_data");

        }

        RecyclerView = findViewById(R.id.recyclerview);
        Adapter = new Adapter(this, scotland_Traffic);
        mLayoutManager = new LinearLayoutManager(this);
        RecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.setAdapter(Adapter);

        final Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateString = Integer.toString(dayOfMonth) + "/" + Integer.toString((monthOfYear + 1)) + "/" + Integer.toString(year);
                userInput.setText(dateString);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        find_Feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectOption1 = "";

                String selectOption2 = Roadworks_Current_Url;
                String handlerSelection = "";
                if (filter_by_Date.isChecked()) {

                    selectOption1 = userInput.getText().toString();
                    handlerSelection = "d";

                } else if (filter_by_Road.isChecked()) {
                    selectOption1 = userInput.getText().toString();

                    handlerSelection = "r";
                } else if (no_filter.isChecked()) {

                    selectOption1 = "";
                }

                if (filter_by_Roadwork.isChecked()) {
                    selectOption2 = Roadworks_Current_Url;

                } else if (filter_by_Planned.isChecked()) {
                    selectOption2 = Roadworks_Planned_Url;

                } else if (filter_by_Incident.isChecked()) {
                    selectOption2 = Incidents_Current_Url;
                }

                if (selectOption1.isEmpty()) {
                    new Rss(RecyclerView, Progress_Bar, scotland_Traffic, display_Error).execute(selectOption2);
                } else {

                    new Rss(RecyclerView, Progress_Bar, scotland_Traffic, display_Error).execute(selectOption2, selectOption1, handlerSelection);
                }


                userInput.clearFocus();

            }
        });


        /*
        @Override
        protected void onPostExecute(String s)  {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: "+s);

            findPlanned.setOnClickListener(v -> {

                XmlParser xmlParser = new XmlParser();
                xmlParser.parse(s);
                ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<>(MainActivity.this,R.layout.list_view,xmlParser.getApplication());
                listData.setAdapter(arrayAdapter);

            });

        }
        */

        filter_by_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime.show();

                userInput.setEnabled(true);
            }
        });

        filter_by_Road.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput.setText("");

                userInput.setEnabled(true);
                userInput.setShowSoftInputOnFocus(true);

            }
        });






/*

        private String downloadXML(String urlAdd){
            StringBuilder xml = new StringBuilder();
            try {
                URL url = new URL(urlAdd);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                int count;
                char[] inputs = new char[1000];
                while (true){
                    count = bufferedReader.read(inputs);
                    if (count<0){
                        break;
                    }
                    if(count>0){
                        xml.append(String.copyValueOf(inputs,0,count));
                    }

                }
                bufferedReader.close();
                return xml.toString();
            }catch (MalformedURLException e){
                Log.e(TAG, "downloadXML: Invalid url" + e.getMessage());
            }catch (IOException e){
                Log.e(TAG, "downloadXML: IOException" +e.getMessage());
            }
            return null;
        }




        */




        no_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput.setText("");
                userInput.setEnabled(false);
            }
        });

    }

    /*

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();

    } //

*/

    @Override
    protected void onStop() {
        super.onStop();
        Bundle outState = new Bundle();
        outState.putString("user_input", userInput.getText().toString());

        ListState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable("traffic_data_state", ListState);
        outState.putParcelableArrayList("traffic_data", scotland_Traffic);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("user_input", userInput.getText().toString());

        if (no_filter.isChecked()) {
            outState.putBoolean("noneState", true);
        } else {
            outState.putBoolean("noneState", false);
        }

        ListState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable("traffic_data_state", ListState);
        outState.putParcelableArrayList("traffic_data", scotland_Traffic);
        Log.v("SAVE", "onSaveInstanceState");
    }





}
