import {NativeModules} from 'react-native';

var stonnePrint = NativeModules.PrintClass;

export default class StonneService {
  async sendPrint(text) {
    await stonnePrint.getPrint(text);
  }
}