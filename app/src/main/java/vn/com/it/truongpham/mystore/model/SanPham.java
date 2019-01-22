package vn.com.it.truongpham.mystore.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SanPham  implements Parcelable {
    private int id ,id_loaisp,soluong;
    private String name;
    private String thongin;
    private String size;
    private String gianhap,giaban;
    private String ngaynhap;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Creator<SanPham> getCREATOR() {
        return CREATOR;
    }

    private String status;


    public SanPham() {
    }

    protected SanPham(Parcel in) {
        id = in.readInt();
        id_loaisp = in.readInt();
        soluong = in.readInt();
        name = in.readString();
        thongin = in.readString();
        size = in.readString();
        gianhap = in.readString();
        giaban = in.readString();
        ngaynhap = in.readString();
    }

    public static final Creator<SanPham> CREATOR = new Creator<SanPham>() {
        @Override
        public SanPham createFromParcel(Parcel in) {
            return new SanPham(in);
        }

        @Override
        public SanPham[] newArray(int size) {
            return new SanPham[size];
        }
    };

    public String getNgaynhap() {
        return ngaynhap;
    }

    public void setNgaynhap(String ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setId_loaisp(int id_loaisp) {
        this.id_loaisp = id_loaisp;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThongin(String thongin) {
        this.thongin = thongin;
    }

    public void setGianhap(String gianhap) {
        this.gianhap = gianhap;
    }

    public void setGiaban(String giaban) {
        this.giaban = giaban;
    }

    public int getId() {
        return id;
    }

    public int getId_loaisp() {
        return id_loaisp;
    }

    public int getSoluong() {
        return soluong;
    }

    public String getName() {
        return name;
    }

    public String getThongin() {
        return thongin;
    }

    public String getGianhap() {
        return gianhap;
    }

    public String getGiaban() {
        return giaban;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(id_loaisp);
        dest.writeInt(soluong);
        dest.writeString(name);
        dest.writeString(thongin);
        dest.writeString(size);
        dest.writeString(gianhap);
        dest.writeString(giaban);
        dest.writeString(ngaynhap);
    }
}
