package com.paymentservice;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

import stone.application.enums.Action;
import stone.application.enums.ErrorsEnum;
import stone.application.enums.InstalmentTransactionEnum;
import stone.application.enums.TypeOfTransactionEnum;
import stone.application.interfaces.StoneActionCallback;
import stone.database.transaction.TransactionObject;
import stone.providers.BaseTransactionProvider;
import stone.user.UserModel;
import stone.utils.Stone;

public abstract class BaseTransiction <T extends BaseTransactionProvider> extends AppCompatActivity implements StoneActionCallback {
    private BaseTransactionProvider transactionProvider;
    protected final TransactionObject transactionObject = new TransactionObject();

    Context mContext = StoneClass.reactContext;

    public void initTransaction(ReadableMap objectDataTransaction) {
        // Informa a quantidade de parcelas.
        transactionObject.setInstalmentTransaction(InstalmentTransactionEnum.ONE_INSTALMENT);

        // Verifica a forma de pagamento selecionada.

        transactionObject.setInitiatorTransactionKey(null);
        transactionObject.setTypeOfTransaction(TypeOfTransactionEnum.CREDIT);
        transactionObject.setCapture(true);
        transactionObject.setAmount("10");

        transactionObject.setSubMerchantCity("city"); //Cidade do sub-merchant
        transactionObject.setSubMerchantPostalAddress("00000000"); //CEP do sub-merchant (Apenas números)
        transactionObject.setSubMerchantRegisteredIdentifier("00000000"); // Identificador do sub-merchant
        transactionObject.setSubMerchantTaxIdentificationNumber("33368443000199"); // CNPJ do sub-merchant (apenas números)

//        Seleciona o mcc do lojista.
//        transactionObject.setSubMerchantCategoryCode("123");

//        Seleciona o endereço do lojista.
//        transactionObject.setSubMerchantAddress("address");

        transactionProvider = buildTransactionProvider();
        transactionProvider.useDefaultUI(true);
        transactionProvider.setConnectionCallback(this);
        transactionProvider.execute();
    }

    protected String getAuthorizationMessage() {
        return transactionProvider.getMessageFromAuthorize();
    }

    protected abstract T buildTransactionProvider();

    protected boolean providerHasErrorEnum(ErrorsEnum errorsEnum) {
        return transactionProvider.theListHasError(errorsEnum);
    }

    @Override
    public void onError() {
        Toast.makeText(mContext, "Erro: " + transactionProvider.getListOfErrors(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(final Action action) {
        Toast.makeText(mContext, "Messagem: " + action.name(), Toast.LENGTH_SHORT).show();
    }

    protected UserModel getSelectedUserModel() {
        return Stone.getUserModel(0);
    }
}
