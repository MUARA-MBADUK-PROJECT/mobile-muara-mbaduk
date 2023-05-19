package com.example.muara_mbaduk.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.model.response.ProductResponse;
import com.example.muara_mbaduk.view.activity.CustomPembelianTiketActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private CustomPembelianTiketActivity activity;
    private ProductResponse productResponse;
    private List<Map<String, String>> orderData = new LinkedList<>();
    private List<String> namaBarang = new LinkedList<>();
    private int totalHarga = 5000;
    private List<Integer> count = new ArrayList<>();
    ProgressBar progressBar;

    public ProductAdapter(ProductResponse productResponse, CustomPembelianTiketActivity activity) {
        this.productResponse = productResponse;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_layouts, parent, false);
        ProductViewHolder holder = new ProductViewHolder(view);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        for (int i = 0; i < productResponse.getData().size(); i++) {
            count.add(0);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
//         set data

        holder.productTitleTextView.setText(productResponse.getData().get(position).getTitle());
        holder.productPriceTextView.setText("Rp." + String.valueOf(productResponse.getData().get(position).getPrice()));
        holder.productImageView.setVisibility(View.VISIBLE);
        holder.qtyTextView.setText(count.get(position).toString());
        Picasso.get().load(productResponse.getData().get(position).getImage()).into(holder.productImageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.productImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);
            }
        });
        holder.addProductImage.setOnClickListener(v -> {
            totalHarga += productResponse.getData().get(position).getPrice();
            activity.setTotalHarga(totalHarga);
//            if(!namaBarang.contains(productResponse.getData().get(position).getTitle())){
//                namaBarang.add(productResponse.getData().get(position).getTitle());
//            }
            addQuantity(position);
            if (count.get(position) != 0) {
                Map<String , String> data = new HashMap<>();
                data.put("name" , productResponse.getData().get(position).getTitle());
                data.put("count" , String.valueOf(count.get(position)));
                data.put("price" , String.valueOf(productResponse.getData().get(position).getPrice()));
                orderData.add(data);
                int oldCount = count.get(position) - 1;
                orderData.removeIf(stringStringMap -> stringStringMap.get("count").contains(String.valueOf(oldCount)));
            }
            orderData.forEach(stringStringMap -> {
                System.out.println(stringStringMap.get("name"));
                System.out.println(stringStringMap.get("count"));
            });
        });
        holder.minProductImage.setOnClickListener(v -> {

            int oldCount = count.get(position);
            minQuantity(position);
            if (count.get(position) != 0) {
                totalHarga -= productResponse.getData().get(position).getPrice();
                activity.setTotalHarga(totalHarga);
                System.out.println(oldCount);
                Map<String , String> tempData = new HashMap<>();
                tempData.put("name" ,productResponse.getData().get(position).getTitle());
                tempData.put("count" , String.valueOf(oldCount));
                tempData.put("price" , String.valueOf(productResponse.getData().get(position).getPrice()));
                int index = orderData.indexOf(tempData);
                tempData.put("count" , String.valueOf(count.get(position)));
                orderData.set(index , tempData );
                System.out.println(orderData);
            }
        });
    }
    public void addQuantity(int position) {
        Integer oldCound = count.get(position);
        oldCound++;
        count.set(position, oldCound);
        notifyDataSetChanged();
    }
    public void minQuantity(int position) {
        Integer oldCound = count.get(position);
        if (oldCound != 0) {
            oldCound--;
            count.set(position, oldCound);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getItemCount() {
        return productResponse.getData().size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productTitleTextView, productPriceTextView, qtyTextView;
        ImageView productImageView;
        ImageButton addProductImage, minProductImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productPriceTextView = itemView.findViewById(R.id.product_price_textview);
            productTitleTextView = itemView.findViewById(R.id.product_title_textview);
            qtyTextView = itemView.findViewById(R.id.qty_textview);
            productImageView = itemView.findViewById(R.id.product_imageview);
            addProductImage = itemView.findViewById(R.id.product_add_imagebtn);
            minProductImage = itemView.findViewById(R.id.product_minus_imagebtn);
        }
    }

    public boolean validateDataOrder(){
      boolean isvalid = false;
        for (int i = 0; i < count.size(); i++) {
            if(count.get(i) != 0){
                // if user not order package
               isvalid = true;
            }
        }
        return isvalid;
    }
    
    public List<Map<String, String>> getOrderData() {
        return orderData;
    }

}
