package com.vimal.r2drink2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DrinkItem 
{
    public static List<DrinkItem> ITEMS = new ArrayList<DrinkItem>();
    public static Map<String, DrinkItem> ITEM_MAP = new HashMap<String, DrinkItem>();


    public String name;
    public int quantity;
    public int[] ratios = new int[8];
    public String[] ingredients = new String[8];
    public JSONObject drink;
    
    public DrinkItem(JSONObject drink, JSONArray ingredients) {
        try 
        {
        	this.drink = drink;
			this.name = drink.getString("name");
			this.quantity = (int)(drink.getDouble("qty")*2.0);
			JSONObject d_ingredients = drink.getJSONObject("ingredients");
			for(int i=0; i<8; i++)
			{
				this.ingredients[i] = ingredients.getString(i);
				this.ratios[i] = 0;
				if(d_ingredients.has(this.ingredients[i]))
				{
					this.ratios[i] = (int)(d_ingredients.getDouble(this.ingredients[i])*2.0);
				}				
			}
			
		} 
        catch (JSONException e) {
			e.printStackTrace();
		}
    }

    @Override
    public String toString() {
        return name;
    }
}