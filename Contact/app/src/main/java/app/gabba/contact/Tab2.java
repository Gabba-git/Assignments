package app.gabba.contact;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arvind on 10/4/16.
 */
public class Tab2 extends Fragment implements OnMapReadyCallback{
    GoogleMap Mymap;
    LatLng CENTER = new LatLng(28.6139, 77.2090);
   // String title = "";
    ArrayList<String> name  = new ArrayList();
    ArrayList <String> email  = new ArrayList ();
    ArrayList <String> numbers = new ArrayList ();
    ArrayList <MainActivity.location> points =  new ArrayList ();
    ListView lview;
    TextView dia;
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.tab2,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog);
        dia = (TextView)dialog.findViewById(R.id.dialog_text);

        return rootview;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Mymap=googleMap;
        LatLng marker2 ;
        final ArrayList <String> title = new ArrayList<String>();
        Marker markerchecker = googleMap.addMarker(new MarkerOptions().position(CENTER).title("Center"));
         final Map<LatLng , Integer> mark = new HashMap<>();

        mark.put(CENTER,0);
        int j=1;
        title.add(0,"center");
        for(int i=0; i<name.size();i++) {
            marker2 = new LatLng(points.get(i).latitude, points.get(i).longitude);

           while(true) {
               if(mark.containsKey(marker2)) {
                    Integer p = mark.get(marker2);
                    Log.d(Tab2.class.getSimpleName(), "i am here"+i +"lkk"+p);
                    markerchecker = googleMap.addMarker(new MarkerOptions().position(marker2));
                    String s = title.get(p) + name.get(i) + "\n" + email.get(i) + "\n" + numbers.get(i) + "\n\n";
                    title.add(p,s);
                    break;
                }
               else {
                    mark.put(marker2,j);
                    markerchecker = googleMap.addMarker(new MarkerOptions().position(marker2));
                    title.add(j, name.get(i) + "\n" + email.get(i) + "\n" + numbers.get(i) + "\n\n");
                    j++;
                    break;
                }
            }
        }

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Integer p = mark.get(marker.getPosition());
                Log.d(Tab2.class.getSimpleName(),marker+"i am 2nd" + p);
                showdata(title.get(p));
                return true;
            }
        });

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom((CENTER), 1.0f));
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);




    }

    public void setVals(ArrayList<String> name, ArrayList<String> email, ArrayList<String> numbers, ArrayList<MainActivity.location> points) {

        this.name = name;
        this.email=email;
        this.numbers=numbers;
        this.points=points;

    }

    public void showdata(String s){

       // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        dia.setText(s);
        dialog.setTitle("Contacts");
        dialog.show();
    }
}
