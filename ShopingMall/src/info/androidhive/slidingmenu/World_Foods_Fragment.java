package info.androidhive.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


////today's special
public class World_Foods_Fragment extends Fragment {

	public World_Foods_Fragment() {
	}

	View rootView;
	Context context;
	GridView gridView;
	ArrayList<Item> gridArray = new ArrayList<Item>();
	CustomGridViewAdapter customGridAdapter;

	private List<Item> myCars = new ArrayList<Item>();
	ArrayList<HashMap<String, String>> todays;
	ArrayList<String> item_name = new ArrayList<String>();
	ArrayList<String> item_price = new ArrayList<String>();
	ArrayList<String> item_detail = new ArrayList<String>();
	ArrayList<String> item_image = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.photolayout, container, false);
		context = rootView.getContext();

		Set_Grid_View();

		return rootView;
	}

	private void Set_Grid_View() {

		All_Data data = new All_Data(context);

		todays = new ArrayList<HashMap<String, String>>();
		todays = data.get_todaydata();

		for (int i = 0; i < todays.size(); i++) {

			HashMap<String, String> dbbasket = new HashMap<String, String>();
			dbbasket = todays.get(i);

			String got_name = dbbasket.get("name");
			String got_price = dbbasket.get("price");
			String got_detail = dbbasket.get("detail");
			String got_image_string = dbbasket.get("image");
			int got_image = Integer.parseInt(got_image_string);

			myCars.add(new Item(got_name, got_price, got_detail, got_image));
			item_name.add(got_name);
			item_price.add(got_price);
			item_detail.add(got_detail);
			item_image.add(got_image_string);

		}

		gridView = (GridView) rootView.findViewById(R.id.gridView1);
		customGridAdapter = new CustomGridViewAdapter();
		gridView.setAdapter(customGridAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

			}
		});
	}

	public class CustomGridViewAdapter extends ArrayAdapter<Item> {
		public CustomGridViewAdapter() {
			super(context, R.layout.row_grid, myCars);
		}

		ArrayList<Item> data = new ArrayList<Item>();

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View itemView = convertView;
			if (itemView == null) {
				LayoutInflater inflater = (LayoutInflater) getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				itemView = inflater.inflate(R.layout.row_grid, null);
			}
			Item currentitem = myCars.get(position);
			ImageView iv = (ImageView) itemView.findViewById(R.id.item_image);
			TextView name = (TextView) itemView.findViewById(R.id.tvitemname);
			TextView price = (TextView) itemView.findViewById(R.id.tvprice);

			iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent slideintent = new Intent(context,
							Slideing_Item.class);
					slideintent.putExtra("item_name", item_name);
					slideintent.putExtra("item_price", item_price);
					slideintent.putExtra("item_detail", item_detail);
					slideintent.putExtra("item_image", item_image);
					slideintent.putExtra("position", position);
					startActivity(slideintent);
				}
			});

			name.setText(currentitem.getName());
			price.setText(currentitem.getPrice());
			iv.setImageResource(currentitem.getImage());

			return itemView;
		}
	}

}
