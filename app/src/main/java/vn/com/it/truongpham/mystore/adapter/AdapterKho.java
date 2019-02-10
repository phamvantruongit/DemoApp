package vn.com.it.truongpham.mystore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.model.SanPham;

public class AdapterKho extends RecyclerView.Adapter<AdapterKho.ViewHolder> {
    Context context;
    List<SanPham> list;

    public AdapterKho(Context context, List<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_kho,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
         viewHolder.tvTenSP.setText(list.get(i).getName());
         viewHolder.tvSLBan.setText(list.get(i).getSoluongban()+"");
         viewHolder.tvSLTonKho.setText(list.get(i).getSoluong()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSP,tvSLTonKho,tvSLBan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSP=itemView.findViewById(R.id.tvTenSP);
            tvSLTonKho=itemView.findViewById(R.id.tvSLTonKho);
            tvSLBan=itemView.findViewById(R.id.tvSLBan);
        }
    }
}
