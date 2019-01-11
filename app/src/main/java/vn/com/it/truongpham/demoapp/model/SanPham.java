package vn.com.it.truongpham.demoapp.model;

public class SanPham {
    private int id ,id_loaisp,soluong;
    private String name;
    private String thongin;
    private String size;
    private double gianhap,giaban;

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

    public void setGianhap(double gianhap) {
        this.gianhap = gianhap;
    }

    public void setGiaban(double giaban) {
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

    public double getGianhap() {
        return gianhap;
    }

    public double getGiaban() {
        return giaban;
    }
}
