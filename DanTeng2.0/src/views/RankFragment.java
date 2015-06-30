package views;

import com.example.dantengten.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RankFragment extends Fragment{
	
	private String name = "Rank_Fragment";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("RankFragment is onCreate ...");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("RankFragment is onCreateView ...");
		return inflater.inflate(R.layout.rankfrag, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		System.out.println("RankFragment is onResume ...");
	}

	@Override
	public void onStop() {
		super.onStop();
		System.out.println("RankFragment is onStop ...");
	}

	public String getName() {
		return name;
	}

}
