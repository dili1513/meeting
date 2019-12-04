package hmh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.meeting.R;

import java.util.List;

/**
 * Created by lenovo on 2019/10/19.
 */

public class meetingRoomAdapter extends ArrayAdapter<MeetingRoom> {

    private int resourceId;
    public meetingRoomAdapter(Context context, int textViewResourceId, List<MeetingRoom> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    private OnItemClickListener listener;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MeetingRoom meetingroom = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);//这个是实例化一个我们自己写的界面Item
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)listener.onItemClick(meetingroom);
            }
        });
        LinearLayout linearLayout = view.findViewById(R.id.meeting);

        TextView meetingName = view.findViewById(R.id.meetingRoomName);
        TextView meetingDate = view.findViewById(R.id.meetingRoomDate);
        TextView meetingTime = view.findViewById(R.id.meetingRoomTime);
        meetingName.setText(meetingroom.getName());
        meetingDate.setText(meetingroom.getDate());
        meetingTime.setText(meetingroom.getTime());
        return view;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    interface OnItemClickListener{
        void onItemClick(MeetingRoom meetingRoom);
    }

}
