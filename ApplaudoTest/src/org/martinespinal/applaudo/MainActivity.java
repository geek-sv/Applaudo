package org.martinespinal.applaudo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends ActionBarActivity {
	
	JSONArray mdata;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Next Fragment
		
		

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
		
		
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
	}

	public ArrayList<Venue> parser(JSONArray respuesta){
		ArrayList<Venue> venueAux = new ArrayList<Venue>();
		for(int i = 0; i< respuesta.length(); i++){
			JSONObject jsonObject;
			Venue venue= new Venue();
			try {
				jsonObject=(JSONObject) respuesta.getJSONObject(i);
				venue.setName(jsonObject.getString("name"));
				venue.setAddress(jsonObject.getString("address"));
				venue.setCity(jsonObject.getString("city"));
				venue.setState(jsonObject.getString("state"));
				venueAux.add(venue);
				
			} catch (JSONException e) {
				// TODO: handle exception
				Log.e("peticion", "Error al parsear");
			}
		}
		return venueAux;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		RequestQueue mRequestQueue = Volley.newRequestQueue(this);
		String url="https://s3.amazonaws.com/jon-hancock-phunware/nflapi-static.json";
		
		final ProgressDialog progressDialog= ProgressDialog.show(this, "Please Wait....", "Downloading Data...",true);
		
		JsonArrayRequest req= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				mdata=response;
				// TODO Auto-generated method stub
				
				ListView list = (ListView) findViewById(R.id.list_view);
				
				HomeAdapter adapter = new HomeAdapter(getApplicationContext(), parser(response));
				Log.e("NULO",parser(response).toString());
				list.setAdapter(adapter);
				progressDialog.dismiss();
				
				list.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						
						
						showDetails(position);
						
						
						
						
						
					}
				});

			}
		}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					// TODO Auto-generated method stub
						VolleyLog.e("Error", error.getMessage());
				}
		});
		
		mRequestQueue.add(req);
	}
	

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	void showDetails(int index){
		
		int mCurCheckPosition = index;
		
		Fragment newFragment= new DetailVenue();
		Bundle parameters= new Bundle();
		JSONObject jsonObject= new JSONObject();
		try{
		jsonObject=(JSONObject) mdata.getJSONObject(mCurCheckPosition);
		}catch(JSONException e){}
		String data = jsonObject.toString();
		parameters.putString("data", data);
		newFragment.setArguments(parameters);
		
		 FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		 
		 transaction.replace(R.id.container, newFragment);
		 this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		 transaction.addToBackStack(null);
		 transaction.commit();	
		
	}
	





}
