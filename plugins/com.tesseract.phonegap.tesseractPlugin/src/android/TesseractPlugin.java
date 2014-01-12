package com.tesseract.phonegap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;

public class TesseractPlugin extends CordovaPlugin {
	// Add tesseractEntry action to our plugin
	public static final String ACTION_ADD_TESSERACT_ENTRY = "addTesseractPluginEntry";
	public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString();
	private static final String lang = "eng";
			//.getExternalStorageDirectory().toString() + "/PhoneGapTessTwo/";

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		try {
			if (ACTION_ADD_TESSERACT_ENTRY.equals(action)) {
				//JSONObject arg_object = args.getJSONObject(0);
				
				String imagePath = args.getString(0);
				this.echo(imagePath, callbackContext);
				return true;
			}
			callbackContext.error("Invalid action");
			return false;
		} catch (Exception e) {
			System.err.println("Exception in Execute: " + e.getMessage());
			callbackContext.error(e.getMessage());
			return false;
		}
	}
	private void echo(String imagePath, CallbackContext callbackContext) {
        if (imagePath != null && imagePath.length() > 0) {
            callbackContext.success(imagePath);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
	

	public String TesseractExampleTwo()
	{
		String recognizedText = "";
		String pathToPic = DATA_PATH + "/PhoneGapTessTwo" + "/tessdata" + "/eurotext4.png";
		String pathToPath = DATA_PATH + "/PhoneGapTessTwo/";
		
		try {
			
		System.err.println("Before BitMapFactory.Options");
			
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;

		System.err.println("Before bitmap = decodeFile");
		Bitmap bitmap = BitmapFactory.decodeFile(pathToPic, options);
		
		
		
		System.err.println("Before bitmap.copy");
		
		try {
			ExifInterface exif = new ExifInterface(pathToPic);
			int exifOrientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			//Log.v(TAG, "Orient: " + exifOrientation);
			System.err.println("Orient: " + exifOrientation);

			int rotate = 0;

			switch (exifOrientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				rotate = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				rotate = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				rotate = 270;
				break;
			}

			//Log.v(TAG, "Rotation: " + rotate);
			System.err.println("Rotation: " + rotate);

			if (rotate != 0) {

				// Getting width & height of the given image.
				int w = bitmap.getWidth();
				int h = bitmap.getHeight();

				// Setting pre rotate
				Matrix mtx = new Matrix();
				mtx.preRotate(rotate);

				// Rotating Bitmap
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
			}

			// Convert to ARGB_8888, required by tess
			bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

		} catch (IOException e) {
			//Log.e(TAG, "Couldn't correct orientation: " + e.toString());
			System.err.println("Couldnt correct orientation");
		}
						
		//Log.v(TAG, "Before baseApi");
		
		
		System.err.println("Before baseApi");

		TessBaseAPI baseApi = new TessBaseAPI();
		baseApi.setDebug(true);
		
		System.err.println("Before baseApi.init");
		baseApi.init(pathToPath, lang);
		//baseApi.setImage(bitmap);
		//File imageFile = new File(bitmap);
		System.err.println("Before baseApi.setImage");
		baseApi.setImage(bitmap);

		if(baseApi.getUTF8Text() == null)
		{
			System.err.println("baseApi.getUTF8Text = null");
		}
		else {
		recognizedText = baseApi.getUTF8Text();
		}

		System.err.println("Before baseApi.end");
		baseApi.end();
		
		}
		catch (Exception e){
			System.err.println("TesseractTwoExample: " + e);
		}

		// You now have the text in recognizedText var, you can do anything with it.
		// We will display a stripped out trimmed alpha-numeric version of it (if lang is eng)
		// so that garbage doesn't make it to the display.

		//Log.v(TAG, "OCRED TEXT: " + recognizedText);
		System.err.println("OCRED TEXT: " + recognizedText);

		//if ( lang.equalsIgnoreCase("eng") ) {
		//	recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
		//}
		
		return recognizedText;
		//recognizedText = recognizedText.trim();

	//	if ( recognizedText.length() != 0 ) {
	//		_field.setText(_field.getText().toString().length() == 0 ? recognizedText : _field.getText() + " " + recognizedText);
	//		_field.setSelection(_field.getText().toString().length());
	//	}

		
	}
	
	
	public String TesseractExample() {
		
		String recognizedText = "";
		//_path = DATA_PATH + "/ocr.jpg";
		
		try
		{
		File imageFile = new File("eurotext.png");
		if(imageFile.exists() != true)
		{
			System.err.println("Image: FALSE");
			
		}
		else{
			System.err.println("Image: TRUE");
		}
		// _path = path to the image to be OCRed

		// File myDir = DATA_PATH;
		// getExternalFilesDir(Environment.MEDIA_MOUNTED);

		// lang = for which the language data exists, usually "eng"
		TessBaseAPI baseApi = new TessBaseAPI();	
		

		// DATA_PATH = Path to the storage
		baseApi.init(DATA_PATH, "eng.traineddata"); // myDir + "/tessdata/eng.traineddata"
								// must be present
		//baseApi.setDebug(true);
		System.err.println("Pass Init: "+DATA_PATH+"/eng.traineddata");
 
		
		// Eg. baseApi.init("/mnt/sdcard/tesseract/tessdata/eng.traineddata",
		// "eng");
		
		System.err.println("Pass SetImage: ");
		baseApi.setImage(imageFile);
		System.err.println("Pass SetImage: ");


		recognizedText = baseApi.getUTF8Text(); // Log or otherwise
														// display this
														// string...
		baseApi.end();
		
		}
		catch (Exception e){
			System.err.println("Exception: " + e.getMessage());
			
			recognizedText = "FEJL";
		
		}
		return recognizedText;
		
	}

	private void SampleTest(String sampletest, CallbackContext callbackContext) {
		if (sampletest != null && sampletest.length() > 0) {
			callbackContext.success(sampletest);
		} else {
			callbackContext.error("Expected one non-empty string argument.");
		}

	}
	
	

}