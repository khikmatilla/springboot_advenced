package com.myproject.springboot_advenced.entity;

import com.myproject.springboot_advenced.dto.TransactionState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_state")
    private TransactionState state;

    private Long amount;

    private LocalDateTime createdTime;
    private LocalDateTime performTime;
    private LocalDateTime cancelTime;

    private Long time;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer reason;

}
