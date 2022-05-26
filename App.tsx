import React, {useState} from 'react';
import {Text} from 'react-native';

import StonneService from './src/services/service_stone';

import styled from 'styled-components/native';

export default function App() {
  var serviceStonne = new StonneService();

  const [valueText, setValueText] = useState('Teste ');

  function printText() {
    serviceStonne.sendPrint(valueText);
  }

  return (
    <Container>
      <Text> Texto </Text>
      <TextInputValues
        value={valueText}
        onChangeText={(text: string) => setValueText(text)}
      />
      <ButtonPrint onPress={printText}>
        <Text style={{color: '#fff'}}> IMPRIMIR </Text>
      </ButtonPrint>
    </Container>
  );
}

const Container = styled.SafeAreaView`
  flex: 1;
  justify-content: center;
  align-items: center;
  padding: 20px;
`;

const TextInputValues = styled.TextInput`
  width: 100%;
  height: 40px;
  border: 1px solid gray;
  border-radius: 5px;
  margin-top: 20px
`;

const ButtonPrint = styled.TouchableOpacity`
  margin-top: 40px;
  width: 100px;
  height: 40px;
  font-size: 14px;
  justify-content: center;
  align-items: center;
  background-color: #26387d;
  color: #fff;
  border-radius: 5px;
`;
