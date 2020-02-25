package com.ducnv1106.message.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ducnv1106.message.Constants;
import com.ducnv1106.message.R;
import com.ducnv1106.message.adapter.BaseAdapter.BaseViewHolder;
import com.ducnv1106.message.databinding.ItemMessageImgRightBinding;
import com.ducnv1106.message.databinding.ItemMessageRightBinding;
import com.ducnv1106.message.model.Message;
import com.ducnv1106.message.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MessageApdater extends RecyclerView.Adapter<MessageApdater.MessageHolder> {

    private ArrayList<Message> data;
    private Context context;
    private FirebaseUser firebaseUser;
    private User user;
    private BaseMessageListener listener;


    public MessageApdater(Context context, User user) {
        this.user = user;
        this.context = context;
    }

    public void setData(ArrayList<Message> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setListener(BaseMessageListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        if (viewType == Constants.MSG_TYPE_TEXT_RIGHT) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_message_right, parent, false);

        } else if(viewType==Constants.MSG_TYPE_TEXT_LEFT){
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_message_left, parent, false);

        } else if(viewType==Constants.MSG_TYPE_IMG_RIGHT){
            binding=DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.item_message_img_right,parent,false);
        }else {
            binding=DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.item_message_img_left,parent,false);
        }
        return new MessageHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        Message message = data.get(position);

        if (position == data.size() - 1) {
            if (holder.binding instanceof ItemMessageRightBinding) {
                ItemMessageRightBinding binding = (ItemMessageRightBinding) holder.binding;
                binding.imgCheckSeen.setVisibility(View.VISIBLE);
                if (message.getIsseen()) {
                    Glide.with(((ItemMessageRightBinding) holder.binding).imgCheckSeen).load(user.getAvatar()).into(((ItemMessageRightBinding) holder.binding).imgCheckSeen);



                } else {
                    binding.imgCheckSeen.setImageResource(R.drawable.ic_check_send_circle);
                }
            } else if (holder.binding instanceof ItemMessageImgRightBinding){
                ItemMessageImgRightBinding binding= (ItemMessageImgRightBinding) holder.binding;
                binding.imgCheckSeen.setVisibility(View.VISIBLE);
                listener.onClicked((float)1);
                if(message.getIsseen()){
                    Glide.with(((ItemMessageImgRightBinding) holder.binding).imgCheckSeen).load(user.getAvatar()).into(((ItemMessageImgRightBinding) holder.binding).imgCheckSeen);
                }else {
                    binding.imgCheckSeen.setVisibility(View.VISIBLE);
                }
            }
        } else {

            if (holder.binding instanceof ItemMessageRightBinding) {
                ItemMessageRightBinding binding = (ItemMessageRightBinding) holder.binding;
                binding.imgCheckSeen.setVisibility(View.INVISIBLE);
            }

        }
        holder.binding.setVariable(BR.user, user);
        holder.binding.setVariable(BR.message, message);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public MessageHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (data.get(position).getSender().equals(firebaseUser.getUid()) && data.get(position).getType().equals("text")) {
            return Constants.MSG_TYPE_TEXT_RIGHT;
        } else if (data.get(position).getSender().equals(user.getUserId()) && data.get(position).getType().equals("text")) {
            return Constants.MSG_TYPE_TEXT_LEFT;
        } else if (data.get(position).getSender().equals(firebaseUser.getUid()) && data.get(position).getType().equals("image")) {
            return Constants.MSG_TYPE_IMG_RIGHT;
        } else {
            return Constants.MSG_TYPE_IMG_LEFT;
        }
    }

    public interface BaseMessageListener{
        void onClicked(float alpha);
    }
}