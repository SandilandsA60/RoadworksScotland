package org.me.gcu.sandilands_adam_s2032103;
// Adam Sandilands s2032103
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.TrafficViewDataHolder> {

    private LayoutInflater Inflater;
    private ArrayList<DataModel> TrafficDataList;
    private Context context;


    public Adapter(Context context, ArrayList<DataModel> mTrafficDataList) {
        Inflater = LayoutInflater.from(context);
        this.TrafficDataList = mTrafficDataList;
        this.context = context;
    }



    @Override
    public TrafficViewDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = Inflater.inflate(R.layout.item,
                parent, false);
        return new TrafficViewDataHolder(mItemView, this);
    }


    public void setTrafficIcon(ImageView view, DataModel tdm) {
        if (tdm.getRoadworksLength() < 2) {
            view.setImageResource(R.drawable.green_icon);
        } else if (tdm.getRoadworksLength() < 8) {
            view.setImageResource(R.drawable.orange_icon);
        }
        else {
            view.setImageResource(R.drawable.red_icon);
        }
    }

    class TrafficViewDataHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView imageTitle;
        public TextView startDate;
        public TextView endDate;
        public Adapter Adapter;

        public TrafficViewDataHolder(@NonNull View itemView, Adapter adapter) {
            super(itemView);
            imageTitle = itemView.findViewById(R.id.title);
            icon = itemView.findViewById(R.id.imageView);
            icon.setImageResource(R.drawable.green_icon);
            startDate = itemView.findViewById(R.id.dateStartDisplay);
            endDate = itemView.findViewById(R.id.dateEnd_display);
            this.Adapter = adapter;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final TrafficViewDataHolder holder, int position) {
        final DataModel Current = TrafficDataList.get(position);
        holder.imageTitle.setText(Current.getTitle());
        holder.endDate.setText(Current.getEndDateAsString());
        holder.startDate.setText(Current.getStartDateAsString());
        setTrafficIcon(holder.icon, Current);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DataActivity.class);
                intent.putExtra("TRAFFIC_DATA", Current);
                context.startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {

        return TrafficDataList.size();
    }
}
