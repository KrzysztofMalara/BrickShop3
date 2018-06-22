package krzysztof.brickshop.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity(name = "orders")

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 140)
    private int brickCount;
    @Column
    private long creationDateTimestamp;

    @Column
    private String referenceId;


    @ManyToOne(optional = false)
    private Customer customer;

    public Order(int brickCount, long creationDateTimestamp, String referenceId, Customer customer) {
        this.brickCount = brickCount;
        this.creationDateTimestamp = creationDateTimestamp;
        this.referenceId = referenceId;
        this.customer = customer;
    }
}