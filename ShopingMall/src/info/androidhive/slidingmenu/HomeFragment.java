package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

	public HomeFragment() {
	}

	View rootView;
	Context context;
	ViewPagerCustomDuration ViewpagerListview;
	Handler mHandler = new Handler();

	int i = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.homefragment, container, false);
		context = rootView.getContext();

		ViewpagerListview = (ViewPagerCustomDuration) rootView.findViewById(R.id.list);

		ViewpagerListview.setScrollDurationFactor(15);
		ViewPagerAdapter adapter = new ViewPagerAdapter(context, 3);

		ViewpagerListview.setAdapter(adapter);

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				{

					ViewpagerListview.setCurrentItem(i);
					i++;
					if (i == 3) {
						i = 0;
					}
					mHandler.postDelayed(this, 5000);
				}
			}
		};
		mHandler.post(runnable);

		LinearLayout mainllayout = (LinearLayout) rootView.findViewById(R.id.linearproductlist);

		for (int i = 0; i < 30; i++) {

			LinearLayout ll = new LinearLayout(context);
			ll.setOrientation(LinearLayout.VERTICAL);

			ImageView iv = new ImageView(context);

			iv.setImageResource(R.drawable.palak);
			ll.addView(iv);

			final int index = i;
			iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Toast.makeText(context, "" + index, Toast.LENGTH_SHORT).show();
				}
			});

			TextView product = new TextView(context);
			product.setText(" Palak " + 1 + "    ");
			ll.addView(product);

			TextView productprice = new TextView(context);
			productprice.setText(" 20 Rs");
			ll.addView(productprice);

			mainllayout.addView(ll);
		}

		LinearLayout linearproductlist22 = (LinearLayout) rootView.findViewById(R.id.linearproductlist22);

		for (int i = 0; i < 30; i++) {

			LinearLayout ll = new LinearLayout(context);
			ll.setOrientation(LinearLayout.VERTICAL);

			ImageView iv = new ImageView(context);
			iv.setImageResource(R.drawable.poteto);
			ll.addView(iv);

			final int index = i;
			iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Toast.makeText(context, "" + index, Toast.LENGTH_SHORT).show();
				}
			});

			TextView product = new TextView(context);
			product.setText(" Poteto" + 1 + "    ");
			ll.addView(product);

			TextView productprice = new TextView(context);
			productprice.setText(" 30 Rs 1kg");
			ll.addView(productprice);

			linearproductlist22.addView(ll);
		}

		return rootView;
	}
}
