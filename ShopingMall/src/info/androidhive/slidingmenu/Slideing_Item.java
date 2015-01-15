package info.androidhive.slidingmenu;

import java.util.ArrayList;
import java.util.Locale;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class Slideing_Item extends SlidingFragmentActivity {

	SectionsPagerAdapter mSectionsPagerAdapter;

	private ViewPager mViewPager;
	static Context context;
	static View rootView;
	static ArrayList<String> item_name = new ArrayList<String>();
	static ArrayList<String> item_price = new ArrayList<String>();

	static ArrayList<String> item_image = new ArrayList<String>();
	static ArrayList<String> item_detail = new ArrayList<String>();
	static DBHelper mydb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slideing_item);
		setBehindContentView(R.layout.slideing_item_behind_view);
		context = this;
		mydb = new DBHelper(context);
		mydb.open();

		item_name = getIntent().getStringArrayListExtra("item_name");
		item_price = getIntent().getStringArrayListExtra("item_price");
		item_detail = getIntent().getStringArrayListExtra("item_detail");
		item_image = getIntent().getStringArrayListExtra("item_image");
		int position = getIntent().getIntExtra("position", 0);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		Set_behind_view();

		mViewPager.setCurrentItem(position);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return item_name.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();

			return item_name.get(position);

		}
	}

	public static class DummySectionFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.slideing_item_dummy,
					container, false);
			final Context context = rootView.getContext();
			int index = getArguments().getInt(ARG_SECTION_NUMBER);
			index--;

			ImageView iv = (ImageView) rootView.findViewById(R.id.ivitemimage);
			TextView tvprice = (TextView) rootView
					.findViewById(R.id.tvitemprice);
			TextView tvdetail = (TextView) rootView
					.findViewById(R.id.tvitem_details);
			Button btbuy = (Button) rootView.findViewById(R.id.btbuynow);
			Button btaddtocart = (Button) rootView
					.findViewById(R.id.btaddtocart);

			btbuy.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			});
			final int perfecrindex = index;
			btaddtocart.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(context, "" + item_name.get(perfecrindex),
							Toast.LENGTH_SHORT).show();

					mydb.addtocart(item_name.get(perfecrindex),
							item_price.get(perfecrindex),
							"" + item_image.get(perfecrindex),
							item_detail.get(perfecrindex));

				}
			});

			tvprice.setText("Rs. " + item_price.get(index));
			tvdetail.setText(item_detail.get(index));

			iv.setImageResource(Integer.parseInt(item_image.get(index)));

			Set_you_may_Also_Like_this(index);

			return rootView;
		}

		private void Set_you_may_Also_Like_this(int index) {

			LinearLayout mainllayout = (LinearLayout) rootView
					.findViewById(R.id.layoutyoumaylikethis);
			for (int i = 0; i < 30; i++) {

				LinearLayout ll = new LinearLayout(context);
				ll.setOrientation(LinearLayout.VERTICAL);

				ImageView iv = new ImageView(context);
				iv.setImageResource(R.drawable.ic_pages);
				ll.addView(iv);

				final int findindex = i;
				iv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						Toast.makeText(context, "" + findindex,
								Toast.LENGTH_SHORT).show();
					}
				});

				TextView product = new TextView(context);
				product.setText(" Product" + 1 + "    ");
				ll.addView(product);

				TextView productprice = new TextView(context);
				productprice.setText(" Product" + 1 + "    ");
				ll.addView(productprice);

				mainllayout.addView(ll);
			}

		}
	}

	private void Set_behind_view() {

		SlidingMenu sm = getSlidingMenu();
		sm.setBehindOffset(250);
		sm.setShadowWidth(20);
		sm.setShadowDrawable(R.drawable.shadowgradient);
		sm.setSlidingEnabled(true); 

		LinearLayout linearmain = (LinearLayout) findViewById(R.id.linearbackground);

		for (int i = 0; i < item_image.size(); i++) {

			LinearLayout ll = new LinearLayout(context);
			ll.setOrientation(LinearLayout.VERTICAL);
			ll.setBackgroundResource(R.drawable.white_bg);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 10, 0, 10);
			ll.setLayoutParams(params);

			ImageView iv = new ImageView(context);

			iv.setImageResource(Integer.parseInt(item_image.get(i)));
			ll.addView(iv);
			iv.getLayoutParams().width = 300;
			iv.getLayoutParams().height = 400;

			final int index = i;
			iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					mViewPager.setCurrentItem(index);
					toggle();
				}
			});

			TextView productname = new TextView(context);
			productname.setText(item_name.get(i));
			ll.addView(productname);

			TextView productprice = new TextView(context);
			productprice.setText("Rs. " + item_price.get(i));
			productprice.setTextColor(Color.parseColor("#C42A2C"));
			ll.addView(productprice);

			linearmain.addView(ll);

			ll.getLayoutParams().width = 400;

		}

	}

}
