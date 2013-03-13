package com.example.doroworkoutorganizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class newExercise extends Activity {
	private EditText name;
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.new_exercise_type);
		name = (EditText)findViewById(R.id.newExerciseType);
		View saveButton = findViewById(R.id.newExerciseTypeButton);
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.newExerciseTypeButton:
					if ( name.getText().toString().equals("") == true)
					{
						new AlertDialog.Builder(newExercise.this)
						.setTitle("Warning")
						.setMessage("You must enter exercise name!")
						.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) { 
								
							}
						})
						.show();
					return;
					}
					NameRepository.addExerciseType(newExercise.this, name.getText().toString());
					Intent a = new Intent(getApplicationContext(),Workout.class);
			        a.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			        startActivity(a);
					break;
				}
			}
		});
	}

}
