package com.burak.healven.helpful;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burak.healven.R;

import java.util.ArrayList;

public class RecyclerViewAdapterForSM extends RecyclerView.Adapter<RecyclerViewAdapterForSM.ViewHolder> {



    //declarations of items and context
    private ArrayList<String> sm_name;
    private ArrayList<String> sm_name2;
    private ArrayList<String> sm_com;
    private ArrayList<String> sm_com2;
    private ArrayList<Integer> img_path;
    private ArrayList<Integer> img_content_path;
    private Context mContext;


    //constructor


    public RecyclerViewAdapterForSM(ArrayList<String> sm_name, ArrayList<String> sm_name2, ArrayList<String> sm_com, ArrayList<String> sm_com2, ArrayList<Integer> img_path, ArrayList<Integer> img_content_path, Context mContext) {
        this.sm_name = sm_name;
        this.sm_name2 = sm_name2;
        this.sm_com = sm_com;
        this.sm_com2 = sm_com2;
        this.img_path = img_path;
        this.img_content_path = img_content_path;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating out resource layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sm_ides, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.contentImg.setImageResource(img_content_path.get(position));

        holder.sm_name.setText(sm_name.get(position));
        holder.sm_name2.setText(sm_name2.get(position));
        holder.sm_comm.setText(sm_com.get(position));
        holder.sm_comm2.setText(sm_com2.get(position));
        holder.image.setImageResource(img_path.get(position));
        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helperFunctions.onSocialMediaItemPressed(mContext, position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return sm_name.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{


        //declarations of items
        ImageView image, contentImg;
        TextView sm_name, sm_name2, sm_comm, sm_comm2;
        LinearLayout lay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contentImg = itemView.findViewById(R.id.iv_sm_img_content);
            image = itemView.findViewById(R.id.rw_sm_image);
            sm_name = itemView.findViewById(R.id.tw_smname);
            sm_name2 = itemView.findViewById(R.id.tw_smname2);
            sm_comm = itemView.findViewById(R.id.tw_smpost);
            sm_comm2 = itemView.findViewById(R.id.tw_smpost2);
            lay = itemView.findViewById(R.id.rw_sm_layout);
        }
    }
}
