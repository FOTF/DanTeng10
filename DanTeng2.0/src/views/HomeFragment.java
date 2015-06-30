package views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.MyASyncTask;

import com.example.dantengten.R;

import entity.ButtonInfor;
import adapter.MyAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class HomeFragment extends ListFragment implements OnClickListener{
	
	private String name = "Home_Fragment";
	
	private Button mButton;
	private Button prexButton;
	private Button nextButton;
	
//	private ListView mListView;
	private SimpleAdapter adapter;
	
	List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
	
	private View view;
	
	private Context context;
	private MyASyncTask yncTask;
	
	private ProgressDialog pd;
	private ButtonInfor bi = new ButtonInfor();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("HomeFragment is on create ...");
		
		adapter = new MyAdapter(getActivity(), listItems,  
                R.layout.detail, new String[] { "title", "imgsrc" },  
                new int[] { R.id.content_title_id, R.id.contont_image_id });
		/*adapter.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if(view instanceof ImageView && data instanceof String){
					ImageView imgView = (ImageView)view;
					ImageASyncTask ias = new ImageASyncTask();
					ias.execute(data, imgView);
					return true;
				}
				return false;
			}
		});*/
        setListAdapter(adapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("HomeFragment is onCreateView ...");
		view = inflater.inflate(R.layout.homefrag, container, false);
		mButton= (Button) view.findViewById(R.id.button_click);
		prexButton= (Button) view.findViewById(R.id.button_prex);
		nextButton= (Button) view.findViewById(R.id.button_next);
//		mListView = (ListView) view.findViewById(android.R.id.list);
		
		mButton.setOnClickListener(this);
		prexButton.setOnClickListener(this);
		nextButton.setOnClickListener(this);
		
		
		context = view.getContext();
		pd = ProgressDialog.show(context, "", "内容加载中", true, true);
		yncTask = new MyASyncTask();
		yncTask.execute(adapter, listItems, pd, bi, 0);
		return view;
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

	public String getName() {
		return name;
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
			case R.id.button_click : 
				
				pd = ProgressDialog.show(context, "", "内容加载中", true, true);
				yncTask = new MyASyncTask();
				yncTask.execute(adapter, listItems, pd, bi, 10);
				break;
			case R.id.button_next : 
				pd = ProgressDialog.show(context, "", "内容加载中", true, true);
				yncTask = new MyASyncTask();
				yncTask.execute(adapter, listItems, pd, bi, 1);
				break;
			case R.id.button_prex : 
				
				pd = ProgressDialog.show(context, "", "内容加载中", true, true);
				yncTask = new MyASyncTask();
				yncTask.execute(adapter, listItems, pd, bi, -1);
				break;
		}
	}
}
