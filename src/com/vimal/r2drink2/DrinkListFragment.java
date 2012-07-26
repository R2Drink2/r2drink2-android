package com.vimal.r2drink2;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DrinkListFragment extends ListFragment {

    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callbacks mCallbacks = sDummyCallbacks;
    private int mActivatedPosition = ListView.INVALID_POSITION;

    public interface Callbacks {

        public void onItemSelected(String id);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        //@Override
        public void onItemSelected(String id) {
        }
    };

    public DrinkListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	try 
    	{
    		new RetrieveDrinksTask().execute();    		
		}
    	catch (Exception e)
    	{
			e.printStackTrace();
		}
    }

    
    public class RetrieveDrinksTask extends AsyncTask<Void, Void, Void> 
    {
        @Override
        protected void onPostExecute(Void params)
        {
            setListAdapter(new ArrayAdapter<DrinkItem>(getActivity(),
                    R.layout.simple_list_item_activated_1,
                    R.id.text1,
                    DrinkItem.ITEMS));
        }

        @Override
		protected Void doInBackground(Void... params) {
            try 
            {
        		JSONArray drinkList = new JSONArray(Utils.getString(Utils.BASE_URL+"drinks"));
        		JSONArray ingredientList = new JSONArray(Utils.getString(Utils.BASE_URL+"ingredients"));
        		
            	DrinkItem.ITEMS = new ArrayList<DrinkItem>();
            	DrinkItem.ITEM_MAP = new HashMap<String, DrinkItem>();
        		for (int i = 0; i < drinkList.length(); i++) {
        			JSONObject drink = drinkList.getJSONObject(i);
        			DrinkItem item = new DrinkItem(drink, ingredientList);
        			DrinkItem.ITEMS.add(item);
        			DrinkItem.ITEM_MAP.put(item.name, item);
        		}
            } 
            catch (Exception e) {
    			e.printStackTrace();
            }
            return null;
		}
     }


    
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null && savedInstanceState
                .containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        mCallbacks.onItemSelected(DrinkItem.ITEMS.get(position).name);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    public void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
}
