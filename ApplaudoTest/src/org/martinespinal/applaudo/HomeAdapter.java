package org.martinespinal.applaudo;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HomeAdapter extends ArrayAdapter<Venue> {
	ArrayList<Venue> data;
	LayoutInflater inflater;
	
	public HomeAdapter(Context context,ArrayList<Venue> objects){
		super(context, -1,objects);
		this.data=objects;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder;
		Venue venue = data.get(position);
		int layout =R.layout.list_row;
		
		if(convertView==null){
			convertView = inflater.inflate(layout, null);
			holder = new ViewHolder();
			holder.mName= (TextView) convertView.findViewById(R.id.mName);
			holder.mAddress= (TextView) convertView.findViewById(R.id.mAddress);
			convertView.setTag(holder);
		}
		else{
			holder= (ViewHolder) convertView.getTag();
		}
		
		holder.mName.setText(venue.getName());
		holder.mAddress.setText(venue.getAddress()+" "+venue.getCity()+" "+venue.getState());
		
		return convertView;
	}
	
	public static class ViewHolder{
		public TextView mName;
		public TextView mAddress;
	}
}
