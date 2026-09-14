import { Order } from '../models/product.model';

export const receiptOrder: Order = {
  code: 'BD-TEST123', buyerName: 'Ana Silva', createdAt: '2026-09-14T12:00:00Z',
  paymentMethod: 'CARTAO_CREDITO', total: 719.70, installments: 3,
  items: [
    { productId: 1, name: 'Camisa Brasil', unitPrice: 299.90, quantity: 2, subtotal: 599.80 },
    { productId: 2, name: 'Boné Brasil', unitPrice: 119.90, quantity: 1, subtotal: 119.90 }
  ],
  address: { logradouro: 'Praça da Sé', numero: '123', complemento: 'Apto 42',
    bairro: 'Sé', localidade: 'São Paulo', uf: 'SP', cep: '01001-000' }
};
