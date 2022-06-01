package com.paymentservice.controller

import android.content.Context
import android.widget.Toast
import br.com.stone.posandroid.providers.PosPrintReceiptProvider
import stone.application.interfaces.StoneCallbackInterface

class PrintController(private val context: Context,
                      private val provider: PosPrintReceiptProvider
) {
    fun print() {
        provider.connectionCallback = object : StoneCallbackInterface {
            override fun onSuccess() {
                Toast.makeText(context, "Recibo impresso", Toast.LENGTH_SHORT).show()
            }

            override fun onError() {
                Toast.makeText(context, "Erro ao imprimir: "
                        + provider.listOfErrors, Toast.LENGTH_SHORT).show()
            }
        }

        provider.execute();
    }
}