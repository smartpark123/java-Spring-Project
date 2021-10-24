package project.first.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import project.first.controller.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private String category;

    private int price;

    private int stockQuantity;

    private String title;

    private String sellerComment;

    private String sellerHP;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String uploadFileName;

    private String storeFileName;






    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0 ){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }




}
