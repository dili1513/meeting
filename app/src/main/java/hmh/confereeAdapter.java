package hmh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.meeting.R;

import java.util.List;

/**
 * Created by lenovo on 2019/11/3.
 */

public class confereeAdapter extends ArrayAdapter<Conferee> {
    private int resourceId;
    public confereeAdapter(Context context, int textViewResourceId, List<Conferee> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Conferee conferee = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);//这个是实例化一个我们自己写的界面Item
        LinearLayout linearLayout = view.findViewById(R.id.conferee);

        TextView ConfereeName = view.findViewById(R.id.ConfereeName);
        TextView ConfereeId = view.findViewById(R.id.ConfereeId);

        ConfereeName.setText(conferee.getName());
        ConfereeId.setText(conferee.getId());

        return view;
    }
}
