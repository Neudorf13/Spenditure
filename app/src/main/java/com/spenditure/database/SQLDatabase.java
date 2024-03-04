package com.spenditure.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Spenditure.db";
    private static final int DATABASE_VERSION = 1;
    public SQLDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables if not exists
        String createUserTable = "CREATE TABLE IF NOT EXISTS users ("
                + "userID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "firstName TEXT,"
                + "lastName TEXT,"
                + "email TEXT,"
                + "password TEXT)";

        db.execSQL(createUserTable);

        String createCategoryTable = "CREATE TABLE IF NOT EXISTS categories ("
                + "categoryID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "categoryName)";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade logic, if needed
    }
}
