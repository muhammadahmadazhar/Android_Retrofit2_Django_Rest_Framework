package com.example.ebuyzone;

import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerHomeList extends RecyclerView.Adapter<RecyclerHomeList.ViewHolder>{

    private static final String TAG = "RecyclerProfileListAdapter";

    private ArrayList<Integer> pkPost = new ArrayList<>();
    private ArrayList<String> namePost = new ArrayList<>();


    private Context mContext;

    public RecyclerHomeList(Context context, ArrayList<Integer> rId, ArrayList<String> rName) {
        pkPost = rId;
        namePost = rName;

        mContext = context;
    }

    @Override
    public RecyclerHomeList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);
        RecyclerHomeList.ViewHolder holder = new RecyclerHomeList.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerHomeList.ViewHolder holder, final int position) {


        holder.TId.setText(String.valueOf(pkPost.get(position)));
        holder.TName.setText(namePost.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Bundle bundle = new Bundle();
                bundle.putInt("post_id",pkPost.get(position)); // Put anything what you want
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new ShowPost();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.navigation_fragment_container, myFragment)
                        .addToBackStack(null)
                        .commit();



            }
        });
    }

    @Override
    public int getItemCount() {
        return pkPost.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView TId;
        TextView TName;

        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            TId = itemView.findViewById(R.id.post_listitem_id);
            TName = itemView.findViewById(R.id.post_listitem_name);


            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }


}