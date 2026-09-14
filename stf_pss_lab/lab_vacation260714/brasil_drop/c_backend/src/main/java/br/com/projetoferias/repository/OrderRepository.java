package br.com.projetoferias.repository;

import br.com.projetoferias.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {
    private final JdbcTemplate jdbc;

    public OrderRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @Transactional
    public void save(OrderSummary order) {
        CheckoutAddress address = order.address();
        jdbc.update("""
                INSERT INTO purchase_orders
                (code, owner_email, buyer_name, created_at, payment_method, installments, total,
                 cep, street, neighborhood, city, state, address_number, complement)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """, order.code(), order.ownerEmail(), order.buyerName(), order.createdAt(),
                order.paymentMethod(), order.installments(), order.total(), address.cep(),
                address.logradouro(), address.bairro(), address.localidade(), address.uf(),
                address.numero(), address.complemento());
        for (int index = 0; index < order.items().size(); index++) {
            OrderItem item = order.items().get(index);
            jdbc.update("""
                    INSERT INTO purchase_order_items
                    (order_code, position, product_id, product_name, unit_price, quantity, subtotal)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                    """, order.code(), index, item.productId(), item.name(), item.unitPrice(),
                    item.quantity(), item.subtotal());
        }
    }

    public Optional<OrderSummary> findByCode(String code) {
        return jdbc.query("SELECT * FROM purchase_orders WHERE code = ?", this::map, code)
                .stream().findFirst();
    }

    public List<OrderSummary> findByOwner(String email) {
        return jdbc.query("SELECT * FROM purchase_orders WHERE owner_email = ? ORDER BY created_at DESC, code",
                this::map, email);
    }

    private OrderSummary map(ResultSet row, int rowNumber) throws SQLException {
        String code = row.getString("code");
        List<OrderItem> items = jdbc.query("SELECT * FROM purchase_order_items WHERE order_code = ? ORDER BY position",
                (item, index) -> new OrderItem(item.getLong("product_id"), item.getString("product_name"),
                        item.getBigDecimal("unit_price"), item.getInt("quantity"), item.getBigDecimal("subtotal")), code);
        return new OrderSummary(code, row.getString("owner_email"), row.getString("buyer_name"), items,
                row.getBigDecimal("total"), new CheckoutAddress(row.getString("cep"), row.getString("street"),
                row.getString("neighborhood"), row.getString("city"), row.getString("state"),
                row.getString("address_number"), row.getString("complement")), row.getString("payment_method"),
                row.getInt("installments"), row.getObject("created_at", OffsetDateTime.class));
    }
}
