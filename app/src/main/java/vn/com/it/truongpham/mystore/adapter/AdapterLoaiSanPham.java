package vn.com.it.truongpham.mystore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.model.LoaiSP;

public class AdapterLoaiSanPham  extends RecyclerView.Adapter<AdapterLoaiSanPham.ViewHolder> {
    Context context;
    List<LoaiSP> listLoaiSP;

    public AdapterLoaiSanPham(Context context, List<LoaiSP> listLoaiSP) {
        this.context = context;
        this.listLoaiSP = listLoaiSP;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_loaisanpham,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.tv_loaisp.setText(listLoaiSP.get(position).getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "ID:" +listLoaiSP.get(position).getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLoaiSP.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_loaisp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_loaisp=itemView.findViewById(R.id.tv_loaisp);
        }
    }

}
