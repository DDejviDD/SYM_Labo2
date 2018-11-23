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
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class CompressedActivity extends AppCompatActivity {

    private Button compressContent;
    private TextView contentToCompress;
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
        contentDecompressed = findViewById(R.id.contentDecompress);
        lengthComparison = findViewById(R.id.lengthCompare);

        compressContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] bytesSended = new byte[0];
                byte[] bytesReceived = new byte[0];

                try {
                    bytesSended = postCompress();
                    bytesReceived = getDecompressed();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DataFormatException e) {
                    e.printStackTrace();
                }
                contentDecompressed.setText(bytesReceived.toString());

                lengthComparison.setText( "Length before compression: " +
                        bytesSended.toString().length()
                        + "\n" + "Length after decompression: "
                        + bytesReceived.toString().length());
            }
        });
    }

    public byte[] postCompress() throws IOException {

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

    public byte[] getDecompressed() throws IOException, DataFormatException {

        byte[] data = textFromServer.getBytes();

        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
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
