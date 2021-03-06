package com.example.doroworkoutorganizer;

import static android.provider.BaseColumns._ID;
import static com.example.doroworkoutorganizer.ExerciseConstants.COUNT_OF_REPS;
import static com.example.doroworkoutorganizer.ExerciseConstants.NAME_ID;
import static com.example.doroworkoutorganizer.ExerciseConstants.WORKOUT_ID;
import static com.example.doroworkoutorganizer.ExerciseConstants.REP_DURATION;
import static com.example.doroworkoutorganizer.ExerciseConstants.REST_DURATION;
import static com.example.doroworkoutorganizer.ExerciseConstants.TABLE_EXERCISE;
import static com.example.doroworkoutorganizer.NameConstants.TABLE_NAME;
import static com.example.doroworkoutorganizer.NameConstants.NAME_VALUE;
import static com.example.doroworkoutorganizer.WorkoutConstants.DESCRIPTION;
import static com.example.doroworkoutorganizer.WorkoutConstants.TABLE_WORKOUT;
import static com.example.doroworkoutorganizer.WorkoutConstants.WORKOUT_NAME;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExerciseRepository {
	
	private static SQLiteOpenHelper data;
	public static void addExercise(Context context,int name_id,int workout_id, int count, int rep_duration, int rest_duration) {
		// TODO Auto-generated method stub
		data = new WorkoutData(context);
		try{
			SQLiteDatabase db = data.getWritableDatabase(); 
			ContentValues values = new ContentValues(); 
			values.put(NAME_ID,name_id); 
			values.put(WORKOUT_ID, workout_id);
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

	public static String getNameById(final Context context, int name_id)
	{
		String result="";
		  data = new WorkoutData(context);
		  try
			{
			  String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE "+_ID+"=="+String.valueOf(name_id);
			  SQLiteDatabase db = data.getReadableDatabase();
			  Cursor cursor = db.rawQuery(selectQuery, null);
			  if (cursor.moveToFirst()) {
				   result = cursor.getString(1);
			  }
			  cursor.close();
			  db.close();
			}
		  finally
		  {
			  data.close();
		  }
		  return result;
	}

	public static List<ExerciseEntity> getData(final Context context, int workout_id ) {
		
		  List<ExerciseEntity> set = new ArrayList<ExerciseEntity>();
		  data = new WorkoutData(context);
		  try
			{
			  String selectQuery = "SELECT * FROM " + TABLE_EXERCISE+ " WHERE "+WORKOUT_ID+"=="+String.valueOf(workout_id);
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
	
	public static void update(final Context context, ExerciseEntity e)
	{
		data = new WorkoutData(context);
		try{
			SQLiteDatabase db = data.getWritableDatabase(); 
			ContentValues values = new ContentValues(); 
			values.put(NAME_ID,e.getName_id()); 
			values.put(WORKOUT_ID, e.getWorkout_id());
			values.put(COUNT_OF_REPS, e.getCount());
			values.put(REP_DURATION,e.getRep_duration()); 
			values.put(REST_DURATION,e.getRest_duration());
			db.update(TABLE_EXERCISE, values, _ID+"="+String.valueOf(e.getId()), null);
		}
		finally
		{
			data.close();
		}
	}
	public static ExerciseEntity getExerciseById(final Context context, int exercise_id ) {
		
		  ExerciseEntity set = null;
		  data = new WorkoutData(context);
		  try
			{
			  String selectQuery = "SELECT * FROM " + TABLE_EXERCISE+ " WHERE "+_ID+"=="+String.valueOf(exercise_id);
			  SQLiteDatabase db = data.getReadableDatabase();
			  Cursor cursor = db.rawQuery(selectQuery, null);
			  if (cursor.moveToFirst()) {
				 
					  set = new ExerciseEntity(cursor) ;
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

	public static void delete(Context context, int id) {
		data = new WorkoutData(context);
		try 
		{
			SQLiteDatabase db = data.getReadableDatabase();
			db.delete(TABLE_EXERCISE, _ID + "=" + String.valueOf(id), null);
		}
		finally
		{
			data.close();
		}
		
	}
}
