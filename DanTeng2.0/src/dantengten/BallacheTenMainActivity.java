package dantengten;

import views.HomeFragment;
import views.PostFragment;
import views.RankFragment;
import views.SubmitFragment;

import com.example.dantengten.R;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class BallacheTenMainActivity extends FragmentActivity implements
		OnCheckedChangeListener {

	private RadioGroup mainGroup;

	private HomeFragment mHomeFragment;
	private PostFragment mPostFragment;
	private RankFragment mRankFragment;
	private SubmitFragment mSubmitFragment;

	private Fragment thizFragment;

	private FragmentManager fragmentManager;
	
	public static float width;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);

		initFragment();
		mHomeFragment = (HomeFragment) showFrag(mHomeFragment,
				HomeFragment.class);
	}

	public void initFragment() {
		mainGroup = (RadioGroup) this.findViewById(R.id.main_group);
		mainGroup.setOnCheckedChangeListener(this);
		fragmentManager = getSupportFragmentManager();
		DisplayMetrics dm = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        width = dm.widthPixels * dm.density;   
	}

	public Fragment showFrag(Fragment frag, Class<?> cla) {
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		if (thizFragment == null || thizFragment != frag) {
			if (frag == null) {
				try {
					frag = (Fragment) cla.newInstance();
				} catch (InstantiationException e) {
					return null;
				} catch (IllegalAccessException e) {
					return null;
				}
				fragmentTransaction.add(R.id.main_container, frag);
			}
			if (thizFragment != null) {
				fragmentTransaction.hide(thizFragment);
			}
			fragmentTransaction.show(frag);
			thizFragment = frag;
		}
		fragmentTransaction.commit();
		return frag;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.tab_main_home:
			mHomeFragment = (HomeFragment) showFrag(mHomeFragment,
					HomeFragment.class);
			break;
		case R.id.tab_main_post:
			mPostFragment = (PostFragment) showFrag(mPostFragment,
					PostFragment.class);
			break;
		case R.id.tab_mian_rank:
			mRankFragment = (RankFragment) showFrag(mRankFragment,
					RankFragment.class);
			break;
		case R.id.tab_main_submit:
			mSubmitFragment = (SubmitFragment) showFrag(mSubmitFragment,
					SubmitFragment.class);
			break;
		case R.id.tab_main_more:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Builder builder = new Builder(BallacheTenMainActivity.this);
			builder.setTitle("提示");
			builder.setMessage("确定要退出吗");
			builder.setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.exit(0);
				}
			});
			builder.setNegativeButton("取消", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			builder.create();
			builder.show();
		}
		return false;
	}
}
