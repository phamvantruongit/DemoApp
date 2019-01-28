package vn.com.it.truongpham.mystore.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
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
    List<LoaiSP> loaiSPList = new ArrayList<>();
    List<SanPham> listSanPham = new ArrayList<>();
    //https://github.com/arcadefire/nice-spinner
    //https://androidjson.com/android-add-search-box-filter-sqlite/
    public static int id_loaisp = 1;
    SanPhamAdapter sanPhamAdapter;
    RecyclerView.LayoutManager layoutManager;
    EditText edTimKiemSP;
    List<SanPham> list;
    List<SanPham> listCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list=new ArrayList<>();
        listCart=new ArrayList<>();
        setContentView(R.layout.layout_ds_sanpham);
        layoutManager = new LinearLayoutManager(this);
        database = new Database(this);
        loaiSPList = database.getListLoaiSP();
        init();
        //Todo test app

    }

    @Override
    protected void onResume() {
        super.onResume();
        getListSanPham(1);
    }

    private void getListSanPham(int pos) {
        if (loaiSPList.size() > 0) {
            listSanPham = database.getListSanPham(pos);
        }
        if (listSanPham.size() > 0) {
            rv_sp.setVisibility(View.VISIBLE);
            sanPhamAdapter = new SanPhamAdapter(DSMuaSanPhamActivity.this,listSanPham, this, true);
            rv_sp.setLayoutManager(layoutManager);
            rv_sp.setAdapter(sanPhamAdapter);
            sanPhamAdapter.notifyDataSetChanged();

        } else {
            rv_sp.setVisibility(View.GONE);
        }
    }

    private void init() {
        rv_sp = findViewById(R.id.rv_sp);
        edTimKiemSP=findViewById(R.id.edTimKiemSanPham);
        NiceSpinner niceSpinner = findViewById(R.id.nice_spinner);
        niceSpinner.setVisibility(View.GONE);
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position++;
                id_loaisp = position;
                Log.d("ID", id_loaisp + "");
                getListSanPham(id_loaisp);
            }
        });
        edTimKiemSP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable name) {
                search(name);

            }
        });


    }

    private void search(Editable name){
        list=database.searchListSanPham(name.toString());

        if (list.size() > 0) {
            listSanPham.clear();
            rv_sp.setVisibility(View.VISIBLE);
            sanPhamAdapter = new SanPhamAdapter(DSMuaSanPhamActivity.this,list,DSMuaSanPhamActivity.this,true);
            rv_sp.setLayoutManager(layoutManager);
            rv_sp.setAdapter(sanPhamAdapter);
            sanPhamAdapter.notifyDataSetChanged();

        }

        if(name.toString().isEmpty()) {
            list.clear();
            getListSanPham(id_loaisp);
        }


    }

    public void OnBackPress(View view) {
        back();
    }

    public void OpenActivity(View view) {
        Intent intent = new Intent(this, ThemSanPhamActivity.class);
        intent.putExtra("id_loaisp", id_loaisp);
        startActivityForResult(intent, 100);
    }

    @Override
    public void iOnClick(final SanPham sanPham, final int position) {
        listCart.add(sanPham);
    }

    @Override
    public void onBackPressed() {
        back();

//        else
//        {
//            Intent returnIntent = new Intent();
//            setResult(Activity.RESULT_CANCELED, returnIntent);
//            finish();
//        }
    }

   private void back(){
       if(listCart.size()>0){
           Intent intent=new Intent();
           intent.putParcelableArrayListExtra("listCart", (ArrayList<? extends Parcelable>) listCart);
           setResult(Activity.RESULT_OK,intent);
           finish();
       }
   }
}

