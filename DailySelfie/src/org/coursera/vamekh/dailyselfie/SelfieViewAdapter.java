package org.coursera.vamekh.dailyselfie;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelfieViewAdapter extends BaseAdapter {

	private ArrayList<Selfie> selfieList = new ArrayList<Selfie>();
	private static LayoutInflater inflater = null;
	private Context mContext;
	
	public SelfieViewAdapter(Context context){
		mContext = context;
		inflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		return selfieList.size();
	}

	@Override
	public Object getItem(int position) {
		return selfieList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View newView = convertView;
		ViewHolder holder;

		Selfie curr = selfieList.get(position);

		if (null == convertView) {
			holder = new ViewHolder();
			newView = inflater
					.inflate(R.layout.selfie_view, parent, false);
			holder.selfieImage = (ImageView) newView.findViewById(R.id.selfie_image);
			holder.selfieText = (TextView) newView.findViewById(R.id.selfie_text);
			newView.setTag(holder);

		} else {
			holder = (ViewHolder) newView.getTag();
		}

		holder.selfieImage.setImageBitmap(curr.getSelfieBitmap());
		holder.selfieText.setText(curr.getFilename());

		return newView;
	}
	
	public void add(Selfie newSelfie){
		selfieList.add(newSelfie);
		notifyDataSetChanged();
	}
	
	public ArrayList<Selfie> getList(){
		return selfieList;
	}
	
	public void removeAllViews() {
		selfieList.clear();
		this.notifyDataSetChanged();
	}
	
	static class ViewHolder{
		ImageView selfieImage;
		TextView selfieText; 
	}

}
