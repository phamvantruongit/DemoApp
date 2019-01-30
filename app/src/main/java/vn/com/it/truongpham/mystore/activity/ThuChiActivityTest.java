package vn.com.it.truongpham.mystore.activity;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.fragment.ViewPagerAdapter;

public class ThuChiActivityTest extends AppCompatActivity {
    ViewPager viewPager;
    RadioButton radChi, radThu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_chi_test);
        viewPager = findViewById(R.id.viewPager);
        radChi = findViewById(R.id.radChi);
        radThu = findViewById(R.id.radThu);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                     PageSelectedThu();
                } else {
                   PageSelectedChi();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        radThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
                PageSelectedThu();
            }
        });

        radChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
                PageSelectedChi();

            }
        });
    }

    private void PageSelectedThu(){
        radThu.setChecked(true);
        radChi.setChecked(false);
        radChi.setTextColor(ContextCompat.getColor(ThuChiActivityTest.this, R.color.black));
        radThu.setTextColor(ContextCompat.getColor(ThuChiActivityTest.this, R.color.red));

    }
    private void PageSelectedChi(){
        radChi.setChecked(true);
        radThu.setChecked(false);
        radThu.setTextColor(ContextCompat.getColor(ThuChiActivityTest.this, R.color.black));
        radChi.setTextColor(ContextCompat.getColor(ThuChiActivityTest.this, R.color.red));

    }
}
