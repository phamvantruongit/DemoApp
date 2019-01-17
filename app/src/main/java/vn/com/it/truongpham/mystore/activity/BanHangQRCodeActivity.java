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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.adapter.HeaderAdapter;
import vn.com.it.truongpham.mystore.adapter.SanPhamAdapter;
import vn.com.it.truongpham.mystore.model.SanPham;

import static io.fabric.sdk.android.services.concurrency.AsyncTask.init;

public class BanHangQRCodeActivity extends AppCompatActivity implements SanPhamAdapter.IOnClick {
    private List<SanPham> list;
    private RecyclerView rv_ListSP;
    private TextView tvDongY, tvTongTien;
    private RecyclerView.LayoutManager layoutManager;
    private EditText edChietKhau;
    private long TongTien = 0;
    DecimalFormat decimalFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                String chietkhau=edChietKhau.getText().toString();
                if(chietkhau.length()>0){

                    final long a = Long.parseLong(chietkhau);
                    final long b = TongTien;
                    final double c = ((double) a / 100) * TongTien;
                    Log.d("TAG",c+ "-" +a  );
                    tvTongTien.setText("Tong Tien:"+decimalFormat.format(c));
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(BanHangQRCodeActivity.this);
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
            list = ActivityQRCodeScanner.getListSanPham();
            for (int i = 0; i < list.size(); i++) {
                TongTien += Long.parseLong(list.get(i).getGiaban()) * list.get(i).getSoluong();
            }
            tvTongTien.setText(decimalFormat.format(TongTien));

            SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(list, this);
            rv_ListSP.setLayoutManager(layoutManager);
            rv_ListSP.setAdapter(sanPhamAdapter);


        }
    }

    public void OpenQRCode(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int checkCallPhonePermission3 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED && checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED && checkCallPhonePermission3 != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 100);
                return;
            } else {
                Intent intent = new Intent(this, ActivityQRCodeScanner.class);
                startActivityForResult(intent, 100);
            }

        }
        Intent intent = new Intent(this, ActivityQRCodeScanner.class);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(this, ActivityQRCodeScanner.class);
                startActivityForResult(intent, 100);
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void iOnClick(SanPham sanPham) {

    }
}
