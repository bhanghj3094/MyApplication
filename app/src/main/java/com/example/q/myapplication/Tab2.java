package com.example.q.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class Tab2  extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("wrong", "start onCreateView");
        View rootView = inflater.inflate(R.layout.tab2, container, false);
        Log.d("wrong", "initialize rootView");
        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        Log.d("wrong", "get gridview from rootview");
        gridview.setAdapter(new ImageAdapter(rootView.getContext()));
        Log.d("wrong", "done setting adapter");

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "This is my Toast message!", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("wrong", "----------------------------- end --------------------------");
        return rootView;
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return 3;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) { // not recycled
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(600,600));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // reference to images
        public Integer[] mThumbIds = {
                R.drawable.baby, R.drawable.bag,
                R.drawable.boat, R.drawable.camera,
                R.drawable.chicken, R.drawable.dog,
                R.drawable.lake, R.drawable.lamp,
                R.drawable.leaf, R.drawable.ritual,
                R.drawable.road, R.drawable.road,
                R.drawable.sea, R.drawable.squirrel
        };
    }


}

// Link for Inference : "Grid View": https://developer.android.com/guide/topics/ui/layout/gridview#java