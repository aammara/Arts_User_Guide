package com.tmm.android.chuck.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.tmm.android.chuck.quiz.Question;

public class DBHelper extends SQLiteOpenHelper{

	//The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/tn.arts.userguide/databases/";
	private static String DB_NAME = "questionsDb1";
	private SQLiteDatabase myDataBase; 
	private final Context myContext;
	 int i=0;
	 
	
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}	

	// Creates a empty database 
	public void createDataBase() throws IOException{

		boolean dbExist = databaseExist();
		if(!dbExist)
		{
			this.getReadableDatabase();

			try {
				copyDataBase(); 
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	// Check if the database already exist 
	public boolean databaseExist()
	{
	    File dbFile = new File(DB_PATH + DB_NAME);
	    return dbFile.exists();
	}

	/* Copies your database from your local assets-folder to the just created empty database in the
	  system folder */
	
	private void copyDataBase() throws IOException{

		//Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer))>0){
			myOutput.write(buffer, 0, length);
		}

		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException{
		//Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}

	@Override
	public synchronized void close() {
		if(myDataBase != null)
			myDataBase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

		public List<Question> getQuestionSet(int difficulty, int numQ){
		List<Question> questionSet = new ArrayList<Question>();
	Cursor c = myDataBase.rawQuery("SELECT * FROM questions WHERE DIFFICULTY=" + difficulty +
			" ORDER BY RANDOM() LIMIT " + numQ, null);
		
		while (c.moveToNext()){
			Question q = new Question();
			q.setQuestion(c.getString(1));
			q.setAnswer(c.getString(2));
			q.setOption1(c.getString(3));
			q.setOption2(c.getString(4));
	
			q.setRating(difficulty);
			questionSet.add(q);
		}
		return questionSet;
	}
}