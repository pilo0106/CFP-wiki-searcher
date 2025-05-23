package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.General_package.General;
import com.example.myapplication.KeyWord_package.KeyWordSet;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<General> general_list;
    private OnNoteListener mOnNoteListener;
    /*
    public MyAdapter(Context context, List<General> general_list){
        this.context = context;
        this.general_list = general_list;
    }

     */
    public MyAdapter(Context context, List<General> general_list, OnNoteListener onNoteListener){
        this.context = context;
        this.general_list = general_list;
        this.mOnNoteListener = onNoteListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.info_list,parent,false);
        return new MyViewHolder(item, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(general_list.get(position).getName());
        holder.start.setText(Integer.toString(general_list.get(position).getStart()));
        holder.end.setText(Integer.toString(general_list.get(position).getEnd()));
        holder.where.setText(general_list.get(position).getWhere());
        holder.deadline.setText(Integer.toString(general_list.get(position).getDeadline()));
        holder.brief.setText(general_list.get(position).getBrief());
    }

    @Override
    public int getItemCount() {
        return general_list.size();
    }

    public General getItemAtPosition(int position) {
        return general_list.get(position);
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
