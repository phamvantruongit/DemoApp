package vn.com.it.truongpham.mystore.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.adapter.AdapterSpinnerLoaiSP;
import vn.com.it.truongpham.mystore.model.LoaiSP;
import vn.com.it.truongpham.mystore.model.SanPham;
import vn.com.it.truongpham.mystore.model.data.Database;

public class ThemSanPhamActivity extends AppCompatActivity {
    EditText edTenSP, edThongTinSP, edGiaNhap, edGiaBan, edSL, edSize;
    Spinner spLoaiSanPham;
    ImageView img_qrcode;
    TextView tvDongY, tvHuy;
    CheckBox ck_tao_qrcode;
    Database database;
    AdapterSpinnerLoaiSP adapterSpinnerLoaiSP;
    List<LoaiSP> loaiSPList;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sanpham);
        database = new Database(this);

        init();

        loaiSPList = database.getListLoaiSP();
        adapterSpinnerLoaiSP = new AdapterSpinnerLoaiSP(this, loaiSPList);
        spLoaiSanPham.setAdapter(adapterSpinnerLoaiSP);
    }

    private void init() {
        edSize = findViewById(R.id.edSize);
        edTenSP = findViewById(R.id.edTenSP);
        edThongTinSP = findViewById(R.id.edThongTinSP);
        edGiaBan = findViewById(R.id.edGiaBanSP);
        edGiaNhap = findViewById(R.id.edGiaNhapSP);
        edSL = findViewById(R.id.edSLSanPham);
        spLoaiSanPham = findViewById(R.id.spLoaiSanPham);
        img_qrcode = findViewById(R.id.img_qrcode);
        tvDongY = findViewById(R.id.tvDongY);
        tvHuy = findViewById(R.id.tvHuy);
        ck_tao_qrcode = findViewById(R.id.ck_tao_qrcode);

        tvDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edTenSP.getText().toString().trim();
                String info = edThongTinSP.getText().toString().trim();
                String giaban = edGiaBan.getText().toString();
                String gianhap = edGiaNhap.getText().toString();
                String soluong = edSL.getText().toString();
                String size = edSize.getText().toString().trim();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(gianhap) && !TextUtils.isEmpty(giaban) && !TextUtils.isEmpty(soluong)) {
                    SanPham sanPham = new SanPham();
                    sanPham.setName(name);
                    sanPham.setThongin(info);
                    if (Double.parseDouble(giaban) < Double.parseDouble(gianhap)) {
                        Toast.makeText(ThemSanPhamActivity.this, "Gia ban khong the nho hon gia nhap", Toast.LENGTH_SHORT).show();
                        edGiaBan.setText("");
                        edGiaNhap.setText("");
                        return;
                    }
                    sanPham.setGianhap(Double.parseDouble(gianhap));
                    sanPham.setGiaban(Double.parseDouble(giaban));
                    sanPham.setSoluong(Integer.parseInt(soluong));
                    sanPham.setSize(size);
                    database.AddSanPham(sanPham);
                    if (ck_tao_qrcode.isChecked()) {
                        createQRcode(name, giaban, soluong);
                    }

                } else {
                    Toast.makeText(ThemSanPhamActivity.this, "Nhap day thong tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createQRcode(String name, String giaban, String soluong) {
        try {

            String json = new JSONObject()
                    .put("name", name)
                    .put("price_out", giaban)
                    .put("number", 1)
                    .put("id", database.getID())
                    .toString();
            Log.d("JSON", json);

            QRCodeWriter writer = new QRCodeWriter();
            try {
                BitMatrix bitMatrix = writer.encode(json, BarcodeFormat.QR_CODE, 512, 512);
                int width = bitMatrix.getWidth();
                int height = bitMatrix.getHeight();
                Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                    }
                }

                img_qrcode.setImageBitmap(bmp);

                saveImage(bmp, name);


            } catch (WriterException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveImage(Bitmap bitmap, String file_name) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Image_QrCode");
        myDir.mkdirs();

        File file = new File(myDir, file_name + ".jpg");

        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
