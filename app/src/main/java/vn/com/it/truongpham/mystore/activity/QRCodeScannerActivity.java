package vn.com.it.truongpham.mystore.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import vn.com.it.truongpham.mystore.R;

public class QRCodeScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

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
        final Dialog dialog = new Dialog(QRCodeScannerActivity.this);
        try {
            JSONObject object = new JSONObject(result.getText());
            Log.d("object", object.toString());
            String name = object.getString("name");
            String soluong = String.valueOf(object.getInt("soluong"));
            String dongia = String.valueOf(object.getInt("dongia"));
            int id = object.getInt("id");

            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pop_up_show_qrcode);
            dialog.show();

            TextView tv_sanpham = dialog.findViewById(R.id.tv_tensp);
            TextView tv_sl = dialog.findViewById(R.id.tv_soluong);
            TextView tv_dongia = dialog.findViewById(R.id.tv_gia);
            TextView tv_size = dialog.findViewById(R.id.tv_size);

            tv_sanpham.setText("Tên sản phẩm: " + name);
            tv_dongia.setText("Đơn giá : " + dongia );
            tv_sl.setText("Số lượng : " + soluong );

            String size = object.getString("size");

            if (size != null && size != "") {
                tv_size.setVisibility(View.VISIBLE);
                tv_size.setText("Size : " + object.getString("size"));
            }

            dialog.findViewById(R.id.tv_qrcode).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mScannerView.resumeCameraPreview(QRCodeScannerActivity.this);
                    dialog.dismiss();
                }
            });

            dialog.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(QRCodeScannerActivity.this, "Mã QR Code không đúng ", Toast.LENGTH_SHORT).show();
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
