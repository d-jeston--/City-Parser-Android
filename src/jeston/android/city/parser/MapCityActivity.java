package jeston.android.city.parser;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapCityActivity extends MapActivity
{
    MapView mapView;
    
	@Override
	protected void onCreate(Bundle icicle) 
	{
		
		super.onCreate(icicle);
		setContentView(R.layout.map);
		
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		MapController mapController = mapView.getController();
		
		Bundle extras = getIntent().getExtras();
		float x = extras.getFloat("lattitude");
		float y = extras.getFloat("longitude");
		String data = extras.getString("name")+",\n"+extras.getString("state")+",\n"+extras.getString("url");
		
		GeoPoint point = new GeoPoint((int)(x*1E6), (int)(y*1E6));
		mapController.animateTo(point);
		mapController.setZoom(16);
		
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
		MyOverlayClass itemizedoverlay = new MyOverlayClass(drawable, this);
		
		OverlayItem overlayitem = new OverlayItem(point, "Информация", data);
		
		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);
	}
	
	@Override
	protected boolean isRouteDisplayed() 
	{
	
		return false;
	}
    
}
