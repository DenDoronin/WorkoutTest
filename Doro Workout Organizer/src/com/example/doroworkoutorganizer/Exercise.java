package com.example.doroworkoutorganizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.doroworkoutorganizer.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;



public class Exercise extends Activity
{
	private WorkoutData data;
	private int workout_id;
	private EditText reps_count;
	private EditText rep_duration;
	private EditText rest_duration;
	private Spinner spinner;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_exercize);
		View saveButton = findViewById(R.id.save_exercise_button);
		reps_count = (EditText)findViewById(R.id.repsCountEdit);
		rep_duration = (EditText)findViewById(R.id.repsDurationEdit);
		rest_duration = (EditText)findViewById(R.id.restDurationEdit);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    workout_id = Integer.parseInt(extras.getString("WORKOUT_ID"));
		}
		else
		{
			/// to do: if new workout, get it id
			workout_id = -1;
			new AlertDialog.Builder(this)
		    .setTitle("Error")
		    .setMessage("Exercise will not save, unknown workout id")
		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	Intent a = new Intent(getApplicationContext(),Editor.class);
			        a.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			        startActivity(a);
		        }
		     })
		     .show();
		}
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				data = new WorkoutData(Exercise.this);
				try {
					saveExercize();
				} finally
				{
					data.close();
				}
				//returning to main activity - list of workout
				Intent a = new Intent(getApplicationContext(),Editor.class);
		        a.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		        startActivity(a);
			}
		});
		
		spinner = (Spinner)findViewById(R.id.exercize_drop_list);
		Set<String> set = NameRepository.getNameData(this);
	       //Convert set into list
	    List<String> list = new ArrayList<String>(set);
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	    		android.R.layout.simple_spinner_item, list);
	   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	   spinner.setAdapter(adapter);
	   spinner.setSelection(0);
		
	}
	
	private void saveExercize() {
		// TODO Auto-generated method stub
		int count = Integer.parseInt(reps_count.getText().toString());
		int reps_dur = Integer.parseInt(rep_duration.getText().toString());
		int rest_dur = Integer.parseInt(rest_duration.getText().toString());
		int name_id = spinner.getCount() - spinner.getSelectedItemPosition();
		ExerciseRepository.addExercise(this,name_id,workout_id, count, reps_dur, rest_dur);
		
	}
	
}
