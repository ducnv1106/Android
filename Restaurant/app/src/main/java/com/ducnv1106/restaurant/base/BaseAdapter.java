package com.ducnv1106.restaurant.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.ducnv1106.restaurant.model.BaseModel;

import java.util.ArrayList;

public class BaseAdapter<T extends BaseModel> extends RecyclerView.Adapter<BaseAdapter.BaseViewModel>{

    private ArrayList<T> data;
    private LayoutInflater inflater;
    private int layoutID;
    private BaseListenner listenner;

    public void setListenner(BaseListenner listenner) {
        this.listenner = listenner;
    }

    public BaseAdapter(Context context, @LayoutRes int layoutID) {
        inflater=LayoutInflater.from(context);
        this.layoutID=layoutID;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding= DataBindingUtil.inflate(inflater,layoutID,parent,false);

        return new BaseViewModel(binding);
    }

    @Override
    public int getItemCount() {
        return data==null ? 0 : data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.BaseViewModel baseViewModel, int position) {

    }

    public class BaseViewModel extends ViewHolder{
        ViewDataBinding binding;
        public BaseViewModel(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface BaseListenner{

    }
}

