package infotech.logwin.com.volley;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;

    private final static String urlJsonArry = "http://192.168.0.109/mystore/getdata.php";

 //   private final static String urlJsonObj = "https://api.androidhive.info/volley/person_object.json";

 //   private final static String urlJsonArry = "https://api.androidhive.info/volley/person_array.json";

    private static String TAG = MainActivity.class.getSimpleName();

    TextView txtname,txtemail,txtcall;

/*    private String jsonResponsename;
    private String jsonResponseemail;
    private String jsonResponsecall;
    private String jsonResponsehome;
    private String jsonResponse;  */
    private String jsonResponsename;
    private String jsonResponseemail;
    private String jsonResponsecall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtname = findViewById(R.id.idTvname);
        txtemail = findViewById(R.id.idTvemail);
        txtcall = findViewById(R.id.idTvcall);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        Jsonarray();

    }


    private void Jsonarray(){
        JsonArrayRequest jsonArrayRequest =  new JsonArrayRequest(urlJsonArry, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    // Parsing json array response
                    // loop through each json object
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject person = (JSONObject) response.get(i);
                        Log.d(TAG, person.toString());


                        String message = person.getString("message");
                        JSONObject data = person.getJSONObject("data");
                        String name = data.getString("name");
                        String email = data.getString("email");
                        String contact = data.getString("contact");
                     //   String mobile = phone.getString("mobile");

                        jsonResponsename = "";
                        jsonResponsename = name + "\n\n";

                        jsonResponseemail = "";
                        jsonResponseemail = email + "\n\n";

                        jsonResponsecall = "";
                        jsonResponsecall = contact + "\n\n";

                    //    jsonResponsehome = "";
                    //    jsonResponsehome = home + "\n\n";

                    }

                    txtname.setText(jsonResponsename);
                    txtemail.setText(jsonResponseemail);
                    txtcall.setText(jsonResponsecall);
                  //  txthome.setText(jsonResponsehome);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

               // hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
               // hidepDialog();
            }
        });

        addToRequestQueue(jsonArrayRequest);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        String tag = "";
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add((Request<Object>) req);
    }

    private <T> RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return  mRequestQueue;
    }


    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {

                    //Getting out sharedpreferences
                    SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                    //Getting editor
                    SharedPreferences.Editor editor = preferences.edit();

                    //Puting the value false for loggedin
                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                    //Putting blank value to email
                    editor.putString(Config.EMAIL_SHARED_PREF, "");

                    //Saving the sharedpreferences
                    editor.commit();

                    //Starting login activity
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });

        alertDialogBuilder.setNegativeButton("No",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            //calling logout method when the logout button is clicked
            logout();
        }
        return super.onOptionsItemSelected(item);
    }


  /*
    private void Jsonobject() {
        Toast.makeText(this, "method called...!", Toast.LENGTH_SHORT).show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                Toast.makeText(MainActivity.this, "onResponse MainActivity....", Toast.LENGTH_SHORT).show();

                try {
                        // Parsing json object response
                        // response will be a json object
                        JSONObject data = response.getJSONObject("data");
                        String name = data.getString("name");
                        String email = data.getString("email");
                        String contact = data.getString("contact");
                      //  String gender = response.getString("gender");

    */
/*
                        jsonResponsehome = "";
                        jsonResponsehome = gender + "\n\n";

                        jsonResponse = "";
                        jsonResponse = name + "\n\n";   *//*


                        jsonResponsename = "";
                        jsonResponsename = name + "\n\n";

                        jsonResponseemail = "";
                        jsonResponseemail = email + "\n\n";

                        jsonResponsecall = "";
                        jsonResponsecall = contact + "\n\n";

                     //   txtname.setText(jsonResponse);
                        txtname.setText(jsonResponsename);
                        txtemail.setText(jsonResponseemail);
                        txtcall.setText(jsonResponsecall);
    */
/*                    txtemail.setText(jsonResponseemail);
                        txtcall.setText(jsonResponsecall);
                        txthome.setText(jsonResponsehome);*//*


                        Toast.makeText(MainActivity.this, "show result Jsonobject"+jsonResponsename, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                //hidepDialog();
            }
        }, new Response.ErrorListener() {
            //private VolleyError error;
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                // hidepDialog();
            }

        });

        addToRequestQueue(jsonObjReq);

        //AppController.getInstance().addToRequestQueue(jsonObjReq);

    }  */


}
