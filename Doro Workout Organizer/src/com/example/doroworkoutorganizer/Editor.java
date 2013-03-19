package com.example.doroworkoutorganizer;



import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class Editor extends ListActivity implements OnClickListener
{
	private int workout_id;
	private int selected_id;
	private List<ExerciseEntity> entities;
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
		getWorkoutId();
		registerForContextMenu(getListView());
		getListView().setSelector( R.drawable.listselector);
		
	}
	private void getWorkoutId()
	{
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    workout_id = Integer.parseInt(extras.getString("WORKOUT_ID"));
		}
		else
		{
			/// to do: if new workout, get it id
		}
	}
	@Override 
	protected void onResume()
	{
		super.onResume();
		UpdateExerciseList();
		//getWorkoutId();
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
			i.putExtra("WORKOUT_ID", String.valueOf(workout_id));
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
		entities = ExerciseRepository.getData(this,workout_id);

		ExerciseAdapter adapter = new ExerciseAdapter(this, 
                R.layout.item, entities);
		setListAdapter(adapter);

		
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.context_menu_exercise, menu);
	    AdapterView.AdapterContextMenuInfo info =
	            (AdapterView.AdapterContextMenuInfo) menuInfo;
	    selected_id = info.position;
	    int id = entities.get(selected_id).getName_id();
	    menu.setHeaderTitle("Exercise: "+ExerciseRepository.getNameById(this,
				id));
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.context_delete_exercise:
	        	
	            DeleteDialog(entities.get(selected_id));
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	private void DeleteDialog(final ExerciseEntity e)
	{
		new AlertDialog.Builder(this)
	    .setTitle("Delete")
	    .setMessage("Are you sure you want to delete this exercise?")
	    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	ExerciseRepository.delete(getApplicationContext(), e.getId());
	        	onResume();
	        }
	     })
	    .setNegativeButton("No", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            
	        }
	     })
	     .show();
	}
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
		
		Intent intent = new Intent(this, Exercise.class);
		intent.putExtra("EXERCISE_ID", String.valueOf(entities.get(position).getId()));
		intent.putExtra("WORKOUT_ID", String.valueOf(workout_id));
		intent.putExtra("BUTTON_TEXT", getString( R.string.button_text_change));
		startActivity(intent);
    }

}
