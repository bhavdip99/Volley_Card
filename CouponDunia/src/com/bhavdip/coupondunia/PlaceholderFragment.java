package com.bhavdip.coupondunia;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhavdip.coupondunia.interfaces.ServiceCallback;
import com.bhavdip.coupondunia.network.CallingAsyncTask;
import com.bhavdip.coupondunia.network.WebService;
import com.bhavdip.coupondunia.util.CommonUtil;
import com.bhavdip.coupondunia.util.ToastUtil;

public class PlaceholderFragment extends Fragment {

	Context context;
	View rootView;
	TextView txt_name;

	public PlaceholderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_main, container, false);
		context = getActivity();
		txt_name = (TextView) rootView.findViewById(R.id.txt_name);

		_webServiceCall();

		return rootView;
	}

	private void _webServiceCall() {

		if (CommonUtil.checkNetwork(context)) {
			new CallingAsyncTask(new ServiceCallback() {

				@Override
				public void onDoneTask(String result) {
					if (result != null) {
						try {
							JSONObject jsonObject = new JSONObject(result);

							System.out.println("JSON:" + result);
							JSONObject statusJson = new JSONObject(jsonObject.getString("status"));
							if (statusJson.getInt("rcode") == 200) {
								JSONObject dataJson = new JSONObject(jsonObject.getString("data"));

								System.out.println("dataJson" + dataJson.toString());
								System.out.println("dataJson Length" + dataJson.length());
								for (Iterator<String> iter = dataJson.keys(); iter.hasNext();) {
									String key = iter.next();
									if (dataJson.has(key)) {

										JSONObject subDataJson = new JSONObject(dataJson.getString(key));
										System.out.println("\nOutletName: " + subDataJson.getString("OutletName"));
										System.out.println("\nBrandName: " + subDataJson.getString("BrandName"));

									}
								}
								/*
								 * for (int i = 0; i < dataJson.length(); i++) {
								 * 
								 * 
								 * 
								 * txt_name.setText(strBuffer); }
								 */

								// userParser.getUserParsedData(authenticateJson);

								// Intent intent = new Intent(LoginActivity.this, DashBoard.class);
								// startActivity(intent);
							} else {
								// CommonUtil.showValidationAlert(context, authenticateJson.getString(getResources().getString(R.string.error)));
								// edtUsername.requestFocus();
								// edtUsername.setText("");
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} else {
						ToastUtil.ToastShort(context, "Server Timed out");
					}
				}
			}, context).execute(WebService.WEB_SERVICE_URL);

		} else {
			ToastUtil.ToastShort(context, "Network not available!");
		}

	}
}