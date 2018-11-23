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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // For logging purposes
    private static final String TAG = MainActivity.class.getSimpleName();

	private static ArrayMap<String,String> validList = new ArrayMap<String, String>();

    // GUI elements
	private Button serialize            = null;
	private Button buttAsynchTrans	    = null;
    private Button delayed              = null;
    private Button graphQl              = null;
    private Button compressed           = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);

		// GUI elements





		/* Then program action associated to "Ok" button
		signIn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

			    // Récupération de la valeur des champs email et password (remplis par le user)
				String mail = email.getText().toString();
				String passwd = password.getText().toString();

                // Vérification des champs (selon la donnée)
				if(!mail.contains("@")) {
					Toast.makeText(MainActivity.this, getResources().getString(R.string.bad), Toast.LENGTH_LONG).show();
				} else if (isValid(mail, passwd)) {

				    // Login réussi -> Toast indicatif + Creation d'un Intent afin de start une nouvelle activité
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.good), Toast.LENGTH_LONG).show();

				    Intent intent = new Intent(MainActivity.this, ch.heigvd.sym.template.OnSuccessActivity.class);
				    intent.putExtra("EMAIL", mail);
				    MainActivity.this.startActivity(intent);

				} else {
					// Wrong combination, display pop-up dialog and stay on login screen
					showErrorDialog(mail, passwd);
					email.setText("");
					password.setText("");
				}
			}

			
		});*/
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

}
