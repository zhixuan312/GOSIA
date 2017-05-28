package csl.sia.com.gosia;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.HashMap;

import static csl.sia.com.gosia.Helper.Global.acceptList;

public class RideRequestListAdapter extends RecyclerView.Adapter<RideRequestListAdapter.MyViewHolder> {

    private ArrayList<HashMap<String, String>> requestList;
    private Context context;
    private int seatAvailableNum;
    private int seatOccupyNum;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ride_request_card_view_to_body;
        public TextView ride_request_card_view_name;
        public TextView ride_request_card_view_from_body;
        public CheckBox ride_request_card_view_checkbox;
        public ImageView ride_request_card_view_profile_image;
        public TextView ride_request_card_view_time_body;


        public MyViewHolder(View view) {
            super(view);
            ride_request_card_view_to_body = (TextView) view.findViewById(R.id.ride_request_card_view_to_body);
            ride_request_card_view_name = (TextView) view.findViewById(R.id.ride_request_card_view_name);
            ride_request_card_view_from_body = (TextView) view.findViewById(R.id.ride_request_card_view_from_body);
            ride_request_card_view_checkbox = (CheckBox) view.findViewById(R.id.ride_request_card_view_checkbox);
            ride_request_card_view_profile_image = (ImageView) view.findViewById(R.id.ride_request_card_view_profile_image);
            ride_request_card_view_time_body = (TextView) view.findViewById(R.id.ride_request_card_view_time_body);
        }
    }


    public RideRequestListAdapter(ArrayList<HashMap<String, String>> requestList, int seatAvailableNum, Context context) {
        acceptList = new ArrayList<>();
        this.requestList = requestList;
        this.seatAvailableNum = seatAvailableNum;
        this.context = context;
        this.seatOccupyNum = 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ride_request_cardview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.ride_request_card_view_name.setText(requestList.get(position).get("name") + " (" + requestList.get(position).get("riderNum") + ")");
        holder.ride_request_card_view_from_body.setText(requestList.get(position).get("from"));
        holder.ride_request_card_view_to_body.setText(requestList.get(position).get("to"));
        holder.ride_request_card_view_time_body.setText(requestList.get(position).get("date"));

        Glide
                .with(context)
                .load(Integer.parseInt(requestList.get(position).get("imageUrl")))
                .asBitmap()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new BitmapImageViewTarget(holder.ride_request_card_view_profile_image) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder.ride_request_card_view_profile_image.setImageDrawable(circularBitmapDrawable);
                    }
                });

        holder.ride_request_card_view_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ride_request_card_view_checkbox.isChecked()) {
                    if (seatAvailableNum - seatOccupyNum - Integer.parseInt(requestList.get(position).get("riderNum")) >= 0) {
                        acceptList.add(requestList.get(position).get("requestId"));
                        seatOccupyNum += Integer.parseInt(requestList.get(position).get("riderNum"));
                        if (seatAvailableNum <= 4) {
                            Toast.makeText(context, "Seats available: " + (seatAvailableNum - seatOccupyNum), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Passenger No.: " + seatOccupyNum, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        holder.ride_request_card_view_checkbox.setChecked(false);
                        Toast.makeText(context, "Exceed available number ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    for (int i = 0; i < acceptList.size(); i++) {
                        if (acceptList.get(i).equals(requestList.get(position).get("requestId"))) {
                            acceptList.remove(i);
                        }
                    }
                    seatOccupyNum -= Integer.parseInt(requestList.get(position).get("riderNum"));
                    if (seatAvailableNum <= 4) {
                        Toast.makeText(context, "Seats available: " + (seatAvailableNum - seatOccupyNum), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Passenger No.: " + seatOccupyNum, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
}