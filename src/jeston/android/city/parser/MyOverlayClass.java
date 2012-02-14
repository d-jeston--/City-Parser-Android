package jeston.android.city.parser;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MyOverlayClass extends ItemizedOverlay {
	
	Context mContext;

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	
	public MyOverlayClass(Drawable defaultMarker, Context context) 
	{
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}

	@Override
	protected OverlayItem createItem(int i) 
	{
		return mOverlays.get(i);
	}

	
	
	public void addOverlay(OverlayItem overlay) 
	{
	    mOverlays.add(overlay);
	    populate();
	}
	
	@Override
	public int size() 
	{
	  return mOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index) 
	{
	  OverlayItem item = mOverlays.get(index);
	  
	  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.setNeutralButton("ОК", new DialogInterface.OnClickListener() {
		
		public void onClick(DialogInterface dialog, int which) 
		{
		dialog.dismiss();
		}
	});
	  dialog.show();
	  
	  return true;
	}

}
