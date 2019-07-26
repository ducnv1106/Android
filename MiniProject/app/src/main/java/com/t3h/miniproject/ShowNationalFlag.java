package com.t3h.miniproject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.miniproject.Adapter.NationalFlagAdapter;
import com.t3h.miniproject.model.NationalFlag;

import java.util.ArrayList;

public class ShowNationalFlag extends DialogFragment implements NationalFlagAdapter.ItemNationalFlagOnClickListener, View.OnClickListener, TextWatcher {
    private ImageView imgSreach;
    private ArrayList<NationalFlag> datasearch = new ArrayList<>();
    private EditText keylanguage;
    private RecyclerView lv_national;
    private ArrayList<NationalFlag> data = new ArrayList<>();
    private NationalFlagAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_nationalflag, container, false);

        lv_national = view.findViewById(R.id.lv_flag);
        keylanguage = view.findViewById(R.id.edit_keylanguage);
        keylanguage.addTextChangedListener(this);
        imgSreach = view.findViewById(R.id.img_search);
        imgSreach.setOnClickListener(this);

        data.add(new NationalFlag("vietname", "vi", R.drawable.ic_vi));
        data.add(new NationalFlag("malaysia", "ms", R.drawable.ic_malaysia));
        data.add(new NationalFlag("korea", "ko", R.drawable.ic_south_korea));
        data.add(new NationalFlag("japan", "ja", R.drawable.ic_japan));
        data.add(new NationalFlag("indonesia", "in", R.drawable.ic_indonesia));
        data.add(new NationalFlag("france", "fr", R.drawable.ic_france));
        data.add(new NationalFlag("germany", "de", R.drawable.ic_germany));


        adapter = new NationalFlagAdapter(getContext());
        adapter.setData(data);
        adapter.setOnClickListener(this);

        lv_national.setAdapter(adapter);



        return view;
    }


    public RecyclerView getLv_national() {
        return lv_national;
    }

    public void setLv_national(RecyclerView lv_national) {
        this.lv_national = lv_national;
    }

    public ArrayList<NationalFlag> getData() {
        return data;
    }

    public void setData(ArrayList<NationalFlag> data) {
        this.data = data;
    }

    public NationalFlagAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(NationalFlagAdapter adapter) {
        this.adapter = adapter;
    }


    @Override
    public void onItemNationalFlagClicked(int position) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (datasearch.size() != 0) {
            mainActivity.setItem(datasearch.get(position).getImg());
            mainActivity.setLanguage(datasearch.get(position).getKeylanguage());
        } else {
            mainActivity.setItem(data.get(position).getImg());
            mainActivity.setLanguage(data.get(position).getKeylanguage());
        }

    }


    @Override
    public void onClick(View v) {
        datasearch.clear();
        String keysearch=keylanguage.getText().toString();
        if(!keysearch.isEmpty()){
            for (NationalFlag nationalFlag:data){
                int indexof=nationalFlag.getKeylanguagefull().indexOf(keysearch);
                if(indexof!=-1){
                    datasearch.add(nationalFlag);
                }
            }
            adapter.setData(datasearch);
        }else {
            adapter.setData(data);

        }


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        datasearch.clear();
       String keyseach=s.toString();
       if(!keyseach.isEmpty()){
           for (NationalFlag nationalFlag: data){
               int indexof=nationalFlag.getKeylanguagefull().indexOf(keyseach);
               if(indexof!=-1){
                   datasearch.add(nationalFlag);
               }
           }
           adapter.setData(datasearch);
       }
    }
}
