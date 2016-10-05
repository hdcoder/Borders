package codescripters.news.borders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.utils.Views;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import codescripters.news.borders.Objects.NewsObject;
import codescripters.news.borders.items.PaintingsAdapter;
import codescripters.news.borders.parsers.NewsJsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    List<NewsObject> newsItemsArray = new ArrayList<NewsObject>();
    private View listTouchInterceptor;
    private View detailsLayout;
    private UnfoldableView unfoldableView;
    MainActivity mainActivityContext;
    List<MyTask> tasks;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tasks = new ArrayList<>();
        requestData("https://thenewsapp.herokuapp.com/api/news");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button button= (Button) findViewById(R.id.more_info);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, WebviewActivity.class);
//                myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainActivityContext = this;

        listTouchInterceptor = Views.find(this, R.id.touch_interceptor_view);
        listTouchInterceptor.setClickable(false);

        detailsLayout = Views.find(this, R.id.details_layout);
        detailsLayout.setVisibility(View.INVISIBLE);

        unfoldableView = Views.find(this, R.id.unfoldable_view);

        Bitmap glance = BitmapFactory.decodeResource(getResources(), R.drawable.unfold_glance);
        unfoldableView.setFoldShading(new GlanceFoldShading(glance));

        unfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
                detailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
                detailsLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void requestData(String uri) {
        //Passing parameters for login authentication with username and password
        System.out.println(uri);
        MyTask task = new MyTask();
        task.execute(uri);
    }


    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            if (tasks.size() == 0)
            {
//                progress.show();
            }
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... params) {
            Request request = new Request.Builder()
                    .url(params[0])
                    .build();


            Response response = null;
            try {
                response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {

            tasks.remove(this);
            if (tasks.size() == 0) {
//                progress.dismiss();
            }
            if(result == null)
            {
                Toast.makeText(getApplicationContext(), "Can not connect to internet ",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                    System.out.println("Success");
                    System.out.println(result);
                    newsItemsArray =NewsJsonParser.parseFeed(result);

                    System.out.println(newsItemsArray);
                ListView listView = Views.find(mainActivityContext, R.id.list_view);
                listView.setAdapter(new PaintingsAdapter(mainActivityContext,newsItemsArray));
            }


        }

    }

    @Override
    public void onBackPressed() {
        if (unfoldableView != null
                && (unfoldableView.isUnfolded() || unfoldableView.isUnfolding())) {
            unfoldableView.foldBack();
        } else {
            super.onBackPressed();
        }
    }

    public void openDetails(View coverView, NewsObject newsObject) {
        final ImageView image = Views.find(detailsLayout, R.id.details_image);
        final TextView title = Views.find(detailsLayout, R.id.details_title);
        final TextView description = Views.find(detailsLayout, R.id.details_text);

//        GlideHelper.loadPaintingImage(image, painting);
        Picasso.with(this).load(newsObject.getMedia_src()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(image);
        title.setText(newsObject.getTitle_full());
        description.setText(newsObject.getBody_full());

//        SpannableBuilder builder = new SpannableBuilder(this);
//        builder
//                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
//                .append("YEAR").append(": ")
//                .clearStyle()
//                .append(painting.getYear()).append("\n")
//                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
//                .clearStyle()
//                .append(painting.getLocation());
//        description.setText(builder.build());

        unfoldableView.unfold(coverView, detailsLayout);
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
