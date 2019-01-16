package vn.com.it.truongpham.mystore.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.model.SanPham;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    List<SanPham> list;
    IOnClick iOnClick;

    public SanPhamAdapter(List<SanPham> list, IOnClick iOnClick) {
        this.list = list;
        this.iOnClick = iOnClick;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sanpham, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tvTenSP.setText(list.get(i).getName());
        if (list.get(i).getThongin().length()>0) {
            viewHolder.tvTTSP.setVisibility(View.VISIBLE);
            viewHolder.tvTTSP.setText(list.get(i).getThongin());
        }
        Log.d("TAG",list.get(i).getSize() + list.get(i).getThongin());
        if (list.get(i).getSize().length()>0) {
            viewHolder.tv_Size.setVisibility(View.VISIBLE);
            viewHolder.tvSize.setVisibility(View.VISIBLE);
            viewHolder.tvSize.setText(list.get(i).getSize());
        }

        viewHolder.tvSLSP.setText(list.get(i).getSoluong()+"");
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.tvGia.setText(decimalFormat.format(list.get(i).getGiaban()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClick.iOnClick(list.get(i));
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSP, tvTTSP, tvSLSP, tvSize, tv_Size, tvGia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSP = itemView.findViewById(R.id.tvtensp);
            tvTTSP = itemView.findViewById(R.id.tv_TTSanPham);
            tvSLSP = itemView.findViewById(R.id.tvSLSP);
            tvGia = itemView.findViewById(R.id.tvGiaSP);
            tvSize = itemView.findViewById(R.id.tvSizeSP);
            tv_Size = itemView.findViewById(R.id.tv_SizeSP);
        }
    }

    public interface  IOnClick{
        void iOnClick(SanPham sanPham);
    }
}
