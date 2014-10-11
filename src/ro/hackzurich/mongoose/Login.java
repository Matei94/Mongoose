package ro.hackzurich.mongoose;

import java.math.BigDecimal;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

public class Login extends Activity {
	private Button btnLogin;
	private Button btnPaypal;
	
	private static PayPalConfiguration config = new PayPalConfiguration()
    .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
    .clientId("AYNZXBB9-C5TvuB_dbNlRfitgvL_3aXeJ_Gn8rN9JbqRNS-9XWUOkm45wP7S");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent goToMainActivityIntent = new Intent(Login.this, 
						MainActivity.class);
				startActivity(goToMainActivityIntent);
			}
		});
		
		btnPaypal = (Button) findViewById(R.id.btnPaypal);
		btnPaypal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 	PayPalPayment payment = new PayPalPayment(new BigDecimal("1.75"), "USD", "hipster jeans",
				 			PayPalPayment.PAYMENT_INTENT_SALE);
				    Intent intent = new Intent(Login.this, PaymentActivity.class);
				    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
				    startActivityForResult(intent, 0);
			}
		});
		
		Intent intent = new Intent(this, PayPalService.class);
	    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
	    startService(intent);
	}
	
	@Override
	protected void onDestroy() {
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
	        PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
	        if (confirm != null) {
	            try {
	                Log.i("MONGOOSE", confirm.toJSONObject().toString(4));

	                // TODO: send 'confirm' to your server for verification.
	                // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
	                // for more details.

	            } catch (JSONException e) {
	                Log.e("MONGOOSE", "an extremely unlikely failure occurred: ", e);
	            }
	        }
	    }
	    else if (resultCode == Activity.RESULT_CANCELED) {
	        Log.i("MONGOOSE", "The user canceled.");
	    }
	    else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
	        Log.i("MONGOOSE", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
	    }
	}
}
