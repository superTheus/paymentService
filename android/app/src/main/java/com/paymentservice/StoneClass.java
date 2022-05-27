package com.paymentservice;
import static android.app.Activity.RESULT_OK;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class StoneClass extends ReactContextBaseJavaModule implements ActivityEventListener{
    public static ReactApplicationContext reactContext;

    Bundle instanceBundle = new Bundle();

    StoneClass(ReactApplicationContext context) {
        super(context);
        reactContext = context;
        reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "StoneClass";
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data){
        //IntentResult resultIntent = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        WritableMap returnCode = Arguments.createMap();
        if (requestCode == 1) {
            if (resultCode == 2) {
                String[] result = data.getStringArrayExtra("result");

                CharSequence cs;
                if (result[0].equals("1")) {

                    returnCode.putString("returnCode",result[1]);
                    returnCode.putString("returnType",result[3]);
                    reactContext
                            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit("eventBarCodeReturn", returnCode);

                } else {
                    returnCode.putString("returnError",result[0]);
                    reactContext
                            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit("eventBarCodeReturn", returnCode);
                    cs = "Erro # " + result[0] + " na leitura do código.";

                }
            }
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                File file = new File(returnUri.getPath());//create path from uri
                final String path = file.getPath();
                reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("eventChoosePrinterImage", path);
            }
        } else {
            Toast.makeText(reactContext, "You haven't picked an Image", Toast.LENGTH_LONG).show();
        }
    }

    public void onNewIntent(Intent intent){}

    @ReactMethod
    public void handlePrintSimple(String text) {
        try{
            getCurrentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final StoneFunctions Print = new StoneFunctions();
                    Print.printTextSimple(reactContext, text);
                }
            });
        }catch (Exception e){
            Toast.makeText(reactContext, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @ReactMethod
    public void handlePrintMultiline(ReadableArray text) {
        try{
            getCurrentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final StoneFunctions Print = new StoneFunctions();
                    Print.printTextMultline(reactContext, text);
                }
            });
        }catch (Exception e){
            Toast.makeText(reactContext, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @ReactMethod
    public void handleValidateCard() {
        try{
            getCurrentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final StoneFunctions Print = new StoneFunctions();
                    Print.validateCard(reactContext);
                }
            });
        }catch (Exception e){
            Toast.makeText(reactContext, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
