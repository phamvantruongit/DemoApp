package vn.com.it.truongpham.mystore.activity;

import android.Manifest;
import android.app.Activity;
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
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.adapter.HeaderAdapter;
import vn.com.it.truongpham.mystore.model.SanPham;

public class BanHangQRCodeActivity extends AppCompatActivity {
   List<SanPham> list;
   RecyclerView rv_top;
   AdapterTitle adapterTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banhang_qrcode);
//        list=new ArrayList<>();
//        rv_top=findViewById(R.id.rv_title);
//        String arr[]=getResources().getStringArray(R.array.arr_title);
//        HeaderAdapter headerViewListAdapter=new HeaderAdapter(arr);
//        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//        rv_top.setLayoutManager(layoutManager);
//        rv_top.setAdapter(headerViewListAdapter);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==Activity.RESULT_OK&&data!=null){
            list = ActivityQRCodeScanner.getListSanPham();
            for(SanPham sanPham:list){
            }

        }
    }

    public void OpenQRCode(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int checkCallPhonePermission3 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED && checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED && checkCallPhonePermission3 != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},100);
                return;
            } else {
                Intent intent=new Intent(this,ActivityQRCodeScanner.class);
                startActivityForResult(intent,100);
            }

        }
        Intent intent=new Intent(this,ActivityQRCodeScanner.class);
        startActivityForResult(intent,100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent=new Intent(this,ActivityQRCodeScanner.class);
                startActivityForResult(intent,100);
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    class AdapterTitle extends RecyclerView.Adapter<AdapterTitle.ViewHolder>{
        String arr[];

        public AdapterTitle(String[] arr) {
            this.arr = arr;
        }

        @NonNull
        @Override
        public AdapterTitle.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hoadon,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AdapterTitle.ViewHolder viewHolder, int i) {
           viewHolder.tv_title.setText(arr[i]);
        }

        @Override
        public int getItemCount() {
            return arr.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_title;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
