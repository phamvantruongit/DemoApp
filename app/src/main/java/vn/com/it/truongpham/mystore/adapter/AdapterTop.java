package vn.com.it.truongpham.mystore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.com.it.truongpham.mystore.activity.ActivityQRCodeScanner;
import vn.com.it.truongpham.mystore.activity.BanHangQRCodeActivity;
import vn.com.it.truongpham.mystore.activity.LoaiSanPhamActivity;
import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.activity.SanPhamActivity;

public class AdapterTop extends RecyclerView.Adapter<AdapterTop.ViewHolder> {
    Context context;
    String arr[];

    public AdapterTop(Context context, String arr[]) {
        this.context = context;
        this.arr=arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_top,viewGroup,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tv_top.setText(arr[i]);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=null;
                switch (i){
                    case 0 :
                        intent=new Intent(context,LoaiSanPhamActivity.class);
                        break;
                    case 1 :
                        intent=new Intent(context,SanPhamActivity.class);
                        break;
                    case 3 :
                        intent=new Intent(context,BanHangQRCodeActivity.class);
                        break;
                }

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_top;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_top=itemView.findViewById(R.id.tv_top);
        }
    }
}
