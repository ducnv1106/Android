package com.ducnv1106.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.ducnv1106.message.R;
import com.ducnv1106.message.databinding.ItemImageBinding;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private ArrayList<String> arrImage;
    private Context context;
    private ImageListener listener;

    public void setListener(ImageListener listener) {
        this.listener = listener;
    }

    public void setArrImage(ArrayList<String> arrImage) {
        this.arrImage = arrImage;
        notifyDataSetChanged();
    }



    public ImageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_image, parent, false);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        String item = arrImage.get(position);

        holder.binding.setVariable(BR.item,item);
        holder.binding.setVariable(BR.listener,listener);
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return arrImage == null ? 0 : arrImage.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private ItemImageBinding binding;

        public ImageViewHolder(ItemImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ImageListener{


    }
}
