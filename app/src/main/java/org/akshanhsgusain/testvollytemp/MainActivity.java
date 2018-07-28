package org.akshanhsgusain.testvollytemp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
   String mURL="https://api.androidhive.info/contacts/";
   String value="";
   private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView=findViewById(R.id.textView);
        callAPI();
        Log.d("kaal", "onCreate: called callAPI method");
        Toast.makeText(this, value, Toast.LENGTH_LONG).show();
    }

    private void callAPI()
    {
        Log.d("kaal", "callAPI: url" + mURL);
          final VolleyMultipartRequest request =new VolleyMultipartRequest(Request.Method.GET, mURL, new Response.Listener<NetworkResponse>() {
              @Override
              public void onResponse(NetworkResponse response) {
                       String resp= new String(response.data);

                  Log.d("kaal", "onResponse: kasaksasa"+resp);
                  try {
                        JSONObject obj=new JSONObject(resp);
                            JSONArray first = obj.getJSONArray("contacts");
                              for(int i=0; i<first.length();i++) {
                                  JSONObject obj2 = first.getJSONObject(i);
                                      value=value+obj2.getString("id")+" ";
                                  value=value+obj2.getString("name")+" ";
                                  value=value+obj2.getString("email")+" ";
                                   value=value+obj2.getString("address")+" ";
                                  value=value+obj2.getString("gender")+",";
                                    JSONObject phone=obj2.getJSONObject("phone");
                                      value=value+phone.getString("mobile")+",";
                                      value=value+phone.getString("home")+",";
                                      value=value+phone.getString("office")+"\n\n";
                              }
                      Log.d("kaal", "onResponse: "+value);
                     mTextView.setText(value);

                  } catch (JSONException e) {
                      e.printStackTrace();
                      Log.d("kaal", "onResponse: "+e);
                  }
              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                  Log.d("kaal", "onErrorResponse: ");
              }
          });
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(request);
    }
}
