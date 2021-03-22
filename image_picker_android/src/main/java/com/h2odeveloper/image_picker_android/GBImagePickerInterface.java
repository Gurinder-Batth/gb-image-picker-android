package com.h2odeveloper.image_picker_android;


import android.graphics.Bitmap;

import java.io.File;
import java.io.InputStream;

public interface GBImagePickerInterface {


    public void gbAfterActionPick(InputStream inputStream , Bitmap bitmap, File file);



}
