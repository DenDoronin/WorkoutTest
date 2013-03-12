package com.example.doroworkoutorganizer;

import android.database.Cursor;

public class WorkoutEntity {
	private int id;
	public int getId() {return this.id;}
	
	private String name;
	public void setName(String name){ this.name = name;}
	public String getName(){ return this.name;}
	
	private String description;
	public void setDescription(String description){ this.description = description;}
	public String getDescription(){ return this.description; }
	
	public WorkoutEntity()
	{
		super();
	}
	
	public WorkoutEntity(String id, String name, String description)
	{
		super();
		this.id =  Integer.parseInt(id);
		this.name = name;
		this.description = description;
	}
	
	public WorkoutEntity(Cursor cursor)
	{
		this(cursor.getString(0), 
					cursor.getString(2), 
					cursor.getString(1));
	}


}
