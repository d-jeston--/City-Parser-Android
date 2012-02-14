package jeston.android.city.parser;

import java.util.List;

import jeston.android.city.parser.CityParserActivity.CityInfo;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CityArrayDataAdapter extends ArrayAdapter<CityInfo>
{
	
	private final Activity context;
	private List<CityInfo> listCitiesInfo;
	private float lattitude;
	private float longitude;
	
	public CityArrayDataAdapter(Activity context, List<CityInfo> listCitiesInfo) 
	{
		super(context, R.layout.listview_item_layout, listCitiesInfo);
		this.context = context;
		this.listCitiesInfo = listCitiesInfo;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
	    View v = convertView;
	    LayoutInflater layoutInflater = context.getLayoutInflater();
	    v = layoutInflater.inflate(R.layout.listview_item_layout, null);
	    
	    TextView tv1 = (TextView) v.findViewById(R.id.nameCity);
	    TextView tv2 = (TextView) v.findViewById(R.id.stateCity);
	    TextView tv3 = (TextView) v.findViewById(R.id.urlCity);
	    
	    tv1.setText(listCitiesInfo.get(position).nameCity);
	    tv2.setText(listCitiesInfo.get(position).stateCity);
	    tv3.setText(listCitiesInfo.get(position).URLCity);
	    
	    lattitude = listCitiesInfo.get(position).lattitude;
	    longitude = listCitiesInfo.get(position).longitude;
	    
	    
		return v;
	}
    
}
