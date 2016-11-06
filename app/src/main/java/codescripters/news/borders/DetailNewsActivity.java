package codescripters.news.borders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.utils.Views;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import codescripters.news.borders.Objects.NewsObject;
import codescripters.news.borders.adapter.TimeLineAdapter;
import codescripters.news.borders.bean.TimeLineItem;

public class DetailNewsActivity extends AppCompatActivity {

    ImageView newsImageView;
    TextView newsTextView;
    String newsBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailnews);

            newsBody = this.getIntent().getStringExtra("news_body");
            String newsTitle  = this.getIntent().getStringExtra("news_title");
            String newsMediaSrc  = this.getIntent().getStringExtra("news_media_src");
            String newsPublishedOn  = this.getIntent().getStringExtra("news_published_on");

            newsImageView = (ImageView) findViewById(R.id.NewsImage);
//            newsTextView = (TextView) findViewById(R.id.NewsBody);
//        GlideHelper.loadPaintingImage(image, painting);
        Picasso.with(this).load(newsMediaSrc).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(newsImageView);
//        newsTextView.setText(newsBody);
            System.out.println(newsBody);

        RecyclerView timeLineRecyclerView = (RecyclerView) findViewById(R.id.timeLineRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        timeLineRecyclerView.setLayoutManager(linearLayoutManager);
        TimeLineAdapter adapter = new TimeLineAdapter(initDatas());
        timeLineRecyclerView.setAdapter(adapter);

    }

    private List<TimeLineItem> initDatas() {
        List<TimeLineItem> timeLineItems = new ArrayList<>();
        timeLineItems.add(new TimeLineItem("26 May 2016",
                getResources().getDrawable(R.drawable.starry_night),
                newsBody,
                "8:41 PM"));

        timeLineItems.add(new TimeLineItem("26 May 2016",
                getResources().getDrawable(R.drawable.starry_night),
                newsBody,
                "27 Aug"));
        timeLineItems.add(new TimeLineItem("26 May 2016",
                getResources().getDrawable(R.drawable.starry_night),
                "Hello World  ",
                "28"));
        timeLineItems.add(new TimeLineItem("26 May 2016",
                getResources().getDrawable(R.drawable.starry_night),
                "Hello World  ",
                "Hello world"));
        timeLineItems.add(new TimeLineItem("26 May 2016",
                getResources().getDrawable(R.drawable.starry_night),
                "Hello World  ",
                "Hello world"));
        timeLineItems.add(new TimeLineItem("26 May 2016",
                getResources().getDrawable(R.drawable.starry_night),
                "Hello World  ",
                "Hello world"));

        timeLineItems.add(new TimeLineItem("26 May 2016",
                getResources().getDrawable(R.drawable.starry_night),
                "Hello World  ",
                "Hello world"));



        return timeLineItems;
    }
}
