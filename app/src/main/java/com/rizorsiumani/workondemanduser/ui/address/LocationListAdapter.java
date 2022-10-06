package com.rizorsiumani.workondemanduser.ui.address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.R;

import java.util.ArrayList;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder>{

    private ArrayList<PlaceModel> addreses;
    private Context context;
    private AddressSelectionListener listener;

    public LocationListAdapter(ArrayList<PlaceModel> places, Context ctx) {
        this.addreses = places;
        this.context = ctx;
    }

    public void setAddressClickListener(AddressSelectionListener listener){
        this.listener = listener;
    }


    @Override @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View addressesList= layoutInflater.inflate(R.layout.location_list_design, parent, false);

        return new ViewHolder(addressesList, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PlaceModel current_place = addreses.get(position);
        holder.title.setText(current_place.getTitle());
        holder.address.setText(current_place.getAddress());


    }


    @Override
    public int getItemCount() {
        return addreses.size();
    }

    public interface AddressSelectionListener{
        void getAddress(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title , address;


        public ViewHolder(View itemView,AddressSelectionListener listener) {
            super(itemView);

            address = (itemView).findViewById(R.id.placeAddress);
            title = (itemView).findViewById(R.id.placeName);

            itemView.setOnClickListener(view -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION){
                    if (listener != null){
                        listener.getAddress(getAdapterPosition());
                    }
                }
            });


        }
    }

}

