package csl.sia.com.gosia;


import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class TripsListAdapter extends RecyclerView.Adapter<TripsListAdapter.MyViewHolder> {

    private ArrayList<HashMap<String, String>> tripsList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView trips_card_status;
        public TextView trips_card_date;
        public TextView trips_card_view_from_body;
        public TextView trips_card_view_to_body;
        public View trips_card_content;
        public RatingBar trips_card_view_ratingBar;

        public MyViewHolder(View view) {
            super(view);
            trips_card_status = (TextView) view.findViewById(R.id.trips_card_status);
            trips_card_date = (TextView) view.findViewById(R.id.trips_card_date);
            trips_card_view_from_body = (TextView) view.findViewById(R.id.trips_card_view_from_body);
            trips_card_view_to_body = (TextView) view.findViewById(R.id.trips_card_view_to_body);
            trips_card_content = view.findViewById(R.id.trips_card_content);
            trips_card_view_ratingBar = (RatingBar) view.findViewById(R.id.trips_card_view_ratingBar);
        }
    }


    public TripsListAdapter(ArrayList<HashMap<String, String>> tripsList, Context context) {
        this.tripsList = tripsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trips_cardview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (!tripsList.get(position).get("status").equals("Cancelled")){
            holder.trips_card_status.setVisibility(View.GONE);
        } else {
            holder.trips_card_status.setVisibility(View.VISIBLE);
            holder.trips_card_status.setText(tripsList.get(position).get("status"));
        }
        holder.trips_card_date.setText(tripsList.get(position).get("date"));
        holder.trips_card_view_from_body.setText(tripsList.get(position).get("fromName"));
        holder.trips_card_view_to_body.setText(tripsList.get(position).get("toName"));
        holder.trips_card_view_ratingBar.setRating(Float.parseFloat(String.valueOf(Math.random()*5)));
        LayerDrawable stars = (LayerDrawable) holder.trips_card_view_ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public int getItemCount() {
        return tripsList.size();
    }
}