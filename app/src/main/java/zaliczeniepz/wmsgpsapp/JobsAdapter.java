package zaliczeniepz.wmsgpsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2016-12-28.
 */

public class JobsAdapter extends ArrayAdapter<Job> {

    private final String TAG = JobsAdapter.class.getSimpleName();

    public JobsAdapter(Activity context, ArrayList<Job> initJobList){
        super(context,0, initJobList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Job job = getItem(position);

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.ativity_jobs_item, parent, false);
        }

        TextView textView = (TextView) listItemView.findViewById(R.id.jobName);
        textView.setText(job.name);



        return listItemView;
    }
}
