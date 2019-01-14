package vn.com.it.truongpham.mystore.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.model.SanPham;

public class BanHangQRCodeActivity extends AppCompatActivity {
   List<SanPham> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banhang_qrcode);
        list=new ArrayList<>();
    }

    public void OpenActivity(View view) {
        Intent intent=new Intent(this,ActivityQRCodeScanner.class);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==Activity.RESULT_OK&&data!=null){
            Bundle bundle = getIntent().getExtras();
            list = bundle.getParcelableArrayList("data");
            for(SanPham sanPham:list){
                Log.d("data",sanPham.getName());
            }

        }
    }
}
