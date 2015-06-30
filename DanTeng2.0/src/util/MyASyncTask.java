package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adapter.MyAdapter;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import entity.ButtonInfor;
import entity.ContentInfor;

public class MyASyncTask extends AsyncTask<Object, Integer, List<ContentInfor>> {

	private static final String regTitleAndImageSrcStr = "<div class=\"entry rich-content\"><p>(.*?)<img src=\"(.*?)\">";

	private static final String mainURL = "http://www.10danteng.com";

	private static final String prexAndNextSrcStr = "<div class=\"qianhou\">(.*?)</div><div class=\"posts\">";

	List<ContentInfor> details = new ArrayList<ContentInfor>();

	private MyAdapter adapter;
	private List<Map<String, Object>> listItems;
	private ProgressDialog pd;
	private ButtonInfor bi;
	private int state;

	@Override
	protected void onPreExecute(){
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected List<ContentInfor> doInBackground(Object... params) {
		StringBuffer sb = new StringBuffer("");
		adapter = (MyAdapter) params[0];
		listItems = (List<Map<String, Object>>) params[1];
		pd = (ProgressDialog) params[2];
		bi = (ButtonInfor) params[3];
		state = (Integer) params[4];
		String loadUrl = mainURL;

		if (state == 0) {
			bi.setThizBtn(loadUrl);
		} else if (state == 1) {
			loadUrl = bi.getNextBtn();
		} else if (state == -1) {
			loadUrl = bi.getPrexBtn();
		} else if (state == 10) {
			loadUrl = bi.getThizBtn();
		}
		
		try {
			InputStream is = null;
			BufferedReader in = null;
			HttpURLConnection conn = null;
			try {
				URL url = new URL(loadUrl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				conn.setConnectTimeout(6000);
				conn.connect();
				conn.setReadTimeout(60000);
				String temp;
				is = conn.getInputStream();
				in = new BufferedReader(new InputStreamReader(is, "utf-8"));

				while ((temp = in.readLine()) != null) {
					sb.append("\n");
					sb.append(temp);
				}
			} catch (Exception ee) {
				System.out.print("ee:" + ee.getMessage());
			} finally {
				in.close();
				is.close();
				conn.disconnect();
			}

			Pattern patternB = Pattern.compile(prexAndNextSrcStr);
			Matcher matcherB = patternB.matcher(sb);
			while (matcherB.find()) {
				String str = matcherB.group();
				String prexSrc = str.substring(str.indexOf("<a "),
						str.lastIndexOf("<a "));
				bi.setPrexBtn(mainURL + getHref(prexSrc));
				String nextSrc = str.substring(str.lastIndexOf("<a "),
						str.lastIndexOf("</a>"));
				bi.setNextBtn(mainURL + getHref(nextSrc));
			}
			Pattern pattern = Pattern.compile(regTitleAndImageSrcStr);
			Matcher matcher = pattern.matcher(sb);
			while (matcher.find()) {
				String str = matcher.group();
				String strTitle = str.substring(str.indexOf("<p>") + 3,
						str.indexOf("</p>"));
				String strImageSrc = str.substring(str.indexOf("http"),
						str.indexOf("\" alt="));
				ContentInfor ci = new ContentInfor(strTitle, strImageSrc);
				details.add(ci);
			}
		} catch (Exception ee) {
			System.out.print("ee:" + ee.getMessage());
		}
		return details;
	}

	public static String getHref(String str) {
		if (str.contains("href")) {
			return str.substring(str.indexOf("href=\"") + 6,
					str.lastIndexOf("\" style"));
		}
		return "";
	}

	@Override
	protected void onPostExecute(List<ContentInfor> result) {
		clear();
		for (int i = 0, j = result.size(); i < j; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();  
            listItem.put("title", result.get(i).getTitle());
            listItem.put("imgsrc", result.get(i).getImgSrc());
            listItems.add(listItem);
		}
		adapter.notifyDataSetChanged();
		pd.dismiss();
	}

	public void clear() {
		if (listItems != null && listItems.size() > 0) {
			listItems.clear();
			adapter.clearImages();
			adapter.initFresh();
		}
	}
}
