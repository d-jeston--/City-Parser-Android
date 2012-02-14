package jeston.android.city.parser;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DatabaseWorkerClass 
{
	
  private SQLiteDatabase database;
  private MySQLiteHelper sqLiteHelper;
  private String[] columns = {"id","name","state","url","lattitude","longitude"};
  
  public DatabaseWorkerClass(Context context)
  {
	sqLiteHelper = new MySQLiteHelper(context);  
  };
  
  public void open() throws SQLException
  {
	database = sqLiteHelper.getWritableDatabase();  
  };
  
  public void close()
  {
	sqLiteHelper.close();  
  };
  
  public long insertRecord(String name, String state, String url, float lattitude, float longitude)
  {
	ContentValues values = new ContentValues();
	values.put("name", name);
	values.put("state", state);
	values.put("url", url);
	values.put("lattitude", lattitude);
	values.put("longitude", longitude);
	
	long insertIdRecord = database.insert(MySQLiteHelper.TABLE_NAME, null, values);
	
	return insertIdRecord;
	
  };
  
  public int deleteRecord(int idDeleteRecord)
  {
	 int deletedId = database.delete(MySQLiteHelper.TABLE_NAME, "id = "+idDeleteRecord, null); 
	 return deletedId;  
  };
  
  public int updateRecord(int idUpdateRecord, String name, String state, String url, float lattitude, float longitude)
  {
	  ContentValues values = new ContentValues();
	  values.put("id", idUpdateRecord);
	  values.put("name", name);
	  values.put("state", state);
	  values.put("url", url);
	  values.put("lattitude", lattitude);
	  values.put("longitude", longitude);
	  
	int updateId = database.update(MySQLiteHelper.TABLE_NAME, values, "id = "+idUpdateRecord, null);
	return updateId;
  };
  
  
  private DataModelClass cursorToSearchResults(Cursor cursor)
  {
	DataModelClass dataModel = new DataModelClass();
	dataModel.setId(cursor.getInt(0));
	dataModel.setName(cursor.getString(1));
	dataModel.setState(cursor.getString(2));
	dataModel.setURL(cursor.getString(3));
	dataModel.setLattitude(cursor.getFloat(4));
	dataModel.setLattitude(cursor.getFloat(5));
	
	return dataModel;
  };
  
  public List<DataModelClass> selectAllDatabase()
  {
	 List<DataModelClass> listDBdata = new ArrayList<DataModelClass>();  
	 Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, columns, null, null, null, null, null);
	 cursor.moveToFirst();
	 while (!cursor.isAfterLast())
	 {
		DataModelClass dataSearch = cursorToSearchResults(cursor);
		listDBdata.add(dataSearch);
		cursor.moveToNext();
	 };
	 cursor.close();
	 return listDBdata;
  };
  
}
