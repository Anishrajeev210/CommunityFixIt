package com.apps.anish.googlemapsapi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class PickIssueActivity extends AppCompatActivity {

    ArrayList<String> issueList = new ArrayList<String>();
    public ListView issueListView;
    String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_issue);
        initialize();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, issueList);
        issueListView = (ListView)findViewById(R.id.listview_id);
        issueListView.setAdapter(adapter);
        issueListView.setEnabled(true);

        // Set an item click listener for the ListView
        issueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);

                Intent intent1 = new Intent(getApplicationContext(), ReportActivity.class);
                intent1.putExtra("selectedIssue", selectedItem);
                startActivity(intent1);
            }
        });
    }

    public void initialize() {
        issueList.add("Damaged SideWalk");
        issueList.add("Damaged Curb/Gutter");
        issueList.add("Dead Animal");
        issueList.add("Litter");
        issueList.add("Graffiti");
        issueList.add("Weeds/HighGrass");
        issueList.add("Illegal/Damaged Sign");
        issueList.add("Broken Traffic Light");
        issueList.add("Sinkhole");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClickReportButton(View v){
        Toast.makeText(PickIssueActivity.this, "Report", Toast.LENGTH_LONG).show();
        Intent intent1 = new Intent(this, ReportActivity.class);
        startActivity(intent1);
    }
    public void onClickIssuestButton(View v){
        Toast.makeText(PickIssueActivity.this, "Issues", Toast.LENGTH_LONG).show();

    }
}
