package codescripters.news.borders;

import android.app.ProgressDialog;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.okhttp.Call;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import codescripters.news.borders.Objects.NewsObject;
import codescripters.news.borders.items.PaintingsAdapter;
import codescripters.news.borders.parsers.NewsJsonParser;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    List<NewsObject> newsItemsArray = new ArrayList<NewsObject>();
    private View listTouchInterceptor;
    private View detailsLayout;
    private UnfoldableView unfoldableView;
    OkHttpClient client = new OkHttpClient();
    private ProgressDialog progress;
    ListView listView;
    PaintingsAdapter adapter;
    Button moreInfoBtn,timeLineBtn;
    JSONObject detailResponse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progress = new ProgressDialog(this);

        progress.setTitle("Loading News !!!"); //title

        progress.setMessage("Contacting LOC !"); // message
        progress.setCancelable(false);
        progress.setCancelable(true);

        progress.show();
//        requestData("https://thenewsapp.herokuapp.com/api/news");
        Request request = new Request.Builder()
                .url("https://thenewsapp.herokuapp.com/api/news")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
               String responseBody = response.body().string();
               processResult(responseBody);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        moreInfoBtn= (Button) findViewById(R.id.more_info);
        timeLineBtn= (Button) findViewById(R.id.timelineBtn);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = Views.find(this, R.id.list_view);
        adapter = new PaintingsAdapter(this,newsItemsArray);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                System.out.println(totalItemsCount);
                Request pageRequest = new Request.Builder()
                        .url("https://thenewsapp.herokuapp.com/api/news?page="+page)
                        .build();
                client.newCall(pageRequest).enqueue(new Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {
                        String responseBody = response.body().string();
                        processResult(responseBody);
                    }
                });
//
//                requestData("https://thenewsapp.herokuapp.com/api/news?page="+page);

                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        FirebaseMessaging.getInstance().subscribeToTopic("news");

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
    }

    public void processResult(String result) {
        System.out.println("Success");
        System.out.println(result);
        newsItemsArray.addAll(NewsJsonParser.parseFeed(result));
        System.out.println(newsItemsArray);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                progress.dismiss();
                adapter.notifyDataSetChanged();
            }

        });
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

    public void openDetails(View coverView, final NewsObject newsObject) {
        final ImageView image = Views.find(detailsLayout, R.id.details_image);
        final TextView title = Views.find(detailsLayout, R.id.details_title);
        final TextView description = Views.find(detailsLayout, R.id.details_text);

//        GlideHelper.loadPaintingImage(image, painting);
        Picasso.with(this).load(newsObject.getMedia_src()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(image);
        title.setText(newsObject.getTitle_full());
        description.setText(newsObject.getBody_full());
//        requestData("https://thenewsapp.herokuapp.com/api/news/"+newsObject.getId());
        Request pageRequest = new Request.Builder()
                .url("https://thenewsapp.herokuapp.com/api/news/"+newsObject.getId())
                .build();
        client.newCall(pageRequest).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                try {
                    detailResponse = new JSONObject(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(detailResponse);
                final JSONObject finalDetailResponse = detailResponse;
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            if(finalDetailResponse != null && finalDetailResponse.getJSONArray("timeline").length()>1){
                                timeLineBtn.setVisibility(View.VISIBLE);
                            }
                            else {
                                timeLineBtn.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });

//                processResult(responseBody);
            }
        });

        timeLineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, DetailNewsActivity.class);
                myIntent.putExtra("news_title", newsObject.getTitle_full());
                myIntent.putExtra("news_body", newsObject.getBody_full());
                myIntent.putExtra("news_media_src", newsObject.getMedia_src());
                myIntent.putExtra("news_published_on", newsObject.getPublishedOn());
                myIntent.putExtra("newsDetail", detailResponse.toString());
//                myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });
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
