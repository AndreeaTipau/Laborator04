package lab04.eim.systems.cs.pub.ro;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
//import ro.pub.cs.systems.eim.lab03.phonedialer.R;
import android.widget.Toast;

public class ContactsManagerActivity extends Activity {

	
	private class BtnListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			Button b = (Button)v;
			String buttonName =  b.getText().toString();
			
			if(buttonName.equalsIgnoreCase("save")){
				
				String name, phone, email, address,jobTitle,company,website,im ;
				
				final int text[] = {R.id.editText1, R.id.editText2, R.id.editText3, 
						R.id.editText4, R.id.editText5, R.id.editText6,
						R.id.editText7,R.id.editText8};
				
				EditText nameText = (EditText)findViewById(text[0]);
				name = nameText.getText().toString();
				
				EditText phoneText = (EditText)findViewById(text[1]);
				phone = phoneText.getText().toString();
				
				EditText emailText = (EditText)findViewById(text[2]);
				email = emailText.getText().toString();
				
				EditText addressText = (EditText)findViewById(text[3]);
				address = addressText.getText().toString();
				
				EditText jobTitleText = (EditText)findViewById(text[4]);
				jobTitle = jobTitleText.getText().toString();
				
				EditText companyText = (EditText)findViewById(text[5]);
				company = companyText.getText().toString();
				
				EditText websiteText = (EditText)findViewById(text[6]);
				website = websiteText.getText().toString();
				
				EditText imText = (EditText)findViewById(text[7]);
				im = imText.getText().toString();
				
				
				
				Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
				intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
				if (name != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
				}
				if (phone != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
				}
				if (email != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
				}
				if (address != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
				}
				if (jobTitle != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
				}
				if (company != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
				}
				ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
				if (website != null) {
				  ContentValues websiteRow = new ContentValues();
				  websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
				  websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
				  contactData.add(websiteRow);
				}
				if (im != null) {
				  ContentValues imRow = new ContentValues();
				  imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
				  imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
				  contactData.add(imRow);
				}
				intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
				//startActivity(intent);
				
				// se duce la aplicatia nativa cu codul 14
				startActivityForResult(intent, 14);
				
			}
			else if(buttonName.equalsIgnoreCase("cancel")){
				//pentru phonediler
				setResult(Activity.RESULT_CANCELED, new Intent());
				finish();
			}
			else if(buttonName.contains("info")){
				
				int linearLayout = R.id.linear;
				final LinearLayout layout = (LinearLayout)findViewById(linearLayout);
				if(layout.getVisibility() == View.VISIBLE ){
					layout.setVisibility(View.INVISIBLE);
					b.setText("Show additional info");		
				}
				else {
					layout.setVisibility(View.VISIBLE);
					b.setText("Hide additional info");							
				}
				
			}
			 
			
			
			
			//
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		switch(requestCode) {
		  case 14:
			// pentru phone_dialer
		    setResult(Activity.RESULT_OK, new Intent());
		    finish();
		    break;
		  }
		}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts_manager);
		
	
		final int buttonSave = R.id.button2;
		final int buttonCancel = R.id.button3;
		final int buttonAdditionalInfo = R.id.button1;
		
		BtnListener btnListener = new BtnListener();
		
		 final Button btnSave = (Button)findViewById(buttonSave);
		 btnSave.setOnClickListener(btnListener);
		 
		 final Button btnCancel = (Button)findViewById(buttonCancel);
		 btnCancel.setOnClickListener(btnListener);
		 
		 final Button btnAdditionalInfo = (Button)findViewById(buttonAdditionalInfo);
		 btnAdditionalInfo.setOnClickListener(btnListener);
		
		 // intent from parent
		
		 Intent intentFromParent = getIntent();
		  if( intentFromParent != null ){
			  
			  
			  Bundle data = intentFromParent.getExtras();
			  if( data != null ){
				  
				  //iau numarul de telefon
				  String phoneNumber = data.getString("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
				  final EditText number = (EditText)findViewById(R.id.editText2);
				  
				  if (phoneNumber != null) {
					//il setez
					  number.setText(phoneNumber);
				  		} else {
				  			// anunt eroarea
				  			Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
				  			}
			  }
		
		  

		  
		  }
		  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts_manager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
