package vn.com.it.truongpham.mystore.activity;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.model.SanPham;

public class ActivityQRCodeScanner extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    public static List<SanPham> list=new ArrayList<>();;
    public static List<SanPham> getListSanPham(){
        return list;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initScannerView();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void handleResult(Result result) {

        final Dialog dialog = new Dialog(ActivityQRCodeScanner.this);
        try {
            JSONObject object = new JSONObject(result.getText());
            Log.d("object", object.toString());
            //{"tensp":"ao" ,"gia":100,"soluong":1,"size":"29","id_type":1}
            String tensp = object.getString("tensp");
            String soluong = String.valueOf(object.getInt("soluong"));
            String dongia = String.valueOf(object.getInt("gia"));
            String thongtin=object.getString("thongtin");
            int id = object.getInt("id_type");

            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pop_up_show_qrcode);
            dialog.show();

            TextView tv_sanpham = dialog.findViewById(R.id.tv_tensp);
            TextView tv_sl = dialog.findViewById(R.id.tv_soluong);
            TextView tv_dongia = dialog.findViewById(R.id.tv_gia);
            TextView tv_size = dialog.findViewById(R.id.tv_size);
            SanPham sanPham = new SanPham();

            tv_sanpham.setText("Tên sản phẩm: " + tensp);
            long gia=Long.parseLong(dongia);
            DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
            tv_dongia.setText("Đơn giá : " + decimalFormat.format(gia));
            tv_sl.setText("Số lượng : " + soluong);



            sanPham.setName(tensp);
            sanPham.setGiaban(dongia);
            sanPham.setSoluong(Integer.parseInt(soluong));
            sanPham.setId(id);
            sanPham.setThongin(thongtin);

            String size = object.getString("size");

            if (size.length()>0) {
                tv_size.setVisibility(View.VISIBLE);
                tv_size.setText("Size : " + object.getString("size"));
                sanPham.setSize(size);
            }
            list.add(sanPham);


            dialog.findViewById(R.id.tv_qrcode).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mScannerView.resumeCameraPreview(ActivityQRCodeScanner.this);
                    dialog.dismiss();
                }
            });
            TextView tv_close = dialog.findViewById(R.id.tv_close);
            tv_close.setText("Finish");
            tv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            mScannerView.resumeCameraPreview(ActivityQRCodeScanner.this);
            Toast.makeText(ActivityQRCodeScanner.this, "Mã QR Code không đúng ", Toast.LENGTH_SHORT).show();
        }
    }

    private void initScannerView() {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);

        ArrayList<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.QR_CODE);
        mScannerView.setFormats(formats);

        mScannerView.setResultHandler(this);

        mScannerView.startCamera();
    }
}

