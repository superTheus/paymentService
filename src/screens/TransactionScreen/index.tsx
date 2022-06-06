import React, { useState } from 'react';
import { Text } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import styled from 'styled-components/native';
import CurrencyInput from 'react-native-currency-input';
import BouncyCheckbox from "react-native-bouncy-checkbox";

import { StackParamList } from '../../routes/types';
import colors from '../../utils/colors';
import StonneService from '../../services/service_stone';
import { TransactionInterface } from '../../utils/types';


interface StyledProps {
  isActive?: boolean;
}

type navigateProps = NativeStackNavigationProp<StackParamList, 'TransactionScreen'>;

export default function TransactionScreen() {
  const navigation = useNavigation<navigateProps>();

  const serviceStonne = new StonneService();

  const [value, setValue] = useState<number | null>(0);
  const [withCapture, setWithCapture] = useState(true);

  const [typePayment, setTypePayment] = useState<'debit' | 'credit' | 'voucher'>('debit');
  const [messageStatus, setMessageStatus] = useState('');

  function handerMessage(message: string) {
    setMessageStatus(message);
  }

  function trasactionInit() {
    const TransactionInfo: TransactionInterface = {
      capture: withCapture,
      value: String(value).replace(/\D/g, ''),
      typePayment: typePayment,
      portion: 1
    }
    serviceStonne.sendTransaction(handerMessage, TransactionInfo);
  }

  return (
    <Container>
      <AreaView>
        <TextLabel>Valor:</TextLabel>
        <Input
          value={value}
          onChangeValue={setValue}
          delimiter="."
          separator=","
          precision={2}
          minValue={0}
        />
      </AreaView>
      <AreaButton>
        <ButtonPayType
          isActive={typePayment === 'debit' ? true : false}
          onPress={() => setTypePayment('debit')}
        >
          <TextButton isActive={typePayment === 'debit' ? true : false} >Débito</TextButton>
        </ButtonPayType>
        <ButtonPayType
          isActive={typePayment === 'credit' ? true : false}
          onPress={() => setTypePayment('credit')}
        >
          <TextButton isActive={typePayment === 'credit' ? true : false}>Crédito</TextButton>
        </ButtonPayType>
        <ButtonPayType
          isActive={typePayment === 'voucher' ? true : false}
          onPress={() => setTypePayment('voucher')}
        >
          <TextButton isActive={typePayment === 'voucher' ? true : false}>Voucher</TextButton>
        </ButtonPayType>
      </AreaButton>

      <BouncyCheckbox
        size={25}
        isChecked={withCapture}
        fillColor={colors.purple}
        unfillColor="#FFFFFF"
        text="Transação com Captura"
        iconStyle={{ borderColor: colors.gray }}
        textStyle={{ fontFamily: "JosefinSans-Regular" }}
        onPress={(isChecked: boolean) => setWithCapture(isChecked)}
      />

      <ButtonSendArea>
        <ButtonSend onPress={trasactionInit} >
          <TextSend>ENVIAR TRANSAÇÃO</TextSend>
        </ButtonSend>
        <ButtonBack onPress={() => navigation.goBack()}>
          <TextBack>VOLTAR</TextBack>
        </ButtonBack>
      </ButtonSendArea>
      <Text>
        {messageStatus}
      </Text>
    </Container >
  );
}

const Container = styled.SafeAreaView` 
  flex: 1;
  align-items: center;
  justify-content: space-evenly;
  padding: 20px;
`;

const AreaView = styled.View`
  flex-direction: row;
  width: 100%;
  height: auto;
  box-sizing: border-box;
`

const TextLabel = styled.Text`
  font-size: 24px;
  width: 20%;
`;

const Input = styled(CurrencyInput)`
  padding: 10px;
  margin: 0px 10px;
  border: 1px solid ${colors.purple};
  width:  243px;
  height: 40px;
  border-radius: 5px;
  text-align: right;
`;

const AreaButton = styled.View`
  flex-direction: row;
  justify-content: space-evenly;
  align-items: center;
  width: 100%;
`;

const ButtonPayType = styled.TouchableOpacity<StyledProps>`
  width: auto;
  max-width: 150px;
  padding: 10px 20px;
  background-color: ${(props) => props.isActive ? colors.purple : colors.whiteBackground};
  border: 1px solid ${(props) => props.isActive ? colors.gray : colors.purple};
  border-radius: 5px;
`;

const TextButton = styled.Text<StyledProps>`
  font-size: 16px;
  color: ${(props) => props.isActive ? '#ffd' : colors.gray};
`;

const ButtonSendArea = styled.View`
  width: 100%;
  justify-content: space-between;
`;

const ButtonSend = styled.TouchableOpacity`
  width: 100%;
  padding: 10px 20px;
  background-color: ${colors.purple};
  border: 1px solid ${colors.gray};
  border-radius: 5px;
  justify-content: center;
  align-items: center;
`;

const ButtonBack = styled(ButtonSend)`
  background-color: ${colors.whiteBackground};
  border: 1px solid ${colors.purple};
  margin-top: 20px;
`;

const TextSend = styled.Text`
  color: #fff;
`;

const TextBack = styled.Text`
  color: ${colors.gray};
`;