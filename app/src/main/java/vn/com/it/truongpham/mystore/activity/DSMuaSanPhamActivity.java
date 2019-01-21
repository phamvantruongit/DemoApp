package vn.com.it.truongpham.mystore.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.adapter.SanPhamAdapter;
import vn.com.it.truongpham.mystore.model.LoaiSP;
import vn.com.it.truongpham.mystore.model.SanPham;
import vn.com.it.truongpham.mystore.model.data.Database;

public class DSMuaSanPhamActivity extends AppCompatActivity implements SanPhamAdapter.IOnClick {
    RecyclerView rv_sp;

    Database database;
    List<LoaiSP> loaiSPList=new ArrayList<>();
    List<SanPham> listSanPham=new ArrayList<>();
    //https://github.com/arcadefire/nice-spinner
    public static int id_loaisp=1;
    SanPhamAdapter sanPhamAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        layoutManager=new LinearLayoutManager(this);
        database=new Database(this);
        loaiSPList=database.getListLoaiSP();
        if(loaiSPList.size()>0) {
            listSanPham = database.getListSanPham(1);
        }
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getListSanPham();
    }

    private void getListSanPham(){
        if(listSanPham.size()>0){
            rv_sp.setVisibility(View.VISIBLE);
            sanPhamAdapter=new SanPhamAdapter(listSanPham,this,true);
            rv_sp.setLayoutManager(layoutManager);
            rv_sp.setAdapter(sanPhamAdapter);
            sanPhamAdapter.notifyDataSetChanged();

        }else {
            rv_sp.setVisibility(View.GONE);
        }
    }
    private void init() {
        rv_sp=findViewById(R.id.rv_sp);
        NiceSpinner niceSpinner = findViewById(R.id.nice_spinner);
        niceSpinner.setVisibility(View.GONE);
        List<String> list=new ArrayList<>();
        if(loaiSPList.size()>0) {
            niceSpinner.setVisibility(View.VISIBLE);
            for (LoaiSP loaiSP : loaiSPList) {
                list.add(loaiSP.getName());
            }
            niceSpinner.attachDataSource(list);
        }
        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position++;
                id_loaisp=position;
                Log.d("ID",id_loaisp+"");
                listSanPham=database.getListSanPham(position);
                getListSanPham();
            }
        });




    }

    public void OnBack(View view) {
        finish();
    }

    public void OpenActivity(View view) {
        Intent intent=new Intent(this,ThemSanPhamActivity.class);
        intent.putExtra("id_loaisp",id_loaisp);
        startActivityForResult(intent,100);
    }

    @Override
    public void iOnClick(final SanPham sanPham , final int position) {
       List<SanPham> list=new ArrayList<>();
       list.add(sanPham);
    }
}

