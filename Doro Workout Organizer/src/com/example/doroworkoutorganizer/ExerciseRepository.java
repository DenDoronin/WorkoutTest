package com.example.doroworkoutorganizer;

import static android.provider.BaseColumns._ID;
import static com.example.doroworkoutorganizer.ExerciseConstants.COUNT_OF_REPS;
import static com.example.doroworkoutorganizer.ExerciseConstants.NAME_ID;
import static com.example.doroworkoutorganizer.ExerciseConstants.REP_DURATION;
import static com.example.doroworkoutorganizer.ExerciseConstants.REST_DURATION;
import static com.example.doroworkoutorganizer.ExerciseConstants.TABLE_EXERCISE;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExerciseRepository {
	
	private static SQLiteOpenHelper data;
	public static void addExercise(Context context,int name_id, int count, int rep_duration, int rest_duration) {
		// TODO Auto-generated method stub
		data = new WorkoutData(context);
		try{
			SQLiteDatabase db = data.getWritableDatabase(); 
			ContentValues values = new ContentValues(); 
			values.put(NAME_ID,name_id); 
			values.put(COUNT_OF_REPS, count);
			values.put(REP_DURATION,rep_duration); 
			values.put(REST_DURATION,rest_duration); 
			db.insertOrThrow(TABLE_EXERCISE, null, values);
		}
		finally
		{
			data.close();
		}
	}


	public static List<ExerciseEntity> getData(final Context context ) {
		
		  List<ExerciseEntity> set = new ArrayList<ExerciseEntity>();
		  data = new WorkoutData(context);
		  try
			{
			  String selectQuery = "SELECT * FROM " + TABLE_EXERCISE;
			  SQLiteDatabase db = data.getReadableDatabase();
			  Cursor cursor = db.rawQuery(selectQuery, null);
			  if (cursor.moveToFirst()) {
				  do {
					  set.add(new ExerciseEntity(cursor) );
				  } while (cursor.moveToNext());
			  }
			  cursor.close();
			  db.close();
			}
		  finally
		  {
			  data.close();
		  }
		  return set;
		 }
}
