package com.t3h.miniproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.t3h.miniproject.databinding.ActivityItemBinding;
import com.t3h.miniproject.model.NewsSaved;

import java.util.ArrayList;

public class NewsSavedAdapter extends RecyclerView.Adapter<NewsSavedAdapter.NewsSavedHolder> {
    private ArrayList<NewsSaved> data;
    private LayoutInflater inflater;
    private ItemNewsSavedClickListener listener;

    public void setListener(ItemNewsSavedClickListener listener) {
        this.listener = listener;
    }

    public NewsSavedAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<NewsSaved> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsSavedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityItemBinding binding = ActivityItemBinding.inflate(inflater, parent, false);
        return new NewsSavedHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsSavedHolder holder, final int position) {
            holder.bindData(data.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemNewsSavedClicked(position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemNewsSavedLongClicked(position,v);
                    return false;
                }
            });
    }

    @Override
    public int getItemCount() {
        return data==null ? 0 : data.size();
    }

    public class NewsSavedHolder extends RecyclerView.ViewHolder {
        private ActivityItemBinding binding;

        public NewsSavedHolder(@NonNull ActivityItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(NewsSaved newsSaved) {

                binding.tvTitle.setText(newsSaved.getTitle());
                binding.tvDesc.setText(newsSaved.getDesc());
                binding.tvDate.setText(newsSaved.getDate());
            Glide.with(binding.img).load(newsSaved.getImg()).into(binding.img);
        }
    }
    public interface ItemNewsSavedClickListener{
        void onItemNewsSavedClicked(int position);
        void onItemNewsSavedLongClicked(int position,View v);
    }
}
