package datnx.doan.timdothatlac;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Tên csdl
    private static final String DATABASE_NAME = "LostItems.db";

    //Phiên bản csdl
    private static final int DATABASE_VERSION = 1;

    //Tạo bảng và cột
    public static final String TABLE_NAME = "Items";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Tạo bảng
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_IMAGE_URL + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT, " +
                    COLUMN_LATITUDE + " REAL, " +
                    COLUMN_LONGITUDE + " REAL, " +
                    COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tạo bảng
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xóa bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Phương thức trả về danh sách đồ vật trong csdl SQlite
    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>(); // biến lưu trữ danh sách các đồ vật
        SQLiteDatabase db = this.getReadableDatabase(); //biến đọc dữ liệu

        //Thực hiện truy vấn lấy tất cả dữ liệu từ bản ghi
        Cursor cursor = db.query(TABLE_NAME,null,null,null,null,null,null);

        //Kiểm tra xem có cursor nào khác null không
        if(cursor != null) {
            //Duyệt tất cả bản ghi trong cursor
            while (cursor.moveToNext()) {
                //Lấy giá trị của các cột từ cursor
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
                @SuppressLint("Range") double latitude = cursor.getDouble((int) cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)));
                @SuppressLint("Range") double longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE));
                @SuppressLint("Range") String timestamp = cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP));
                //Tạo đối tương Item để thêm vào danh sách
                Item item = new Item(id,name,description,imageUrl,address,latitude,longitude,timestamp);
                itemList.add(item);
            }
            cursor.close();
        }
        db.close();
        return itemList;
    }
}
