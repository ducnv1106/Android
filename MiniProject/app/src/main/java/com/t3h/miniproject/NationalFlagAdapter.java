package com.t3h.miniproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.miniproject.databinding.ItemNationalflagBinding;
import com.t3h.miniproject.model.NationalFlag;

import java.util.ArrayList;

public class NationalFlagAdapter extends RecyclerView.Adapter<NationalFlagAdapter.NationalFlagHodler> {
    private ArrayList<NationalFlag> data;
    private LayoutInflater inflater;
    private ItemNationalFlagOnClickListener onClickListener;

    public void setOnClickListener(ItemNationalFlagOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setData(ArrayList<NationalFlag> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public NationalFlagAdapter(Context context) {
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NationalFlagHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNationalflagBinding binding=ItemNationalflagBinding.inflate(inflater,parent,false);
        return new NationalFlagHodler(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NationalFlagHodler holder, final int position) {
            NationalFlag nationalFlag=data.get(position);
                holder.binData(nationalFlag);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onItemNationalFlagClicked(position);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return data==null ? 0 : data.size();
    }

    public class NationalFlagHodler extends RecyclerView.ViewHolder{
        public ItemNationalflagBinding binding;
        public NationalFlagHodler(@NonNull ItemNationalflagBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        public void binData(NationalFlag item){
            binding.imgFlag.setImageResource(item.getImg());
        }
    }
    public interface ItemNationalFlagOnClickListener{
        void onItemNationalFlagClicked(int position);
    }
}
