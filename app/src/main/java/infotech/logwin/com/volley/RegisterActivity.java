package infotech.logwin.com.volley;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

import java.util.HashMap;
import java.util.Map;

import infotech.logwin.com.volley.R;

public class RegisterActivity extends AppCompatActivity {
    private final static String URL_REGIST = "http://192.168.0.109/mystore/register.php";
    EditText name,email,password,phone;
    RadioButton male,female;
    Button submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        name = findViewById(R.id.idetname);
        male = findViewById(R.id.idrbmale);
        female = findViewById(R.id.idrbfemale);
        email = findViewById(R.id.idetemail);
        password = findViewById(R.id.idetpassword);
        phone = findViewById(R.id.idetphone);
        submit = findViewById(R.id.idetsubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "tetetete", Toast.LENGTH_SHORT).show();
                register();
            }
        });

    }

    private void register() {
        final String name = this.name.getText().toString();
        final String gendermale = this.male.getText().toString();
        final String genderfemale = this.female.getText().toString();
        final String email = this.email.getText().toString();
        final String password = this.password.getText().toString();
        final String phone = this.phone.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")){
                        Toast.makeText(RegisterActivity.this, "Registration Success!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "Register Special Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                    //btn_regist.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterActivity.this, "Register Error2! " + error.toString(), Toast.LENGTH_SHORT).show();
                    //btn_regist.setVisibility(View.VISIBLE);
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name);
                    params.put("email", email);
                    params.put("password", password);
                    if(male.isChecked()){
                        params.put("gender", gendermale);
                    }else{
                        params.put("gender", genderfemale);
                    }
                    params.put("contact", phone);

                    return params;
                }
            };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
