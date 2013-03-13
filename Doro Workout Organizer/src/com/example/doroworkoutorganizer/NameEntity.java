package com.example.doroworkoutorganizer;

import android.database.Cursor;

public class NameEntity {
	private int id;
	public int getId() {return this.id;}
	
	private String name;
	public void setName(String name){ this.name = name;}
	public String getName(){ return this.name;}
	
	public NameEntity()
	{
		super();
	}
	
	public NameEntity(String id, String name)
	{
		super();
		this.id =  Integer.parseInt(id);
		this.name = name;
	}
	
	public NameEntity(Cursor cursor)
	{
		this(cursor.getString(0), 
					cursor.getString(1));
	}


}
