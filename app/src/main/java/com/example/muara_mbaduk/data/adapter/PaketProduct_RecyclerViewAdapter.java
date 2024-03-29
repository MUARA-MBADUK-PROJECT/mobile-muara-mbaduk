package com.example.muara_mbaduk.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.model.entity.Packages;
import com.example.muara_mbaduk.model.response.PackageBySlugResponse;
import com.example.muara_mbaduk.model.response.PackagesResponse;

public class PaketProduct_RecyclerViewAdapter extends RecyclerView.Adapter<PaketProduct_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    PackageBySlugResponse packageBySlugResponse;


    public PaketProduct_RecyclerViewAdapter(Context context, PackageBySlugResponse packageBySlugResponse) {
        this.context = context;
        this.packageBySlugResponse = packageBySlugResponse;
    }

    @NonNull
    @Override
    public PaketProduct_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_paketreview, parent, false);
        return new PaketProduct_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaketProduct_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.product.setText(packageBySlugResponse.getData().getProducts().get(position).getTitle());
//        System.out.println(packagesResponse.getData().get(position).getProducts().get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return packageBySlugResponse.getData().getProducts().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.product);
        }
    }
}
