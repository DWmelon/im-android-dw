package melon.im;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private EditText mEtTAccont;
    private EditText mEtMyAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtTAccont = (EditText) findViewById(R.id.et_target_uid);
        mEtMyAccount = (EditText) findViewById(R.id.et_my_account);

        findViewById(R.id.bt_uid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = mEtTAccont.getText().toString();
                String myAccount = mEtMyAccount.getText().toString();


                Intent intent = new Intent(MainActivity.this,ImActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("target_user",uid);
                bundle.putString("user_account",myAccount);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}
