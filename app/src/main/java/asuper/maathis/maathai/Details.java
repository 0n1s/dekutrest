package asuper.maathis.maathai;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import us.feras.mdv.MarkdownView;

import static asuper.maathis.maathai.MainActivity.item_id;
import static asuper.maathis.maathai.MainActivity.jsonArray;
import static asuper.maathis.maathai.SignIN.useruser;
import static asuper.maathis.maathai.URLs.img_url;

public class Details extends AppCompatActivity {


    MarkdownView markdownView;
    BootstrapLabel name;
    public static final String MyPREFERENCES = "MyPrefs";
    ImageView image;
    BootstrapButton btn;
    SharedPreferences sharedPreferences;
    String quantity_remaining;
    TextView price;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3A1212")));

        markdownView =(MarkdownView)findViewById(R.id.markdownView);
        name =(BootstrapLabel)findViewById(R.id.textView13);
        image =(ImageView)findViewById(R.id.imageView5);

        btn=(BootstrapButton)findViewById(R.id.button);

        BootstrapButton cart= (BootstrapButton)findViewById(R.id.button4);

        cart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(Details.this, Cart.class));


            }
        });

        price=(TextView)findViewById(R.id.textView15);
        Intent intent = getIntent();
        final   String item_price= intent.getStringExtra("item_price");
        String image_url= intent.getStringExtra("image_url");
        final String item_name= intent.getStringExtra("item_name");
        quantity_remaining=intent.getStringExtra("quantity_remaining");
        String item_description= intent.getStringExtra("item_description");
        price.setText("KES "+item_price);
        //Toast.makeText(this, item_description, Toast.LENGTH_SHORT).show();
        markdownView.loadMarkdown("## "+item_description);
        name.setText(item_name);
        String final_url=img_url+image_url;



        Picasso.Builder builder = new Picasso.Builder(Details.this);
        builder.listener(new Picasso.Listener()
        {

            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
            }
        });
        builder.build().load(final_url).into(image);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = Calendar.getInstance().getTime();
                //
                // Display a date in day, month, year format
                //
                DateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy hh:mm:ss aaa");
                final String today = formatter.format(date);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Details.this);
                builder1.setMessage("Are you sure you want to add the items to your cart?");
                builder1.setCancelable(false);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();


                                EditText editText = (EditText)findViewById(R.id.editText);
                                String qnty = editText.getText().toString().trim();

                                if(qnty.length()<1)
                                {
                                    Toast.makeText(Details.this, "Enter the quantity", Toast.LENGTH_SHORT).show();


                                }else if (Integer.parseInt(qnty)>Integer.parseInt(quantity_remaining))
                                {
                                    Toast.makeText(Details.this, "Please reduce your quantity", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    {


                                        String shopping_list= sharedPreferences.getString("shopping_list", "null");

                                        if(shopping_list.equals("null"))
                                        {
                                            jsonArray   = new JSONArray();
                                        }
                                        else {

                                            try {
                                                jsonArray = new JSONArray(shopping_list);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    int amt = Integer.parseInt(item_price);
                                    int qy = Integer.parseInt(qnty);

                                    int final_price = amt * qy;

                                    JSONObject current_item = new JSONObject();
                                    try {
                                        current_item.put("id", String.valueOf(item_id));
                                        current_item.put("food_name", item_name);
                                        current_item.put("quantity", String.valueOf(qy));
                                        current_item.put("final_amt", String.valueOf(final_price));
                                        current_item.put("date_time", today);


                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    item_id = item_id + 1;
                                    jsonArray.put(current_item);
                                       // Toast.makeText(Details.this, String.valueOf(jsonArray), Toast.LENGTH_SHORT).show();

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("shopping_list", String.valueOf(jsonArray));
                                        editor.commit();

                                        final AlertDialog.Builder alert = new AlertDialog.Builder(Details.this);
                                    alert.setTitle("Succcess");
                                    alert.setMessage(item_name +" added to the shopping list");
                                    alert.setPositiveButton("Purchase now!", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            startActivity(new Intent(Details.this, Cart.class));


                                        }
                                    }).setNegativeButton("continue", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();


                                }





                            }
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();



            }
        });



    }



}
