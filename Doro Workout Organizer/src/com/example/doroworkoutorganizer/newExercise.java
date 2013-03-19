package com.example.doroworkoutorganizer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

public class newExercise extends ListActivity  {
	private EditText name;
	private int selected_id = -1;
	private List<NameEntity> entities;
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.new_exercise_type);
		name = (EditText)findViewById(R.id.newExerciseType);
		View saveButton = findViewById(R.id.newExerciseTypeButton);
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.newExerciseTypeButton:
					if ( name.getText().toString().equals("") == true)
					{
						
						AlertDialog.Builder builder = new AlertDialog.Builder(newExercise.this)
						.setTitle("Warning")
						.setMessage("You must enter exercise name!")
						.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) { 
								
							}
						});
					AlertDialog alert = builder.create();
					alert.show();
					((Button)alert.findViewById(android.R.id.button1)).setBackgroundResource(R.drawable.custom_button);
					return;
					}
					if (selected_id == -1)
					{
						NameRepository.addExerciseType(newExercise.this, name.getText().toString());
						Intent a = new Intent(getApplicationContext(),Workout.class);
			     	  a.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			     	  startActivity(a);
					}
					else
					{
						entities.get(selected_id).setName(name.getText().toString());
						NameRepository.update(newExercise.this, entities.get(selected_id));
						name.setText("");
			        	Button saveButton = (Button)findViewById(R.id.newExerciseTypeButton);
			        	saveButton.setText(R.string.add_exercise_type_button);
			        	UpdateNameList();
			        	selected_id = -1;
			        	getListView().setVisibility(View.VISIBLE);
			        	InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE); 

			        	inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                   InputMethodManager.HIDE_NOT_ALWAYS);
			        	return;
					}
					break;
				}
			}
		});
		UpdateNameList();
		registerForContextMenu(getListView());
		
	}
	private void UpdateNameList()
	{	
		entities = NameRepository.getData(this);
		List<String> a = new ArrayList<String>();
		for ( NameEntity e : entities)
		{
			a.add(e.getName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
                android.R.layout.simple_list_item_1, a);
		setListAdapter(adapter);

		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.context_menu_exercise_type, menu);
	    AdapterView.AdapterContextMenuInfo info =
	            (AdapterView.AdapterContextMenuInfo) menuInfo;
	    selected_id = info.position;
	    menu.setHeaderTitle("Exercise: "+entities.get(selected_id).getName());
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.context_delete_exercise_type:
	        	
	            DeleteDialog(entities.get(selected_id));
	            return true;
	        case R.id.context_rename_exercise_type:
	        	name.setText(entities.get(selected_id).getName());
	        	Button saveButton = (Button)findViewById(R.id.newExerciseTypeButton);
	        	saveButton.setText(R.string.change_exercise_type_button);
	        	getListView().setVisibility(View.INVISIBLE);
	        	return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
	private void DeleteDialog(final NameEntity e)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
	    .setTitle("Delete")
	    .setMessage("Are you sure you want to delete "+e.getName()+"?")
	    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	NameRepository.delete(getApplicationContext(), e.getId());
	        	onResume();
	        }
	     })
	    .setNegativeButton("No", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            
	        }
	     });
		
		AlertDialog alert = builder.create();
		alert.show();
		((Button)alert.findViewById(android.R.id.button1)).setBackgroundResource(R.drawable.custom_button);
		((Button)alert.findViewById(android.R.id.button2)).setBackgroundResource(R.drawable.custom_button);
	}
	@Override 
	protected void onResume()
	{
		super.onResume();
		UpdateNameList();
	}
	
	@Override 
	
	protected void onPause()
	{
		super.onPause();
	}

}
