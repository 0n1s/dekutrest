package asuper.maathis.maathai;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Grocery extends AppCompatActivity {

    Button button;
    String fetcher="http://code0.co.ke/apps/maathai/android/item_fetcher.php";
    String saver="http://code0.co.ke/apps/maathai/android/saver.php";
    ListView listView;
    public static final String MyPREFERENCES = "MyPrefs";
    String what="";
    List<String> myList = new ArrayList<String>();
    List<String> fetcherlist = new ArrayList<String>();
    SharedPreferences sharedpreferences;
    HashMap<String, String> shoppinglist = new HashMap<>();
    String shopping;
    StringBuilder stringBuilder = new StringBuilder();
    int bei;
    String finalString;
    String activity;
    String beibei;
    String dater;
    String uID;
    String finalStringsl;
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
        setContentView(R.layout.activity_grocery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //button=(Button)findViewById(R.id.button);
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        listView=(ListView)findViewById(R.id.listview);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick();
            }
        });
        Intent intent =getIntent();
        what=intent.getStringExtra("category");
        uID=intent.getStringExtra("uDI");

        getJSON();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date datee= new Date();
        dater=dateFormat.format(datee);

         finalStringsl= sharedpreferences.getString("sl", "null");
        beibei=sharedpreferences.getString("bei","null");



        if(finalStringsl.isEmpty())
        {
            Toast.makeText(Grocery.this, "Shopping list is empty", Toast.LENGTH_SHORT).show();
            finalString="";
        }
        else
        {
           // Toast.makeText(this, finalStringsl, Toast.LENGTH_SHORT).show();
            finalString=finalStringsl;
        }
        activity=sharedpreferences.getString("activity","null");




        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                final  String itemid =   map.get("name");
                final  String price=map.get("price");

                AlertDialog.Builder build = new AlertDialog.Builder(Grocery.this);
                build.setTitle("Please Confirm");
                build.setMessage("Are you sure you want to add \n"+itemid+"\nto the shopping list");

                build.setPositiveButton("YES", new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String finalStringsl = sharedpreferences.getString("sl", "null");
//                        if(finalStringsl.isEmpty())
//                        {
//                            bei =0;
//                            stringBuilder.append(itemid+"\t"+price+"\n");
//                            finalString = stringBuilder.toString();
//                            bei=bei+Integer.parseInt(price);
//                            Toast.makeText(Grocery.this,itemid +" Added to the shopping list", Toast.LENGTH_SHORT).show();
//                            SharedPreferences.Editor editor = sharedpreferences.edit();
//                            editor.putString("sl", finalString);
//                            editor.putString("bei",Integer.toString(bei));
//                            editor.commit();
//                        }
//                        else
//                        {
                        if (activity != what) {


                            if (finalStringsl.isEmpty()) {

                                stringBuilder.setLength(0);
                                finalString="";
                                bei = 0;
                                stringBuilder.append(itemid + "\t" + price + "\n");
                                finalString = stringBuilder.toString();
                                bei = bei + Integer.parseInt(price);
                                Toast.makeText(Grocery.this, itemid + " Added to the shopping list", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("sl", finalString);
                                editor.putString("bei", Integer.toString(bei));
                                editor.commit();
                                finalStringsl=finalString;

                            } else {
                                bei=0;
                                stringBuilder.setLength(0);
                                stringBuilder.append(finalStringsl);
                                stringBuilder.append(itemid + "\t" + price + "\n");
                                finalString = stringBuilder.toString();
                                bei=Integer.parseInt(beibei)+Integer.parseInt(price);
                                Toast.makeText(Grocery.this, itemid + " Added to the shopping list", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("sl", finalString);
                                editor.putString("bei", Integer.toString(bei));
                                editor.commit();
                               // bei = bei +  + Integer.parseInt(beibei);
                                //  Toast.makeText(Grocery.this, bei, Toast.LENGTH_SHORT).show();
                                activity = what;
                            }
                        } else if (activity.equals(what))
                        {
                            if (finalStringsl.isEmpty())
                            {

                                bei = 0;
                                finalString="";
                                stringBuilder.setLength(0);

                                stringBuilder.append(itemid + "\t" + price + "\n");
                                finalString = stringBuilder.toString();
                                bei = bei + Integer.parseInt(price);

                                Toast.makeText(Grocery.this, itemid + " Added to the shopping list", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("sl", finalString);
                                editor.putString("bei", Integer.toString(bei));
                                editor.commit();
                                finalStringsl=finalString;
                            }
                            else {

                                stringBuilder.append(itemid + "\t" + price + "\n");
                                bei = bei + Integer.parseInt(price);
                                finalString = stringBuilder.toString();
                                Toast.makeText(Grocery.this, itemid + " Added to the shopping list", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("sl", finalString);
                                editor.putString("activity", what);
                                editor.commit();
                                finalStringsl=finalString;
                            }


                        }

                        //}


                    }
                });
                build.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                build.show();



            }
        });




    }

    public void onclick()

    {


        String[] arr = {finalString,"Total Price \t"+Integer.toString(bei)};
        AlertDialog.Builder build = new AlertDialog.Builder(Grocery.this);
        build.setTitle("SHOPPING LIST CONTENTS\nITEM\t\t\t\tPRICE");
        build.setItems(arr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        build.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                saveshoppinglisttodb(finalString,Integer.toString(bei));
                finalString = "";
                stringBuilder.setLength(0);
                bei=0;
             //   Toast.makeText(Grocery.this,"Shopping list deleted", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sl", finalString);
                editor.commit();
            }
        });
        build.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finalString = "";
                stringBuilder.setLength(0);
                Toast.makeText(Grocery.this,"Shopping list deleted", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sl", finalString);
                editor.commit();
            }
        });
        build.show();


    }



    public void getJSON()
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            SweetAlertDialog pDialog = new SweetAlertDialog(Grocery.this, SweetAlertDialog.PROGRESS_TYPE);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading items");
                pDialog.setCancelable(false);
                pDialog.show();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String,String> parabms = new HashMap<>();
                parabms.put("item_category",what);
                String res = rh.sendPostRequest(fetcher, parabms);
                return res;

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                showthem(s);
               // Toast.makeText(Grocery.this, s, Toast.LENGTH_SHORT).show();

            }


        }
        GetJSON jj =new GetJSON();
        jj.execute();
    }


    public void saveshoppinglisttodb(final String contents, final String prizex)
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            SweetAlertDialog pDialog = new SweetAlertDialog(Grocery.this, SweetAlertDialog.PROGRESS_TYPE);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading items");
                pDialog.setCancelable(false);
                pDialog.show();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String,String> parabms = new HashMap<>();
                parabms.put("items",contents);
                parabms.put("price",prizex);
                parabms.put("date",dater);
                parabms.put("trxID",String.valueOf(System.currentTimeMillis()));
                parabms.put("uid",uID);
                String res = rh.sendPostRequest(saver, parabms);
                return res;

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                showthem2(s);
                Toast.makeText(Grocery.this, s, Toast.LENGTH_SHORT).show();

            }


        }
        GetJSON jj =new GetJSON();
        jj.execute();
    }

    public void showthem2(String s)
    {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(s);
            JSONArray result = jsonObject.getJSONArray("result");

            String itemID="";
            String succes="0";
            for (int i = 0; i < result.length(); i++)
            {  JSONObject jo = result.getJSONObject(i);

                String success=jo.getString("status");

                if(success.equals("0"))
                {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Something went wrong")
                            .show();
                }
                else{
                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Huuuray!")
                            .setContentText("Shpping list saved succesifully")
                            .show();
                }

            }
        } catch (JSONException e) {

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Something went wrong")
                    .show();
            e.printStackTrace();
        }}

    private void showthem(String s) {

        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(s);
            JSONArray result = jsonObject.getJSONArray("result");

            String itemID="";
            String succes="0";
            for (int i = 0; i < result.length(); i++)
            {  JSONObject jo = result.getJSONObject(i);

                String name=jo.getString("name");
                String price=jo.getString("price");

                HashMap<String, String> employees = new HashMap<>();
                employees.put("name", name);
                employees.put("price", price);
                list.add(employees);
            }



        } catch (JSONException e) {

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText(e.toString())
                    .show();

        }

        if(what.equals("grocery"))
        {
            ListAdapter adapter = new SimpleAdapter(Grocery.this, list, R.layout.itemlayout,
                    new String[]{"name", "price"}, new int[]{R.id.name, R.id.price});
            listView.setAdapter(adapter);
        }
        else if(what.equals("foods"))
        {
            ListAdapter adapter = new SimpleAdapter(Grocery.this, list, R.layout.foods,
                    new String[]{"name", "price"}, new int[]{R.id.name, R.id.price});
            listView.setAdapter(adapter);
        }
        else if(what.equals("furniture"))
        {
            ListAdapter adapter = new SimpleAdapter(Grocery.this, list, R.layout.furniture,
                    new String[]{"name", "price"}, new int[]{R.id.name, R.id.price});
            listView.setAdapter(adapter);
        }
        else if(what.equals("utensils"))
        {
            ListAdapter adapter = new SimpleAdapter(Grocery.this, list, R.layout.utensils,
                    new String[]{"name", "price"}, new int[]{R.id.name, R.id.price});
            listView.setAdapter(adapter);
        }
        else if(what.equals("electronics"))
        {
            ListAdapter adapter = new SimpleAdapter(Grocery.this, list, R.layout.electronics,
                    new String[]{"name", "price"}, new int[]{R.id.name, R.id.price});
            listView.setAdapter(adapter);
        }
        else if(what.equals("offers"))
        {
            ListAdapter adapter = new SimpleAdapter(Grocery.this, list, R.layout.offers,
                    new String[]{"name", "price"}, new int[]{R.id.name, R.id.price});
            listView.setAdapter(adapter);
        }


    }




}


/*

if(!succes.equals("1"))
{
    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
            .setContentText("An error occured")
            .setTitleText("Whooops!")
            .show();
}
            else
{
    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            .setContentText("Save succeeded")
            .setTitleText("Huuray")
            .show();
}


        } catch (JSONException e) {

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText(e.toString())
                    .show();

        }

 */






/*
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.commit();
 */






/*
Loading Array Data from SharedPreferences

public static void loadArray(Context mContext)
{
    SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(mContext);
    sKey.clear();
    int size = mSharedPreference1.getInt("Status_size", 0);

    for(int i=0;i<size;i++)
    {
     sKey.add(mSharedPreference1.getString("Status_" + i, null));
    }

}
 */



/*


Saving Array in SharedPreferences:

public static boolean saveArray()
{
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor mEdit1 = sp.edit();
     sKey is an array
mEdit1.putInt("Status_size", sKey.size());

        for(int i=0;i<sKey.size();i++)
        {
        mEdit1.remove("Status_" + i);
        mEdit1.putString("Status_" + i, sKey.get(i));
        }

        return mEdit1.commit();
        }


 */


