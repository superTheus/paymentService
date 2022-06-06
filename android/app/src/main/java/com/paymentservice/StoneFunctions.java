package com.paymentservice;

import android.content.Context;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import br.com.stone.posandroid.providers.PosPrintProvider;
import br.com.stone.posandroid.providers.PosValidateTransactionByCardProvider;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import java.util.List;

import stone.application.enums.Action;
import stone.application.interfaces.StoneActionCallback;
import stone.application.interfaces.StoneCallbackInterface;
import stone.database.transaction.TransactionObject;

public class StoneFunctions extends AppCompatActivity {
    Context mContext = StoneClass.reactContext;

    public void printTextSimple(String text) {
        try{
            final PosPrintProvider customPosPrintProvider = new PosPrintProvider(mContext);
            customPosPrintProvider.addLine(text);
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
        }catch (Exception e){
            Toast.makeText(mContext, "Erro ao imprimir: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void printTextMultline(ReadableArray text) {
        try{
            final PosPrintProvider customPosPrintProvider = new PosPrintProvider(mContext);
            for (int i = 0; i < text.size(); i++) {
                ReadableType type = text.getType(i);
                if(type == ReadableType.String){
                    customPosPrintProvider.addLine(text.getString(i));
                }
            }
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
        }catch (Exception e){
            Toast.makeText(mContext, "Erro ao imprimir: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void validateCard(){
        try{
            final PosValidateTransactionByCardProvider posValidateTransactionByCardProvider = new PosValidateTransactionByCardProvider(mContext);
            posValidateTransactionByCardProvider.setConnectionCallback(new StoneActionCallback() {
                @Override
                public void onStatusChanged(final Action action) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, action.name(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final List<TransactionObject> transactionsWithCurrentCard = posValidateTransactionByCardProvider.getTransactionsWithCurrentCard();
                            if (transactionsWithCurrentCard.isEmpty())
                                Toast.makeText(mContext, "Cartão não fez transação.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                @Override
                public void onError() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                            Log.e("posValidateCardOption", "onError: " + posValidateTransactionByCardProvider.getListOfErrors());
                        }
                    });
                }

            });
            posValidateTransactionByCardProvider.execute();
        }catch (Exception e){
            Toast.makeText(mContext, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Transaction(ReadableMap objectDataTransaction, Callback callBack){
        try {
            final PosTransaction transactionObject = new PosTransaction(callBack);
            transactionObject.buildTransactionProvider();
            transactionObject.initTransaction(objectDataTransaction);
        }catch (Exception e){
            Toast.makeText(mContext, "Error: "+e.getMessage(), Toast.LENGTH_SHORT);
        }
    }
}
