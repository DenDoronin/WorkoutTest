package com.example.doroworkoutorganizer;



import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Editor extends ListActivity implements OnClickListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_workout);
		// new gg
		View addExersizeButton = findViewById(R.id.addExercize); 
		addExersizeButton.setOnClickListener(this);
		View saveWorkoutButton = findViewById(R.id.saveWorkout); 
		saveWorkoutButton.setOnClickListener(this);
		UpdateExerciseList();
		
	}
	@Override 
	protected void onResume()
	{
		super.onResume();
		UpdateExerciseList();
	}
	
	@Override 
	
	protected void onPause()
	{
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.addExercize:
			Intent i = new Intent(this, Exercise.class); 
			startActivity(i);
		break;
		case R.id.saveWorkout:
			saveWorkout();
		break;
		      // More buttons go here (if any) ...
		}
	}

	private void saveWorkout() {
		//returning to main activity - list of workout
		Intent a = new Intent(getApplicationContext(),Workout.class);
        a.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(a); 
	}
	
	private void UpdateExerciseList()
	{	
		List<ExerciseEntity> entities = ExerciseRepository.getData(this);

		ExerciseAdapter adapter = new ExerciseAdapter(this, 
                R.layout.item, entities);
		setListAdapter(adapter);

		
	}

}
