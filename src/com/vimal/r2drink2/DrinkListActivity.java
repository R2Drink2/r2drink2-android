package com.vimal.r2drink2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;

public class DrinkListActivity extends FragmentActivity
        implements DrinkListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_list);

        if (findViewById(R.id.drink_detail_container) != null) {
            mTwoPane = true;
            ((DrinkListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.drink_list))
                    .setActivateOnItemClick(true);
        }        
    }

    //@Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(DrinkDetailFragment.ARG_ITEM_ID, id);
            DrinkDetailFragment fragment = new DrinkDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.drink_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, DrinkDetailActivity.class);
            detailIntent.putExtra(DrinkDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
