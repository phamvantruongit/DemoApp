package vn.com.it.truongpham.mystore.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.adapter.AdapterKho;
import vn.com.it.truongpham.mystore.model.LoaiSP;
import vn.com.it.truongpham.mystore.model.SanPham;
import vn.com.it.truongpham.mystore.model.data.Database;

public class ActivityKho extends AppCompatActivity {
    RecyclerView rv;
    AdapterKho adapterKho;
    LinearLayoutManager linearLayoutManager;
    List<SanPham> list;
    Database database;
    List<LoaiSP> loaiSPList = new ArrayList<>();
    int id=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kho);
        database=new Database(this);
        loaiSPList = database.getListLoaiSP();
        NiceSpinner niceSpinner = findViewById(R.id.nice_spinner);
        rv=findViewById(R.id.rv);
        getDatạ(1);

        final List<String> list = new ArrayList<>();
        if (loaiSPList.size() > 0) {
            niceSpinner.setVisibility(View.VISIBLE);
            for (LoaiSP loaiSP : loaiSPList) {
                list.add(loaiSP.getName());
            }
            niceSpinner.attachDataSource(list);
        }
        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long i_d) {
                position++;
                id=position;
                getDatạ(id);
            }
        });

    }
    private void getDatạ(int id){
        list=database.getListSanPham(id);
        adapterKho=new AdapterKho(this,list);
        linearLayoutManager=new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapterKho);
    }
}
