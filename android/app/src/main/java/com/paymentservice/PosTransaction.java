package com.paymentservice;
import android.content.Context;
import android.widget.Toast;

import br.com.stone.posandroid.providers.PosTransactionProvider;
import stone.application.enums.Action;
import stone.application.enums.ErrorsEnum;
import stone.application.enums.TransactionStatusEnum;

public class PosTransaction extends BaseTransiction<PosTransactionProvider>{

    Context mContext = StoneClass.reactContext;

    @Override
    protected PosTransactionProvider buildTransactionProvider() {
        return new PosTransactionProvider(mContext, transactionObject, getSelectedUserModel());
    }

    @Override
    public void onStatusChanged(Action action) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(
                        mContext,
                        "Código: " + action.hashCode() + "\n Messagem: "+ action.name(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    @Override
    public void onSuccess() {
        if (transactionObject.getTransactionStatus() == TransactionStatusEnum.APPROVED) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(
                            mContext,
                            "Sucesso na Transação",
                            Toast.LENGTH_LONG
                    ).show();
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(
                            mContext,
                            "Erro na transação: \"" + getAuthorizationMessage() + "\"",
                            Toast.LENGTH_LONG
                    ).show();
                }
            });
        }
    }

    @Override
    public void onError() {
        super.onError();
        if (providerHasErrorEnum(ErrorsEnum.DEVICE_NOT_COMPATIBLE)) {
            Toast.makeText(
                    mContext,
                    "Dispositivo não compatível ou dependência relacionada não está presente",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
