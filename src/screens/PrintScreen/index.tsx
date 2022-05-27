import React, { useState } from 'react';
import { Text } from 'react-native';

import StonneService from '../../services/service_stone';

import styled from 'styled-components/native';

export default function PrintScreen() {
  var serviceStonne = new StonneService();

  const [valueText, setValueText] = useState('Teste ');

  function printText() {
    serviceStonne.sendPrintSimple(valueText);
  }

  const relatorio = [
    '---- IMPRESSÃO DE RELATÓRIO ----',
    ' ',
    'Venda Total............R$ 250,00',
    'Venda Dinheiro.........R$ 150,00',
    'Venda Cartão Crédito...R$  50,00',
    'Venda Cartão Débito....R$  50,00',
    ' ',
    '----    ATX TECNOLOGIA      ----',
  ];

  function printReport() {
    serviceStonne.sendPrintMultline(relatorio);
  }

  return (
    <Container>
      <Text> Texto </Text>
      <TextInputValues
        value={valueText}
        onChangeText={(text: string) => setValueText(text)}
      />
      <ButtonPrint onPress={printText}>
        <Text style={{ color: '#fff' }}> IMPRIMIR TEXTO</Text>
      </ButtonPrint>
      <ButtonPrint onPress={printReport}>
        <Text style={{ color: '#fff' }}> IMPRIMIR RELATÓRIO EXEMPLO</Text>
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
  width: auto;
  height: 40px;
  font-size: 14px;
  justify-content: center;
  align-items: center;
  background-color: #26387d;
  color: #fff;
  border-radius: 5px;
  padding: 0px 20px;
`;
