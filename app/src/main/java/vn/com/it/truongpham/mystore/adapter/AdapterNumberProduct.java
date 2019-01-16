package vn.com.it.truongpham.mystore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.model.LoaiSP;


/**
 * Created by t420 on 01-Nov-17.
 */

public class AdapterNumberProduct extends BaseAdapter {
  private Context context;
  private List<LoaiSP> list;
  private  int position;
    public AdapterNumberProduct(Context context, List<LoaiSP> list , int po) {
        this.context = context;
        this.list = list;
        this.position=po;
    }

    @Override
    public int getCount() {
        return list.size();
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
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.item_numberproduct,parent,false);
        TextView txtNumber= (TextView) view.findViewById(R.id.txt_numberproduct);
        ImageView imageView= (ImageView) view.findViewById(R.id.img_check);
        txtNumber.setText(list.get(position).getName());
        if(this.position==position){
            imageView.setVisibility(View.VISIBLE);
        }else  imageView.setVisibility(View.GONE);
        return view;
    }
    public void adapterNumberProduct(int po) {
        this.position=po;
        notifyDataSetChanged();
    }
}
