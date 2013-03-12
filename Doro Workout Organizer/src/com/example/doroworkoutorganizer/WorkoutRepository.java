package com.example.doroworkoutorganizer;

import static com.example.doroworkoutorganizer.WorkoutConstants.DESCRIPTION;
import static com.example.doroworkoutorganizer.WorkoutConstants.WORKOUT_NAME;
import static com.example.doroworkoutorganizer.WorkoutConstants.TABLE_WORKOUT;
import static com.example.doroworkoutorganizer.ExerciseConstants.TABLE_EXERCISE;
import static com.example.doroworkoutorganizer.ExerciseConstants.WORKOUT_ID;
import static com.example.doroworkoutorganizer.ExerciseConstants.COUNT_OF_REPS;
import static com.example.doroworkoutorganizer.ExerciseConstants.REP_DURATION;
import static com.example.doroworkoutorganizer.ExerciseConstants.REST_DURATION;
import static android.provider.BaseColumns._ID;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WorkoutRepository {
	private static SQLiteOpenHelper data;
	public static void addWorkout(Context context,String name,String description) {
		// TODO Auto-generated method stub
		data = new WorkoutData(context);
		try{
			SQLiteDatabase db = data.getWritableDatabase(); 
			ContentValues values = new ContentValues(); 
			values.put(DESCRIPTION, description);
			values.put(WORKOUT_NAME,name); 
			db.insertOrThrow(TABLE_WORKOUT, null, values);
		}
		finally
		{
			data.close();
		}
	}
	
	public static int getLastAddedWokout(final Context context)
	{
		List<WorkoutEntity> set = getData(context);
		WorkoutEntity e = set.get(set.size()-1);
		return e.getId();
	}
	
	public static void delete(final Context context, int workout_id)
	{
		data = new WorkoutData(context);
		try 
		{
			SQLiteDatabase db = data.getReadableDatabase();
			db.delete(TABLE_WORKOUT, _ID + "=" + String.valueOf(workout_id), null);
		}
		finally
		{
			
		}
	}

	public static int getExercisesCount(final Context context, int workout_id)
	{
		int result = -1;
		  data = new WorkoutData(context);
		  try
			{
			  String selectQuery = "SELECT Count(*) FROM " + TABLE_EXERCISE
					  				+" WHERE "+WORKOUT_ID+"=="+String.valueOf(workout_id);
			  SQLiteDatabase db = data.getReadableDatabase();
			  Cursor cursor = db.rawQuery(selectQuery, null);
			  if (cursor.moveToFirst()) {
				  result = Integer.parseInt(cursor.getString(0));
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
	public static int getDuration(final Context context, int workout_id)
	{
		int result = 0;
		  data = new WorkoutData(context);
		  try
			{
			  String selectQuery = "SELECT "+COUNT_OF_REPS+", "+REP_DURATION+", "+REST_DURATION+" FROM " + TABLE_EXERCISE
					  				+" WHERE "+WORKOUT_ID+"=="+String.valueOf(workout_id);
			  SQLiteDatabase db = data.getReadableDatabase();
			  Cursor cursor = db.rawQuery(selectQuery, null);
			  if (cursor.moveToFirst()) {
				  do {
				  int tmp = 0;
				  tmp = Integer.parseInt(cursor.getString(0));
				  tmp *= Integer.parseInt(cursor.getString(1));
				  tmp += Integer.parseInt(cursor.getString(2));
				  result+=tmp;
				  } while (cursor.moveToNext());
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

	public static List<WorkoutEntity> getData(final Context context ) {
		
		  List<WorkoutEntity> set = new ArrayList<WorkoutEntity>();
		  data = new WorkoutData(context);
		  try
			{
			  String selectQuery = "SELECT * FROM " + TABLE_WORKOUT;
			  SQLiteDatabase db = data.getReadableDatabase();
			  Cursor cursor = db.rawQuery(selectQuery, null);
			  if (cursor.moveToFirst()) {
				  do {
					  set.add(new WorkoutEntity(cursor) );
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
