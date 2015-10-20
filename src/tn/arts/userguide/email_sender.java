package tn.arts.userguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class email_sender extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent(Intent.ACTION_SEND);
	    intent.setType("plain/text");
	    intent.putExtra(Intent.EXTRA_EMAIL,new String[] { "achref.amar@gmail.com" });
	    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of the mail");
	    intent.putExtra(Intent.EXTRA_TEXT, "body of the mail");
	    startActivity(Intent.createChooser(intent, "Title of the chooser dialog"));
		}
}
