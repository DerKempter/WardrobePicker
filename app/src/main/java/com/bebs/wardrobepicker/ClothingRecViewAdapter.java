package com.bebs.wardrobepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class ClothingRecViewAdapter extends RecyclerView.Adapter<ClothingRecViewAdapter.ViewHolder> {

    private List<Clothing> clothing;
    private Context mContext;

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    private OnClothingListener onClothingListener;

    public ClothingRecViewAdapter(OnClothingListener onClothingListener) {
        this.onClothingListener = onClothingListener;
        clothing = new ArrayList<Clothing>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothing_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view, this.onClothingListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Clothing thisClothing = clothing.get(position);
        holder.txtName.setText(thisClothing.getName());
        holder.txtDesc.setText(thisClothing.getDescription());
        holder.txtType.setText(thisClothing.getTypeString());
    }

    @Override
    public int getItemCount() {
        return clothing.size();
    }

    public void setClothing(List<Clothing> clothing) {
        this.clothing = clothing;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private LinearLayout parent;
        private ImageView imgClothing;
        private TextView txtName, txtDesc, txtType;
        OnClothingListener onClothingListener;


        public ViewHolder(@NonNull View itemView, OnClothingListener onClothingListener) {
            super(itemView);

            this.onClothingListener = onClothingListener;

            parent = itemView.findViewById(R.id.parent);
            imgClothing = itemView.findViewById(R.id.imgClothing );
            txtName = itemView.findViewById(R.id.txtName);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtType = itemView.findViewById(R.id.txtType);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.onClothingListener.onClothingClick(getAdapterPosition());
        }
    }

    public interface OnClothingListener{
        void onClothingClick(int position);
    }

    // change the View to Display more Details

    //TODO allow for Filtering here
}
