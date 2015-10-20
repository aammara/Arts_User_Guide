package com.tmm.android.chuck.quiz;

import tn.arts.userguide.*;

public class Helper {

	public static String getResultComment(int numCorrect, int numRounds, int diff)
	{
		String comm="";
		int percentage = calculatePercentage(numCorrect, numRounds);
		switch (diff)
		{
		case Constants.EXTREME :
			if (percentage > 90){
				comm = "Excellent!";
			}else if (percentage >= 80){
				comm="Bien!";
			}else if (percentage >= 60){
				comm="Pas mal..";
			}else if (percentage >= 40){
				comm="Votre score est très bas. Allez faire un tour du côté de se former et revenez..";
			}else{
				comm="Vous avez échoué. Vous devez enrichir vos connaissances en secourisme..";
			}
			break;
			
		default:
			if (percentage > 90){
				comm = "Excellent!";
			}else if (percentage >= 80){
				comm="Bien!";
			}else if (percentage >= 60){
				comm="Pas mal..";
			}else if (percentage >= 40){
				comm="Votre score est très bas. Allez faire un tour du côté de se former et revenez..";
			}else{
				comm="Vous avez échoué. Vous devez enrichir vos connaissances en secourisme..";
			}
		}
		
		return comm;
	}
	
	
	
		private static int calculatePercentage(int numCorrect, int numRounds) {
		double frac = (double)numCorrect/(double)numRounds;
		int percentage = (int) (frac*100);
		return percentage;
	}
}
