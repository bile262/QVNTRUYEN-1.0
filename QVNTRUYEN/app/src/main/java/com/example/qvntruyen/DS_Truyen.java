package com.example.qvntruyen;

public class DS_Truyen {
    private int ID;
    private String TenTruyen;
    private String TheLoai;
    private  String Anh;
    private  String TacGia;
    private  String TomTat;

    public DS_Truyen(int ID, String tenTruyen, String theLoai, String anh, String tacGia, String tomTat) {
        this.ID = ID;
        TenTruyen = tenTruyen;
        TheLoai = theLoai;
        Anh = anh;
        TacGia = tacGia;
        TomTat=tomTat;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenTruyen() {
        return TenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        TenTruyen = tenTruyen;
    }

    public String getTheLoai() {
        return TheLoai;
    }

    public void setTheLoai(String theLoai) {
        TheLoai = theLoai;
    }

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String anh) {
        Anh = anh;
    }

    public String getTacGia() {
        return TacGia;
    }

    public void setTacGia(String tacGia) {
        TacGia = tacGia;
    }
    public String getTomTat() {
        return TomTat;
    }

    public void setTomTat(String tomTat) {
        TomTat = tomTat;
    }
}
