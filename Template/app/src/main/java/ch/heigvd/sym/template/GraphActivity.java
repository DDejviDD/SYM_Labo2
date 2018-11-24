package ch.heigvd.sym.template;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;

class Author {
    private String id;
    private String firstName;
    private String lastName;


    public Author(String id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}

public class GraphActivity extends AppCompatActivity {

    // GUI elements
    Spinner authorsSpinner = null;
    ScrollView postView = null;

    // Useful list and context
    ArrayAdapter<String> authorsAdapterSpinner;
    List<Author> authorsList = new ArrayList<>();
    List<String> authorsNamesList = new ArrayList<>();
    List<String> postsList = new ArrayList<>();
    Context savedContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);

        // Link the GUI element with view
        authorsSpinner = findViewById(R.id.authorsSpinner);


        // Request to fill the Spinner options
        AsynchronousRequest authorsRequest = new AsynchronousRequest();
        authorsRequest.newListener(
                new RequestListener() {
                    @Override
                    public boolean handlerForRequest(final String response) {
                        try {
                            JSONArray authorsJSON = new JSONObject(response).getJSONObject("data").getJSONArray("allAuthors");
                            for (int i = 0; i < authorsJSON.length(); ++i) {
                                String id = authorsJSON.getJSONObject(i).getString("id");
                                String firstName = authorsJSON.getJSONObject(i).getString("first_name");
                                String last_name = authorsJSON.getJSONObject(i).getString("last_name");
                                Author author = new Author(id, firstName, last_name);

                                authorsList.add(author);
                                authorsNamesList.add(author.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return false;
                        }

                        runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        authorsAdapterSpinner = new ArrayAdapter<>(savedContext, android.R.layout.simple_spinner_item, authorsNamesList);
                                        authorsAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        authorsSpinner.setAdapter(authorsAdapterSpinner);
                                    }
                                }
                        );
                        return true;
                    }
                }
        );
        authorsRequest.postRequest("{ \"query\": \"{allAuthors{id, first_name, last_name }}\" }", "http://sym.iict.ch/api/graphql", MediaType.parse("application/json; charset=utf-8"));

        // Request to get the posts according to the author selected
        authorsSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Author author = authorsList.get((int)id);

                        AsynchronousRequest postsRequest = new AsynchronousRequest();
                        postsRequest.newListener(
                                new RequestListener() {
                                    @Override
                                    public boolean handlerForRequest(final String response) {
                                        try {
                                            JSONArray postsJSON = new JSONObject(response).getJSONObject("data").getJSONArray("allPostByAuthor");
                                            postsList.clear();
                                            for (int i = 0; i < postsJSON.length(); ++i) {

                                                String title = postsJSON.getJSONObject(i).getString("title");
                                                String content = postsJSON.getJSONObject(i).getString("content");
                                                String date = postsJSON.getJSONObject(i).getString("date");
                                                postsList.add(title + "\n\n" + content + "\n\n" + date + "\n\n\n\n");
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            return false;
                                        }


                                        runOnUiThread(
                                                new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        String posts = "";
                                                        for (String post : postsList) {
                                                            posts += post;
                                                        }
                                                        postView = findViewById(R.id.postView);
                                                        postView.removeAllViews();
                                                        TextView content = new TextView(savedContext);
                                                        content.setText(posts);
                                                        postView.addView(content);
                                                    }
                                                }
                                        );
                                        return true;
                                    }
                                }
                        );
                        postsRequest.postRequest("{\"query\": \"{allPostByAuthor(authorId: " + author.getId() + "){title content date}}\"}", "http://sym.iict.ch/api/graphql", MediaType.parse("application/json; charset=utf-8"));

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        TextView content = new TextView(savedContext);
                        content.setText("Veuillez selectionner un auteur");
                        postView.addView(content);
                    }
                }
        );
    }
}