package sqlitedemo.com.sqlitedemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by JAWED on 03-07-2017.
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private Context mContext;
    private EditText etName, etPhone, etAdd, etEmail, etPass;
    private Button btnSignUp;
    //private ImageView ivPhoto;
    private AlertDialog alertD;
    private static final int REQUEST_GALLERY = 1;
    private static final int REQUEST_KITKAT_GALLERY = 2;
    private static final int REQUEST_CAMERA = 3;
    private String imagepath, encodedImage = "";
    private  Bitmap bMap;
    private ImageViewRounded ivPhoto;
    private DBAdapterClass dbAdapterClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        mContext = SignUpActivity.this;

        etName= (EditText) findViewById(R.id.etName);
        etPhone= (EditText) findViewById(R.id.etPhone);
        etAdd= (EditText) findViewById(R.id.etAdd);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etPass= (EditText) findViewById(R.id.etPassword);
        btnSignUp= (Button) findViewById(R.id.btnSignUp);
        ivPhoto= (ImageViewRounded) findViewById(R.id.ivPhoto);
        btnSignUp.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);

        dbAdapterClass=new DBAdapterClass(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp:
                if (validateLoginForm()){
                    String name=etName.getText().toString();
                    String phone=etPhone.getText().toString();
                    String address=etAdd.getText().toString();
                    String email=etEmail.getText().toString();
                    String pass=etPass.getText().toString();
                    long id= dbAdapterClass.insertData(name, phone,address, email, pass);
                    if (id<0){
                        Message.message(mContext,"unsuccessful");
                    }else {
                        Intent intent=new Intent(mContext, UserDetailsList.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Message.message(mContext, "Successfully login");
                    }
                }
                break;
            case R.id.ivPhoto:
                choosePopup();
                break;
            default:
                break;
        }

    }

    public void choosePopup() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_update_photo, null);

        alertD = new AlertDialog.Builder(this).create();
        Button btnGallery = (Button) promptView.findViewById(R.id.btn_gallery);
        Button btnCamera = (Button) promptView.findViewById(R.id.btn_camera);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getGallery();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getCamera();

            }
        });
        alertD.setView(promptView);
        alertD.show();
    }

    public void getGallery() {
        alertD.dismiss();
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image*//*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_KITKAT_GALLERY);
        }
    }

    public void getCamera() {
        alertD.dismiss();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImageUri = null;
        switch (requestCode) {
            case REQUEST_GALLERY:
                if (data == null || data.getData() == null)
                    return;
                imagepath = getRealPathFromURI(selectedImageUri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                // options.inJustDecodeBounds = true;
                options.inScaled = false;
                options.inDither = false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                bMap = BitmapFactory.decodeFile(imagepath);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                bMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();

                ivPhoto.setImageBitmap(bMap);
                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                break;
            case REQUEST_KITKAT_GALLERY:
                if (data == null || data.getData() == null)
                    return;
                selectedImageUri = data.getData();
                imagepath = getRealPathFromURI(selectedImageUri);
                BitmapFactory.Options options1 = new BitmapFactory.Options();
                options1.inSampleSize = 2;
                // options.inJustDecodeBounds = true;
                options1.inScaled = false;
                options1.inDither = false;
                options1.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bMap = BitmapFactory.decodeFile(imagepath);

                ByteArrayOutputStream byteArrayOutputStreamaos = new ByteArrayOutputStream();

                bMap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamaos);
                byte[] imageBytes1 = byteArrayOutputStreamaos.toByteArray();

                ivPhoto.setImageBitmap(bMap);
                encodedImage = Base64.encodeToString(imageBytes1, Base64.DEFAULT);
                break;
            case REQUEST_CAMERA:
                try {
                    if (resultCode == Activity.RESULT_CANCELED) {
                        return;
                    }
                    bMap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream baos2 = new ByteArrayOutputStream();

                    bMap.compress(Bitmap.CompressFormat.JPEG, 100, baos2);
                    byte[] imageBytes2 = baos2.toByteArray();

                    encodedImage = Base64.encodeToString(imageBytes2, Base64.DEFAULT);
                    ivPhoto.setImageBitmap(bMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public boolean validateLoginForm() {
        if (etName.getText().toString().trim().isEmpty()) {
            Message.message(mContext,"Please enter your Name");
            return false;
        }
        if (etPhone.getText().toString().trim().isEmpty()) {
            Message.message(mContext,"Please enter your Phone");
            return false;
        }

        if (etAdd.getText().toString().trim().isEmpty()) {
            Message.message(mContext,"Please enter your Address");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            if (etEmail.getText().toString().isEmpty()) {
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
