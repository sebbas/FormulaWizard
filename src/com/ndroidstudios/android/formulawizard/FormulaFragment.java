package com.ndroidstudios.android.formulawizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class FormulaFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.menu_main, container, false);
		
		// Set up area button and set on-click listener
        rootView.findViewById(R.id.area)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), AreaChooser.class);
                        startActivity(intent);
                    }
                });
        
        // Set up volume button and set on-click listener
        rootView.findViewById(R.id.volume)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), VolumeChooser.class);
                        startActivity(intent);
                    }
                });
            
        // Set up quadratic button and set on-click listener
        rootView.findViewById(R.id.quadratic)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), QuadraticCalculator.class);
                        startActivity(intent);
                    }
                });
            
        // Set up length/ distance button and set on-click listener
        rootView.findViewById(R.id.length)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), DistanceCalculator.class);
                        startActivity(intent);
                    }
                });
            
        // Set up binomial button and set on-click listener
        rootView.findViewById(R.id.binomial)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), BinomialChooser.class);
                        startActivity(intent);
                    }
                });
            
        // Set up perimeter button and set on-click listener
        rootView.findViewById(R.id.perimeter)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), PerimeterChooser.class);
                        startActivity(intent);
                    }
                });
            
        // Set up slope button and set on-click listener
        rootView.findViewById(R.id.slope)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SlopeCalculator.class);
                        startActivity(intent);
                    }
                });
            
        // Set up pythagoras button and set on-click listener
        rootView.findViewById(R.id.pythagoras)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), PythagoreanTheorem.class);
                        startActivity(intent);
                    }
                });
            
        // Set up midpoint button and set on-click listener
        rootView.findViewById(R.id.midpoint)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), MidpointCalculator.class);
                        startActivity(intent);
                    }
                });
            
        
		return rootView;
	}
	
}
