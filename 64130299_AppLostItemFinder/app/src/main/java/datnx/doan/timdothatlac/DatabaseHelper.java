package datnx.doan.timdothatlac;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        List<Item> itemList = new ArrayList<>(); // Tạo danh sách lưu các đồ vật
        SQLiteDatabase db = this.getReadableDatabase(); // Lấy cơ sở dữ liệu ở chế độ đọc

        // Thực hiện truy vấn để lấy toàn bộ dữ liệu từ bảng Items
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        // Kiểm tra nếu con trỏ không null
        if (cursor != null) {
            // Duyệt qua từng hàng trong bảng
            while (cursor.moveToNext()) {
                try {
                    // Lấy giá trị của từng cột, kiểm tra null trước khi đọc
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)); // Lấy ID
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)); // Lấy tên
                    @SuppressLint("Range") String description = cursor.isNull(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                            ? "" : cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)); // Mô tả
                    @SuppressLint("Range") String imageUrl = cursor.isNull(cursor.getColumnIndex(COLUMN_IMAGE_URL))
                            ? "" : cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL)); // URL ảnh
                    @SuppressLint("Range") String address = cursor.isNull(cursor.getColumnIndex(COLUMN_ADDRESS))
                            ? "" : cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)); // Địa chỉ
                    @SuppressLint("Range") double latitude = cursor.isNull(cursor.getColumnIndex(COLUMN_LATITUDE))
                            ? 0.0 : cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)); // Vĩ độ
                    @SuppressLint("Range") double longitude = cursor.isNull(cursor.getColumnIndex(COLUMN_LONGITUDE))
                            ? 0.0 : cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)); // Kinh độ
                    @SuppressLint("Range") String timestamp = cursor.isNull(cursor.getColumnIndex(COLUMN_TIMESTAMP))
                            ? "" : cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)); // Thời gian

                    // Tạo đối tượng Item từ dữ liệu đã đọc
                    Item item = new Item(id, name, description, imageUrl, address, latitude, longitude, timestamp);

                    // Thêm item vào danh sách
                    itemList.add(item);
                } catch (Exception e) {
                    // Ghi log nếu có lỗi khi đọc dữ liệu từ con trỏ
                    Log.e("DatabaseError", "Error reading cursor: " + e.getMessage());
                }
            }
            cursor.close(); // Đóng con trỏ để tránh rò rỉ bộ nhớ
        }
        db.close(); // Đóng cơ sở dữ liệu sau khi sử dụng
        return itemList; // Trả về danh sách các đồ vật
    }

    //Tìm kiếm tên đồ vật dựa theo tên
    public List<Item> searchItemsByName(String query) {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Tìm kiếm gần đúng theo tên
        String sql = "SELECT * FROM items WHERE LOWER(name) LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%" + query.toLowerCase() + "%"});

        if (cursor.moveToFirst()) {
            do {
                // Tạo đối tượng Item từ dữ liệu trong bảng
                Item item = new Item(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getString(cursor.getColumnIndexOrThrow("image_url")),
                        cursor.getString(cursor.getColumnIndexOrThrow("address")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("latitude")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("longitude")),
                        cursor.getString(cursor.getColumnIndexOrThrow("timestamp"))
                );
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return items;
    }
}
