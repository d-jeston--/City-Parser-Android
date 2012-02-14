package jeston.android.city.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CityParserActivity extends ListActivity {
    
	public static ProgressDialog progressDialog;
    
    private static final int ID_DELETE_RECORD = 505;
    private static final int ID_UPDATE_RECORD = 506;
    private static final int ID_ADD_RECORD = 507;
    
    private static final int ID_CLOSE_APP = 500;
    
    private int positionSelected;
    CityInfo itemSelected;
    
    private boolean DATA_WAS_SAVED = false;
    
    CityArrayDataAdapter cityArrayDataAdapter;
    
    private Menu myMenu;
    
	public static class CityInfo
	{
		int idCity;
		String nameCity;
		float lattitude;
		float longitude;
		String URLCity;
		String stateCity;
	}
	
	CityInfo selectedCityInfo;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {// start of Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // содержимое списка без элементов
        getListView().setEmptyView(findViewById(R.id.emptyLayout));
        
        getListView().setOnItemLongClickListener(showUpdateRecordActivity);
        
   
    } // end of Create

    
    // get data for parsing****************************************************
    OnClickListener getDataForParsing = new OnClickListener() {
		
		public void onClick(View v) {
			if (getListAdapter() != null) setListAdapter(null);
			progressDialog = ProgressDialog.show(CityParserActivity.this, "Подождите...","Подождите, выполняется загрузка и анализ данных", true, true);
			new CityAsyncParserClass().execute("");
			
		                            }
	};
	// get data for parsing(end)************************************************
	
	
	// show activity to update the selected record
	OnItemLongClickListener showUpdateRecordActivity = new OnItemLongClickListener() 
	{

		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) 
		{
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), UpdateRecordActivityClass.class);
			
			CityInfo cityInfo = (CityInfo) getListAdapter().getItem(arg2);
			itemSelected = cityInfo;
			
			intent.putExtra("name", cityInfo.nameCity);
			intent.putExtra("state", cityInfo.stateCity);
			intent.putExtra("url", cityInfo.URLCity);
			intent.putExtra("lattitude", cityInfo.lattitude);
			intent.putExtra("longitude", cityInfo.longitude);
			//startActivity(intent);
			startActivityForResult(intent, 250);
			positionSelected = (int) arg3;
			return true;
		}
	};
	
	// click on item of LstView data *******************************************
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		CityInfo cityInfo = (CityInfo) getListAdapter().getItem(position);
		Intent intent = new Intent();
		intent.setClass(this, MapCityActivity.class);
		intent.putExtra("name", cityInfo.nameCity);
		intent.putExtra("state", cityInfo.stateCity);
		intent.putExtra("url", cityInfo.URLCity);
		intent.putExtra("lattitude", cityInfo.lattitude);
		intent.putExtra("longitude", cityInfo.longitude);
		startActivity(intent);
	};
	// click on item of LstView data (end)**************************************
	
	
	
	// menu inflating***********************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	MenuInflater menuInflater = getMenuInflater();
    	menuInflater.inflate(R.menu.menu, menu);
    	this.myMenu = menu;
    	return true;
    }
 // menu inflating (end) *******************************************************
   
    
 @Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) 
 {
	super.onActivityResult(requestCode, resultCode, data);
	
	if (resultCode == 200)
	{
		// обновить 
		
	
		Bundle gotData = data.getExtras();
		String name = gotData.getString("name");
		String state = gotData.getString("state");
		String url = gotData.getString("url");
		float lattitude = Float.parseFloat(gotData.getString("lattitude"));
		float longitude = Float.parseFloat(gotData.getString("longitude"));
		
		
		
		CityInfo cityInfo = new CityInfo();
		cityInfo.idCity = (int) getListView().getSelectedItemId();
		cityInfo.nameCity = name;
		cityInfo.stateCity = state;
		cityInfo.URLCity = url;
		cityInfo.lattitude = lattitude;
		cityInfo.longitude = longitude;
		
		
		cityArrayDataAdapter.remove(itemSelected);
	    cityArrayDataAdapter.insert(cityInfo, positionSelected);
	    
	    
	    cityArrayDataAdapter.notifyDataSetChanged();
		
		return;
	};	
	if (resultCode == 100)
	{
		Bundle gotData = data.getExtras();
		String name = gotData.getString("name");
		String state = gotData.getString("state");
		String url = gotData.getString("url");
		float lattitude = Float.parseFloat(gotData.getString("lattitude"));
		float longitude = Float.parseFloat(gotData.getString("longitude"));
		
		CityInfo cityInfo = new CityInfo();
		cityInfo.idCity = positionSelected;
		cityInfo.nameCity = name;
		cityInfo.stateCity = state;
		cityInfo.URLCity = url;
		cityInfo.lattitude = lattitude;
		cityInfo.longitude = longitude;
		
		cityArrayDataAdapter.add(cityInfo);
	    cityArrayDataAdapter.notifyDataSetChanged();
	    
		return;
	};
	if (resultCode == RESULT_CANCELED)
	{
		return;
	};
	
		
 }
    
    
 // menu items handling*********************************************************  
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) 
    {
    	long flag = 0;
    	switch (item.getItemId())
    	{
    	// get data from internet
    	case R.id.menu_item_new_search:
    	{
    		//startActivity(new Intent(this, MapCityActivity.class));
    		if (getListAdapter() != null) setListAdapter(null);
    		
    		progressDialog = ProgressDialog.show(CityParserActivity.this, "Подождите...","Подождите, выполняется загрузка и анализ данных", true, true);
			new CityAsyncParserClass().execute("");
			break;
			
    	}
    	// save listview to database
    	case R.id.menu_item_save_db:
    	{
    		
    		for (int i=0; i< getListAdapter().getCount(); i++)
    		{
    			DatabaseWorkerClass database = new DatabaseWorkerClass(this);
    			database.open();
    			CityInfo itemCityInfo = (CityInfo) getListAdapter().getItem(i);
    			flag = database.insertRecord(itemCityInfo.nameCity, itemCityInfo.stateCity, itemCityInfo.URLCity, itemCityInfo.lattitude, itemCityInfo.longitude);
    			database.close();
    			
    		};
    		Toast.makeText(this, R.string.data_was_saved_string, Toast.LENGTH_SHORT).show();
    		DATA_WAS_SAVED = true;
    		break;
    	}
    	// delete record in database
    	case R.id.menu_item_delete_record:
    	{
    		showDialog(ID_DELETE_RECORD);
    		break;
    	}
    	
    	//insert new record in database
    	case R.id.menu_item_add_record:
    	{
    		Intent intent = new Intent();
			intent.setClass(getApplicationContext(), UpdateRecordActivityClass.class);
			intent.putExtra("new_record", "new_record");
			//startActivity(intent);
			startActivityForResult(intent, 350);
    	   break;
    	}
    	// open saved data
    	case R.id.menu_item_open_db:
    	{
    		
    		
    		DatabaseWorkerClass database  = new DatabaseWorkerClass(this);
    		database.open();
    		List<DataModelClass> listFromDB = database.selectAllDatabase();
    		database.close();
    		
    		if (listFromDB.size() == 0)
    		{
    			Toast.makeText(this, R.string.no_data_was_saved, Toast.LENGTH_SHORT).show();
    			return false;
    		};
    		
    		List<CityInfo> listForAdapter = new ArrayList<CityParserActivity.CityInfo>();
    		
    		for (int i=0; i<listFromDB.size(); i++)
    		{
    			CityInfo cityInfo = new CityInfo();
    			cityInfo.idCity = listFromDB.get(i).getId();
    			cityInfo.nameCity = listFromDB.get(i).getName();
    			cityInfo.stateCity = listFromDB.get(i).getState();
    			cityInfo.URLCity = listFromDB.get(i).getURL();
    			cityInfo.lattitude = listFromDB.get(i).getLattitude();
    			cityInfo.longitude = listFromDB.get(i).getLongitude();
    			
    			listForAdapter.add(cityInfo);
    		};
    		setListAdapter(null);
    		//CityArrayDataAdapter cityArrayDataAdapter = new CityArrayDataAdapter(this, listForAdapter);
    		cityArrayDataAdapter = new CityArrayDataAdapter(this, listForAdapter);
    		setListAdapter(cityArrayDataAdapter);
    		Toast.makeText(this, "Были открыты сохраненные ранее данные", Toast.LENGTH_SHORT).show();
    		
    		// включаем видимость группы элементов меню для работы с БД  
    		myMenu.setGroupVisible(R.id.group_menu_operation, true);
    		break;
    	}
    	case R.id.mebu_item_close_app:
    	{
    		showDialog(ID_CLOSE_APP);
    		break;
    	}
    	
    	}
    	return true;
    }
 // menu items handling(end)****************************************************
 
    
 // dialog variants handling****************************************************
    @Override
    protected Dialog onCreateDialog(int id) 
    {
    	
    	AlertDialog.Builder builder = null;
    	AlertDialog alertDialog;
    	switch (id)
    	{
    	case ID_DELETE_RECORD:
    	                    {   // dialog delete record
    	                    	builder = new AlertDialog.Builder(this);
    	                    	builder.setCancelable(false);
    	                    	builder.setMessage(R.string.dialog_delete_item);
    	                    	builder.setPositiveButton(R.string.ok_string, new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface arg0, int arg1) //positive button 
									{
									  CityArrayDataAdapter cityArrayDataAdapter = (CityArrayDataAdapter) getListView().getAdapter();
									  if (getListView().getSelectedItem() != null)
									  {
										  cityArrayDataAdapter.remove((CityInfo) getListView().getSelectedItem());	 
									  }
									  else
										  Toast.makeText(getApplicationContext(), "Выберите сначала элемент, чтобы удалить его", Toast.LENGTH_SHORT).show();
									};

								                                                       }); // positive button (end)
    	                    	
    	                    	builder.setNegativeButton(R.string.cancel_string, null);
    	                        break;	
    	                    }  // dialog delete record - end
    	                    
    	case ID_CLOSE_APP:
    	                 { // dialog close app
    		                 builder = new AlertDialog.Builder(this);
    		                 builder.setCancelable(false);
    		                 builder.setMessage(R.string.close_app_string);
    		                 builder.setPositiveButton(R.string.ok_string, new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int which) {
									CityParserActivity.this.finish();
								                                                       }
								
							                                                                                      });
    		                 builder.setNegativeButton(R.string.cancel_string, null);
    	                	 break;
    	                 } // dialog close app - end
    	                
    	                 
    	}
    	return builder.create();
    }
 // dialog variants handling(end)***********************************************
    
    
    
// parser--------------------------------------------------------------
    public class CityAsyncParserClass extends AsyncTask<String, Void, List<CityInfo>>
    {
    	// url, где находится xml-файл для парсинга
    	private static final String URL_REQUEST = "http://api.sba.gov/geodata/all_data_for_county_of/orange%20county/ca.xml";
    	// тег, обрамляющий элементы xml для парсинга
    	private static final String MAIN_TAG_PARSING = "site";
    	// список для хранения объектов
    	private List<CityInfo> listCitiesData;

    	@Override
    	protected List<CityInfo> doInBackground(String... arg0) 
    	{
    		
    		listCitiesData = new ArrayList<CityParserActivity.CityInfo>();
    		
    	    try {
    			Document doc = Jsoup.connect(URL_REQUEST).get();
    			Elements listCities = doc.getElementsByTag(MAIN_TAG_PARSING);
    			
    			for (Element city : listCities)
    			{
    				CityInfo cityInfo = new CityInfo();
    				cityInfo.idCity = 1;
    				cityInfo.nameCity = city.child(9).text();
    				cityInfo.stateCity = city.child(13).text();
    				cityInfo.lattitude = Float.parseFloat(city.child(10).text());
    				cityInfo.longitude = Float.parseFloat(city.child(11).text());
    				cityInfo.URLCity = city.child(8).text();
    				
    				listCitiesData.add(cityInfo);
    			};
    			
    			
    			
    		} catch (IOException e) {
    			
    			e.printStackTrace();
    		}
    		return listCitiesData;
    	}
    	
 // ---------Post Execute -----------------------------------------------------------------------------   	
    	@Override
    	protected void onPostExecute(List<CityInfo> result) 
    	{
    		super.onPostExecute(result);
    		progressDialog.dismiss();
    		
    		// если список с объектами, полученными из интернета, не заполнен 
    		if (listCitiesData.size() == 0)
    		{
    			//...то, что-то пошло не так
    			Toast.makeText(getApplicationContext(), "Похоже, что при загрузке данных произошла ошибка. Повторите запрос еощё раз через некоторое время.", Toast.LENGTH_SHORT)
    			.show();
    			return;
    		};
    	
    		// включаем видимость группы элементов меню для работы с БД  
    		myMenu.setGroupVisible(R.id.group_menu_operation, true);
    		
    		// создавем наш адаптер списка
    	    cityArrayDataAdapter = new CityArrayDataAdapter(CityParserActivity.this, listCitiesData);
    	    // и устанавливаем его адаптером списка
    		setListAdapter(cityArrayDataAdapter);
    	} //end of PostExecute
// ----------Post Execute (END)--------------------------------------------------------------------------
    }
// parser(end)---------------------------------------------------------    
    
} // end of class
