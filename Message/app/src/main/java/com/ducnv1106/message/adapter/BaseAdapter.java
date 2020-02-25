package com.ducnv1106.message.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.ducnv1106.message.model.BaseModel;
import com.ducnv1106.message.model.Message;
import com.ducnv1106.message.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class BaseAdapter<T extends BaseModel> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {

    private ArrayList<T> data = new ArrayList<>();
    private Context context;
    private int layoutId;
    private BaseListener listener;
    private Boolean isStatus;
    private HashMap<T, Boolean> hashMap = new HashMap<>();
    private Boolean isFriend;
    private Boolean isMessage;

    public void setListener(BaseListener listener) {
        this.listener = listener;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setHashMap(HashMap<T, Boolean> hashMap) {
        data.clear();
        this.hashMap = hashMap;
        Set<T> set = hashMap.keySet();

        for (T t : set) {
            data.add(t);
        }
        notifyDataSetChanged();

    }

    public BaseAdapter(Context context, @LayoutRes int layoutId, Boolean isStatus, Boolean isFriend, Boolean isMessage) {

        this.context = context;
        this.layoutId = layoutId;
        this.isStatus = isStatus;
        this.isFriend = isFriend;
        this.isMessage = isMessage;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public int getItemCount() {

        return data == null ? 0 : data.size();
    }


    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.BaseViewHolder holder, int position) {

        T item = data.get(position);

        Boolean friend = hashMap.get(item);

        holder.binding.setVariable(BR.item, item);
        holder.binding.setVariable(BR.listener, listener);
        holder.binding.setVariable(BR.isStatus, isStatus);

        holder.binding.setVariable(BR.isFriend, isFriend);
        holder.binding.setVariable(BR.Friend, friend);
        holder.binding.setVariable(BR.isMessage, isMessage);

        lastMessage((User) item, holder);


        holder.binding.executePendingBindings();
    }

    public void lastMessage(final User user, final BaseAdapter.BaseViewHolder holder) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Message");
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);

                    if ((message.getSender().equals(firebaseUser.getUid()) && message.getReceiver().equals(user.getUserId())) ||
                            (message.getSender().equals(user.getUserId()) && message.getReceiver().equals(firebaseUser.getUid()))) {
                               holder.binding.setVariable(BR.message, message);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BaseViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public interface BaseListener {

    }


}
