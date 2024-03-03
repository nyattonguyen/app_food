package com.example.appfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.appfood.Adapter.CartAdapter;
import com.example.appfood.Helper.ChangeNumberItemsListener;
import com.example.appfood.Helper.ManagmentCart;
import com.example.appfood.R;
import com.example.appfood.databinding.ActivityCartBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CartActivity extends BaseActivity {
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
        ConfirmOrder();
    }
    private void initList() {
        if(managmentCart.getListCart().isEmpty()) {
            binding.txtEmpty.setVisibility(View.VISIBLE);
            binding.scrollViewCart.setVisibility(View.GONE);
        } else {
            binding.txtEmpty.setVisibility(View.GONE);
            binding.scrollViewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cardView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managmentCart.getListCart(), this, () -> calculateCart());

        binding.cardView.setAdapter(adapter);
    }

    private void ConfirmOrder() {
        binding.btnConfirmOrder.setOnClickListener(v -> new SweetAlertDialog(CartActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Your order successfully !")
                .setConfirmClickListener(sDialog -> {
                    managmentCart.clearList();
                    adapter.notifyItemRangeRemoved(0, managmentCart.getListCart().size());
                    sDialog.dismissWithAnimation();
                    Intent intent = new Intent(CartActivity.this, MainActivity.class);
                    startActivity(intent);
                })
                .show());
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