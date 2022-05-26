import {NativeModules} from 'react-native';

var stonnePrint = NativeModules.StoneClass;

export default class StonneService {
  sendPrint(text) {
    stonnePrint.getPrint(text);
  }
}