package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView name, where, start, end, deadline, brief;
    MyAdapter.OnNoteListener onNoteListener;
    public MyViewHolder(@NonNull View itemView, MyAdapter.OnNoteListener onNoteListener) {
        super(itemView);
        name = itemView.findViewById(R.id.name_list);
        where = itemView.findViewById(R.id.where_list);
        start = itemView.findViewById(R.id.when_date_list);
        end = itemView.findViewById(R.id.when_date_list2);
        deadline = itemView.findViewById(R.id.deadline_date_list);
        brief = itemView.findViewById(R.id.briefText);
        this.onNoteListener = onNoteListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onNoteListener.onNoteClick(getAdapterPosition());
    }
}
