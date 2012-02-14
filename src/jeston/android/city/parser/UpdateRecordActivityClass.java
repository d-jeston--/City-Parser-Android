package jeston.android.city.parser;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateRecordActivityClass extends Activity
{
	boolean FLAG_NEW_RECORD = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_dialog);
		
		Bundle extras = getIntent().getExtras();
		
		if (extras.containsKey("new_record"))
		{
			setTitle("Введите новые данные");
			FLAG_NEW_RECORD = true;
		}
		
		else
		{
			EditText name = (EditText) findViewById(R.id.city_dialog_value);
			EditText state = (EditText) findViewById(R.id.state_dialog_value);
			EditText url = (EditText) findViewById(R.id.url_dialog_value);
			EditText lattitude = (EditText) findViewById(R.id.lattitude_dialog_value);
			EditText longitude = (EditText) findViewById(R.id.longitude_dialog_value);
			
			name.setText(extras.getString("name"));
			state.setText(extras.getString("state"));
			url.setText(extras.getString("url"));
			
			lattitude.setText(String.valueOf(extras.getFloat("lattitude")));
			longitude.setText(String.valueOf(extras.getFloat("longitude")));
		};
			
		
		Button buttonUpdate = (Button) findViewById(R.id.button_update_record);
		Button buttonCancel = (Button) findViewById(R.id.button_cancel);
		
		buttonUpdate.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) 
			{
				
				
				EditText name = (EditText) findViewById(R.id.city_dialog_value);
				EditText state = (EditText) findViewById(R.id.state_dialog_value);
				EditText url = (EditText) findViewById(R.id.url_dialog_value);
				EditText lattitude = (EditText) findViewById(R.id.lattitude_dialog_value);
				EditText longitude = (EditText) findViewById(R.id.longitude_dialog_value);
				
				if (name.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), R.string.no_city_text, Toast.LENGTH_SHORT).show();
					return;
				};
				
				if (state.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), R.string.no_state_text, Toast.LENGTH_SHORT).show();
					return;
				};
				
				if (url.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), R.string.no_url_text, Toast.LENGTH_SHORT).show();
					return;
				};
				
				if (lattitude.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), R.string.no_lattitude_text, Toast.LENGTH_SHORT).show();
					return;
				};
				
				if (longitude.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), R.string.no_longitude_text, Toast.LENGTH_SHORT).show();
					return;
				};
				
				Intent intent = new Intent();
				intent.putExtra("name", name.getText().toString());
				intent.putExtra("state", state.getText().toString());
				intent.putExtra("url", url.getText().toString());
				intent.putExtra("lattitude", lattitude.getText().toString());
				intent.putExtra("longitude", longitude.getText().toString());
				
				if (FLAG_NEW_RECORD)
				{
					setResult(100, intent);
					FLAG_NEW_RECORD = false;
					finish();
				}
				else
				{
					setResult(200, intent);
					finish();
				};
				
			}
		                                                        });
		
		
		   buttonCancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				finish();
			                            }
		});
		
	}
}
	
	

