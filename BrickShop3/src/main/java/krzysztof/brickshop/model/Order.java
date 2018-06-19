package krzysztof.brickshop.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity

public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(length = 140)
    private int brickCount;
    @Column
    private long creationDateTimestamp;

    public Order(int brickCount, long creationDateTimestamp) {
        this.brickCount = brickCount;
        this.creationDateTimestamp = creationDateTimestamp;
    }

    @ManyToOne(optional = false)
    private Customer customer;


}