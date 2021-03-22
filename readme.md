# Image Picker Android Retrofit

Image picker android use to pick the image and upload to server with retrofit instance it's help to remove the complected code with some lines of beautiful code.

## Installation

```bash

implementation 'com.github.Gurinder-Batth:gb-image-picker-android:1.0.0'

allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

## Usage

```python

import com.h2odeveloper.image_picker_android.GBImagePicker;

import com.h2odeveloper.image_picker_android.GBImagePickerInterface;


public class MainActivity extends AppCompatActivity  implements GBImagePickerInterface {

   GBImagePicker gbImagePicker;
   ImageView imageView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	imageView = findViewById(R.id.img);
	 /* Assign the reference of GBImagePicker */
	gbImagePicker = new GBImagePicker(this);
    }

    public void upload(View view)
    {
        /* Invoke the pickImage method for get image selection from Gallery*/
         gbImagePicker.pickImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       /* Pass all the params which */
       gbImagePicker.passActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void gbAfterActionPick(InputStream inputStream,  Bitmap bitmap, File file) {

	/*GET Bitmap Object */
	 imageView.setImageBitmap(bitmap);

	/*GET THE RETROFIT MultipartBody.Part Object*/
	MultipartBody.Part photo = gbImagePicker.getRetrofitMultipartBodyInstance("photo");


    }

}
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Author
Gurinder Batth
