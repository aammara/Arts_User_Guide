package com.tmm.android.chuck;

import tn.arts.userguide.DashBoard;
import tn.arts.userguide.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.tmm.android.chuck.quiz.GamePlay;
import com.tmm.android.chuck.util.Utility;

public class AnswersActivity extends Activity implements OnClickListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answers);
		
		
		GamePlay currentGame = ((ChuckApplication)getApplication()).getCurrentGame();
		
		TextView results = (TextView)findViewById(R.id.answers);
		String answers = Utility.getAnswers(currentGame.getQuestions());
		System.out.println(answers); 
		results.setText(answers);
		
		//handle button actions
		Button finishBtn = (Button) findViewById(R.id.finishBtn);
		finishBtn.setOnClickListener(this);
		
	}
	

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
		case KeyEvent.KEYCODE_BACK :
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}


	public void onClick(View v) {
		switch (v.getId()){
		case R.id.finishBtn :
			finish();
		}
	}

}
