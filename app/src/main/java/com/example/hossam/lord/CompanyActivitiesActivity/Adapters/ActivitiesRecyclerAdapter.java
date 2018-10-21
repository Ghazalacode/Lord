package com.example.hossam.lord.CompanyActivitiesActivity.Adapters;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.hossam.lord.CompanyActivitiesActivity.Data.ActivitiesModel;
import com.example.hossam.lord.R;

import java.util.ArrayList;

public class ActivitiesRecyclerAdapter extends RecyclerView.Adapter<ActivitiesRecyclerAdapter.ProfileViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mData;

    public ActivitiesRecyclerAdapter(Context mContext, ArrayList<String> mData) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mData = mData;

    }

  /*  public ActivitiesRecyclerAdapter(Context context*//*, ArrayList<ActivitiesModel> data*//*) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = new ArrayList<ActivitiesModel>();
        this.mContext =  context;
    }*/


    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProfileViewHolder(mInflater.inflate(R.layout.recycler_item_company_activities,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        String title = mData.get(position);
        holder.tvTitle.setText(title);



    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }
     class ProfileViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;


        public ProfileViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);


        }
    }
}
