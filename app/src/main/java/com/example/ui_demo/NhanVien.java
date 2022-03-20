package com.example.ui_demo;

public class NhanVien {
    private int ma;
    private String hoten;
    private String gioiTinh;
    private String donVi;
    private String image;

    public NhanVien(){

    }
    public NhanVien(int ma, String hoten, String gioiTinh, String donVi){
        this.ma=ma;
        this.hoten=hoten;
        this.gioiTinh=gioiTinh;
        this.donVi=donVi;
    }
    public NhanVien(int ma, String hoten, String gioiTinh, String donVi, String image){
        this.ma=ma;
        this.hoten=hoten;
        this.gioiTinh=gioiTinh;
        this.donVi=donVi;
        this.image=image;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "ma=" + ma +
                ", hoten='" + hoten + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", donVi='" + donVi + '\'' +
                ", image=" + image +
                '}';
    }
}
