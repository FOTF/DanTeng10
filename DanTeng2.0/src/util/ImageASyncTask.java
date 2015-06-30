package util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import dantengten.BallacheTenMainActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageASyncTask extends AsyncTask<Object, Integer, Bitmap> {

	private ImageView mImageView = null;

	private int _displaywidth;
	private int _displayheight = 400;
	private int _displaypixels;
	
	private int position;
	private Bitmap[] images;

	@Override
	protected void onPreExecute(){
		_displaywidth = (int) BallacheTenMainActivity.width - 40;
		_displaypixels = _displaywidth * _displayheight;
	}
	
	@Override
	protected Bitmap doInBackground(Object... params) {
		String url = (String) params[0];
		mImageView = (ImageView) params[1];
		position = (Integer) params[2];
		images = (Bitmap[]) params[3];
		
		Bitmap bitmap = null;
		try {
			bitmap = getBitmap(url, _displaypixels, true);
		} catch (Exception e) {
			return null;
		}
		return bitmap;
	}

	/**
	 * 通过URL获得网上图片。如:http://www.xxxxxx.com/xx.jpg
	 * */
	public Bitmap getBitmap(String url, int displaypixels, Boolean isBig)
			throws MalformedURLException, IOException {
		Bitmap bmp = null;
		if(getImageExtendName(url).equals("jpg")){
			BitmapFactory.Options opts = new BitmapFactory.Options();
			InputStream stream = new URL(url).openStream();
			byte[] bytes = getBytes(stream);
			// 这3句是处理图片溢出的begin( 如果不需要处理溢出直接 opts.inSampleSize=1;)
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
			opts.inSampleSize = computeSampleSize(opts, -1, displaypixels);
			// end
			opts.inJustDecodeBounds = false;
			bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
		}
		return bmp;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if (mImageView != null && result != null) {
			mImageView.setImageBitmap(result);
			images[position] = result;
			if (null != result && result.isRecycled() == false)
				System.gc();
		}
	}

	/**
	 * 数据流转成btyle[]数组
	 * */
	private byte[] getBytes(InputStream is) {
		try {
			return IOUtils.toByteArray(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		/*ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[2048];
		int len = 0;
		try {
			while ((len = is.read(b, 0, 2048)) != -1) {
				baos.write(b, 0, len);
				baos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = baos.toByteArray();
		return bytes;*/
	}

	/****
	 * 处理图片bitmap size exceeds VM budget （Out Of Memory 内存溢出）
	 */
	private int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 描述： 获取图片的格式 <br/>
	 * 作者： fotf
	 * 
	 * @param imagePath
	 *            文件路径
	 * @return String <br/>
	 */
	public String getImageExtendName(String url) {
		return url.substring(url.lastIndexOf(".") + 1, url.length());
	}
}
