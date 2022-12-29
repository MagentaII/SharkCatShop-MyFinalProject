package com.example.sharkcatshop.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sharkcatshop.R;
import com.example.sharkcatshop.Room.StickerCart;
import com.example.sharkcatshop.Room.StickerItem;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private List<StickerItem> allStickerList = new ArrayList<>();
    private Context context;
    private StickerClickedListeners stickerClickedListeners;

    public HomeAdapter(Context context, StickerClickedListeners stickerClickedListeners) {
        this.context = context;
        this.stickerClickedListeners = stickerClickedListeners;
    }

    public void setAllStickerList(List<StickerItem> allStickerList) {
        this.allStickerList = allStickerList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cell_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        StickerItem StickerItem = allStickerList.get(position);
        holder.tvName.setText(StickerItem.getStickerName());
        holder.tvPrice.setText(String.valueOf(StickerItem.getStickerPrice()));
        Glide.with(context).load(StickerItem.getStickerImage()).into(holder.stickerImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                stickerClickedListeners.onCardClicked(StickerItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allStickerList.size();
    }

    static class HomeViewHolder extends RecyclerView.ViewHolder {

        ImageView stickerImage;
        TextView tvPrice, tvName;
        CardView cardView;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            stickerImage = itemView.findViewById(R.id.stickerImage);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

    public interface StickerClickedListeners{
        void onCardClicked(StickerItem StickerItem);
    }

}
