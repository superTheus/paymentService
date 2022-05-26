package com.paymentservice;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import br.com.stone.posandroid.providers.PosPrintProvider;
import com.paymentservice.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import stone.application.enums.Action;
import stone.application.interfaces.StoneActionCallback;
import stone.application.interfaces.StoneCallbackInterface;
import stone.database.transaction.TransactionObject;
import stone.providers.ActiveApplicationProvider;
import stone.providers.DisplayMessageProvider;
import stone.providers.ReversalProvider;
import stone.utils.Stone;
import stone.application.StoneStart;

public class StoneFunctions extends AppCompatActivity {
    public String printText(Context mContext, String text) {
        try{
            final PosPrintProvider customPosPrintProvider = new PosPrintProvider(mContext);
            customPosPrintProvider.addLine(text);
            customPosPrintProvider.addLine("ATK : 123456789");
            customPosPrintProvider.setConnectionCallback(new StoneCallbackInterface() {
                @Override
                public void onSuccess() {
                    Toast.makeText(mContext, "Recibo impresso", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError() {
                    Toast.makeText(mContext, "Erro ao imprimir: " + customPosPrintProvider.getListOfErrors(), Toast.LENGTH_SHORT).show();
                }
            });

            customPosPrintProvider.execute();

            return "Success";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
