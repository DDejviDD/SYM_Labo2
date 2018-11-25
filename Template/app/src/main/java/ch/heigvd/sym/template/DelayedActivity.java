package ch.heigvd.sym.template;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import okhttp3.MediaType;

public class DelayedActivity extends AppCompatActivity {
    private EditText edit_delayed_data;
    private Button delayed_send_request;
    private TextView delayed_result;

    private List<String> pool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delayed);

        pool = Collections.synchronizedList(new ArrayList<String>());

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()){
                    while (!pool.isEmpty()){
                        sendRequest(pool.get(0));
                        pool.remove(0);
                    }
                }
                try{
                    Thread.sleep(30000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
        edit_delayed_data = findViewById(R.id.edit_delayed_data);
        delayed_send_request = findViewById(R.id.delayed_send_request);
        delayed_result = findViewById(R.id.delayed_result);

        delayed_send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pool.add(edit_delayed_data.getText().toString());
                edit_delayed_data.getText().clear();
            }
        });
    }

    public void sendRequest(String request) {
        AsynchronousRequest aRequest = new AsynchronousRequest();
        aRequest.newListener(new RequestListener() {
            @Override
            public boolean handlerForRequest(final String response) throws IOException, DataFormatException {

                ArrayList<String> lines = responseParser(response);

                final String serverResponse = lines.get(0) +"\n";

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // delayed_result.append(serverResponse, 0, 0);
                        String tmp = serverResponse + delayed_result.getText().toString();
                        delayed_result.setText(tmp);
                    }
                });

                return true;
            }
        });
        aRequest.postRequest(request,"http://sym.iict.ch/rest/txt", MediaType.parse("text/plain; charset=utf-8"));
    }
    public ArrayList responseParser(String response){

        ArrayList<String> parsedLines = new ArrayList<>();
        Scanner scanner = new Scanner(response);

        while (scanner.hasNextLine()) {
            String parsedLine = scanner.nextLine();
            parsedLines.add(parsedLine);
        }

        scanner.close();

        return parsedLines;

    }
}
