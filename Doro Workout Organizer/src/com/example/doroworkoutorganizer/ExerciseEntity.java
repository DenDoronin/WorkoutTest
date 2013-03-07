package com.example.doroworkoutorganizer;

import android.database.Cursor;

public class ExerciseEntity {
	
	private int id;
	public int getId() {return this.id;}
	
	private int name_id;
	public void setName_id(int name_id){ this.name_id = name_id;}
	public int getName_id(){ return this.name_id;}
	
	private int count;
	public void setCount(int count){ this.count = count;}
	public int getCount(){ return this.count; }
	
	private int rep_duration;
	public void setRep_duration(int rep_duration) { this.rep_duration = rep_duration; }
	public int getRep_duration() { return this.rep_duration; }
	
	private int rest_duration;
	public void setRest_duration(int rest_duration){ this.rest_duration = rest_duration; }
	public int getRest_duration() { return rest_duration;}
	
	public ExerciseEntity()
	{
		super();
	}
	
	public ExerciseEntity(String id, String name_id, String count, String rep_dur, String rest_dur)
	{
		super();
		this.id =  Integer.parseInt(id);
		this.name_id = Integer.parseInt(name_id);
		this.count = Integer.parseInt(count);
		this.rep_duration = Integer.parseInt(rep_dur);
		this.rest_duration = Integer.parseInt(rest_dur);
	}
	
	public ExerciseEntity(Cursor cursor)
	{
		this(cursor.getString(0), 
					cursor.getString(1), 
					cursor.getString(2), 
					cursor.getString(3), 
					cursor.getString(4));
	}

}
