package com.vimal.r2drink2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vimal.r2drink2.DrinkListFragment.RetrieveDrinksTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class DrinkDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    DrinkItem mItem;
	
    public DrinkDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = DrinkItem.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }


    public View rootView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_drink_detail, container, false);
        if (mItem != null) {
        	try {
				JSONObject logItem = new JSONObject();
				logItem.put("timestamp", Logger.getTimestamp());
				logItem.put("action","view");
				logItem.put("drink", mItem.drink);
				Logger.appendLog(logItem.toString()+",");
			} catch (Exception e) {
				e.printStackTrace();
			}
            ((TextView) rootView.findViewById(R.id.drinkname_text)).setText(mItem.name);
            ((SeekBar) rootView.findViewById(R.id.qty_slider)).setProgress(mItem.quantity);
            
            ((TextView) rootView.findViewById(R.id.i0_text)).setText(mItem.ingredients[0]);
            ((TextView) rootView.findViewById(R.id.i1_text)).setText(mItem.ingredients[1]);
            ((TextView) rootView.findViewById(R.id.i2_text)).setText(mItem.ingredients[2]);
            ((TextView) rootView.findViewById(R.id.i3_text)).setText(mItem.ingredients[3]);
            ((TextView) rootView.findViewById(R.id.i4_text)).setText(mItem.ingredients[4]);
            ((TextView) rootView.findViewById(R.id.i5_text)).setText(mItem.ingredients[5]);
            ((TextView) rootView.findViewById(R.id.i6_text)).setText(mItem.ingredients[6]);
            ((TextView) rootView.findViewById(R.id.i7_text)).setText(mItem.ingredients[7]);

            ((SeekBar) rootView.findViewById(R.id.i0_slider)).setProgress(mItem.ratios[0]);
            ((SeekBar) rootView.findViewById(R.id.i1_slider)).setProgress(mItem.ratios[1]);
            ((SeekBar) rootView.findViewById(R.id.i2_slider)).setProgress(mItem.ratios[2]);
            ((SeekBar) rootView.findViewById(R.id.i3_slider)).setProgress(mItem.ratios[3]);
            ((SeekBar) rootView.findViewById(R.id.i4_slider)).setProgress(mItem.ratios[4]);
            ((SeekBar) rootView.findViewById(R.id.i5_slider)).setProgress(mItem.ratios[5]);
            ((SeekBar) rootView.findViewById(R.id.i6_slider)).setProgress(mItem.ratios[6]);
            ((SeekBar) rootView.findViewById(R.id.i7_slider)).setProgress(mItem.ratios[7]);
            
            ((Button) rootView.findViewById(R.id.gimme_button)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	try 
                	{
                		new PourTask().execute();    		
            		}
                	catch (Exception e)
                	{
            			e.printStackTrace();
            		}
                }
            });
            
        }
        return rootView;
    }

    
    public boolean pour;
    
    public class PourTask extends AsyncTask<Void, Void, Void> 
    {
        @Override
        protected void onPreExecute()
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Pouring a "+mItem.name)
                   .setCancelable(false)
                   .setNegativeButton("Stop!", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
	                       	pour = false;
	                    	try {
	            				JSONObject logItem = new JSONObject();
	            				logItem.put("timestamp", Logger.getTimestamp());
	            				logItem.put("action","cancel");
	            				logItem.put("drink", mItem.drink);
	            				Logger.appendLog(logItem.toString()+",");
	            			} catch (Exception e) {
	            				e.printStackTrace();
	            			}
                       }
                   });
            alert = builder.create();
            pour = true;
            alert.show();
        }

        AlertDialog alert;
        
        @Override
		protected Void doInBackground(Void... params) {
            try 
            {
            	
                double qty = ((double)((SeekBar) rootView.findViewById(R.id.qty_slider)).getProgress())/2.0;
                double i0 = ((double)((SeekBar) rootView.findViewById(R.id.i0_slider)).getProgress())/2.0;
                double i1 = ((double)((SeekBar) rootView.findViewById(R.id.i1_slider)).getProgress())/2.0;
                double i2 = ((double)((SeekBar) rootView.findViewById(R.id.i2_slider)).getProgress())/2.0;
                double i3 = ((double)((SeekBar) rootView.findViewById(R.id.i3_slider)).getProgress())/2.0;
                double i4 = ((double)((SeekBar) rootView.findViewById(R.id.i4_slider)).getProgress())/2.0;
                double i5 = ((double)((SeekBar) rootView.findViewById(R.id.i5_slider)).getProgress())/2.0;
                double i6 = ((double)((SeekBar) rootView.findViewById(R.id.i6_slider)).getProgress())/2.0;
                double i7 = ((double)((SeekBar) rootView.findViewById(R.id.i7_slider)).getProgress())/2.0;

            	JSONObject req = new JSONObject();
            	req.put("qty", qty);
            	JSONObject ingredients = new JSONObject();
            	req.put("ingredients", ingredients);
            	ingredients.put(mItem.ingredients[0], i0);
            	ingredients.put(mItem.ingredients[1], i1);
            	ingredients.put(mItem.ingredients[2], i2);
            	ingredients.put(mItem.ingredients[3], i3);
            	ingredients.put(mItem.ingredients[4], i4);
            	ingredients.put(mItem.ingredients[5], i5);
            	ingredients.put(mItem.ingredients[6], i6);
            	ingredients.put(mItem.ingredients[7], i7);
            	
            	String reqString = req.toString();

            	try {
    				JSONObject logItem = new JSONObject();
    				logItem.put("timestamp", Logger.getTimestamp());
    				logItem.put("action","pour");
    				logItem.put("drink", mItem.drink);
    				logItem.put("request", req);
    				Logger.appendLog(logItem.toString()+",");
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
            	
            	
            	JSONObject done = new JSONObject(Utils.postString(Utils.BASE_URL+"pour",reqString));
            	boolean isRunning = true;
            	
            	while(isRunning && pour)
            	{
                	done = new JSONObject(Utils.getString(Utils.BASE_URL+"status"));
                	Iterator<String> keys = done.keys(); 
                	isRunning = false;
            		while(keys.hasNext()){
            			String key = keys.next();
            			isRunning = isRunning || done.getBoolean(key);
            	    }            	
                	Thread.sleep(100);            	
            	}
            } 
            catch (Exception e) {
    			e.printStackTrace();
            }
        	Utils.postString(Utils.BASE_URL+"stop","");           	
        	alert.cancel();
            return null;
		}
     }    
}
