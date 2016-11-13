package codescripters.news.borders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.utils.Views;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
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
    JSONObject newsJsonObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailnews);

        newsBody = this.getIntent().getStringExtra("news_body");
        String newsMediaSrc = this.getIntent().getStringExtra("news_media_src");
        try {
            newsJsonObject = new JSONObject(this.getIntent().getStringExtra("newsDetail"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        newsImageView = (ImageView) findViewById(R.id.NewsImage);
//            newsTextView = (TextView) findViewById(R.id.NewsBody);
//        GlideHelper.loadPaintingImage(image, painting);
        Picasso.with(this).load(newsMediaSrc).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(newsImageView);
//        newsTextView.setText(newsBody);

        RecyclerView timeLineRecyclerView = (RecyclerView) findViewById(R.id.timeLineRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        timeLineRecyclerView.setLayoutManager(linearLayoutManager);
        TimeLineAdapter adapter = new TimeLineAdapter(initDatas());
        timeLineRecyclerView.setAdapter(adapter);

    }

    private List<TimeLineItem> initDatas() {
        List<TimeLineItem> timeLineItems = new ArrayList<>();

        try {
            JSONArray newsJsonArray = newsJsonObject.getJSONArray("timeline");

            for (int i = 0; i < newsJsonArray.length(); i++) {
                JSONObject obj = newsJsonArray.optJSONObject(i);

                String time = DateUtils.formatDateTime(this, obj.getLong("date"), DateUtils.FORMAT_SHOW_TIME);

                timeLineItems.add(new TimeLineItem(obj.getString("publishedOn"),
                        getResources().getDrawable(R.drawable.starry_night),
                        obj.getString("text"),
                        time));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return timeLineItems;
    }
}
