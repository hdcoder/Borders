package codescripters.news.borders.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.alexvasilkov.android.commons.adapters.ItemsAdapter;
import com.alexvasilkov.android.commons.utils.Views;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import codescripters.news.borders.MainActivity;
import codescripters.news.borders.Objects.NewsObject;
import codescripters.news.borders.R;
import codescripters.news.borders.utils.GlideHelper;

public class PaintingsAdapter extends ItemsAdapter<NewsObject> implements View.OnClickListener {

    private Context context;
    private List<NewsObject> newsItemList;


    public PaintingsAdapter(Context context, List<NewsObject> list) {
        super(context);
        this.context = context;
        this.newsItemList = list;
        setItemsList(newsItemList);
    }

    @Override
    protected View createView(NewsObject item, int pos, ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder();
        vh.image = Views.find(view, R.id.list_item_image);
        vh.image.setOnClickListener(this);
        vh.title = Views.find(view, R.id.list_item_title);
        view.setTag(vh);

        return view;
    }

    @Override
    protected void bindView(NewsObject item, int pos, View convertView) {
        ViewHolder vh = (ViewHolder) convertView.getTag();

        vh.image.setTag(R.id.list_item_image, item);
        Picasso.with(context).load(item.getMedia_src()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.image);
        vh.title.setText(item.getTitle());
    }

    @Override
    public void onClick(View view) {
        NewsObject item = (NewsObject) view.getTag(R.id.list_item_image);
        MainActivity mainActivity = (MainActivity) getContext();

            (mainActivity).openDetails(view, item);
    }

    private static class ViewHolder {
        ImageView image;
        TextView title;
    }

}
