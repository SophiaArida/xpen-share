package com.expenshare.model.entity;

import io.micronaut.data.annotation.Relation;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.AUTO;

@Serdeable
@Entity
@Table(name = "expense_shares")
public class ExpenseShareEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
//            onDelete = ForeignKeyAction.CASCADE)
    private ExpenseEntity expense_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user_id;

    @Column(name = "share_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal share_amount; // Positive = owes, Negative = is owed
}
