package com.burak.healven.helpful;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burak.healven.R;

import java.util.ArrayList;

public class RecyclerViewAdapterForMain extends RecyclerView.Adapter<RecyclerViewAdapterForMain.ViewHolder> {



    //declarations of items and context
    private ArrayList<String> item_desc;
    private ArrayList<String> item_desc2;
    private ArrayList<String> item_name;
    private ArrayList<String> item_name2;
    private ArrayList<Integer> img_path;

    private Context mContext;


    //constructor


    public RecyclerViewAdapterForMain(ArrayList<String> item_desc, ArrayList<String> item_desc2, ArrayList<String> item_name, ArrayList<String> item_name2, ArrayList<Integer> img_path, Context mContext) {
        this.item_desc = item_desc;
        this.item_desc2 = item_desc2;
        this.item_name = item_name;
        this.item_name2 = item_name2;
        this.img_path = img_path;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating out resource layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fs_ides, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tw_itemName.setText(item_name.get(position));
        holder.tw_itemName2.setText(item_name2.get(position));
        holder.tw_itemDescription.setText(item_desc.get(position));
        holder.tw_itemDescription2.setText(item_desc2.get(position));
        holder.image.setImageResource(img_path.get(position));
        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helperFunctions.onMainMenuItemPressed(mContext, position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return item_desc.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{


        //declarations of items
        ImageView image;
        ImageButton dislike;
        TextView tw_itemName, tw_itemName2, tw_itemDescription, tw_itemDescription2;
        LinearLayout lay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dislike = itemView.findViewById(R.id.ib_dislike);
            dislike.setVisibility(View.GONE);
            image = itemView.findViewById(R.id.rw_image);
            tw_itemName = itemView.findViewById(R.id.tw_itemname);
            tw_itemName2 = itemView.findViewById(R.id.tw_itemname2);
            tw_itemDescription = itemView.findViewById(R.id.tw_itemdesc);
            tw_itemDescription2 = itemView.findViewById(R.id.tw_itemdesc2);
            lay = itemView.findViewById(R.id.rw_layout);
        }
    }
}
