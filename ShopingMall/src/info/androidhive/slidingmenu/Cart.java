package info.androidhive.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Cart extends Activity {

	private List<CartData> myCars = new ArrayList<CartData>();
	Context context;
	ListView lv;
	ArrayAdapter<CartData> adapter;
	DBHelper mydb;
	public static final String KEY_ITEM_NAME = "name";
	public static final String KEY_ITEM_PRICE = "price";
	public static final String KEY_ITEM_IMAGE = "image";
	public static final String KEY_ITEM_DETAIL = "detail";

	int total = 0;
	TextView tvtotalwithtax;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart);
		context = this;
		initialize();

		Load_from_DB();

		lv.setOnTouchListener(new ListView.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					// Disallow ScrollView to intercept touch events.
					v.getParent().requestDisallowInterceptTouchEvent(true);
					break;

				case MotionEvent.ACTION_UP:
					// Allow ScrollView to intercept touch events.
					v.getParent().requestDisallowInterceptTouchEvent(false);
					break;
				}

				// Handle ListView touch events.
				v.onTouchEvent(event);
				return true;
			}
		});

	}

	private void initialize() {
		mydb = new DBHelper(context);
		mydb.open();

		tvtotalwithtax = (TextView) findViewById(R.id.tvprivewithtex);
		lv = (ListView) findViewById(R.id.listcart);
		adapter = new myListAdapter();
		lv.setAdapter(adapter);
	}

	private void Load_from_DB() {

		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
		contactList = mydb.get_all_Data_cart();

		for (int k = 0; k < contactList.size(); k++) {

			HashMap<String, String> dbbasket = new HashMap<String, String>();
			dbbasket = contactList.get(k);
			myCars.add(new CartData(dbbasket.get(KEY_ITEM_NAME), dbbasket.get(KEY_ITEM_DETAIL), Integer.parseInt(dbbasket.get(KEY_ITEM_IMAGE)),
					Integer.parseInt(dbbasket.get(KEY_ITEM_PRICE))));
			total += Integer.parseInt(dbbasket.get(KEY_ITEM_PRICE));

		}
		lv.setAdapter(adapter);
		tvtotalwithtax.setText("" + total + "\n" + "INCLUDING TAX");
	}

	private class myListAdapter extends ArrayAdapter<CartData> {

		public myListAdapter() {
			super(context, R.layout.custom_cart_row, myCars);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.custom_cart_row, parent, false);
			}
			final CartData currentCar = myCars.get(position);

			TextView tvname = (TextView) itemView.findViewById(R.id.tvitemnamecustom);
			TextView tvprice = (TextView) itemView.findViewById(R.id.tvpricetotal);

			ImageView ivimage = (ImageView) itemView.findViewById(R.id.ivitemimagecustom);
			Button btdelete = (Button) itemView.findViewById(R.id.btdelete);

			tvname.setText("" + currentCar.getName());
			tvprice.setText("" + currentCar.getPrice());

			ivimage.setImageResource(currentCar.getImage());

			btdelete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					total -= currentCar.getPrice();
					myCars.remove(position);
					mydb.deleteRowname(currentCar.getName());
					lv.setAdapter(adapter);
					tvtotalwithtax.setText("" + total + "\n" + "INCLUDING TAX");
				}
			});
			return itemView;
		}
	}
}
