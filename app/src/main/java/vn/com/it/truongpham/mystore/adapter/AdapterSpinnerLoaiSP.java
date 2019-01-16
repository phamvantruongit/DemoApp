package vn.com.it.truongpham.mystore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.model.LoaiSP;

public class AdapterSpinnerLoaiSP extends BaseAdapter{
    Context context;
    List<LoaiSP> list;

    public AdapterSpinnerLoaiSP(Context context, List<LoaiSP> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_sanpham,parent,false);
        TextView tv_loaisp=view.findViewById(R.id.tv_loaisp);
        tv_loaisp.setText(list.get(position).getName());
        return view;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_loaisp,parent,false);
        TextView tv_loaisp=view.findViewById(R.id.tv_loaisp);
        tv_loaisp.setText(list.get(position).getName());
        return view;
    }
}
