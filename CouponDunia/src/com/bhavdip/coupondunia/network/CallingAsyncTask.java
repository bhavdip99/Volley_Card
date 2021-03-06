package com.bhavdip.coupondunia.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bhavdip.coupondunia.interfaces.ServiceCallback;

public class CallingAsyncTask extends AsyncTask<String, Void, String> {
	
	private Context mContext;
	ProgressDialog progressDialog;
	private ServiceCallback mFragmentcallBack;
	

	public CallingAsyncTask(ServiceCallback fragmentCallback, Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mFragmentcallBack = fragmentCallback;
	}

	@Override
	protected String doInBackground(String... webServiceURL) {
		ServiceHandler httpServiceHandler = new ServiceHandler();

		// Making a request to url and getting response
		String jsonStr = httpServiceHandler.makeServiceCall(webServiceURL[0],ServiceHandler.POST);

		Log.d("Response: ", "> " + jsonStr);
		// TODO Auto-generated method stub
		return jsonStr;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (progressDialog.isShowing())
				progressDialog.dismiss();
		/*try {
	        if ((this.progressDialog != null) && this.progressDialog.isShowing()) {
	            this.progressDialog.dismiss();
	        }
	    } catch (final IllegalArgumentException e) {
	        // Handle or log or ignore
	    } catch (final Exception e) {
	        // Handle or log or ignore
	    } finally {
	        this.progressDialog = null;
	    }  */
		mFragmentcallBack.onDoneTask(result);
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog = new ProgressDialog(mContext);
		progressDialog.setMessage("Please wait...");
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

}