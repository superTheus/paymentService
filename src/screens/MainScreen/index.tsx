import React from "react";
import { useNavigation } from '@react-navigation/core';
import { Text } from "react-native";
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { StackParamList } from "../../routes/types";
import styled from "styled-components/native";

import StonneService from '../../services/service_stone';
import colors from "../../utils/colors";

type navigateProps = NativeStackNavigationProp<StackParamList, 'MainScreen'>;

export default function MainScreen() {
  const navigation = useNavigation<navigateProps>();
  var serviceStonne = new StonneService();

  function validateCard() {
    serviceStonne.sendValidateCard();
  }

  return (
    <Container>
      <ButtonPrimary onPress={() => navigation.navigate('PrintScreen')}>
        <Text style={{ color: '#fff' }}> IMPRESSÃO</Text>
      </ButtonPrimary>
      <ButtonPrimary onPress={() => navigation.navigate('TransactionScreen')} >
        <Text style={{ color: '#fff' }}> TRANSAÇÃO </Text>
      </ButtonPrimary>
    </Container >
  );
}

const Container = styled.SafeAreaView`
  flex: 1;
  align-items: center;
  justify-content: center;
`;

const ButtonPrimary = styled.TouchableOpacity`
  margin-top: 40px;
  width: auto;
  height: 40px;
  font-size: 14px;
  justify-content: center;
  align-items: center;
  background-color: ${colors.purple};
  color: #fff;
  border-radius: 5px;
  padding: 0px 20px;
`;
