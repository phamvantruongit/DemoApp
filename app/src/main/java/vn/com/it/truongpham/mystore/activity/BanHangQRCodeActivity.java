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

import vn.com.it.truongpham.mystore.R;

public class BanHangQRCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banhang_qrcode);
    }

    public void OpenActivity(View view) {
        Intent intent=new Intent(this,ActivityQRCodeScanner.class);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==Activity.RESULT_OK&&data!=null){
            Bundle bundle=data.getExtras();
            String qrcode=bundle.getString("qrcode_data");
            Log.d("data",qrcode);
            try {

                JSONObject object = new JSONObject(qrcode);
                String tensp = object.getString("tensp");
                String soluong = String.valueOf(object.getInt("soluong"));
                String dongia = String.valueOf(object.getInt("gia"));
                String size=object.getString("size");
                String id= String.valueOf(object.getInt("id"));



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(BanHangQRCodeActivity.this, "Mã QR Code không đúng ", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
