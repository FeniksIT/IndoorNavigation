package by.grsu.ftf.activity;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import by.grsu.ftf.maths.MapView;

public class MapActivity extends AppCompatActivity implements View.OnClickListener {

    MapView map;
    ArrayList<PointF> coordinatesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        coordinatesList = bundle.getParcelableArrayList("mylist");
        Button goMapButton = (Button) findViewById(R.id.goActivity);
        goMapButton.setOnClickListener(this);
        map = (MapView) findViewById(R.id.ImageMap);
        map.setPointFS(coordinatesList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(coordinatesList.size()>0) {
            //map.setPointFS();
        }
    }

    @Override
    public void onClick(View view) {
        Intent goMapActivity = new Intent(this,MainActivity.class);
        startActivity(goMapActivity);
    }
}
