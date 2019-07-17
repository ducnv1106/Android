package com.t3h.readfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.readfile.databinding.ItemFileBinding;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileHodler>{
    private File[] data;
    private SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
    private LayoutInflater inflater;
    private ItemFileOnClickListener itemFileOnClickListener;

    public void setItemFileOnClickListener(ItemFileOnClickListener itemFileOnClickListener) {
        this.itemFileOnClickListener = itemFileOnClickListener;
    }

    public FileAdapter(Context context) {
        inflater=LayoutInflater.from(context);
    }

    public void setData(File[] data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FileHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFileBinding binding= ItemFileBinding.inflate(inflater,parent,false);
        return new FileHodler(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FileHodler holder, int position) {
            final File file=data[position];
            holder.binData(file);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemFileOnClickListener.onItemFileClicked(file);
                }
            });
    }

    @Override
    public int getItemCount() {
        return data==null ?0 :data.length;
    }

    public class FileHodler extends RecyclerView.ViewHolder{
            private ItemFileBinding binding;
        public FileHodler(@NonNull ItemFileBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        public void binData(File item){
            if (item.isFile()){
                binding.imFile.setImageResource(R.drawable.ic_file);
            }else {
                binding.imFile.setImageResource(R.drawable.ic_folder);
            }
            binding.tvName.setText(item.getName());
            String time=format.format(new Date(item.lastModified()));
            binding.tvDuration.setText(time);

        }
        public String readableFileSize(long size) {
            if(size <= 0) return "0";
            final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
            int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
            return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
        }

    }
    public interface ItemFileOnClickListener{
        void onItemFileClicked(File file);


    }
}
