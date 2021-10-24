package project.first.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import project.first.domain.Item;
import project.first.domain.Member;
import project.first.domain.Order;
import project.first.domain.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class OrderRepository {

    private final EntityManager em;




    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }


    public List<Order> findAll(){
        return em.createQuery("select o from Order o where o.member = 1", Order.class).getResultList();
    }

    public List<Order> findUserOrder(Long id, OrderSearch orderSearch){

        return em.createQuery("select o from Order o where o.member.id = :id and o.orderStatus = :orderStatus", Order.class)
                .setParameter("id", id)
                .setParameter("orderStatus", orderSearch.getStatus())
                .getResultList();
    }


    public List<Order> findAll(OrderSearch orderSearch){

        return em.createQuery("select o from Order o join o.member m"+
                "where o.status = :status" +
                "and m.name like :name", Order.class)
                .setParameter("status",orderSearch.getStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000) // 최대 1000건
                .getResultList();
    }



}
