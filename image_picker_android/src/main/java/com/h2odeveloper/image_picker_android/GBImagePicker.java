package com.h2odeveloper.image_picker_android;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class GBImagePicker {

    Uri uri;
    File file;
    int request_code = 100;
    AppCompatActivity activity;

    public Boolean isSuccess() {
        return is_success;
    }

    Boolean is_success = false;
    GBImagePickerInterface gbImagePickerInterface;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getRequest_code() {
        return request_code;
    }

    public void setRequest_code(int request_code) {
        this.request_code = request_code;
    }

    public GBImagePicker(GBImagePickerInterface gbImagePickerInterface)
    {
        this.activity = (AppCompatActivity) gbImagePickerInterface;
        this.gbImagePickerInterface = gbImagePickerInterface;
    }

    public GBImagePicker(Context context , int request_code) {
        this.request_code = request_code;
    }

    public void pickImage()
    {
        is_success = false;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent,request_code);
    }

    public void passActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode == request_code && resultCode ==  activity.RESULT_OK )
        {
            uri = data.getData();
            try{
                InputStream inputStream = activity.getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                file = new File( getRealPathFromURI(uri) );
                is_success = true;
                gbImagePickerInterface.gbAfterActionPick(inputStream , bitmap , file );
            }catch(Exception e){

            }

        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(activity, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public MultipartBody.Part getRetrofitMultipartBodyInstance(String name)
    {
        RequestBody requestBody = RequestBody.create( MediaType.parse( activity.getContentResolver().getType(uri) ) ,file );
        return MultipartBody.Part.createFormData( name ,this.file.getName() , requestBody );
    }

    public MultipartBody.Part getRetrofitMultipartBodyInstance(String name,File file)
    {
        RequestBody requestBody = RequestBody.create( MediaType.parse( activity.getContentResolver().getType(this.uri) ) ,file );
        return MultipartBody.Part.createFormData( name ,file.getName() , requestBody );
    }

    public MultipartBody.Part getRetrofitMultipartBodyInstance(String name,File file,Uri uri)
    {
        RequestBody requestBody = RequestBody.create( MediaType.parse( activity.getContentResolver().getType(uri) ) ,file );
        return MultipartBody.Part.createFormData( name ,file.getName() , requestBody );
    }


    public  RequestBody stringRequestBody(String name)
    {
        return RequestBody.create( okhttp3.MultipartBody.FORM , name ) ;
    }


    public  RequestBody ediTextRequestBody(EditText name)
    {
        return RequestBody.create( okhttp3.MultipartBody.FORM , name.getText().toString() ) ;
    }


}
