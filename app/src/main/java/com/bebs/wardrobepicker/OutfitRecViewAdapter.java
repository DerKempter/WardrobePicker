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

public class OutfitRecViewAdapter extends RecyclerView.Adapter<OutfitRecViewAdapter.ViewHolder> {

    private List<Outfit> outfit;
    private Context mContext;

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    private OnOutfitListener onOutfitListener;

    public OutfitRecViewAdapter(OnOutfitListener onOutfitListener) {
        this.onOutfitListener = onOutfitListener;
        outfit = new ArrayList<Outfit>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.outfit_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view, this.onOutfitListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Outfit thisOutfit = outfit.get(position);
        holder.txtName.setText(thisOutfit.getName());
        holder.txtSize.setText(Integer.toString(thisOutfit.getSize()));
        holder.txtCreation.setText(thisOutfit.getCreated().toString());
    }

    @Override
    public int getItemCount() {
        return outfit.size();
    }

    public void setOutfit(List<Outfit> outfit) {
        this.outfit = outfit;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private LinearLayout parent;
        private ImageView imgClothing;
        private TextView txtName, txtSize, txtCreation;
        OnOutfitListener onOutfitListener;


        public ViewHolder(@NonNull View itemView, OnOutfitListener onOutfitListener) {
            super(itemView);

            this.onOutfitListener = onOutfitListener;

            parent = itemView.findViewById(R.id.parent);
            imgClothing = itemView.findViewById(R.id.imgClothing );
            txtName = itemView.findViewById(R.id.txtName);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtCreation = itemView.findViewById(R.id.txtCreation);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.onOutfitListener.onOutfitClick(getAdapterPosition());
        }
    }

    public interface OnOutfitListener{
        void onOutfitClick(int position);
    }

    // change the View to Display more Details

    //TODO allow for Filtering here
}
