package vn.com.it.truongpham.mystore.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.com.it.truongpham.mystore.model.LoaiSP;
import vn.com.it.truongpham.mystore.model.SanPham;

public class Database extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 2;


    private static final String TABLE_TYPE_PRODUCT = "tb_type_product";
    private static final String TABLE_PRODUCT = "tb_product";

    private static final String KEY_ID = "id";

    private static final String KEY_NAME = "name";

    private static final String KEY_INFO = "info";

    private static final String KEY_NUMBER = "number";

    private static final String KEY_TYPE_ID = "id_loai_sp";

    private static final String KEY_PRICE_IN = "price_in";

    private static final String KEY_PRICE_OUT = "price_out";

    private static final String KEY_SIZE="size";

    private static final String KEY_DATE="date";

    private static final String KEY_STATUS="status";


    private static final String CREATE_TABLE_TYPE_PRODUCT = "CREATE TABLE "
            + TABLE_TYPE_PRODUCT + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT );";

    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE "

            + TABLE_PRODUCT + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + KEY_NAME + " TEXT , " + KEY_INFO + " TEXT , " + KEY_SIZE + "  TEXT ,"
            + KEY_NUMBER + " INTEGER ," + KEY_PRICE_IN + " TEXT ,"
            + KEY_PRICE_OUT + " TEXT," + KEY_TYPE_ID + " INTEGER , " + KEY_DATE + " TEXT ," + KEY_STATUS + " TEXT );";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TYPE_PRODUCT);
        db.execSQL(CREATE_TABLE_PRODUCT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_TYPE_PRODUCT + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_PRODUCT + "'");
        onCreate(db);
    }

    public void AddSanPham(SanPham sanPham) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, sanPham.getName());
        values.put(KEY_INFO, sanPham.getThongin());
        values.put(KEY_SIZE,sanPham.getSize());
        values.put(KEY_NUMBER, sanPham.getSoluong());
        values.put(KEY_PRICE_IN, sanPham.getGianhap());
        values.put(KEY_PRICE_OUT, sanPham.getGiaban());

        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        Date nowtime=new Date();
        String strnowtime = sdf.format(nowtime);

        values.put(KEY_DATE,strnowtime);
        values.put(KEY_TYPE_ID,sanPham.getId_loaisp());
        values.put(KEY_STATUS,"0");

        db.insert(TABLE_PRODUCT,null,values);
        db.close();
    }

    public void editSanPham(SanPham sanPham, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, sanPham.getName());
        values.put(KEY_INFO, sanPham.getThongin());
        values.put(KEY_SIZE,sanPham.getSize());
        values.put(KEY_NUMBER, sanPham.getSoluong());
        values.put(KEY_PRICE_IN, sanPham.getGianhap());
        values.put(KEY_PRICE_OUT, sanPham.getGiaban());

        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        Date nowtime=new Date();
        String strnowtime = sdf.format(nowtime);

        values.put(KEY_DATE,strnowtime);
        values.put(KEY_TYPE_ID,sanPham.getId_loaisp());

        db.update(TABLE_PRODUCT,values,KEY_ID + " = ?" ,new String[]{String.valueOf(id)});
        db.close();
    }


    public void xoaSanPham(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT,KEY_ID + " = ?" ,new String[]{String.valueOf(id)});
        db.close();
    }

    public List<SanPham> getListSanPham(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        List<SanPham> list=new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT *  FROM " + TABLE_PRODUCT +" WHERE " + KEY_TYPE_ID + " = " +id , null);
        while (cursor.moveToNext()){
            SanPham sanPham=new SanPham();
            sanPham.setId(cursor.getInt(0));
            sanPham.setName(cursor.getString(1));
            sanPham.setThongin(cursor.getString(2));
            sanPham.setSize(cursor.getString(3));
            sanPham.setSoluong(cursor.getInt(4));
            sanPham.setGianhap(cursor.getString(5));
            sanPham.setGiaban(cursor.getString(6));
            sanPham.setStatus(cursor.getString(9));
            list.add(sanPham);
        }
        return list;

    }

    public  int getID(){
        SQLiteDatabase db=this.getWritableDatabase();
        int id=0;
        Cursor cursor=db.rawQuery("SELECT * FROM " + TABLE_PRODUCT + " order by id desc limit 1 ",null);
        while (cursor.moveToNext()){
            id=cursor.getInt(0);
        }
        return id;
    }

    public void AddLoaiSanPham(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        db.insert(TABLE_TYPE_PRODUCT, null, values);
        db.close();
    }

    public List<LoaiSP> getListLoaiSP() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<LoaiSP> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT *  FROM " + TABLE_TYPE_PRODUCT , null);
        while (cursor.moveToNext()) {
            LoaiSP loaiSP = new LoaiSP();
            loaiSP.setId(cursor.getInt(0));
            loaiSP.setName(cursor.getString(1));
            list.add(loaiSP);
        }
        return list;
    }

    public String getNameLoaiSanPham(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String name="";
        Cursor cursor = db.rawQuery(" SELECT *  FROM " + TABLE_TYPE_PRODUCT + " WHERE " + KEY_ID + " = "+ id, null);
        while (cursor.moveToNext()) {
          name=cursor.getString(1);
        }
        return name;
    }

    public void update(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("update tb_product set number=number-1 where id = "+ id,null);
        cursor.moveToNext();
    }

    public void updateStatus(int id ,String status){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_STATUS,status);
        db.update(TABLE_PRODUCT,values,KEY_ID + " = ?" ,new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateStatus(){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_STATUS,"0");
        db.update(TABLE_PRODUCT,values,null ,null);
        db.close();
    }


}
