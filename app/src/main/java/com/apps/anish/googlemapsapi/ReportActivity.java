package com.apps.anish.googlemapsapi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.R.attr.bitmap;
import static com.apps.anish.googlemapsapi.R.id.description_id;

public class ReportActivity extends AppCompatActivity {

    // Map variables
    LocationManager locationManager;
    Location location;
    GoogleMap gMap;
    LatLng latLng;

    //Photo variables
    public static final int REQUEST_CAPTURE = 1;
    ImageView resultPhoto;
    Bitmap photo;
    String photoPath;

    // Issue variables
    String selectedIssue;
    TextView selectedIssueTextView;
    Button selectIssueButton;

    //Email Variables
    String[] recipient;
    EditText description;
    String subject;
    String emailDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        selectIssueButton = (Button) findViewById(R.id.issuetitle_id);
        selectIssueButton.requestFocus();
        if (getIntent() != null && getIntent().getExtras() != null) {
            selectedIssue = (String) getIntent().getExtras().get("selectedIssue");
            Toast.makeText(ReportActivity.this, "Selected:" + selectedIssue, Toast.LENGTH_LONG).show();
            selectedIssueTextView = (TextView) findViewById(R.id.viewissue_id);
            selectedIssueTextView.setText(selectedIssue);
        }

        // Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Current location"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                gMap = googleMap;


                googleMap.setOnMapClickListener(new OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng point) {

                        Toast.makeText(ReportActivity.this, "Point:" + point.toString(), Toast.LENGTH_LONG).show();
                        gMap.clear();
                        gMap.addMarker(new MarkerOptions().position(point).title("Current location"));
                        latLng = point;
                        //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
                    }

                });

            }

        });


        getLocation();

        // Photos

        Button photoButton = (Button)findViewById(R.id.photo_id);
        resultPhoto = (ImageView) findViewById(R.id.imageview_id);
        if(!hasCamera()){
            photoButton.setEnabled(false);
        }
    }

    @Override
    protected void onStart () {
        super.onStart();
        Toast.makeText(ReportActivity.this, "onStart", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onResume () {
        super.onResume();
        Toast.makeText(ReportActivity.this, "onResume", Toast.LENGTH_LONG).show();
    }




        public boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
    public void launchCamera(View v){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i , REQUEST_CAPTURE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
      super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");
            resultPhoto.setImageBitmap(photo);
            photoPath = MediaStore.Images.Media.insertImage(getContentResolver(), photo, "title", null);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onClickIssueTitleButton(View v){
        Intent intent1 = new Intent(this, PickIssueActivity.class);
        startActivity(intent1);
    }

    public void getLocation() {


        try{
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, false);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Toast.makeText(ReportActivity.this, String.valueOf(location.getLatitude()), Toast.LENGTH_LONG).show();
            Toast.makeText(ReportActivity.this, String.valueOf(location.getLongitude()), Toast.LENGTH_LONG).show();
        }
        catch(SecurityException se){
            se.printStackTrace();
            Toast.makeText(ReportActivity.this, se.getMessage(), Toast.LENGTH_LONG).show();
        }

        catch(Exception e){
            e.printStackTrace();
        }


    }
    public void emailReport(View v){
        description = (EditText)findViewById(R.id.description_id);
        if (description!=null){
            emailDescription = description.getText().toString();
        }
        Spinner spinner = (Spinner)findViewById(R.id.selectIssueSpinner_id);
        if(spinner!=null){
            subject = spinner.getSelectedItem().toString();
            recipient = new String[]{
                    "anish.rajeev210@gmail.com"
            };

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, recipient);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject + " issue");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "User's Comments:" + emailDescription + "\r\n" +" Coordinates:" + latLng);
            if(photoPath == null) {
                emailIntent.setType("message/rfc882");
            }
            else{
                emailIntent.setType("image/*");
                Uri bitmapUri = Uri.parse(photoPath);
                emailIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
            }
            startActivity(Intent.createChooser(emailIntent, "Choose an email client"));
        }
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(ReportActivity.this);
        builder.setTitle("Thankyou!");
        builder.setMessage("Your Email has been sent!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ReportActivity.this.finish();
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
            }

        });
        AlertDialog alert = builder.create();
        alert.show();

    }



}
