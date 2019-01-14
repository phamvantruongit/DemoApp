package vn.com.it.truongpham.mystore.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.adapter.AdapterLoaiSP;
import vn.com.it.truongpham.mystore.model.LoaiSP;
import vn.com.it.truongpham.mystore.model.data.Database;


public class LoaiSanPhamActivity extends AppCompatActivity {
    ImageView iv_add;
    Database database;
    RecyclerView rv_loaisp;
    AdapterLoaiSP adapterLoaiSP;
    List<LoaiSP> loaiSPList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_san_pham);
        database = new Database(this);
        init();
    }

    private void getData() {
        loaiSPList = database.getListLoaiSP();
        adapterLoaiSP = new AdapterLoaiSP(this, loaiSPList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_loaisp.setLayoutManager(layoutManager);
        rv_loaisp.setAdapter(adapterLoaiSP);
    }

    private void init() {
        iv_add = findViewById(R.id.iv_add);
        rv_loaisp = findViewById(R.id.rv_loaisp);
        loaiSPList = database.getListLoaiSP();
        getData();

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LoaiSanPhamActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.popup_add_loaisp);
                dialog.show();
                final EditText edt = dialog.findViewById(R.id.edTenLoaiSP);
                TextView btn_them = dialog.findViewById(R.id.btn_them);
                TextView btn_huy = dialog.findViewById(R.id.btn_huy);

                btn_them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edt.getText().toString() != "") {
                            database.AddLoaiSanPham(edt.getText().toString());
                            Toast.makeText(LoaiSanPhamActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            getData();
                        }
                    }
                });

                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

    public void OnBack(View view) {
        finish();
    }
}
