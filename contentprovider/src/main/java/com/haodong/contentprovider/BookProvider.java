package com.haodong.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class BookProvider extends ContentProvider {
    private static final String TAG = "BookProvider";
    public static final String AUTHORITY = "com.haodong.contentprovider.BookProvider";

    // 自定义Uri的组成  外部通过这个找到对应的uri
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");
    // 在ContentProvider 中注册Uri对象
    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    // 初始化UriMatcher
    // 全局变量
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private Context mContext;
    private SQLiteDatabase mDb;

    static {
        // 若URI资源路径 = content://cn.scu.myprovider/user1 ，则返回注册码URI_CODE_a
        // 若URI资源路径 = content://cn.scu.myprovider/user2 ，则返回注册码URI_CODE_b
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }


    @Override
    public boolean onCreate() {
        mContext = getContext();
        MyInitService myInitService = new MyInitService();
        InitRunner initRunner = new InitRunner(myInitService, mContext, mDb);
        initRunner.start();
        return true;
    }

    public static class MyInitService {
        public synchronized static void init(Context context, SQLiteDatabase db) {
            WeakReference<Context> weakReference = new WeakReference<>(context);
            db = new DbOpenHelper(weakReference.get()).getWritableDatabase();
            db.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
            db.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
            db.execSQL("insert into book values (3,'Android');");
            db.execSQL("insert into book values(4,'ios');");
            db.execSQL("insert into book values(5,'Html5');");
            db.execSQL("insert into user values(1,'jake',1);");
            db.execSQL("insert into user values(2,'jasmine',0);");
        }
    }

    public static class InitRunner extends Thread {
        private MyInitService myInitService;
        private Context context;
        private SQLiteDatabase db;

        public InitRunner(MyInitService myInitService, Context context, SQLiteDatabase db) {
            this.myInitService = myInitService;
            this.context = context;
            this.db = db;
        }

        @Override
        public void run() {
            super.run();
            myInitService.init(context, db);
        }
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        return mDb.query(table, strings, s, strings1, null, null, s1);


    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        String table = getTableName(uri);
        // 判断是否有合格的返回值，否则抛出非法参数异常
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        mDb.insert(table, null, contentValues);
        //启用观察者，当该COntentProvider发生改变时，通知外界的使用者
        mContext.getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        int count = mDb.delete(table, s, strings);
        // 如果有数据删除，通知
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        int row=mDb.update(table,contentValues,s,strings);
        if (row>0){
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return row;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }

}
