package vn.com.it.truongpham.demoapp.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vn.com.it.truongpham.demoapp.model.LoaiSP;
import vn.com.it.truongpham.demoapp.model.SanPham;

public class Database extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_TYPE_PRODUCT = "tb_loaisp";
    private static final String TABLE_PRODUCT = "tb_product";

    private static final String KEY_ID = "id";

    private static final String KEY_NAME = "name";

    private static final String KEY_INFO = "info";

    private static final String KEY_NUMBER = "number";

    private static final String KEY_TYPE_ID = "id_loai_sp";

    private static final String KEY_PRICE_IN = "price_in";

    private static final String KEY_PRICE_OUT = "price_out";


    private static final String CREATE_TABLE_TYPE_PRODUCT = "CREATE TABLE "
            + TABLE_TYPE_PRODUCT + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT );";

    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE "

            + TABLE_PRODUCT + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + KEY_NAME + " TEXT , " + KEY_INFO + " TEXT , "
            + KEY_NUMBER + " INTEGER ," + KEY_PRICE_IN + " TEXT ,"
            + KEY_PRICE_OUT + " TEXT," + KEY_TYPE_ID + " INTEGER );";


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
        values.put(KEY_NUMBER, sanPham.getSoluong());
        values.put(KEY_PRICE_IN, sanPham.getGianhap());
        values.put(KEY_PRICE_OUT, sanPham.getGiaban());
        db.insert(TABLE_PRODUCT,null,values);
        db.close();
    }

    public List<SanPham> getListSanPham(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<SanPham> list=new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT *  FROM " + TABLE_PRODUCT, null);
        while (cursor.moveToNext()){
            SanPham sanPham=new SanPham();
            sanPham.setId(cursor.getInt(0));
            sanPham.setName(cursor.getString(1));
            sanPham.setThongin(cursor.getString(2));
            sanPham.setSoluong(cursor.getInt(3));
            sanPham.setGianhap(Double.parseDouble(cursor.getString(4)));
            sanPham.setGiaban(Double.parseDouble(cursor.getString(5)));
            list.add(sanPham);
        }
        return list;

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
        Cursor cursor = db.rawQuery("SELECT *  FROM " + TABLE_TYPE_PRODUCT, null);
        while (cursor.moveToNext()) {
            LoaiSP loaiSP = new LoaiSP();
            loaiSP.setId(cursor.getInt(0));
            loaiSP.setName(cursor.getString(1));
            list.add(loaiSP);
        }
        return list;
    }


}