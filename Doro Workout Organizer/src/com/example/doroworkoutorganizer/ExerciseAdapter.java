package com.example.doroworkoutorganizer;

import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.app.Application;

public class ExerciseAdapter extends ArrayAdapter<ExerciseEntity> {
	Context context; 
    int layoutResourceId;    
    List<ExerciseEntity> data = null;

    public ExerciseAdapter(Context context, int layoutResourceId, List<ExerciseEntity> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ExerciseHolder holder = null;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new ExerciseHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.id = (TextView)row.findViewById(R.id.exercise_id);
            holder.name_id = (TextView)row.findViewById(R.id.name_id);
            holder.count = (TextView)row.findViewById(R.id.count);
            holder.rep_duration = (TextView)row.findViewById(R.id.rep_duration);
            holder.rest_duration = (TextView)row.findViewById(R.id.rest_duration);
            row.setTag(holder);
        }
        else
        {
            holder = (ExerciseHolder)row.getTag();
        }
        
        ExerciseEntity exercise = data.get(position);
        //crash!!!
     //   holder.id.setText(String.valueOf(exercise.getId()));
        String name = ExerciseRepository.getNameById(getContext(), exercise.getName_id());
        String icon_name = "icon_"+name.replace(" ", "");
        int resourceId;
       
        	resourceId = ((Activity)context).getResources().getIdentifier(icon_name, 
        		"drawable", 
        		"com.example.doroworkoutorganizer");
        if(resourceId == 0 )
        {
        	resourceId = R.drawable.icon_unknown;
        }
        holder.imgIcon.setImageResource(resourceId);
        holder.name_id.setText("Task: "+name);
        holder.count.setText("Count: "+String.valueOf(exercise.getCount())+ " times");
        holder.rep_duration.setText("One rep: "+String.valueOf(exercise.getRep_duration())+" sec");
        holder.rest_duration.setText("Rest duration: "+String.valueOf(exercise.getRest_duration())+" sec");
        return row;
    }
    
    static class ExerciseHolder
    {
        ImageView imgIcon;
        TextView id;
        TextView name_id;
        TextView count;
        TextView rep_duration;
        TextView rest_duration;
    }
}
