export interface Product {
  id: number;
  name: string;
  brand: string;
  category: string;
  description: string;
  price: number;
  imageUrl: string;
}

export interface CartItem {
  product: Product;
  quantity: number;
  subtotal: number;
}

export interface OrderItem {
  productId: number;
  name: string;
  unitPrice: number;
  quantity: number;
  subtotal: number;
}

export interface Order {
  code: string;
  buyerName: string;
  createdAt: string;
  items: OrderItem[];
  paymentMethod: string;
  total: number;
  installments: number;
  address: {
    logradouro: string;
    bairro: string;
    localidade: string;
    uf: string;
    numero: string;
    complemento: string;
    cep: string;
  };
}

export interface ViaCepAddress {
  logradouro: string;
  bairro: string;
  localidade: string;
  uf: string;
  erro?: boolean;
}

export interface SessionState {
  wishlistIds: number[];
  cartCount: number;
  wishlistCount: number;
}
