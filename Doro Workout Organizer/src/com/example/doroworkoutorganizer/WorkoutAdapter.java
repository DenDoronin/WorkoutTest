package com.example.doroworkoutorganizer;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doroworkoutorganizer.ExerciseAdapter.ExerciseHolder;

public class WorkoutAdapter extends ArrayAdapter<WorkoutEntity> {
	Context context; 
    int layoutResourceId;    
    List<WorkoutEntity> data = null;

    public WorkoutAdapter(Context context, int layoutResourceId, List<WorkoutEntity> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WorkoutHolder holder = null;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new WorkoutHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.workout_item_imgIcon);
            holder.name = (TextView)row.findViewById(R.id.workout_item_name);
            holder.duration = (TextView)row.findViewById(R.id.workout_item_workout_duration);
            holder.description = (TextView)row.findViewById(R.id.workout_item_description);
            holder.exercises = (TextView)row.findViewById(R.id.workout_item_exercises);
            row.setTag(holder);
        }
        else
        {
            holder = (WorkoutHolder)row.getTag();
        }
        
        WorkoutEntity workout = data.get(position);
        holder.name.setText(workout.getName());
        holder.imgIcon.setImageResource(R.drawable.exercise);
        holder.description.setText(workout.getDescription());
        int duration = WorkoutRepository.getDuration(getContext(), workout.getId());
        holder.duration.setText("Duration: "+duration / 60+" min "+duration % 60+" sec");
        holder.exercises.setText("Contains: "+
        						WorkoutRepository.getExercisesCount(getContext(), workout.getId())
        						+" exercises");
        return row;
    }
    
    static class WorkoutHolder
    {
        ImageView imgIcon;
        TextView name;
        TextView description;
        TextView duration;
        TextView exercises;
    }
}
