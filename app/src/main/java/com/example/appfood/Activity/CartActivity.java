package com.example.appfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.appfood.Helper.ManagmentCart;
import com.example.appfood.R;
import com.example.appfood.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;
    private double tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        setVariable();
        calculateCart();
        initList();

    }
    private void initList() {
        if(managmentCart.getListCart().isEmpty()) {
            binding.txtEmpty.setVisibility(View.VISIBLE);
            binding.scrollViewCart.setVisibility(View.GONE);
        } else {
            binding.txtEmpty.setVisibility(View.GONE);
            binding.scrollViewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.cardView.setLayoutManager(linearLayoutManager);
//        adapter = new
    }
    private void calculateCart() {
        double percentTax = 0.02; //percent 2% tax
        double delivery = 10;//Dollar

        tax = Math.round(managmentCart.getTotalFee()*percentTax*100.0) /100;

        double total = Math.round((managmentCart.getTotalFee() + tax + delivery)*100) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee()*100)/100;

        binding.txtTotalFee.setText("$" +itemTotal);
        binding.txtTax.setText("$" +tax);
        binding.txtDelivery.setText("$" +delivery);
        binding.txtTotalCart.setText("$" +total);

    }
    private void setVariable() {
        binding.btnBackCart.setOnClickListener(v -> finish());
    }
}