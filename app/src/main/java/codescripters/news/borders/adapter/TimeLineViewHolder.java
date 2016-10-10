package codescripters.news.borders.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import codescripters.news.borders.R;
import codescripters.news.borders.bean.TimeLineItem;
import codescripters.news.borders.bean.TimeLineItemType;
import codescripters.news.borders.view.TimeLineView;


public class TimeLineViewHolder extends RecyclerView.ViewHolder {
    private TextView name;
    private ImageView icon;
    private TextView date;
    private TextView time;

    public TimeLineViewHolder(View itemView, int type, Context context) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.timeLineName);
        date = (TextView) itemView.findViewById(R.id.timeLineDate);
        time = (TextView) itemView.findViewById(R.id.timeLineTime);
        icon = (ImageView) itemView.findViewById(R.id.timeLineIcon);
        final TimeLineView timeLineView = (TimeLineView) itemView.findViewById(R.id.timeLineView);
        Drawable drawable = context.getResources().getDrawable(R.drawable.newspaper);
        Drawable drawable2 = context.getResources().getDrawable(R.drawable.timeline_marker2);
        Drawable drawable3 = context.getResources().getDrawable(R.drawable.timeline_marker3);
        Drawable drawable4 = context.getResources().getDrawable(R.drawable.cafe_terrace);
        Drawable drawable5 = context.getResources().getDrawable(R.drawable.timeline_marker5);
        Drawable drawable6 = context.getResources().getDrawable(R.drawable.timeline_marker6);
        Drawable drawable7 = context.getResources().getDrawable(R.drawable.timeline_marker7);
        Random random = new Random();
//        final int i = random.nextInt(7);
        final int i =0;
        final Drawable drawables[] = {drawable,drawable2,drawable3,drawable4,drawable5,drawable6,drawable7};
        timeLineView.setTimeLineMarker(drawables[i]);
        if (type == TimeLineItemType.ATOM) {
            timeLineView.setBeginLine(null);
            timeLineView.setEndLine(null);
        } else if (type == TimeLineItemType.START) {
            timeLineView.setBeginLine(null);
        } else if (type == TimeLineItemType.END) {
            timeLineView.setEndLine(null);
        }
    }

    public void setData(TimeLineItem timeLineItem) {
        name.setText(timeLineItem.getTimeLineName());
        icon.setImageDrawable(timeLineItem.getTimeLineIcon());
        date.setText(timeLineItem.getTimeLineDate());
        time.setText(timeLineItem.getTimeLineTime());
    }

}
