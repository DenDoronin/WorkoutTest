package com.example.doroworkoutorganizer;


import static com.example.doroworkoutorganizer.NameConstants.TABLE_NAME;
import static com.example.doroworkoutorganizer.NameConstants.NAME_VALUE;

import java.util.HashSet;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NameRepository {
	static private WorkoutData data;
	static public Set<String> getNameData(final Context context) {
		
		  Set<String> set = new HashSet<String>();
		  data = new WorkoutData(context);
		  try
			{
			  String selectQuery = "SELECT * FROM " + TABLE_NAME;
			  SQLiteDatabase db = data.getReadableDatabase();
			  Cursor cursor = db.rawQuery(selectQuery, null);
			  if (cursor.moveToFirst()) {
				  do {
					  set.add(cursor.getString(1));
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
	static private void addExerciseType(final Context context,final String exercise_name) {
		// TODO Auto-generated method stub
		data = new WorkoutData(context);
		try{
			SQLiteDatabase db = data.getWritableDatabase(); 
			ContentValues values = new ContentValues(); 
			values.put(NAME_VALUE,exercise_name);  
			db.insertOrThrow(TABLE_NAME, null, values);
		}
		finally
		{
			data.close();
		}
	}

}