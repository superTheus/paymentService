import { NativeModules } from 'react-native';
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
}