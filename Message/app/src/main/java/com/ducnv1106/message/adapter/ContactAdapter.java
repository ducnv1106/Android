package com.ducnv1106.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter<T> extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private ArrayList<T> data;
    private Context context;
    private int layoutId;
    private ContacListener listener;
    private String nameData;

    public ContactAdapter(Context context, @LayoutRes int layoutId) {

        this.context = context;
        this.layoutId = layoutId;

    }

    public void setNameData(String nameData) {
        this.nameData = nameData;
    }

    public void setListener(ContacListener listener) {
        this.listener = listener;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false);

        return new ContactViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {

        T item = data.get(position);
        holder.binding.setVariable(BR.item, item);
        holder.binding.setVariable(BR.listener, listener);
        holder.binding.setVariable(BR.nameData, nameData);
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public ContactViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ContacListener {

    }
}
