package com.example.muara_mbaduk.data.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.model.entity.PackagePurchaseRequest;
import com.example.muara_mbaduk.model.response.TicketCheckinResponse;
import com.example.muara_mbaduk.view.fragment.TicketAndCampingFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.PackagesViewHolder> {

    TicketCheckinResponse ticketCheckinResponse;



    private List<Map<String, String>> count;
    Fragment fragment;
    private Context context;

    List<PackagePurchaseRequest> packagePurchaseRequests = new ArrayList<>();

    public List<PackagePurchaseRequest> getPackagePurchaseRequests() {
        return packagePurchaseRequests;
    }

    public PackagesAdapter(List<Map<String, String>> data, TicketCheckinResponse packagesResponses , Fragment fragment,Context context) {
        this.count = data;
        this.ticketCheckinResponse = packagesResponses;
        this.fragment = fragment;
        this.context = context;
    }

    @NonNull
    @Override
    public PackagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.packages_layout, parent, false);
        return new PackagesViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull PackagesViewHolder holder, int position) {
        holder.jumlahPesan.setText(count.get(position).get("count"));
        holder.namaPaket.setText(ticketCheckinResponse.getData().getPackages().get(position).getTitle());
        holder.hargaPaket.setText("Rp." + ticketCheckinResponse.getData().getPackages().get(position).getPrice());

        holder.progressBar.setVisibility(View.VISIBLE);
        Picasso.get().load(ticketCheckinResponse.getData().getPackages().get(position).getImage()).
                into(holder.packageImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                });
        TicketAndCampingFragment ticketAndCampingFragment =(TicketAndCampingFragment) this.fragment;

        holder.btnAdd.setOnClickListener(v -> {
            String number = count.get(position).get("count");
            if(number != null){
                int countAsInt = Integer.parseInt(number);
                countAsInt++;
                ticketAndCampingFragment.tambahJumlah(ticketCheckinResponse.getData().getPackages().get(position).getPrice());
                count.get(position).put("count", String.valueOf(countAsInt));
                PackagePurchaseRequest model = new PackagePurchaseRequest();
                model.setId(ticketCheckinResponse.getData().getPackages().get(position).getId());
                model.setQuantity(countAsInt);
                if(packagePurchaseRequests.size() != 0){
                    if(packagePurchaseRequests.size() > position){
                        if(packagePurchaseRequests.get(position).getId().equalsIgnoreCase(model.getId())){
                            int index = packagePurchaseRequests.indexOf(packagePurchaseRequests.get(position));
                            packagePurchaseRequests.set(index,model);
                        }else{
                            packagePurchaseRequests.add(model);
                        }
                    }else{
                        packagePurchaseRequests.add(model);
                    }
                }else{
                    packagePurchaseRequests.add(model);
                }
                notifyDataSetChanged();
                updateCount(count);
            }
        });
        holder.btnMinus.setOnClickListener(v -> {
            String number = count.get(position).get("count");
            if(number != null){
                int countAsInt = Integer.parseInt(number);
                PackagePurchaseRequest model = new PackagePurchaseRequest();
                model.setId(ticketCheckinResponse.getData().getPackages().get(position).getId());
                if (countAsInt != 0) {
                    countAsInt--;
                    model.setQuantity(countAsInt);
                    if(countAsInt != 0){
                        if(packagePurchaseRequests.size() != 0){
                            if(packagePurchaseRequests.size() > position){
                                if(packagePurchaseRequests.get(position).getId().equalsIgnoreCase(model.getId())){
                                    int index = packagePurchaseRequests.indexOf(packagePurchaseRequests.get(position));
                                    packagePurchaseRequests.set(index,model);
                                }else{
                                    packagePurchaseRequests.add(model);
                                }
                            }else{
                                packagePurchaseRequests.add(model);
                            }
                        }else{
                            packagePurchaseRequests.add(model);
                        }
                    }else{
                        if(packagePurchaseRequests.get(position).getId().equalsIgnoreCase(model.getId())){
                            packagePurchaseRequests.remove(packagePurchaseRequests.get(position));
                        }
                    }
                    ticketAndCampingFragment.minusJumlah(ticketCheckinResponse.getData().getPackages().get(position).getPrice());
                }
                count.get(position).put("count", String.valueOf(countAsInt));
                notifyDataSetChanged();
                updateCount(count);
            }
        });
        holder.seeAllProductBtn.setOnClickListener(v -> {
            PackagesProductAdapter packagesProductAdapter
                    = new PackagesProductAdapter
                    (ticketCheckinResponse.getData().getPackages().get(position).getProducts());
            LinearLayoutManager layoutManager  = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
            holder.productRecycleView.setAdapter(packagesProductAdapter);
            holder.productRecycleView.setLayoutManager(layoutManager);
            holder.productRecycleView.setVisibility(View.VISIBLE);
            Animation slideDown = AnimationUtils.loadAnimation(context,R.anim.swape_down);
            LayoutAnimationController controller = new LayoutAnimationController(slideDown);
            holder.productRecycleView.setLayoutAnimation(controller);
            holder.hideAllProductBtn.setVisibility(View.VISIBLE);
            holder.seeAllProductBtn.setVisibility(View.GONE);

        });

        holder.hideAllProductBtn.setOnClickListener(v -> {
            holder.productRecycleView.setVisibility(View.GONE);
            holder.hideAllProductBtn.setVisibility(View.GONE);
            holder.seeAllProductBtn.setVisibility(View.VISIBLE);
            Animation slideUp = AnimationUtils.loadAnimation(v.getContext(),R.anim.swap_up);
            LayoutAnimationController controller = new LayoutAnimationController(slideUp);
            holder.productRecycleView.setLayoutAnimation(controller);
            slideUp.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    holder.productRecycleView.setAnimation(animation);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

            });
            holder.productRecycleView.startAnimation(slideUp);
        });

    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateCount(List<Map<String, String>> newData) {
        this.count = newData;
        notifyDataSetChanged();
    }
    public boolean checkIfCountZero(){
        if(this.count.size() == 0){
            return true;
        }else{
            boolean isReturn = false;
            for (int i = 0; i < this.count.size(); i++) {
                if(!this.count.get(i).get("count").equalsIgnoreCase("0")){
                    isReturn = true;
                }
            }
            return isReturn;
        }
    }
    @Override
    public int getItemCount() {
        return count.size();
    }
    public static class PackagesViewHolder extends RecyclerView.ViewHolder {
        TextView jumlahPesan, namaPaket, hargaPaket;
        ImageButton btnAdd,btnMinus;
        ProgressBar progressBar;
        ImageView packageImageView;
        Button seeAllProductBtn,hideAllProductBtn;

        RecyclerView productRecycleView;

        public PackagesViewHolder(@NonNull View itemView) {
            super(itemView);
            hargaPaket = itemView.findViewById(R.id.harga_paket_textview);
            jumlahPesan = itemView.findViewById(R.id.jumlah_tiket_pesan);
            namaPaket = itemView.findViewById(R.id.nama_paket_textview);
            btnMinus = itemView.findViewById(R.id.btnMinStandard);
            btnAdd = itemView.findViewById(R.id.btnAddStandard);
            packageImageView = itemView.findViewById(R.id.package_image_Imageview);
            progressBar = itemView.findViewById(R.id.progressBar);
            seeAllProductBtn = itemView.findViewById(R.id.lihat_selengkapnya_btn_down);
            hideAllProductBtn = itemView.findViewById(R.id.lihat_selengkapnya_btn_up);
            productRecycleView = itemView.findViewById(R.id.product_list_recycleview);
        }
    }
}
