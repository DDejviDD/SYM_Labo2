package ch.heigvd.sym.template;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import okhttp3.MediaType;

public class DelayedActivity extends AppCompatActivity {
    private EditText edit_delayed_data;
    private Button delayed_send_request;
    private TextView delayed_result;
    private Handler handler = new Handler();

    private final int DELAY = 10000;

    private ArrayList<String> pool = new ArrayList<>();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isOnline() && !pool.isEmpty()) {
                while (!pool.isEmpty()) {
                    if (isOnline()) {
                        sendRequest(pool.get(0));
                        pool.remove(0);
                    } else {
                        handler.postDelayed(runnable, DELAY);
                    }
                }
            } else {
                handler.postDelayed(runnable, DELAY);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delayed);

        edit_delayed_data = findViewById(R.id.edit_delayed_data);
        delayed_send_request = findViewById(R.id.delayed_send_request);
        delayed_result = findViewById(R.id.delayed_result);

        delayed_send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pool.add(edit_delayed_data.getText().toString());
                edit_delayed_data.getText().clear();
                if (!pool.isEmpty()) {
                    handler.postDelayed(runnable, DELAY);
                }
            }
        });
    }

    public void sendRequest(String request) {
        AsynchronousRequest aRequest = new AsynchronousRequest();
        aRequest.newListener(new RequestListener() {
            @Override
            public boolean handlerForRequest(final String response) throws IOException, DataFormatException {

                ArrayList<String> lines = responseParser(response);

                final String serverResponse = lines.get(0) + "\n";

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
        aRequest.postRequest(request, "http://sym.iict.ch/rest/txt", MediaType.parse("text/plain; charset=utf-8"));

    }

    public ArrayList responseParser(String response) {

        ArrayList<String> parsedLines = new ArrayList<>();
        Scanner scanner = new Scanner(response);

        while (scanner.hasNextLine()) {
            String parsedLine = scanner.nextLine();
            parsedLines.add(parsedLine);
        }

        scanner.close();

        return parsedLines;

    }

    /**
     * Check internet connections as seen in :
     * https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
