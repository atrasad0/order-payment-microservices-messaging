package com.msdemo.oporder.v1.entity;

import com.msdemo.oporder.v1.enums.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;


/* Versões futuras a entidade poderá ter: Cliente, ItemPedido */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "orders")
public class Order implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private BigDecimal totalAmount;

    private ZonedDateTime orderDate;

    private ZonedDateTime updatedAt;


    @PrePersist
    public void onCreate() {
        if (Objects.isNull(this.orderStatus)) {
            this.orderStatus = OrderStatus.PENDING;
        }

        if (Objects.isNull(this.orderDate)) {
            this.orderDate = ZonedDateTime.now();
        }

        if (Objects.isNull(this.updatedAt)) {
            this.updatedAt = ZonedDateTime.now();
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }
}
