package com.example.doroworkoutorganizer;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Workout extends ListActivity {
	

	private List<WorkoutEntity> entities;
	private int selected_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout);
		registerForContextMenu(getListView());
		UpdateWorkoutList();
		getListView().setSelector( R.drawable.listselector);

	}
	
	@Override 
	protected void onResume()
	{
		super.onResume();
		UpdateWorkoutList();
	}
	
	@Override 
	
	protected void onPause()
	{
		super.onPause();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.workout, menu);
		return true;
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.context_menu, menu);
	    AdapterView.AdapterContextMenuInfo info =
	            (AdapterView.AdapterContextMenuInfo) menuInfo;
	    selected_id = info.position;
	    menu.setHeaderTitle("Workout: "+entities.get(selected_id).getName());
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.context_delete_workout:
	        	
	            DeleteDialog(entities.get(selected_id));
	            return true;
	        case R.id.context_rename_workout:
	        	Intent intent = new Intent(this, newWorkout.class);
	        	intent.putExtra("WORKOUT_ID", String.valueOf(entities.get(selected_id).getId()));
				intent.putExtra("WORKOUT_NAME", entities.get(selected_id).getName());
				intent.putExtra("WORKOUT_DESCRIPTION", entities.get(selected_id).getDescription());
				intent.putExtra("BUTTON_TEXT", getString( R.string.button_text_change));
				startActivity(intent);
	        	return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) { 
		case R.id.settings_add_workout:
		
			intent = new Intent(this, newWorkout.class);
			//intent.putExtra("WORKOUT_ID", -1);
			startActivity(intent);
			return true;
			// More items go here (if any) ... 
		case R.id.settings_add_exercise_type:
			
			intent = new Intent(this, newExercise.class);
			startActivity(intent);
			return true;
		}
			return false;
	}
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
		
		Intent intent = new Intent(this, Editor.class);
		intent.putExtra("WORKOUT_ID", String.valueOf(entities.get(position).getId()));
		startActivity(intent);
    }
	private void UpdateWorkoutList()
	{	
		entities = WorkoutRepository.getData(this);

		WorkoutAdapter adapter = new WorkoutAdapter(this, 
                R.layout.workout_item, entities);
		setListAdapter(adapter);

		
	}
	private void DeleteDialog(final WorkoutEntity e)
	{
		new AlertDialog.Builder(this)
	    .setTitle("Delete")
	    .setMessage("Are you sure you want to delete "+e.getName()+"?")
	    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	WorkoutRepository.delete(getApplicationContext(), e.getId());
	        	onResume();
	        }
	     })
	    .setNegativeButton("No", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            
	        }
	     })
	     .show();
	}


}
