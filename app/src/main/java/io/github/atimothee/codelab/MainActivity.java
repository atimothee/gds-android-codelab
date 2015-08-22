package io.github.atimothee.codelab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    CollapsingToolbarLayout ctb;
    int mutedColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	/* Top toolbar */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* Bottom toolbar. */
        Toolbar bottomToolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
        bottomToolbar.inflateMenu(R.menu.menu_bottom);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Add 8 cards
        MyAdapter adapter = new MyAdapter(new String[8]);
        recyclerView.setAdapter(adapter);
        ctb = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
/* Define the image */
        ImageView image = (ImageView) findViewById(R.id.image);
/* Decode bitmap from the image */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
/* Generate palette from the image bitmap */
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                mutedColor = palette.getMutedColor(R.attr.colorPrimary);
       				/* Set toolbar color from the palette */
                ctb.setContentScrimColor(mutedColor);
            }
        });
    }
    

    /* Create RecylcerView Adapter. */
    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private String[] mDataset;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public View view;
            public TextView title;

            public ViewHolder(View v) {
                super(v);
                view = v;
                title = (TextView) v.findViewById(R.id.card_title);
            }
        }

        public MyAdapter(String[] myDataset) {
            mDataset = myDataset;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View cardview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_view, parent, false);
            return new ViewHolder(cardview);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.title.setText("Card " + (position + 1));
        }


        @Override
        public int getItemCount() {
            return mDataset.length;
        }

    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

}
