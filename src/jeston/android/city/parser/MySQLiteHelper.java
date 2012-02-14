package jeston.android.city.parser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper
{
   public static final String DB_NAME = "results_database.db";
   public static final String TABLE_NAME = "results_table";
   private static final int DB_VERSION = 1;
   
   public static final String SQLExpressionCreatingTable = " create table "+TABLE_NAME+
		   "(" +
		   "id integer primary key autoincrement," +    // id 
		   "name text," +                               // name place
		   "state text," +                              // state
		   "url text," +                                // url
		   "lattitude float," +                         // lattitude
		   "longitude float" +                          // longitude
		   ")";
	
   
   
   public MySQLiteHelper(Context context)
   {
	 super(context, DB_NAME, null, DB_VERSION);  
   }



@Override
public void onCreate(SQLiteDatabase arg0) 
 {
	arg0.execSQL(SQLExpressionCreatingTable);	
 }


@Override
public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) 
 {
	arg0.execSQL("DROP TABLE if exists "+TABLE_NAME);
	onCreate(arg0);
 };
   
   
       }// end of class
