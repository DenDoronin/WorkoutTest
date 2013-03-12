package com.example.doroworkoutorganizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class newWorkout extends Activity{
	
	private EditText name;
	private EditText description;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.new_wokout);
		name = (EditText)findViewById(R.id.newWorkoutName);
		description = (EditText)findViewById(R.id.newWorkoutDescription);
		View saveButton = findViewById(R.id.newWorkoutAdd);
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.newWorkoutAdd:
					Intent i = new Intent(getApplicationContext(), Editor.class); 
					String n = name.getText().toString();
					String d = description.getText().toString();
					WorkoutRepository.addWorkout(getApplicationContext(), n, d);
					int id = WorkoutRepository.getLastAddedWokout(getApplicationContext());
					i.putExtra("WORKOUT_ID", String.valueOf(id));
					startActivity(i);
				break;
				}
			}
		});
	}

	
}
