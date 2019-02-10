package vn.com.it.truongpham.mystore.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TextView;

import org.angmarch.views.NiceSpinner;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.model.LoaiSP;
import vn.com.it.truongpham.mystore.model.MuaVao;
import vn.com.it.truongpham.mystore.model.data.Database;

public class ActivityThuChi extends AppCompatActivity implements View.OnClickListener {
    NiceSpinner niceSpinner;
    Database database;
    List<LoaiSP> loaiSPList = new ArrayList<>();
    TextView tvFrom, tvTo ,tvSLMuaVao,tvThanhTienMuaVao ,tvSLBanRa,tvTTBanRa,tvSLCanDoi,tvTTCanDoi;
    String From, To;
    DecimalFormat decimalFormat;
    int id_type =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new Database(this);
        setContentView(R.layout.activity_thu_chi);
        decimalFormat=new DecimalFormat("###,###,###");

        niceSpinner = findViewById(R.id.nice_spinner);
        tvFrom = findViewById(R.id.edFrom);
        tvTo = findViewById(R.id.edTo);
        tvSLMuaVao=findViewById(R.id.tvSLMuaVao);
        tvThanhTienMuaVao=findViewById(R.id.tvThanhTienMuaVao);
        tvSLBanRa=findViewById(R.id.tvSLBanRa);
        tvSLCanDoi=findViewById(R.id.tvSLBanRa);

        loaiSPList = database.getListLoaiSP();

        final List<String> list = new ArrayList<>();
        if (loaiSPList.size() > 0) {
            niceSpinner.setVisibility(View.VISIBLE);
            for (LoaiSP loaiSP : loaiSPList) {
                list.add(loaiSP.getName());
            }
            niceSpinner.attachDataSource(list);
        }
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.get(Calendar.YEAR);
        myCalendar.get(Calendar.MONTH);
        myCalendar.get(Calendar.DAY_OF_MONTH);
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        tvFrom.setText(sdf.format(myCalendar.getTime()));
        tvTo.setText(sdf.format(myCalendar.getTime()));

        From = "01/01/2019";
        To = tvTo.getText().toString();


        tvTo.setOnClickListener(this);
        tvFrom.setOnClickListener(this);

        getMuaVao(id_type);

        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 position++;
                 id_type=position;
                 getMuaVao(id_type);
            }
        });

    }

    private  void getMuaVao(int id){
        MuaVao muaVao =database.getMuaVao(id ,From,To);

        if(muaVao!=null){
            try {
                tvSLMuaVao.setText(muaVao.soluong + "");
                Long gia = Long.parseLong(muaVao.price);
                Long thanhtien = muaVao.soluong * gia;
                tvThanhTienMuaVao.setText(decimalFormat.format(thanhtien));
            }
            catch (Exception e){
                  tvThanhTienMuaVao.setText("0");
                  tvSLMuaVao.setText("0");
            }
        }
    }



    public void OnBackPress(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edFrom:
                showDateFrom();
                break;
            case R.id.edTo:
                showDateTo();
                break;
        }

    }

    private void showDateFrom() {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

                tvFrom.setText(sdf.format(myCalendar.getTime()));

                From = tvFrom.getText().toString();
            }

        };
        new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showDateTo() {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

                tvTo.setText(sdf.format(myCalendar.getTime()));

                To = tvTo.getText().toString();
            }

        };
        new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}
