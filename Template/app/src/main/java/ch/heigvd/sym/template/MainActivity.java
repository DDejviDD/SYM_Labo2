/*
 * File     : MainActivity.java
 * Project  : TemplateActivity
 * Author   : Markus Jaton 2 juillet 2014
 * 			  Fabien Dutoit 28 août 2018
 *            IICT / HEIG-VD
 *
 * Modified by : FRUEH Loïc
 * 				 KOUBAA Walid
 * 				 MUAREMI DEJVID
 *
 * mailto:fabien.dutoit@heig-vd.ch
 *
 * This piece of code reads a [email_account / password ] combination.
 * It is used as a template project for the SYM module element given at HEIG-VD
 * Target audience : students IL, TS, IE [generally semester 1, third bachelor year]
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE REGENTS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package ch.heigvd.sym.template;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // For logging purposes
    private static final String TAG = MainActivity.class.getSimpleName();

    // GUI elements
    private Button buttObjTrans         = null;
    private Button buttAsynchTrans	    = null;
    private Button buttDiffTrans        = null;
    private Button buttGraphTrans       = null;
    private Button buttCompTrans        = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        // Link GUI elements
        buttObjTrans = findViewById(R.id.buttObjTrans);
        buttAsynchTrans = findViewById(R.id.buttAsynchTrans);
        buttDiffTrans = findViewById(R.id.buttDiffTrans);
        buttGraphTrans = findViewById(R.id.buttGraphTrans);
        buttCompTrans = findViewById(R.id.buttCompTrans);

        // Give buttons an action
        buttObjTrans.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast indicatif + Creation d'un Intent afin de start une nouvelle activité
                Toast.makeText(MainActivity.this, getResources().getString(R.string.good), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, SerializedActivity.class);
                MainActivity.this.startActivity(intent);
            }

        });

        buttAsynchTrans.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Toast indicatif + Creation d'un Intent afin de start une nouvelle activité
                Toast.makeText(MainActivity.this, getResources().getString(R.string.good), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, ch.heigvd.sym.template.AsynchActivity.class);
                MainActivity.this.startActivity(intent);
            }

        });

        buttDiffTrans.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Toast indicatif + Creation d'un Intent afin de start une nouvelle activité
                Toast.makeText(MainActivity.this, getResources().getString(R.string.good), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, ch.heigvd.sym.template.DelayedActivity.class);
                MainActivity.this.startActivity(intent);
            }

        });

        buttGraphTrans.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Toast indicatif + Creation d'un Intent afin de start une nouvelle activité
                Toast.makeText(MainActivity.this, getResources().getString(R.string.good), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, ch.heigvd.sym.template.GraphActivity.class);
                MainActivity.this.startActivity(intent);
            }

        });

        buttCompTrans.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Toast indicatif + Creation d'un Intent afin de start une nouvelle activité
                Toast.makeText(MainActivity.this, getResources().getString(R.string.good), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, ch.heigvd.sym.template.CompressedActivity.class);
                MainActivity.this.startActivity(intent);
            }

        });

    }


}
