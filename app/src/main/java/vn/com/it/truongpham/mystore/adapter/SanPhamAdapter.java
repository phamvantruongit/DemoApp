package vn.com.it.truongpham.mystore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import vn.com.it.truongpham.mystore.R;
import vn.com.it.truongpham.mystore.model.SanPham;
import vn.com.it.truongpham.mystore.model.data.Database;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    List<SanPham> listMain;
    IOnClick iOnClick;
    boolean check;
    Database database;
    Context context;


    public SanPhamAdapter(Context context,List<SanPham> list, IOnClick iOnClick,boolean check) {
        this.context=context;
        this.iOnClick = iOnClick;
        this.check=check;
        listMain=list;

        database=new Database(context);

    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sanpham, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.tvTenSP.setText(listMain.get(position).getName());
        if(check){
            viewHolder.ckbox.setVisibility(View.VISIBLE);
            if(listMain.get(position).getStatus().equals("1")){
                viewHolder.ckbox.setChecked(true);
            }
        }
        if (listMain.get(position).getThongin().length()>0) {
            viewHolder.tvTTSP.setVisibility(View.VISIBLE);
            viewHolder.tvTTSP.setText(listMain.get(position).getThongin());
        }
        if (listMain.get(position).getSize().length()>0) {
            viewHolder.tv_Size.setVisibility(View.VISIBLE);
            viewHolder.tvSize.setVisibility(View.VISIBLE);
            viewHolder.tvSize.setText(listMain.get(position).getSize());
        }

        viewHolder.tvSLSP.setText(listMain.get(position).getSoluong()+"");
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        long gia= Long.parseLong(listMain.get(position).getGiaban());
        viewHolder.tvGia.setText(decimalFormat.format(gia));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClick.iOnClick(listMain.get(position),position);
            }
        });

        viewHolder.ckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    database.updateStatus(listMain.get(position).getId(),"1");
                    iOnClick.iOnClick(listMain.get(position),position);
                }else {
                   database.updateStatus(listMain.get(position).getId(),"0");
                }

            }
        });


    }


    @Override
    public int getItemCount() {
        return listMain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSP, tvTTSP, tvSLSP, tvSize, tv_Size, tvGia;
        CheckBox ckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSP = itemView.findViewById(R.id.tvtensp);
            tvTTSP = itemView.findViewById(R.id.tv_TTSanPham);
            tvSLSP = itemView.findViewById(R.id.tvSLSP);
            tvGia = itemView.findViewById(R.id.tvGiaSP);
            tvSize = itemView.findViewById(R.id.tvSizeSP);
            tv_Size = itemView.findViewById(R.id.tv_SizeSP);
            ckbox=itemView.findViewById(R.id.ckbox);
        }
    }

    public interface  IOnClick{
        void iOnClick(SanPham sanPham,int position);
    }


}
