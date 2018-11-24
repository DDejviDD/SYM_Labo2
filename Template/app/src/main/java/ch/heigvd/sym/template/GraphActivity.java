package ch.heigvd.sym.template;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;

public class GraphActivity extends AppCompatActivity {

    // GUI elements
    Spinner authorsSpinner = null;
    ScrollView postView = null;


    ArrayAdapter<String> authorsAdapterSpinner;
    List<String> authorsList = new ArrayList<>();
    Context savedContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);

        // Link the GUI element with view
        authorsSpinner = findViewById(R.id.authorsSpinner);
        postView = findViewById(R.id.postView);

        // Request to fill the Spinner options
        AsynchronousRequest request = new AsynchronousRequest();
        request.newListener(
                new RequestListener() {
                    @Override
                    public boolean handlerForRequest(final String response) {
                        try {
                            JSONArray authorsJSON = new JSONObject(response).getJSONObject("data").getJSONArray("allAuthors");
                            for (int i = 0; i < authorsJSON.length(); ++i) {
                                authorsList.add(authorsJSON.getJSONObject(i).getString("first_name") + " "
                                        + authorsJSON.getJSONObject(i).getString("last_name"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return false;
                        }

                        runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        authorsAdapterSpinner = new ArrayAdapter<>(savedContext, android.R.layout.simple_spinner_item, authorsList);
                                        authorsAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        authorsSpinner.setAdapter(authorsAdapterSpinner);
                                    }
                                }
                        );
                        return true;
                    }
                }
        );
        request.postRequest("{ \"query\": \"{allAuthors{id, first_name, last_name }}\" }", "http://sym.iict.ch/api/graphql", MediaType.parse("application/json; charset=utf-8"));

        // Request to get the post according to the author selected
        
    }
}