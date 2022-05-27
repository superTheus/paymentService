import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

import { StackParamList } from './types';
import MainScreen from '../screens/MainScreen';
import PrintScreen from '../screens/PrintScreen';

const StackNavigator = createNativeStackNavigator<StackParamList>();

export default function Stack() {
  return (
    <NavigationContainer>
      <StackNavigator.Navigator>
        <StackNavigator.Screen options={{
          headerShown: false
        }}
          name="MainScreen" component={MainScreen} />
        <StackNavigator.Screen options={{
          headerShown: false
        }}
          name="PrintScreen" component={PrintScreen} />
      </StackNavigator.Navigator>
    </NavigationContainer>
  );
}
