package hmh;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lenovo.meeting.R;

import java.util.ArrayList;
import java.util.List;

import hmh.applicant;
/**
 * Created by lenovo on 2019/10/19.
 */

public class meetingAdapter extends ArrayAdapter<Meeting> {
    private int resourceId;
    public meetingAdapter(Context context,int textViewResourceId,List<Meeting> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
        @Override
        public View getView(final int position,View convertView,ViewGroup parent) {
            Meeting meeting = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);//这个是实例化一个我们自己写的界面Item
            LinearLayout linearLayout = view.findViewById(R.id.meeting);

            TextView meetingName = view.findViewById(R.id.meetingName);
            TextView meetingDate = view.findViewById(R.id.meetingDate);
            TextView meetingTime = view.findViewById(R.id.meetingTime);
            TextView shenqingzhuangtai = view.findViewById(R.id.shenqingzhuangtai);

            meetingName.setText(meeting.getName());
            meetingDate.setText(meeting.getDate());
            meetingTime.setText(meeting.getTime());
            shenqingzhuangtai.setText(meeting.getShenqingzhuangtai());

            return view;
        }
}
