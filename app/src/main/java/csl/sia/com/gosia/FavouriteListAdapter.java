package csl.sia.com.gosia;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class FavouriteListAdapter extends RecyclerView.Adapter<FavouriteListAdapter.MyViewHolder> {

    private ArrayList<HashMap<String, String>> favouriteList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView favourite_title;
        public TextView favourite_address;
        public ImageView favourite_icon_image;

        public MyViewHolder(View view) {
            super(view);
            favourite_title = (TextView) view.findViewById(R.id.favourite_title);
            favourite_address = (TextView) view.findViewById(R.id.favourite_address);
            favourite_icon_image = (ImageView)view.findViewById(R.id.favourite_icon_image);
        }
    }


    public FavouriteListAdapter(ArrayList<HashMap<String, String>> favouriteList, Context context) {
        this.favouriteList = favouriteList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_cardview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.favourite_title.setText(favouriteList.get(position).get("title"));
        holder.favourite_address.setText(favouriteList.get(position).get("address"));
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }
}