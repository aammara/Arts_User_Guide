package tn.arts.userguide;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class subscribe extends Activity{
	
	private EditText edt_name, edt_lastname, edt_email, edt_city;
	private Button subscribe;
	private String name, lastname, email, city;
	
	CustomHttpClient client = new CustomHttpClient();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subscribe_layout);
		edt_name = (EditText) findViewById(R.id.editText1);
		edt_lastname = (EditText) findViewById(R.id.editText2);
		edt_email = (EditText) findViewById(R.id.editText3);
		edt_city = (EditText) findViewById(R.id.editText4);
		subscribe = (Button) findViewById(R.id.subscribe_button);
		subscribe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			name = edt_name.getText().toString();
			lastname = edt_lastname.getText().toString();
			email = edt_email.getText().toString();
			city = edt_city.getText().toString();
			
		    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("name",name));
				postParameters.add(new BasicNameValuePair("lastname",lastname));
				postParameters.add(new BasicNameValuePair("email",email));
				postParameters.add(new BasicNameValuePair("city",city));
			        String response = "";		
					try { response = client.executeHttpPost("http://www.masante.tn/new_user.php", postParameters); }
					catch (Exception e) { }
					String res=response.toString();
					Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
		
			
			}
		});
		}
}
