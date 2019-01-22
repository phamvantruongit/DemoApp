package vn.com.it.truongpham.mystore.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.adapter.SanPhamAdapter;
import vn.com.it.truongpham.mystore.model.SanPham;
import vn.com.it.truongpham.mystore.model.data.Database;


public class BanHangActivity extends AppCompatActivity implements SanPhamAdapter.IOnClick {
    private List<SanPham> list;
    private RecyclerView rv_ListSP;
    private TextView tvDongY, tvTongTien;
    private RecyclerView.LayoutManager layoutManager;
    private EditText edChietKhau;
    private long TongTien = 0;
    DecimalFormat decimalFormat;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=new Database(this);
        setContentView(R.layout.activity_banhang_qrcode);
        list = new ArrayList<>();
        decimalFormat = new DecimalFormat("###,###,###");
        layoutManager = new LinearLayoutManager(this);
        init();
    }

    private void init() {
        rv_ListSP = findViewById(R.id.rv_ListSP);
        tvDongY = findViewById(R.id.tvDongY);
        tvTongTien = findViewById(R.id.tvTongTien);
        edChietKhau = findViewById(R.id.edChietKhau);
        tvDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chietkhau = edChietKhau.getText().toString();
                if (chietkhau.length() > 0) {

                    final long a = Long.parseLong(chietkhau);
                    final long b = TongTien;
                    final double c = ((double) a / 100L) * TongTien;
                    Log.d("TAG", c + "-" + a);
                    tvTongTien.setText("Tong Tien:" + decimalFormat.format(c));
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(BanHangActivity.this);
                builder.setCancelable(false);
                builder.setTitle("Thong bao");
                builder.setMessage("Ban co muon in hoa don ?");
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                //builder.create().show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
//            list = ActivityQRCodeScanner.getListSanPham();
//            for (int i = 0; i < list.size(); i++) {
//                TongTien += Long.parseLong(list.get(i).getGiaban()) * list.get(i).getSoluong();
//            }
//            tvTongTien.setText(decimalFormat.format(TongTien));
//
//            SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(list, this,true);
//            rv_ListSP.setLayoutManager(layoutManager);
//            rv_ListSP.setAdapter(sanPhamAdapter);


        }
    }

    public void OpenQRCode(View view) {

        Intent intent = new Intent(this, DSMuaSanPhamActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    public void iOnClick(SanPham sanPham, int position) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.updateStatus();
    }
}

