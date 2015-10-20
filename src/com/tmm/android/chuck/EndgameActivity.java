package com.tmm.android.chuck;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.tmm.android.chuck.quiz.Constants;
import com.tmm.android.chuck.quiz.GamePlay;
import com.tmm.android.chuck.quiz.Helper;

import tn.arts.userguide.DashBoard;
import tn.arts.userguide.R;

public class EndgameActivity extends Activity implements OnClickListener {
	String result;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.endgame);
		
		
		GamePlay currentGame = ((ChuckApplication)getApplication()).getCurrentGame();
		result = currentGame.getRight() + "/" + currentGame.getNumRounds();
		String comment = Helper.getResultComment(currentGame.getRight(), currentGame.getNumRounds(), getDifficultySettings());
		
		
		TextView results = (TextView)findViewById(R.id.endgameResult);
		results.setText(result);
		
		TextView commentMSG = (TextView)findViewById(R.id.endgameComment);
		commentMSG.setText(comment);
		
		
		//handle button actions
		Button answerBtn = (Button) findViewById(R.id.answerBtn);
		answerBtn.setOnClickListener(this);
		
	}
	
	
	private int getDifficultySettings() {
		SharedPreferences settings = getSharedPreferences(Constants.SETTINGS, 0);
		int diff = settings.getInt(Constants.DIFFICULTY, 2);
		return diff;
	}
	
	
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
		case R.id.answerBtn :
			Intent i = new Intent(this, AnswersActivity.class);
			startActivityForResult(i, Constants.PLAYBUTTON);
			break;
		}
	}
	
}
