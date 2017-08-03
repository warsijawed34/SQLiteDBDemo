package sqlitedemo.com.sqlitedemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by JAWED on 03-07-2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private EditText etUserName, etPass;
    private Button btnLogin, btnSignUp;
    private DBAdapterClass dbAdapterClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mContext = LoginActivity.this;

        etUserName= (EditText) findViewById(R.id.etUserName);
        etPass= (EditText) findViewById(R.id.etPassword);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnSignUp= (Button) findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        dbAdapterClass=new DBAdapterClass(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                if (validateLoginForm()){
                    String email=etUserName.getText().toString();
                    String pass=etPass.getText().toString();
                    String Id= dbAdapterClass.getID(email, pass);
                    if (!Id.isEmpty()){
                        Intent intent=new Intent(mContext, UserDetailsList.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Message.message(mContext, "Successfully login");
                    }else {
                        Message.message(mContext, "Email and password are incorrect");
                    }
                }
                break;
            case R.id.btnSignUp:
                Intent intent=new Intent(mContext, SignUpActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    public boolean validateLoginForm() {

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etUserName.getText().toString()).matches()) {
            if (etUserName.getText().toString().isEmpty()) {
                Message.message(mContext, "Please enter your Email Id");
                return false;
            }
            Message.message(mContext, "Please enter a valid email address");
            return false;
        }
        if (etPass.getText().toString().trim().isEmpty()) {
            Message.message(mContext,"Please enter your Password");
            return false;
        }

        return true;
    }
}
