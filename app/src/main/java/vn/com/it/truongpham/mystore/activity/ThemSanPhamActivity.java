package vn.com.it.truongpham.mystore.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.adapter.AdapterNumberProduct;
import vn.com.it.truongpham.mystore.adapter.AdapterSpinnerLoaiSP;
import vn.com.it.truongpham.mystore.model.LoaiSP;
import vn.com.it.truongpham.mystore.model.SanPham;
import vn.com.it.truongpham.mystore.model.data.Database;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;
import static android.text.InputType.TYPE_NUMBER_FLAG_SIGNED;

public class ThemSanPhamActivity extends AppCompatActivity {
    EditText edTenSP, edThongTinSP, edGiaNhap, edGiaBan, edSL, edSize;
    ImageView img_qrcode;
    TextView tvDongY, tvHuy, tv_loaisp, tv_showLoaiSP;

    CheckBox ck_tao_qrcode;
    Database database;
    List<LoaiSP> loaiSPList = new ArrayList<>();
    public int positions, id_loaisanpham=0, id;
    SanPham sanPham;
    boolean checkEditSP;
    String name="";
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = new Database(this);
        id_loaisanpham = getIntent().getIntExtra("id_loaisp", 1);
        checkEditSP = getIntent().getBooleanExtra("editSP", false);

        setContentView(R.layout.activity_them_sanpham);

        init();

        if (checkEditSP) {

            Bundle bundle = getIntent().getBundleExtra("sanpham");
            sanPham = bundle.getParcelable("sanpham");
            id = sanPham.getId();
            edTenSP.setText(sanPham.getName());
            edSL.setText(sanPham.getSoluong() + "");
            edSize.setText(sanPham.getSize().length() > 0 ? sanPham.getSize() : "");
            edThongTinSP.setText(sanPham.getThongin().length() > 0 ? sanPham.getThongin() : "");
            edGiaBan.setText(sanPham.getGiaban() + "");
            edGiaNhap.setText(sanPham.getGianhap() + "");
            TextView tv_title = findViewById(R.id.tv_title);
            tv_title.setText("Sua thong tin san pham ");

        }



        loaiSPList = database.getListLoaiSP();
        if (loaiSPList.size() > 0) {
            findViewById(R.id.ln_loaisp).setVisibility(View.VISIBLE);
        }


    }

    private void init() {
        tv_loaisp = findViewById(R.id.tv_loaisp);
        tv_showLoaiSP = findViewById(R.id.tv_showLoaiSP);
        edSize = findViewById(R.id.edSize);
        edTenSP = findViewById(R.id.edTenSP);
        edThongTinSP = findViewById(R.id.edThongTinSP);
        edGiaBan = findViewById(R.id.edGiaBanSP);
        edGiaNhap = findViewById(R.id.edGiaNhapSP);
        edSL = findViewById(R.id.edSLSanPham);
        img_qrcode = findViewById(R.id.img_qrcode);
        tvDongY = findViewById(R.id.tvDongY);
        tvHuy = findViewById(R.id.tvHuy);
        ck_tao_qrcode = findViewById(R.id.ck_tao_qrcode);
        name=database.getNameLoaiSanPham(id_loaisanpham);

        tv_showLoaiSP.setText(name);



        edGiaNhap.addTextChangedListener(new NumberTextWatcherForThousand(edGiaNhap));

        NumberTextWatcherForThousand numberTextWatcherForThousand = new NumberTextWatcherForThousand();
        numberTextWatcherForThousand.trimCommaOfString(edGiaNhap.getText().toString());

        edGiaBan.addTextChangedListener(new NumberTextWatcherForThousand(edGiaBan));
        numberTextWatcherForThousand.trimCommaOfString(edGiaBan.getText().toString());


        tvDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edTenSP.getText().toString().trim();
                String info = edThongTinSP.getText().toString().trim();
                String giaban = edGiaBan.getText().toString().replace(",", "");
                String gianhap = edGiaNhap.getText().toString().replace(",", "");

                String soluong = edSL.getText().toString();
                String size = edSize.getText().toString().trim();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(gianhap) && !TextUtils.isEmpty(giaban) && !TextUtils.isEmpty(soluong)) {
                    SanPham sanPham = new SanPham();
                    sanPham.setName(name);
                    sanPham.setThongin(info);

                    if (Long.parseLong(giaban) < Long.parseLong(gianhap)) {
                        Toast.makeText(ThemSanPhamActivity.this, "Gia ban khong the nho hon gia nhap", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (Integer.parseInt(soluong) <= 0) {
                        Toast.makeText(ThemSanPhamActivity.this, "So luong phai >0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    sanPham.setGianhap(gianhap);
                    sanPham.setGiaban(giaban);
                    sanPham.setSoluong(Integer.parseInt(soluong));
                    sanPham.setSize(size);
                    sanPham.setId_loaisp(id_loaisanpham);

                    if (checkEditSP) {
                        database.editSanPham(sanPham, id);
                        Toast.makeText(ThemSanPhamActivity.this, "Sua san pham thanh cong", Toast.LENGTH_SHORT).show();

                    } else {
                        database.AddSanPham(sanPham);
                        Toast.makeText(ThemSanPhamActivity.this, "Them san pham thanh cong", Toast.LENGTH_SHORT).show();
                    }


                    if (ck_tao_qrcode.isChecked()) {
                        createQRcode(name, giaban, soluong, size, edThongTinSP.getText().toString());
                    }


                } else {
                    Toast.makeText(ThemSanPhamActivity.this, "Nhap day thong tin", Toast.LENGTH_SHORT).show();
                }
            }
        });




        tvHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestFocus();
            }
        });

    }

    private void requestFocus() {
        edTenSP.setText("");
        edThongTinSP.setText("");
        edSize.setText("");
        edGiaNhap.setText("");
        edGiaBan.setText("");
        edSL.setText("");
        edTenSP.requestFocus();

    }

    private void createQRcode(String name, String giaban, String soluong, String size, String thongtin) {
        try {

            String json = new JSONObject()
                    .put("tensp", name)
                    .put("gia", giaban)
                    .put("tongsoluong", soluong)
                    .put("thongtin", thongtin)
                    .put("soluong", 1)
                    .put("id", id_loaisanpham)
                    .put("size", size)
                    .toString();
            Log.d("JSON", json);

            QRCodeWriter writer = new QRCodeWriter();
            try {
                BitMatrix bitMatrix = writer.encode(json, BarcodeFormat.QR_CODE, 100, 90);
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

    public void showPopup(View view) {
        dialog = new Dialog(ThemSanPhamActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.listview_number_product);
        dialog.show();
        ListView lv_product = dialog.findViewById(R.id.lv_product);
        final AdapterNumberProduct adapterNumberProduct = new AdapterNumberProduct(getApplicationContext(), loaiSPList, positions);
        lv_product.setAdapter(adapterNumberProduct);
        lv_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positions = position;
                id_loaisanpham = loaiSPList.get(position).getId();
                adapterNumberProduct.adapterNumberProduct(positions);
                tv_showLoaiSP.setText(loaiSPList.get(positions).getName());
                dialog.dismiss();
            }
        });


    }

    public void onBack(View view) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public class NumberTextWatcherForThousand implements TextWatcher {

        EditText editText;

        public NumberTextWatcherForThousand() {
        }

        public NumberTextWatcherForThousand(EditText editText) {
            this.editText = editText;


        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                editText.removeTextChangedListener(this);
                String value = editText.getText().toString();


                if (value != null && !value.equals("")) {

                    if (value.startsWith(".")) {
                        editText.setText("0.");
                    }
                    if (value.startsWith("0") && !value.startsWith("0.")) {
                        editText.setText("");

                    }


                    String str = editText.getText().toString().replaceAll(",", "");
                    if (!value.equals(""))
                        editText.setText(getDecimalFormattedString(str));
                    editText.setSelection(editText.getText().toString().length());
                }
                editText.addTextChangedListener(this);
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
                editText.addTextChangedListener(this);
            }

        }

        public String getDecimalFormattedString(String value) {
            StringTokenizer lst = new StringTokenizer(value, ".");
            String str1 = value;
            String str2 = "";
            if (lst.countTokens() > 1) {
                str1 = lst.nextToken();
                str2 = lst.nextToken();
            }
            String str3 = "";
            int i = 0;
            int j = -1 + str1.length();
            if (str1.charAt(-1 + str1.length()) == '.') {
                j--;
                str3 = ".";
            }
            for (int k = j; ; k--) {
                if (k < 0) {
                    if (str2.length() > 0)
                        str3 = str3 + "." + str2;
                    return str3;
                }
                if (i == 3) {
                    str3 = "," + str3;
                    i = 0;
                }
                str3 = str1.charAt(k) + str3;
                i++;
            }

        }

        public String trimCommaOfString(String string) {
            if (string.contains(",")) {
                return string.replace(",", "");
            } else {
                return string;
            }

        }
    }

}
