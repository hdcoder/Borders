package codescripters.news.borders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.utils.Views;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import codescripters.news.borders.Objects.NewsObject;

public class DetailNewsActivity extends AppCompatActivity {

    ImageView newsImageView;
    TextView newsTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailnews);

            String newsBody = this.getIntent().getStringExtra("news_body");
            String newsTitle  = this.getIntent().getStringExtra("news_title");
            String newsMediaSrc  = this.getIntent().getStringExtra("news_media_src");
            String newsPublishedOn  = this.getIntent().getStringExtra("news_published_on");

            newsImageView = (ImageView) findViewById(R.id.NewsImage);
            newsTextView = (TextView) findViewById(R.id.NewsBody);
//        GlideHelper.loadPaintingImage(image, painting);
        Picasso.with(this).load(newsMediaSrc).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(newsImageView);
        newsTextView.setText(newsBody);
            System.out.println(newsBody);


    }
}
