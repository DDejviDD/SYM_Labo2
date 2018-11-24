package ch.heigvd.sym.template;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

import okhttp3.MediaType;

public class CompressedActivity extends AppCompatActivity {

    private Button compressContent;
    private TextView contentToCompress;
    private TextView contentCompressed;

    private TextView contentDecompressed;
    private TextView lengthComparison;

    private String textFromServer;

    // Source : https://dzone.com/articles/how-compress-and-uncompress

    @Override
    protected void onCreate(Bundle instanceState) {
        super.onCreate(instanceState);
        setContentView(R.layout.compressed);

        compressContent = findViewById(R.id.buttComp);
        contentToCompress = findViewById(R.id.contentCompress);
        contentCompressed = findViewById(R.id.contentCompressed);
        contentDecompressed = findViewById(R.id.contentDecompress);
        lengthComparison = findViewById(R.id.lengthCompare);

        compressContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(contentToCompress.getText().toString());

                try {
                    postRequest(contentToCompress.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                lengthComparison.setText( "Length before compression: " +
                        contentToCompress.toString().length()
                        + "\n" + "Length after compression: "
                        + contentCompressed.toString().length());

            }
        });
    }

    public void postRequest(String rqst) throws IOException {
        AsynchronousRequest request = new AsynchronousRequest() ;
        request.newListener(
                new RequestListener(){
                    public boolean handlerForRequest(final String response) throws IOException, DataFormatException {

                        // here we should decompress response

                        System.out.println(response);

                        // decompress data on server

                        final String serverResponse = getDecompressed(response).toString();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                contentDecompressed.setText(serverResponse);
                            }
                        });

                        return true;
                    }
                }
        );

        // here we should compress data before sending it

        String compressedData = compressData().toString();

        // show how data was compressed
        contentCompressed.setText(compressedData);

        request.postRequest(compressedData,"http://sym.iict.ch/rest/txt", MediaType.parse("text/plain; charset=utf-8"));
    }


    public byte[] compressData() throws IOException {

        byte[] data = contentToCompress.getText().toString().getBytes();

        Deflater deflater = new Deflater();
        deflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        DeflaterOutputStream compressed = new DeflaterOutputStream(outputStream,deflater);

        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();

        byte[] output = outputStream.toByteArray();


        return output;
    }

    public byte[] getDecompressed(final String data) throws IOException, DataFormatException {


        Inflater inflater = new Inflater();
        inflater.setInput(data.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length());
        InflaterOutputStream decompressed = new InflaterOutputStream(outputStream,inflater);

        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();

        byte[] output = outputStream.toByteArray();

        return output;
    }
}
