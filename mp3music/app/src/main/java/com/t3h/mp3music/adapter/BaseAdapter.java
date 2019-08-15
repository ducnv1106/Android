package com.t3h.mp3music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.mp3music.BR;
import com.t3h.mp3music.fragment.BaseFragment;
import com.t3h.mp3music.model.BaseModel;

import java.util.ArrayList;

public class BaseAdapter<T extends BaseModel> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {

    private ArrayList<T> data;
    private LayoutInflater inflater;
    private int layoutID;
    private BaseItemListener listener;

    public BaseAdapter(Context context,@LayoutRes int layoutID) {
        inflater=LayoutInflater.from(context);
        this.layoutID=layoutID;
    }

    public void setListener(BaseItemListener listener) {
        this.listener = listener;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding=DataBindingUtil.inflate(inflater,layoutID,parent,false);
        return new BaseViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return data==null ? 0 : data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.BaseViewHolder holder, int position) {
        T item=data.get(position);
        holder.binding.setVariable(BR.listener,listener);
        holder.binding.setVariable(BR.item, item);
        holder.binding.setVariable(BR.position,position+1);

        holder.binding.executePendingBindings();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder{
            ViewDataBinding binding;

        public BaseViewHolder(@NonNull  ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
    public interface BaseItemListener{

    }
}
