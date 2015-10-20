
package com.tmm.android.chuck;

import com.tmm.android.chuck.quiz.GamePlay;

import android.app.Application;

public class ChuckApplication extends Application{
	private GamePlay currentGame;

	
	public void setCurrentGame(GamePlay currentGame) {
		this.currentGame = currentGame;
	}

	
	public GamePlay getCurrentGame() {
		return currentGame;
	}
}
