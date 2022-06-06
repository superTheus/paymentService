import { NativeModules } from 'react-native';
import { TransactionInterface } from '../utils/types';
var stonnePrint = NativeModules.StoneClass;

export default class StonneService {
  sendPrintSimple(text: string) {
    stonnePrint.handlePrintSimple(text);
  }

  sendPrintMultline(text: string[]) {
    stonnePrint.handlePrintMultiline(text);
  }

  sendValidateCard() {
    stonnePrint.handleValidateCard();
  }

  sendTransaction(handlerMessageStatus: (message: string) => void, TransactionInfo: TransactionInterface) {
    stonnePrint.handleTransaction({ Name: 'Transaction' }, (message: string) => {
      handlerMessageStatus(message);
    });
  }
}