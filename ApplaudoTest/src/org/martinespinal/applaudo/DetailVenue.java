package org.martinespinal.applaudo;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailVenue  extends Fragment{
	
	
	private String s;
	private String nameVenue;
	private String addressVenue;
	private String cityVenue;
	private String imageUrl;
	
	public DetailVenue() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		
		s = b.getString("data");
		
		
		Log.e("Mensaje",s);
		try {
			JSONObject jsonObject= new JSONObject(s);
			Venue venue= new Venue();
			ScheduleItem schedule= new ScheduleItem();
				venue.setName(jsonObject.getString("name"));
				venue.setAddress(jsonObject.getString("address"));
				venue.setCity(jsonObject.getString("city"));
				venue.setState(jsonObject.getString("state"));
				venue.setZip(jsonObject.getString("zip"));
				venue.setImageUrl(jsonObject.getString("image_url"));
				
				ArrayList<ScheduleItem> scheduleAux = new ArrayList<ScheduleItem>();
				JSONArray scheduleObject= jsonObject.getJSONArray("schedule");
				for (int i = 0; i <scheduleObject.length(); i++) {
					try {
						jsonObject=(JSONObject) scheduleObject.getJSONObject(i);
						try {
							schedule.setStartDate(jsonObject.getString("start_date"));
							schedule.setEndDate(jsonObject.getString("end_state"));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						scheduleAux.add(schedule);
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				
				
				imageUrl= venue.getImageUrl();
				nameVenue= venue.getName();
				Log.e("DATO",nameVenue);
				addressVenue=venue.getAddress();
				cityVenue=venue.getCity()+" "+venue.getState()+" "+venue.getZip();
				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log.e("nuevobundle",s);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		LinearLayout p = (LinearLayout)  inflater.inflate(R.layout.fragment_detail_venue,container,false);
		ImageView imageUrlView = (ImageView) p.findViewById(R.id.imgVenue);
		
		  if (imageUrl != null && !imageUrl.isEmpty()) {
			  Picasso.with(getActivity()).load(imageUrl).resize(300,300).into(imageUrlView);
			  
		  }else{
			  Picasso.with(getActivity()).load(R.drawable.errorimage).resize(300,300).into(imageUrlView);
		  }

		
		
		TextView textName = (TextView) p.findViewById(R.id.txtName);
		textName.setText(nameVenue);
		TextView textAddress = (TextView) p.findViewById(R.id.txtAddress);
		textAddress.setText(addressVenue);
		TextView textCity = (TextView)p.findViewById(R.id.txtCityState);
		textCity.setText(cityVenue);
		return p;
		
		
	}
	
	
}
