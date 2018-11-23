/*
 * File     : OnSuccessActivity.java
 * Project  : TemplateActivity
 * Authors  : FRUEH Loïc
 *            KOUBAA WALID
 *            MUAREMI DEJVID

 */
package ch.heigvd.sym.template;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class OnSuccessActivity extends AppCompatActivity {

    private TextView email = null;
    private TextView Imei = null;
    private ImageView image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_success);

        // Récuperation de l'e-mail (et du password)
        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");

        // Linkage entre les attribut et les éléments GUI
        this.email = findViewById(R.id.email);
        this.Imei = findViewById(R.id.IMEI);
        this.image = findViewById(R.id.image);

        // "Remplissage" des éléments GUI avec les bonnes infos
        this.email.setText(email);
        this.Imei.setText(getIMEINumber());



        // Affichage de l'image de profile
        File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File avatar = new File(downloads.getAbsolutePath() + "/perso.png");
        Bitmap image = BitmapFactory.decodeFile(avatar.getAbsolutePath());
        this.image.setImageBitmap(image);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.println(Log.INFO, "", "ON START !!!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.println(Log.INFO, "", "ON RESUME !!!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.println(Log.INFO, "", "ON PAUSE !!!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.println(Log.INFO, "", "ON STOP !!!");
    }



    @SuppressWarnings("deprecation")
    private String getIMEINumber() {
        String IMEINumber = "";
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                IMEINumber = telephonyMgr.getImei();
            } else {
                IMEINumber = telephonyMgr.getDeviceId();
            }
        }
        return IMEINumber;
    }



}
