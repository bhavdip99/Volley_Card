package com.kpbird.volleytest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.FadeInImageListener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kpbird.volleytest.model.CategoryModel;
import com.kpbird.volleytest.model.DataModel;
import com.mani.volleydemo.util.BitmapUtil;

public class MainActivity extends Activity implements LocationListener {

	Context context;
	private String TAG = this.getClass().getSimpleName();
	private ListView lstView;
	private RequestQueue mRequestQueue;
	private ArrayList<DataModel> dataList;
	private VolleyAdapter va;
	private ProgressDialog pd;
	private ImageLoader mImageLoader;

	protected double longitude, latitude;
	protected LocationManager locationManager;

	/*
	 * Extends from DisckBasedCache --> Utility from volley toolbox. Also implements ImageCache, so that we can pass this custom implementation to
	 * ImageLoader.
	 */
	public class DiskBitmapCache extends DiskBasedCache implements ImageCache {

		public DiskBitmapCache(File rootDirectory, int maxCacheSizeInBytes) {
			super(rootDirectory, maxCacheSizeInBytes);
		}

		public DiskBitmapCache(File cacheDir) {
			super(cacheDir);
		}

		public Bitmap getBitmap(String url) {
			final Entry requestedItem = get(url);

			if (requestedItem == null)
				return null;

			return BitmapFactory.decodeByteArray(requestedItem.data, 0, requestedItem.data.length);
		}

		public void putBitmap(String url, Bitmap bitmap) {

			final Entry entry = new Entry();

			/*
			 * //Down size the bitmap.If not done, OutofMemoryError occurs while decoding large bitmaps. // If w & h is set during image request (
			 * using ImageLoader ) then this is not required. ByteArrayOutputStream baos = new ByteArrayOutputStream(); Bitmap downSized =
			 * BitmapUtil.downSizeBitmap(bitmap, 50);
			 * 
			 * downSized.compress(Bitmap.CompressFormat.JPEG, 100, baos); byte[] data = baos.toByteArray();
			 * 
			 * System.out.println("####### Size of bitmap is ######### "+url+" : "+data.length); entry.data = data ;
			 */

			entry.data = BitmapUtil.convertBitmapToBytes(bitmap);
			put(url, entry);
		}
	}

	public class MyComparatorGlobal implements Comparator<DataModel> {

		@Override
		public int compare(DataModel lhs, DataModel rhs) {
			return lhs.getOutletName().compareToIgnoreCase(rhs.getOutletName());
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = this;

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

		dataList = new ArrayList<DataModel>();
		va = new VolleyAdapter(this);

		showToast("1");
		lstView = (ListView) findViewById(R.id.listView);

	}

	private void showToast(String msg) {
		Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
	}

	private void parseJSON(JSONObject json) throws JSONException {

		if (json.has("data")) {
			try {
				JSONObject data = json.getJSONObject("data");
				for (Iterator<String> iter = data.keys(); iter.hasNext();) {
					String key = iter.next();
					if (data.has(key)) {
						System.out.println("key : " + key);
						JSONObject keyJobj = data.getJSONObject(key);

						DataModel dataModel = new DataModel();

						dataModel.setSubFranchiseID(keyJobj.optString("SubFranchiseID"));
						dataModel.setOutletID(keyJobj.optString("OutletID"));
						dataModel.setOutletName(keyJobj.optString("OutletName"));
						dataModel.setBrandID(keyJobj.optString("BrandID"));
						dataModel.setAddress(keyJobj.optString("Address"));

						dataModel.setNeighbourhoodID(keyJobj.optString("NeighbourhoodID"));
						dataModel.setCityID(keyJobj.optString("CityID"));
						dataModel.setEmail(keyJobj.optString("Email"));
						dataModel.setTimings(keyJobj.optString("Timings"));

						dataModel.setCityRank(keyJobj.optString("CityRank"));
						dataModel.setLatitude(keyJobj.optString("Latitude"));
						dataModel.setLongitude(keyJobj.optString("Longitude"));
						dataModel.setPincode(keyJobj.optString("Pincode"));
						dataModel.setLandmark(keyJobj.optString("Landmark"));
						dataModel.setStreetname(keyJobj.optString("Streetname"));

						dataModel.setBrandName(keyJobj.optString("BrandName"));
						dataModel.setOutletURL(keyJobj.optString("OutletURL"));
						dataModel.setNumCoupons(keyJobj.optInt("NumCoupons"));
						dataModel.setNeighbourhoodName(keyJobj.optString("NeighbourhoodName"));
						dataModel.setPhoneNumber(keyJobj.optString("PhoneNumber"));

						dataModel.setCityName(keyJobj.optString("CityName"));
						dataModel.setDistance(keyJobj.optLong("Distance"));
						dataModel.setLogoURL(keyJobj.optString("LogoURL"));
						dataModel.setCoverURL(keyJobj.optString("CoverURL"));

						if (keyJobj.has("Categories")) {
							if (!keyJobj.isNull("Categories")) {
								JSONArray cat_Array = keyJobj.getJSONArray("Categories");

								ArrayList<CategoryModel> categoryList = new ArrayList<CategoryModel>();

								for (int k = 0; k < cat_Array.length(); k++) {
									CategoryModel categoryModel = new CategoryModel();
									JSONObject catJobj = cat_Array.getJSONObject(k);

									categoryModel.setCategoryType(catJobj.getString("CategoryType"));
									categoryModel.setName(catJobj.getString("Name"));
									categoryModel.setOfflineCategoryID(catJobj.getString("OfflineCategoryID"));
									categoryModel.setParentCategoryID(catJobj.getString("ParentCategoryID"));

									categoryList.add(categoryModel);
								}
								dataModel.setCategory(categoryList);
							}
						}
						dataList.add(dataModel);
					}
				}
				fileStore();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		showToast("4");
		showToast("lat:" + latitude + "\n" + "Long:" + longitude);
		Collections.sort(dataList, new MyComparatorGlobal());
	}

	private void fileStore() {
		// TODO Auto-generated method stub
		File yFile = new File(context.getFilesDir() + "/ReelApp_Video");

		if (!yFile.exists()) {
			yFile.mkdirs();
		}

		String fileName = "your file name";
		try {
			if (fileName != null && fileName.length() > 0) {

				Log.i("TAG", "Video download Filename: " + fileName);
			}
			file = new File(yFile.getAbsolutePath() + File.separator + Common.getTimeStamp() + "VIDD" + fileName);

			// getting file length
			int lenghtOfFile = ucon.getContentLength();
			long total = 0;

		} catch (Exception e) {
			e.printStackTrace();
			if (file.exists()) {
				file.delete();
			}
		}
	}

	class VolleyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public VolleyAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int i) {
			return dataList.get(i);
		}

		@Override
		public long getItemId(int i) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup viewGroup) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.row_listview, null);

				holder = new ViewHolder();
				holder.txtOutletName = (TextView) convertView.findViewById(R.id.txt_outletName);
				holder.txtOffers = (TextView) convertView.findViewById(R.id.txt_offers);
				holder.txtCategory = (TextView) convertView.findViewById(R.id.txt_category);
				holder.txtDistance = (TextView) convertView.findViewById(R.id.txt_distance);
				holder.imgLogo = (ImageView) convertView.findViewById(R.id.imgLogo);
				holder.imgWishHeart = (ImageView) convertView.findViewById(R.id.img_wishHeart);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			DataModel dataModel = dataList.get(position);
			holder.txtOutletName.setText(dataModel.getOutletName());
			holder.txtDistance.setText(String.valueOf(dataModel.getDistance()) + " m " + dataModel.getNeighbourhoodName());

			holder.txtOffers.setText(String.valueOf(dataModel.getNumCoupons()));
			// vh.txtOffers.setText(dataModel.getNumCoupons() == 1 ? String.valueOf(dataModel.getNumCoupons()) + " Offer" : String.valueOf(dataModel
			// .getNumCoupons()) + " Offers"); // same but converted int to String
			holder.txtOffers.setText(dataModel.getNumCoupons() == 1 ? dataModel.getNumCoupons() + " Offer" : dataModel.getNumCoupons() + " Offers");

			mImageLoader.get(dataList.get(position).getLogoURL(), new FadeInImageListener(holder.imgLogo, MainActivity.this));

			return convertView;
		}

		class ViewHolder {
			TextView txtOutletName;
			TextView txtOffers;
			TextView txtCategory;
			TextView txtDistance;

			ImageView imgLogo;
			ImageView imgWishHeart;
		}

	}

	@Override
	public void onLocationChanged(Location location) {
		// 21.1177565, 79.046385 //room
		// txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

		lstView.setAdapter(va);
		mRequestQueue = Volley.newRequestQueue(context);

		int max_cache_size = 1000000;
		mImageLoader = new ImageLoader(mRequestQueue, new DiskBitmapCache(getCacheDir(), max_cache_size));
		showToast("2");
		String url = "http://staging.couponapitest.com/task_data.txt";
		pd = ProgressDialog.show(context, "Please Wait...", "Please Wait...");

		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					showToast("3");
					Log.i(TAG, response.toString());
					parseJSON(response);
					va.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
					showToast("JSON parse error");
				}
				pd.dismiss();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.i(TAG, error.getMessage());
			}
		});
		mRequestQueue.add(jr);
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		showToast("5");
		showToast("123 lat:" + latitude + "\n" + "Long:" + longitude);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}
}
