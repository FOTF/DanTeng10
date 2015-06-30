package views;

import util.ImageASyncTask;

import com.example.dantengten.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class DetailFragment extends Fragment{
	
	private TextView mTextView;
	private ImageView mImageView;
	
	private String title;
	private String imgSrc;
	
	public DetailFragment() {
		super();
	}

	public DetailFragment(String title, String imgSrc) {
		super();
		this.title = title;
		this.imgSrc = imgSrc;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("DetailFragment is on create ...");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("DetailFragment is before onCreateView ...");
		View view = inflater.inflate(R.layout.detail, container, false);
		mTextView = (TextView) view.findViewById(R.id.content_title_id);
		mImageView = (ImageView) view.findViewById(R.id.contont_image_id);
		setTitle(title);
		ImageASyncTask ias = new ImageASyncTask();
		ias.execute(imgSrc, mImageView);
		return view;
	}
	
	public void setTitle(String title){
		this.mTextView.setText(title);
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		System.out.println("HomeFragment is onResume ...");
	}

	@Override
	public void onStop() {
		super.onStop();
		System.out.println("HomeFragment is onStop ...");
	}

}
