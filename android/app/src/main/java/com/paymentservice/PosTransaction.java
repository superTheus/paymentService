package com.paymentservice;
import android.content.Context;
import android.widget.Toast;

import com.facebook.react.bridge.Callback;

import br.com.stone.posandroid.providers.PosTransactionProvider;
import stone.application.enums.Action;
import stone.application.enums.ErrorsEnum;
import stone.application.enums.TransactionStatusEnum;

public class PosTransaction extends BaseTransiction<PosTransactionProvider>{
    Context mContext = StoneClass.reactContext;
    protected final Callback callback;

    public PosTransaction(Callback callback){
        this.callback = callback;
    }

    @Override
    protected PosTransactionProvider buildTransactionProvider() {
        return new PosTransactionProvider(mContext, transactionObject, getSelectedUserModel());
    }

    @Override
    public void onStatusChanged(Action action) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, "Message: " + action.name(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSuccess() {
        if (transactionObject.getTransactionStatus() == TransactionStatusEnum.APPROVED) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callback.invoke("Sucesso na Transação");
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callback.invoke("Erro na transação: \"" + getAuthorizationMessage() + "\"");
                }
            });
        }
    }

    @Override
    public void onError() {
        super.onError();
        if (providerHasErrorEnum(ErrorsEnum.DEVICE_NOT_COMPATIBLE)) {
            callback.invoke("Dispositivo não compatível ou dependência relacionada não está presente");
        }
    }
}
