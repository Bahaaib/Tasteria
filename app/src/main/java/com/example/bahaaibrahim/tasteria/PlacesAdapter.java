package com.example.bahaaibrahim.tasteria;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static java.lang.Float.valueOf;

/**
 * Created by Bahaa Ibrahim on 9/6/2017.
 */

public class PlacesAdapter extends RecyclerView.Adapter {

    private Context context;
    protected ArrayList<PlaceModel> adapterModel = new ArrayList<>();

    public PlacesAdapter(Context context, ArrayList<PlaceModel> adapterModel) {
        this.context = context;
        this.adapterModel = adapterModel;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new PlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PlacesViewHolder) holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return adapterModel.size();
    }


    public class PlacesViewHolder extends RecyclerView.ViewHolder {

        TextView primaryText, subText, rateValue;
        ImageView cardImage;
        MaterialRatingBar ratingBar;


        public PlacesViewHolder(View itemView) {
            super(itemView);

            primaryText = (TextView) itemView.findViewById(R.id.primaryText);
            subText = (TextView) itemView.findViewById(R.id.subText);
            rateValue = (TextView) itemView.findViewById(R.id.rateValue);
            cardImage = (ImageView) itemView.findViewById(R.id.cardImage);
            ratingBar = (MaterialRatingBar) itemView.findViewById(R.id.ratingBar);


        }

        public void bindView(int position) {
            primaryText.setText(adapterModel.get(position).getmPrimaryText());
            subText.setText(adapterModel.get(position).getmSubText());
            Picasso.with(context)
                    .load(adapterModel.get(position).getmCardImageURL())
                    .fit()
                    .into(cardImage);
            ratingBar.setRating(valueOf(adapterModel.get(position).getmRateValue()));
            rateValue.setText("( " + adapterModel.get(position).getmRateValue() + " )");


        }
    }
}