package app.gabba.contact;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by arvind on 28/3/16.
 */
public class PageAdapter extends FragmentPagerAdapter {

    ArrayList <String> name  = new ArrayList();
    ArrayList <String> email  = new ArrayList ();
    ArrayList <String> numbers = new ArrayList ();
    ArrayList <MainActivity.location> points =  new ArrayList ();

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setVals(ArrayList<String> name, ArrayList<String> email, ArrayList<String> numbers,ArrayList<MainActivity.location> points){
        //Log.d(Tab1.class.getSimpleName(),name.get(2)+ "is here");

        this.name = name;
        this.email = email;
        this.numbers = numbers;
        this.points = points;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Tab1 t1 = new Tab1();
                t1.setVals(name, email, numbers);
                return t1;
            case 1:
                Tab2 t2 = new Tab2();
                t2.setVals(name,email,numbers,points);
                return t2 ;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "All Contacts";
            case 1:

                return "Contact Maps";
        }
        return null;
    }
}
