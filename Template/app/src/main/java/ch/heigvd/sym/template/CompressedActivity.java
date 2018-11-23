package ch.heigvd.sym.template;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

public class CompressedActivity extends Activity {

    private Button compressContent;
    private TextView contentToCompress;
    private TextView contentCompressed;
    private TextView lengthComparison;

    @Override
    protected void onCreate(Bundle instanceState) {
        super.onCreate(instanceState);

        compressContent = findViewById(R.id.buttCompTrans);
        contentToCompress = findViewById(R.id.buttCompTrans);
        contentCompressed = findViewById(R.id.buttCompTrans);
        lengthComparison = findViewById(R.id.buttCompTrans);

        compressContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] bytes = new byte[0];
                try {
                    bytes = postCompress();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                contentCompressed.setText(bytes.toString());
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

    public void getDecompressed(){

        Inflater inflater = new Inflater();




        lengthComparison.setText( "Length before compression: " +
                                    compressContent.getText().length()
                                    + "\n" + "Length after compression: "
                                    );

    }
}
