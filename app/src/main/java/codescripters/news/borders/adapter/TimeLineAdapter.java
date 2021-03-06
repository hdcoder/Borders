package codescripters.news.borders.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import codescripters.news.borders.R;
import codescripters.news.borders.bean.TimeLineItem;
import codescripters.news.borders.bean.TimeLineItemType;


public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private List<TimeLineItem> timeLineItems;

    public TimeLineAdapter(List<TimeLineItem> timeLineItems) {
        this.timeLineItems = timeLineItems;
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_timeline, null);
        return new TimeLineViewHolder(view, viewType,parent.getContext());
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        holder.setData(timeLineItems.get(position));
    }

    @Override
    public int getItemCount() {
        return timeLineItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        int size = timeLineItems.size() - 1;
        if (size == 0) {
            return TimeLineItemType.ATOM;
        } else if (position == 0) {
            return TimeLineItemType.START;
        } else if (position == size) {
            return TimeLineItemType.END;
        } else {
            return TimeLineItemType.NORMAL;
        }
    }
}
