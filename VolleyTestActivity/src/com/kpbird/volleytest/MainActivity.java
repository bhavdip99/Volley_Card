package com.kpbird.volleytest;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {

	private String TAG = this.getClass().getSimpleName();
	private ListView lstView;
	private RequestQueue mRequestQueue;
	private ArrayList<NewsModel> arrNews;
	private LayoutInflater lf;
	private VolleyAdapter va;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lf = LayoutInflater.from(this);

		arrNews = new ArrayList<NewsModel>();
		va = new VolleyAdapter();

		lstView = (ListView) findViewById(R.id.listView);
		lstView.setAdapter(va);
		mRequestQueue = Volley.newRequestQueue(this);
		String url = "http://staging.couponapitest.com/task_data.txt";
		pd = ProgressDialog.show(this, "Please Wait...", "Please Wait...");

		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.i(TAG, response.toString());
				parseJSON(response);
				va.notifyDataSetChanged();
				pd.dismiss();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.i(TAG, error.getMessage());
			}
		});
		mRequestQueue.add(jr);

	}

	private void parseJSON(JSONObject json) {
		try {
			JSONObject data = json.getJSONObject("data");
			for (Iterator<String> iter = data.keys(); iter.hasNext();) {
				String key = iter.next();
				if (data.has(key)) {
					System.out.println("key : " + key);
					JSONObject keyJobj = data.getJSONObject(key);

					NewsModel nm = new NewsModel();
					nm.setTitle(keyJobj.optString("OutletName"));
					nm.setDescription(keyJobj.optString("Address"));
					nm.setLink(keyJobj.optString("PhoneNumber"));
					nm.setPubDate(keyJobj.optString("CityName"));
					arrNews.add(nm);

					JSONArray Categories = keyJobj.getJSONArray("Categories");
					for (int i = 0; i < Categories.length(); i++) {

						JSONObject item = Categories.getJSONObject(i);
						// NewsModel nm = new NewsModel();
						// nm.setTitle(item.optString("Name"));
						// nm.setDescription(item.optString("CategoryType"));
						// nm.setLink(item.optString("ParentCategoryID"));
						// nm.setPubDate(item.optString("OfflineCategoryID"));
						// arrNews.add(nm);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	class NewsModel {
		private String title;
		private String link;
		private String description;
		private String pubDate;

		void setTitle(String title) {
			this.title = title;
		}

		void setLink(String link) {
			this.link = link;
		}

		void setDescription(String description) {
			this.description = description;
		}

		void setPubDate(String pubDate) {
			this.pubDate = pubDate;
		}

		String getLink() {
			return link;
		}

		String getDescription() {
			return description;
		}

		String getPubDate() {
			return pubDate;
		}

		String getTitle() {

			return title;
		}
	}

	class VolleyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return arrNews.size();
		}

		@Override
		public Object getItem(int i) {
			return arrNews.get(i);
		}

		@Override
		public long getItemId(int i) {
			return 0;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			ViewHolder vh;
			if (view == null) {
				vh = new ViewHolder();
				view = lf.inflate(R.layout.row_listview, null);
				vh.tvTitle = (TextView) view.findViewById(R.id.txtTitle);
				vh.tvDesc = (TextView) view.findViewById(R.id.txtDesc);
				vh.tvDate = (TextView) view.findViewById(R.id.txtDate);
				vh.imgLogo = (ImageView) view.findViewById(R.id.imgLogo);
				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			}

			NewsModel nm = arrNews.get(i);
			vh.tvTitle.setText(nm.getTitle());
			vh.tvDesc.setText(nm.getDescription());
			vh.tvDate.setText(nm.getPubDate());
			// vh.imgLogo.setImageBitmap(nm.getPubDate());
			return view;
		}

		class ViewHolder {
			TextView tvTitle;
			ImageView imgLogo;
			TextView tvDesc;
			TextView tvDate;

		}

	}
}
