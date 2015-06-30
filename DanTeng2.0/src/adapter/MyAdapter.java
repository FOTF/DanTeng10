package adapter;

import java.util.List;
import java.util.Map;

import util.ImageASyncTask;

import com.example.dantengten.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MyAdapter extends SimpleAdapter{
	
	private Context mContext;
	private Bitmap[] images;// 缓存网络下载的图片
	private String[] titles;
	private List<? extends Map<String, ?>> mData;
	
	private boolean isFreshs[];

	public MyAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.mContext = context;
		images = new Bitmap[11];
		titles = new String[11];
		initFresh();
		mData = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(isFreshs[position] == true || convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.detail, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.mTextView = (TextView) convertView.findViewById(R.id.content_title_id);
			viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.contont_image_id);
			convertView.setTag(viewHolder);
			isFreshs[position] = false;
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Bitmap bitmap = images[position];//从缓存中取图片
		if (bitmap != null) {
			viewHolder.mImageView.setImageBitmap(bitmap);
			viewHolder.mTextView.setText(titles[position]);
		} else {// 缓存没有就设置为默认图片，并且从网络异步下载
			viewHolder.mTextView.setText(mData.get(position).get("title").toString());
			titles[position] = mData.get(position).get("title").toString();
			viewHolder.mImageView.setImageResource(R.drawable.ic_launcher);
			ImageASyncTask ias = new ImageASyncTask();
			ias.execute(mData.get(position).get("imgsrc"), viewHolder.mImageView, position, images);
		}
		return convertView;
	}
	class ViewHolder {
		TextView mTextView;
		ImageView mImageView;
	}
	public void initFresh() {
		isFreshs = new boolean[11];
		for(int i = 0; i < 11; i++){
			isFreshs[i] = true;
		}
	}
	
	public void clearImages(){
		images = new Bitmap[11];
		titles = new String[11];
	}
}
