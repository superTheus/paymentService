export interface TransactionInterface {
  value: string;
  typePayment: 'debit' | 'credit' | 'voucher';
  capture: boolean;
  portion: number;
}