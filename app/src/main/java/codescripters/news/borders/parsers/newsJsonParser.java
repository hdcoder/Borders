package codescripters.news.borders.parsers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import codescripters.news.borders.Objects.NewsObject;

public class NewsJsonParser {

	public static List<NewsObject> parseFeed(String content) {

        List<NewsObject> newsItemList = new ArrayList<>();

            try{
                JSONArray newsJsonArray = new JSONArray(content);

                NewsObject newsObject;
                for (int i=0;i<newsJsonArray.length();i++)
                {
                    newsObject = new NewsObject();
                    JSONObject obj = newsJsonArray.optJSONObject(i);

                    newsObject.setId(obj.getString("_id"));
                    System.out.println(newsObject.getId());
                    newsObject.setTitle(obj.getString("title"));
                    newsObject.setUrl(obj.getString("url"));
                    newsObject.setSource(obj.getString("source"));
                    newsObject.setTitle_full(obj.getString("title_full"));
                    newsObject.setBody_full(obj.getString("body_full"));
//                    newsObject.setMedia_type(obj.getString(""));
//                    newsObject.setMedia_src(obj.getString(""));
                    newsObject.setPublishedOn(obj.getString("publishedOn"));

                    //log message to make sure the rides are coming from the server
                    Log.i("News ", newsObject.getTitle() + " | ");

                    newsItemList.add(newsObject);
                }
                return newsItemList;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
	}

