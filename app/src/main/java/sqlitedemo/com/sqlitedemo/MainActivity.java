package sqlitedemo.com.sqlitedemo;

import android.content.Context;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DBAdapterClass dbAdapterClass;
    private EditText etName, etPass, etEnterUser;
    private Button btnLogin, btnAllDetails, btnGetSomeDetails, btnUpdate, btnDelete;
    private Context mContext, hello;
    private  Button btnLogin1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=MainActivity.this;
        etName= (EditText) findViewById(R.id.etName);
        etPass= (EditText) findViewById(R.id.etPassword);
        etEnterUser= (EditText) findViewById(R.id.etEnterUserName);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnAllDetails= (Button) findViewById(R.id.btnGetAllDetails);
        btnGetSomeDetails= (Button) findViewById(R.id.btnGetSomeDetails);
        btnUpdate= (Button) findViewById(R.id.btnUpdate);
        btnDelete= (Button) findViewById(R.id.btnDelete);
        btnLogin.setOnClickListener(this);
        btnAllDetails.setOnClickListener(this);
        btnGetSomeDetails.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);




        dbAdapterClass=new DBAdapterClass(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
           /*     String name=etName.getText().toString();
                String pass=etPass.getText().toString();
               long id= dbAdapterClass.insertData(name, pass);
                if (id<0){
                   Message.message(mContext,"unsuccessful");
                }else {
                    Message.message(mContext,"successfull inserted a row");
                }*/
                break;
            case R.id.btnGetAllDetails:
              /*  String allData=dbAdapterClass.getAllData();
                Message.message(mContext,allData);*/
                break;
            case R.id.btnGetSomeDetails:
               /* String userName=etEnterUser.getText().toString();
                String someData=dbAdapterClass.getData(userName);
                Message.message(mContext,someData);*/
                String userPass=etEnterUser.getText().toString();
                String sub1=userPass.substring(0, userPass.indexOf(" "));
                String sub2=userPass.substring(userPass.indexOf(" ")+1);

                String getId=dbAdapterClass.getID(sub1, sub2);
                Message.message(mContext,getId);
                break;
            case R.id.btnUpdate:
                dbAdapterClass.updateName("Jawed", "bottle");
                break;
            case R.id.btnDelete:
                int count=dbAdapterClass.deleteRow(etEnterUser.getText().toString());
                Message.message(mContext, ""+count);
                break;
            default:
                break;
        }
    }
}
