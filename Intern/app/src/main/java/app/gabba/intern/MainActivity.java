package app.gabba.intern;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final int select_photo = 1;
    private DatePickerDialog day;
    private TimePickerDialog TIME;
    private SimpleDateFormat dateformatter,timeformatter;
    Toolbar tool;
    EditText name,company,number;
    TextView date,time;
    Button butt;
    ImageView image;
    ImageButton camera,date_button,time_button,insert;
    RelativeLayout layout;
    String imgDecodableString,Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tool = (Toolbar)findViewById(R.id.tool);
        name = (EditText)findViewById(R.id.name);
        company = (EditText)findViewById(R.id.company);
        number = (EditText)findViewById(R.id.number);
        time = (TextView)findViewById(R.id.Time);
        date = (TextView)findViewById(R.id.date);
        butt = (Button)findViewById(R.id.butt);
        date_button = (ImageButton)findViewById(R.id.date_button);
        time_button = (ImageButton)findViewById(R.id.time_button);
        camera = (ImageButton)findViewById(R.id.click);
        insert = (ImageButton)findViewById(R.id.insert);
        layout = (RelativeLayout)findViewById(R.id.image_layout);
        image = (ImageView)findViewById(R.id.Image);
        dateformatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        timeformatter = new SimpleDateFormat("hh:mm a");
        setSupportActionBar(tool);
        tool.setTitle(R.string.app_name);
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        date.setText(dateformatter.format(c.getTime()));
        Log.d(MainActivity.class.getSimpleName(), "found" + hour + ":" + min);
        time.setText(hour + ":" + min);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post_data();
            }
        });
        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
                day.show();
            }
        });
        time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
                TIME.show();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click();
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setTime() {
        Calendar c = Calendar.getInstance();
        int hour= c.get(Calendar.HOUR);
        int min= c.get(Calendar.MINUTE);

        TIME = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newtime = Calendar.getInstance();
                time.setText(hourOfDay+":"+minute);
            }
        },hour,min,true);
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        day = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newdate = Calendar.getInstance();
                newdate.set(year,monthOfYear,dayOfMonth);
                date.setText(dateformatter.format(newdate.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void click() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);

    }
    private void upload() {
        Intent in = new Intent(Intent.ACTION_PICK);
        in.setType("image/*");
        startActivityForResult(in, select_photo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == select_photo && resultCode == RESULT_OK
                    ) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);

                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                // Move to first row
                cursor.moveToFirst();

                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
           //     image.setImageBitmap(BitmapFactory
             //           .decodeFile(imgDecodableString));
               // image.setVisibility(View.VISIBLE);
               //layout.setVisibility(View.INVISIBLE);
                Bitmap bitmap = decodeUri(MainActivity.this, selectedImage, 300);// call
                Image = getStringImage(bitmap);
                Log.d(MainActivity.class.getSimpleName(),"made"+Image);
                // deocde
                // uri
                // method
                // Check if bitmap is not null then set image else show
                // toast
                if (bitmap != null)
                    image.setImageBitmap(bitmap);// Set image over
                    image.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.INVISIBLE);


            } else if (requestCode == 0 && resultCode == RESULT_OK){
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(bp);
                image.setVisibility(View.VISIBLE);
                layout.setVisibility(View.INVISIBLE);

            }else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
    // Method that deocde uri into bitmap. This method is necessary to deocde
    // large size images to load over imageview
    public static Bitmap decodeUri(Context context, Uri uri,
                                   final int requiredSize) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(uri), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(uri), null, o2);
    }
    private void post_data() {
        final String Name,Company,num,Date,Time,log;
        int t=0;
         Name = name.getText().toString();
         if(Name.length() == 0 || Name.length() < 3){
             if(Name.length() ==0)
                name.setError("Required");
             else
                 name.setError("Atleast 3 letter required");
         }else{
             t++;
         }
         Company = company.getText().toString();
         if(Company.length() == 0){
             company.setError("Required");
         }else{
             t++;
         }
         num = number.getText().toString();
         if(num.length() == 0 || num.length()<10){
             if(num.length() == 0)
                 number.setError("Required");
             else{
                 number.setError("Enter "+(10-num.length())+" more digits");
             }
         }
         else{
             t++;
         }
         Date = date.getText().toString();
         Time = time.getText().toString();
        Log.d(MainActivity.class.getSimpleName(),Date + "nan"+Time);
        log = date +" "+time;
if(t==3){
        StringRequest sreq = new StringRequest(Request.Method.POST, "http://52.36.221.91/webapi/imageload.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        try {
                            JSONObject obj = new JSONObject(response);
                            int api = obj.getInt("API Result");
                            if(api == 0){
                                name.setText("");
                                company.setText("");
                                number.setText("");
                                image.setVisibility(View.INVISIBLE);
                                layout.setVisibility(View.VISIBLE);
                                Calendar c = Calendar.getInstance();
                                int hour = c.get(Calendar.HOUR_OF_DAY);
                                int min = c.get(Calendar.MINUTE);
                                date.setText(dateformatter.format(c.getTime()));
                                Log.d(MainActivity.class.getSimpleName(), "found" + hour + ":" + min);
                                time.setText(hour + ":" + min);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("VName",Name );
                params.put("VFrom", Company);
                params.put("VMobno",num);
                params.put("VLogDate", log);
                params.put("FILES",Image );
                return params;
            }
        };
        RequestQueue req = Volley.newRequestQueue(this);
        req.add(sreq);
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
