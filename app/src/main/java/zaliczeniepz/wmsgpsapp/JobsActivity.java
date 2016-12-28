package zaliczeniepz.wmsgpsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class JobsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        ArrayList<Job> jobsList = new ArrayList<Job>();
        jobsList.add(new Job(1, "One"));
        jobsList.add(new Job(2, "Two"));
        jobsList.add(new Job(3, "Three"));
        jobsList.add(new Job(4, "Four"));
        jobsList.add(new Job(5, "Five"));
        jobsList.add(new Job(6, "Six"));
        jobsList.add(new Job(7, "Seven"));
        jobsList.add(new Job(8, "Eight"));
        jobsList.add(new Job(9, "Nine"));
        jobsList.add(new Job(10, "Ten"));
        jobsList.add(new Job(11, "Eleven"));

        JobsAdapter jobsAdapter = new JobsAdapter(this, jobsList);

        ListView listView = (ListView) findViewById(R.id.activity_jobs);
        listView.setAdapter(jobsAdapter);
    }
}
