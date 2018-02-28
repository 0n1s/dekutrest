package asuper.maathis.maathai;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Events extends AppCompatActivity {
String fetcher="http://code0.co.ke/apps/maathai/android/eventfetcher.php";
    ListView listview;

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
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        listview=(ListView)findViewById(R.id.listview);
        getJSON();

    }



    public void getJSON()
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            SweetAlertDialog pDialog = new SweetAlertDialog(Events.this, SweetAlertDialog.PROGRESS_TYPE);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading Events");
                pDialog.setCancelable(false);
                pDialog.show();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String,String> parabms = new HashMap<>();
                parabms.put("item_category","");
                String res = rh.sendPostRequest(fetcher, parabms);
                return res;

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                showthem2(s);
                 //Toast.makeText(Events.this, s, Toast.LENGTH_SHORT).show();

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


            for (int i = 0; i < result.length(); i++)
            {  JSONObject jo = result.getJSONObject(i);


                    String name=jo.getString("title");
                    String price=jo.getString("description");
                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("name", name);
                    employees.put("price", price);
                    list.add(employees);


            }


            ListAdapter adapter = new SimpleAdapter(Events.this, list, R.layout.events,
                    new String[]{"name", "price"}, new int[]{R.id.activity, R.id.descripption});
            listview.setAdapter(adapter);


        } catch (JSONException e)
        {

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Something went wrong"+e)
                    .show();
            e.printStackTrace();
        }
    }






}
