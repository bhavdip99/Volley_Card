package com.bhavdip.coupondunia;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bhavdip.coupondunia.model.DataModel;
import com.bhavdip.coupondunia.util.GsonRequest;
import com.bhavdip.coupondunia.util.ToastUtil;

public class CouponDuniaFragment extends Fragment {

	Context context;
	View rootView;
	TextView txt_name;
	private ProgressDialog mProgress;
	
	private final String TAG_REQUEST = "MY_TAG";
	
	GsonRequest<DataModel> gsonObjRequest;
	private RequestQueue mVolleyQueue;

	public CouponDuniaFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_main, container, false);
		context = getActivity();
		txt_name = (TextView) rootView.findViewById(R.id.txt_name);

		// Initialise Volley Request Queue. 
		mVolleyQueue = Volley.newRequestQueue(context);
		
		_webServiceCall();

		return rootView;
	}

	private void _webServiceCall() {
		showProgress();

		String url = "staging.couponapitest.com/task_data.txt";
		Uri.Builder builder = Uri.parse(url).buildUpon();

		gsonObjRequest = new GsonRequest<DataModel>(Request.Method.GET, builder.toString(), DataModel.class, null,
				new Response.Listener<DataModel>() {
					@Override
					public void onResponse(DataModel response) {
						try {

							// parseFlickrImageResponse(response);
							// mAdapter.notifyDataSetChanged();
						} catch (Exception e) {
							e.printStackTrace();
							ToastUtil.ToastShort(context, "JSON parse error");
						}
						stopProgress();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError err) {
						// TODO Auto-generated method stub
						if (err instanceof NetworkError) {
						} else if (err instanceof ClientError) {
						} else if (err instanceof ServerError) {
						} else if (err instanceof AuthFailureError) {
						} else if (err instanceof ParseError) {
						} else if (err instanceof NoConnectionError) {
						} else if (err instanceof TimeoutError) {
						}
					}
				});
		gsonObjRequest.setTag(TAG_REQUEST);
		mVolleyQueue.add(gsonObjRequest);
	}

	private void showProgress() {
		mProgress = ProgressDialog.show(context, "", "Loading...");
	}

	private void stopProgress() {
		mProgress.cancel();
	}

}