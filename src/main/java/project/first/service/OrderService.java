package project.first.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.first.domain.*;
import project.first.repository.ItemRepository;
import project.first.repository.MemberRepository;
import project.first.repository.OrderRepository;
import project.first.repository.OrderSearch;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        Member member = memberRepository.findById(memberId);
        Item item = itemRepository.findOne(itemId);

        OrderItem orderItem = OrderItem.createOrderItem(member, item, item.getPrice(), count);

        Order order = Order.createOrder(member, orderItem);
        orderRepository.save(order);

        return order.getId();

    }

    public List<Order> findOrders(Long id, OrderSearch orderSearch){

        return orderRepository.findUserOrder(id, orderSearch);
    }

    @Transactional
    public void cancelOrder(Long orderId, Member member){

        Order order = orderRepository.findOne(orderId);
        Member user = memberRepository.findById(member.getId());

        order.cancel();
        member.setMoney(user.getMoney());
    }



    public List<Order> findOrder(){
        return orderRepository.findAll();
    }



}
