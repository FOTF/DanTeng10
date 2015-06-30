package views;

import com.example.dantengten.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PostFragment extends Fragment{
	
	private String name = "Post_Fragment";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("PostFragment is onCreate ...");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("PostFragment is onCreateView ...");
		return inflater.inflate(R.layout.postfrag, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		System.out.println("PostFragment is onResume ...");
	}

	@Override
	public void onStop() {
		super.onStop();
		System.out.println("PostFragment is onStop ...");
	}

	public String getName() {
		return name;
	}

}
