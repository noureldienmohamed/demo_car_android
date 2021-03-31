package com.softxpert.softxpert.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.softxpert.softxpert.R;
import com.softxpert.softxpert.model.CarsListModel;

import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.MyViewHolder> {
    List<CarsListModel.Datum> sectionModelList;
    Context context;

    public CarsAdapter(List<CarsListModel.Datum> sectionsModelList, Context context) {
        this.sectionModelList = sectionsModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CarsListModel.Datum carsListModel = sectionModelList.get(position);

        holder.brandName.setText(carsListModel.getBrand());

        if (carsListModel.getIsUsed()){
            holder.used.setText("Used");
        }else {
            holder.used.setText("New Car");

        }
        if (carsListModel.getImageUrl() != null){
            Glide.with(context).load(carsListModel.getImageUrl())
                    .into(holder.carImage);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    public int getItemCount() {
        return sectionModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView brandName;
        private TextView used;

        private ImageView carImage ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            brandName = (TextView) itemView.findViewById(R.id.brand);
            used = (TextView) itemView.findViewById(R.id.is_used);
            carImage = (ImageView) itemView.findViewById(R.id.car_image);
        }
    }
}