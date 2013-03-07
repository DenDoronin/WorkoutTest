package com.example.doroworkoutorganizer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.doroworkoutorganizer.ExerciseConstants.NAME_ID;
import static com.example.doroworkoutorganizer.ExerciseConstants.COUNT_OF_REPS;
import static com.example.doroworkoutorganizer.ExerciseConstants.REP_DURATION;
import static com.example.doroworkoutorganizer.ExerciseConstants.TABLE_EXERCISE;
import static com.example.doroworkoutorganizer.ExerciseConstants.REST_DURATION;
import static com.example.doroworkoutorganizer.NameConstants.TABLE_NAME;
import static com.example.doroworkoutorganizer.NameConstants.NAME_VALUE;
import static com.example.doroworkoutorganizer.WorkoutConstants.TABLE_WORKOUT;
import static com.example.doroworkoutorganizer.WorkoutConstants.EXERCISE_ID;
import static com.example.doroworkoutorganizer.WorkoutConstants.WORKOUT_NAME;


public class WorkoutData extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "workout.db"; 
	private static final int DATABASE_VERSION = 1;

	public WorkoutData(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		db.execSQL("CREATE TABLE " + TABLE_NAME
				+ " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ NAME_VALUE + " TEXT NOT NULL);");	
		db.execSQL("CREATE TABLE " + TABLE_EXERCISE 
				+ " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ NAME_ID + " INTEGER NOT NULL, "
				+ COUNT_OF_REPS + " INTEGER NOT NULL, " 
				+ REP_DURATION + " INTEGER NOT NULL, "
				+ REST_DURATION + " INTEGER NOT NULL, "
				+"FOREIGN KEY("+NAME_ID
				+") REFERENCES "+TABLE_NAME+"("+_ID+"));");	
		db.execSQL("CREATE TABLE " + TABLE_WORKOUT
				+ " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ EXERCISE_ID + " INTEGER NOT NULL, "
				+ WORKOUT_NAME + " TEXT NOT NULL, "
				+"FOREIGN KEY("+ EXERCISE_ID
				+") REFERENCES "+TABLE_EXERCISE+"("+_ID+"));");
		db.execSQL("INSERT INTO "+TABLE_NAME+"("+NAME_VALUE+") VALUES ("+"'pull ups'"+");");
		db.execSQL("INSERT INTO "+TABLE_NAME+"("+NAME_VALUE+") VALUES ("+"'push ups'"+");");
		db.execSQL("INSERT INTO "+TABLE_NAME+"("+NAME_VALUE+") VALUES ("+"'press up'"+");");
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		// in future implement this method in other way
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE); 
		onCreate(db);

	}

}
