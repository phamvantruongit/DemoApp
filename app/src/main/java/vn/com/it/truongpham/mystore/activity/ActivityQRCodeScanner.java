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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.model.SanPham;

public class ActivityQRCodeScanner extends Activity implements ZXingScannerView.ResultHandler {
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


        final Dialog dialog = new Dialog(ActivityQRCodeScanner.this);
        try {
            JSONObject object = new JSONObject(result.getText());
            Log.d("object", object.toString());
            String name = object.getString("tensp");
            String soluong = String.valueOf(object.getInt("soluong"));
            String dongia = String.valueOf(object.getInt("gia"));
            int id = object.getInt("id");

            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pop_up_show_qrcode);
            dialog.show();

            TextView tv_sanpham = dialog.findViewById(R.id.tv_tensp);
            TextView tv_sl = dialog.findViewById(R.id.tv_soluong);
            TextView tv_dongia = dialog.findViewById(R.id.tv_gia);
            TextView tv_size = dialog.findViewById(R.id.tv_size);
            SanPham sanPham = new SanPham();

            tv_sanpham.setText("Tên sản phẩm: " + name);
            tv_dongia.setText("Đơn giá : " + dongia);
            tv_sl.setText("Số lượng : " + soluong);

            sanPham.setName(name);
            sanPham.setGiaban(Double.parseDouble(dongia));
            sanPham.setSoluong(Integer.parseInt(soluong));
            sanPham.setId_loaisp(id);

            String size = object.getString("size");

            if (size != null && size != "") {
                tv_size.setVisibility(View.VISIBLE);
                tv_size.setText("Size : " + object.getString("size"));
                sanPham.setSize(size);
            }
            final List<SanPham> list = new ArrayList<>();
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
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) list);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
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

