package com.bhavdip.coupondunia.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

public class CommonUtil {
	public static AlertDialog alertDialogSpinner;
	static int posi = 0;

	public static void setPreferences(Context act, String key, String value) {
		SharedPreferences settings = act.getSharedPreferences(act.getApplicationInfo().packageName + "_preferences", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);

		// Commit the edits!
		editor.commit();
	}

	public static String getPreferences(Context act, String key, String def) {

		// [[NSUSerDefault standardUserDefault]setValue:firstName.text ForKey:@"keyname"]
		SharedPreferences settings = act.getSharedPreferences(act.getApplicationInfo().packageName + "_preferences", 0);
		return settings.getString(key, def);
	}

	public static void clearPref(Context act) {
		SharedPreferences settings = act.getSharedPreferences(act.getApplicationInfo().packageName + "_preferences", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();

		// Commit the edits!
		editor.commit();
	}

	public static boolean checkNetwork(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else
			return false;

	}

	public static void showValidationAlert(Context context, String alertString) {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle("ADLware");
		alertDialog.setMessage(alertString);
		// Setting Positive "Yes" Btn
		alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to execute after dialog
				dialog.cancel();
			}
		});

		// Showing Alert Dialog
		alertDialog.show();

	}

	public static void showSpinnerPopup(Context context, ListView listViewItems, String string) {
		// TODO Auto-generated method stub
		alertDialogSpinner = new AlertDialog.Builder(context).setView(listViewItems).setTitle(string).setInverseBackgroundForced(true).show();
	}

	public static void cancelSpinnerPopup(Context context) {
		// TODO Auto-generated method stub
		alertDialogSpinner.cancel();
	}

	public static String convertDateTimeToDate(String adlwareDate) {
		// TODO convertDateToString
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
		String newDateString = "";
		if (!adlwareDate.equals("") || !adlwareDate.equals(null)) {
			try {
				Date d = sdf.parse(adlwareDate);
				sdf.applyPattern("MM-dd-yyyy");
				newDateString = sdf.format(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return newDateString;
	}

	public static String convertCalendarDateTimeToDate(String adlwareDate) {
		// TODO convertDateToString
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String newDateString = "";
		if (!adlwareDate.equals("") || !adlwareDate.equals(null)) {
			try {
				Date d = sdf.parse(adlwareDate);
				sdf.applyPattern("MM-dd-yyyy");
				newDateString = sdf.format(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return newDateString;
	}

	public static String convertDateToDateTime(String adlwareDate) {
		// TODO convertDateToString
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String newDateString = "";
		if (!adlwareDate.equals("") || !adlwareDate.equals(null)) {
			try {
				Date d = sdf.parse(adlwareDate);
				sdf.applyPattern("MM-dd-yyyy hh:mm:ss");
				newDateString = sdf.format(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return newDateString;
	}

	/*
	 * public static void setDateFromPicker(Context context, View view) { // TODO Auto-generated method stub DialogFragment newFragment = new DatePickerFragment();
	 * newFragment.show(context.getFragmentManager(), "datePicker");
	 * 
	 * }
	 */

	// public static String getCurrentTime() {
	// SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
	// Date now = new Date();
	// String strTime = sdfDate.format(now);
	// System.out.println(strTime);
	// return strTime;
	// }

	public static String getCurrentDateTime() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");// dd/MM/yyyy
		Date now = new Date();
		String strDateTime = sdfDate.format(now);
		System.out.println(strDateTime);
		return strDateTime;
	}

	/**
	 * Hide keyboard on touch of UI
	 */
	public static void hideKeyboard(View view, final Activity _activity) {

		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				View innerView = ((ViewGroup) view).getChildAt(i);
				hideKeyboard(innerView, _activity);
			}
		}
		if (!(view instanceof EditText)) {
			view.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					hideSoftKeyboard(v, _activity);
					return false;
				}
			});
		}
	}

	/**
	 * Hide keyboard while focus is moved
	 * 
	 * @param _activity
	 */
	public static void hideSoftKeyboard(View view, Activity _activity) {
		if (view != null) {
			InputMethodManager inputManager = (InputMethodManager) _activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (inputManager != null) {
				if (android.os.Build.VERSION.SDK_INT < 11) {
					inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
				} else {
					if (_activity.getCurrentFocus() != null) {
						inputManager.hideSoftInputFromWindow(_activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
					}
					view.clearFocus();
				}
				view.clearFocus();
			}
		}
	}



	public static int convertDiptoPx(Context context, Float dip) {
		Resources r = context.getResources();
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
		return px;
	}

	/*
	 * Set Rounded BitMap Image
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		int targetWidth = 300;
		// bitmap.getWidth();
		int targetHeight = 300;
		Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(targetBitmap);
		Path path = new Path();
		path.addCircle(((float) targetWidth - 1) / 2, ((float) targetHeight - 1) / 2, (Math.min(((float) targetWidth), ((float) targetHeight)) / 2), Path.Direction.CCW);

		canvas.clipPath(path);
		Bitmap sourceBitmap = bitmap;
		canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()), new Rect(0, 0, targetWidth, targetHeight), null);
		return targetBitmap;
	}

	// Padding for time and date stamp if there is a less than 10
	public static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	public static String checkJsonKey(JSONObject jObj, String key) {
		String value = "";
		try {
			if (jObj.has(key)) {
				if (!jObj.isNull(key))
					value = jObj.getString(key);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;

	}
}
