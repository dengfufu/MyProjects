package com.dengfufu.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengfufu.ahaweather.HomepageActivity;
import com.dengfufu.ahaweather.R;
import com.dengfufu.entity.Index;
import com.dengfufu.entity.Results;

public class LifeIndexFragment extends Fragment {
	
	GridView gridView ;
	TextView describe;
	HomepageActivity activity;
	ArrayList<Index> index;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.centerfragment_lifeindex, null);
		gridView = (GridView) view.findViewById(R.id.gridview);
		describe = (TextView) view.findViewById(R.id.describe);
		
		Results results = activity.getResultWrapper().getResults().get(0);
		index = results.getIndex();
		
		gridView.setAdapter(new BaseAdapter(){

			@Override
			public int getCount() {
				
				return index.size();
			}

			@Override
			public Object getItem(int position) {
				
				return index.get(position);
			}

			@Override
			public long getItemId(int position) {
				
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView = View.inflate(getActivity(), R.layout.item_gridview, null);
				
				ImageView picture = (ImageView) convertView.findViewById(R.id.picture);
				TextView tipt = (TextView) convertView.findViewById(R.id.tipt);
				TextView zs = (TextView) convertView.findViewById(R.id.zs);
				ImageView arrow = (ImageView) convertView.findViewById(R.id.arrow);
				
				int[] pictureArrays = {R.drawable.dress,R.drawable.washcar,R.drawable.cold,
						R.drawable.sport,R.drawable.ziwaixian};
				picture.setBackgroundResource(pictureArrays[position]);
				if(position == 4) tipt.setText("紫外线指数");
				else tipt.setText(index.get(position).getTipt());
				zs.setText(index.get(position).getZs());
				arrow.setBackgroundResource(R.drawable.jiantou);
				
				return convertView;
			}
			
		});
		
		describe.setText(index.get(0).getDes());
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				describe.setText(index.get(position).getDes());
			}
			
		});
		
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		this.activity = (HomepageActivity) activity;
	}
}
