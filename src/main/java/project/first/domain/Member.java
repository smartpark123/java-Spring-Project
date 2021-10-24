package project.first.domain;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.CreationTimestamp;
import project.first.controller.exception.NotEnoughStockException;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String user_id;

    private String password;

    private String email;

    private String address;

    private int money;

    @CreationTimestamp //insert된 시간
    private Timestamp signupDate;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Item> items = new ArrayList<>();




    public void payment(int money){
        this.money += money;
    }
    public void purchase(int money, int count){
        int TotalPrice= money * count;
        int restMoney = this.money - TotalPrice;
        if(restMoney < 0 ){
            throw new NotEnoughStockException("돈이 부족합니다.");
        }
        this.money = restMoney;
    }

    public void moneyReturn(int orderMoney, int count){
        int returnMoney = this.money+(orderMoney * count);
        System.out.println(returnMoney);

        this.money = returnMoney;
    }



}
