package com.example.doroworkoutorganizer;



import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class Editor extends Activity implements OnClickListener
{
	private  ListView ExerciseList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_workout);
		// new gg
		View addExersizeButton = findViewById(R.id.addExercize); 
		addExersizeButton.setOnClickListener(this);
		View saveWorkoutButton = findViewById(R.id.saveWorkout); 
		saveWorkoutButton.setOnClickListener(this);
		
		ExerciseList = (ListView) findViewById(R.id.exercise_list);
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
		// TODO save workout and return to main menu
		
		
		
		//returning to main activity - list of workout
		Intent a = new Intent(getApplicationContext(),Workout.class);
        a.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(a);
        
	}
	
	private void UpdateExerciseList()
	{
		List<ExerciseEntity> set = ExerciseRepository.getExerciseData(this);
	
		ExerciseEntity[] array = new ExerciseEntity[]
				{
					new ExerciseEntity("1","2","3","4","5"),
					new ExerciseEntity("7","8","8","8","5")
				};
				
		ExerciseAdapter adapter = new ExerciseAdapter(this, 
                R.layout.item, set);
        
		ExerciseList.setAdapter(adapter);
        
		int i = 0;
		i=0;
	}

}
