package project.first.repository;

import lombok.Getter;
import lombok.Setter;
import project.first.domain.OrderStatus;

@Getter @Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus status;


}
