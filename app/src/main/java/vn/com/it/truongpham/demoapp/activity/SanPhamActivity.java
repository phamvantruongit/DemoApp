package vn.com.it.truongpham.demoapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import vn.com.it.truongpham.demoapp.R;

public class SanPhamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        init();
    }

    private void init() {
        //findViewById(R.id.tv_show);
    }

    public void OnBack(View view) {
        finish();
    }

    public void OpenActivity(View view) {
        Intent intent=new Intent(this,ThemSanPhamActivity.class);
        startActivityForResult(intent,100);
    }
}
