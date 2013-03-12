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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;



public class Exercise extends Activity
{
	private WorkoutData data;
	private int workout_id;
	private int exercise_id;
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
		spinner = (Spinner)findViewById(R.id.exercize_drop_list);
		Set<String> set = NameRepository.getNameData(this);
	       //Convert set into list
	    List<String> list = new ArrayList<String>(set);
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	    		android.R.layout.simple_spinner_item, list);
	   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	   spinner.setAdapter(adapter);
	   spinner.setSelection(0);
		workout_id = -1;
		exercise_id = -1;
		if (extras != null) {
			try
			{
		    workout_id = Integer.parseInt(extras.getString("WORKOUT_ID"));
		    exercise_id = Integer.parseInt(extras.getString("EXERCISE_ID"));
		    ExerciseEntity e = ExerciseRepository.getExerciseById(this, exercise_id);
		    reps_count.setText(String.valueOf(e.getCount()));
		    rep_duration.setText(String.valueOf(e.getRep_duration()));
		    rest_duration.setText(String.valueOf(e.getRest_duration()));
		    spinner.setSelection(spinner.getCount()-e.getName_id());
		    String button_text = extras.getString("BUTTON_TEXT"); 
		    Button b = (Button) saveButton;
		    b.setText(button_text);
			}
			catch(NumberFormatException e)
			{
				
			}
		}
		else
		{
			/// to do: if new workout, get it id
			
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
				if (Validate()==false) 
				{
					new AlertDialog.Builder(Exercise.this)
			    		.setTitle("Warning")
			    		.setMessage("You must fill all fields!")
			    		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			    			public void onClick(DialogInterface dialog, int which) { 
			    			}
			    		})
			    		.show();
					return;
				}
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
		
		
		
	}
	private boolean Validate()
	{
		String tmp = reps_count.getText().toString();
		if (reps_count.getText().toString().equals("")) return false;
		if(rep_duration.getText().toString().equals("")) return false;
		if (rest_duration.getText().toString().equals("")) return false;
		
		return true;
	}
	
	private void saveExercize() {
		// TODO Auto-generated method stub

		int count = Integer.parseInt(reps_count.getText().toString());
		int reps_dur = Integer.parseInt(rep_duration.getText().toString());
		int rest_dur = Integer.parseInt(rest_duration.getText().toString());
		int name_id = spinner.getCount() - spinner.getSelectedItemPosition();
		if (exercise_id ==-1)
		ExerciseRepository.addExercise(this,name_id,workout_id, count, reps_dur, rest_dur);
		else
		{
			ExerciseEntity e = new ExerciseEntity(String.valueOf(exercise_id), 
						String.valueOf(name_id), 
						String.valueOf(workout_id),
						String.valueOf(count),
						String.valueOf(reps_dur), 
						String.valueOf(rest_dur));
			ExerciseRepository.update(this, e);
		}
		
	}
	
}
