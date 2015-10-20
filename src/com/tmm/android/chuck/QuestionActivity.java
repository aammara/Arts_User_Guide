package com.tmm.android.chuck;

import java.util.ArrayList;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tmm.android.chuck.quiz.GamePlay;
import com.tmm.android.chuck.quiz.Question;
import com.tmm.android.chuck.util.Utility;

import tn.arts.userguide.DashBoard;
import tn.arts.userguide.R;

public class QuestionActivity extends Activity implements OnClickListener{

	private Question currentQ;
	private GamePlay currentGame;
	public boolean goToNext = false;
	int i=0;
	Button c1 ;
	Button c2 ;
	Button c3 ;
	ProgressBar mProgressBar;
	CountDownTimer mCountDownTimer;
	Button nextBtn;
	ArrayList<Button> buttons = new ArrayList<Button>();
	static boolean noTimerOverFlow = true;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.question);
        
		mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
		mProgressBar.setProgress(i);
		mProgressBar.setMax(145);
		mCountDownTimer=new CountDownTimer(15000,100) {

			@Override
			public void onTick(long millisUntilFinished) {
				//Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
				i++;
				mProgressBar.setProgress(i);
			}

			@Override
			public void onFinish() {
                //Do what you want 
				i++;
				mProgressBar.setProgress(i);
				if (currentGame.isGameOver() ){
					if (noTimerOverFlow){
						noTimerOverFlow = false ; 
						Intent i = new Intent(getApplicationContext(), EndgameActivity.class);
						startActivity(i);
						finish();
						return ; }
					else return ; 
				}

				for (Button button : buttons)
				{
					simpleSwitch(button);
				}
				goToNext = true;



			}
		};
		mCountDownTimer.start();
		currentGame = ((ChuckApplication)getApplication()).getCurrentGame();
		currentQ = currentGame.getNextQuestion();

		c1 = (Button)findViewById(R.id.answer1);
		c2 = (Button)findViewById(R.id.answer2);
		c3 = (Button)findViewById(R.id.answer3);
		buttons.add(c1);
		buttons.add(c2);
		buttons.add(c3);

		c1.setOnClickListener(this);
		c2.setOnClickListener(this);
		c3.setOnClickListener(this);
		setQuestions();

	}


	private void setQuestions() {
		//set the question text from current question
		String question = Utility.capitalise(currentQ.getQuestion()) + "?";
		TextView qText = (TextView) findViewById(R.id.question);
		qText.setText(question);

		//set the available options
		List<String> answers = currentQ.getQuestionOptions();
		TextView option1 = (TextView) findViewById(R.id.answer1);
		option1.setText(Utility.capitalise(answers.get(0)));

		TextView option2 = (TextView) findViewById(R.id.answer2);
		option2.setText(Utility.capitalise(answers.get(1)));

		TextView option3 = (TextView) findViewById(R.id.answer3);
		option3.setText(Utility.capitalise(answers.get(2)));

	}


	public void onClick(View arg0) {


		if (currentGame.isGameOver() && noTimerOverFlow){
			noTimerOverFlow = false ; 
			mCountDownTimer.cancel();
			Intent i = new Intent(this, EndgameActivity.class);
			startActivity(i);
			finish();
			return; 
		}

		/**
		 * validate a checkbox has been selected
		 */
		boolean state =  (checkAnswer(arg0)); 
		if (!state)  mCountDownTimer.cancel();



		switchColor (c1,arg0, state);
		switchColor (c2,arg0, state);
		switchColor (c3,arg0, state);
		switchUnclicked(c1, buttons);
		switchUnclicked(c2, buttons);
		switchUnclicked(c3, buttons);



		/**
		 * check if end of game
		 */

	}


	private Button fetchClicked (ArrayList<Button> buttons){
		for (Button button : buttons){
			if (button.getTag()!=null) return button;
		}
		return null ;
	}

	private void switchUnclicked (Button b, ArrayList<Button> buttons){
		int match = R.drawable.gqs_btn_quizz_ok; 
		int mismatch = R.drawable.gqs_btn_quizz_ko; 
		Button clicked = fetchClicked(buttons);
		if (b!=clicked){
			if (checkAnswerNoIncrement(b))
				b.setBackgroundResource(match);
			else b.setBackgroundResource(mismatch);
		}

	}

	private void switchColor(Button b, View arg0, boolean state) {

		int match;
		if (state) {
			match = R.drawable.gqs_btn_quizz_ok; 
		}
		else{
			match =  R.drawable.gqs_btn_quizz_ko ;
		}
		if (b==arg0){
			b.setBackgroundResource(match);
			b.setTag(state);
		}
		b.invalidate();
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


	private boolean checkAnswer(View view) {
		String answer = getSelectedAnswer(view);
		goToNext = true;
		mCountDownTimer.cancel();
		if (currentQ.getAnswer().equalsIgnoreCase(answer))
		{
			currentGame.incrementRightAnswers();
			return true ; 
		}
		currentGame.incrementWrongAnswers();
		return false ; 

	}


	private String getSelectedAnswer(View view) {

		Button button = (Button) view;
		return (button!=null) ?  button.getText().toString() : null;
	}


	private boolean checkAnswerNoIncrement(View view){
		String answer = getSelectedAnswer(view);

		return (currentQ.getAnswer().equalsIgnoreCase(answer));
	}


	private void simpleSwitch (Button b){
		if (checkAnswerNoIncrement(b)){
			b.setBackgroundResource(R.drawable.gqs_btn_quizz_ok);
		}
		else b.setBackgroundResource(R.drawable.gqs_btn_quizz_ko);
	}
	
}
