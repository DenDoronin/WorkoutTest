package com.example.doroworkoutorganizer;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class newWorkout extends Activity{
	
	private EditText name;
	private EditText description;
	private boolean changeMode;
	private String workout_id;
	private String workout_name;
	private String workout_description;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.new_wokout);
		name = (EditText)findViewById(R.id.newWorkoutName);
		description = (EditText)findViewById(R.id.newWorkoutDescription);
		View saveButton = findViewById(R.id.newWorkoutAdd);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			workout_id = extras.getString("WORKOUT_ID");
		    workout_name = extras.getString("WORKOUT_NAME");
		    workout_description = extras.getString("WORKOUT_DESCRIPTION");
		    String button_text = extras.getString("BUTTON_TEXT");  
		    Button b = (Button) saveButton;
		    b.setText(button_text);
		    name.setText(workout_name);
		    description.setText(workout_description);
		    changeMode = true;
		}
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.newWorkoutAdd:
					if ( name.getText().toString().equals("") == true)
					{
						AlertDialog.Builder builder = new AlertDialog.Builder(newWorkout.this)
						.setTitle("Warning")
						.setMessage("You must enter workout name!")
						.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) { 
							}
						});
						AlertDialog alert = builder.create();
						alert.show();
						((Button)alert.findViewById(android.R.id.button1)).setBackgroundResource(R.drawable.custom_button);
					return;
					}
					if (changeMode !=true)
					{
						Intent i = new Intent(getApplicationContext(), Editor.class); 
						String n = name.getText().toString();
						
						String d = description.getText().toString();
						WorkoutRepository.addWorkout(getApplicationContext(), n, d);
						int id = WorkoutRepository.getLastAddedWokout(getApplicationContext());
						i.putExtra("WORKOUT_ID", String.valueOf(id));
						startActivity(i);
					}
					else
					{
						Intent a = new Intent(getApplicationContext(),Workout.class);
				        a.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				        WorkoutEntity e = new WorkoutEntity(workout_id, 
				        									name.getText().toString(), 
				        									description.getText().toString());
				        WorkoutRepository.update(getApplicationContext(), e);
				        startActivity(a);
					}
				break;
				}
			}
		});
	}

	
}
