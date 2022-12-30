package com.example.sharkcatshop.ShoppingCart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sharkcatshop.Home.HomeAdapter;
import com.example.sharkcatshop.R;
import com.example.sharkcatshop.Room.StickerCart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder> {

    private List<StickerCart> allStickerCartList = new ArrayList<>();
    private Context context;

    public ShoppingCartAdapter(Context context) {
        this.context = context;
    }

    public void setAllStickerCartList(List<StickerCart> allStickerCartList) {
        this.allStickerCartList = allStickerCartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cell_cart, parent, false);
        return new ShoppingCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartViewHolder holder, int position) {
        StickerCart stickerCart = allStickerCartList.get(position);
        holder.tvName.setText(stickerCart.getStickerName());
        holder.tvPrice.setText(String.valueOf(stickerCart.getStickerPrice()));
        Glide.with(context).load(stickerCart.getStickerImage()).into(holder.stickerImage);

    }

    @Override
    public int getItemCount() {
        return allStickerCartList.size();
    }

    static class ShoppingCartViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPrice;
        ImageView stickerImage;

        public ShoppingCartViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            stickerImage = itemView.findViewById(R.id.stickerImage);

        }
    }

}
