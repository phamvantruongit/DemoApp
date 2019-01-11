package vn.com.it.truongpham.demoapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import vn.com.it.truongpham.demoapp.R;
import vn.com.it.truongpham.demoapp.adapter.AdapterLoaiSanPham;
import vn.com.it.truongpham.demoapp.model.LoaiSP;
import vn.com.it.truongpham.demoapp.model.data.Database;

public class SanPhamActivity extends AppCompatActivity {
    RecyclerView rv_loaisp;
    Database database;
    List<LoaiSP> loaiSPList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        database=new Database(this);
        loaiSPList=database.getListLoaiSP();
        init();
    }

    private void init() {
        //findViewById(R.id.tv_show);
        rv_loaisp=findViewById(R.id.rv_loaisp);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rv_loaisp.setLayoutManager(layoutManager);
        AdapterLoaiSanPham adapterLoaiSanPham=new AdapterLoaiSanPham(this,loaiSPList);
        rv_loaisp.setAdapter(adapterLoaiSanPham);

    }

    public void OnBack(View view) {
        finish();
    }

    public void OpenActivity(View view) {
        Intent intent=new Intent(this,ThemSanPhamActivity.class);
        startActivityForResult(intent,100);
    }
}
