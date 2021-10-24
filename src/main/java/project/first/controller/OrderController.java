package project.first.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.first.Manager.SessionConst;
import project.first.domain.Member;
import project.first.domain.Order;
import project.first.domain.OrderStatus;
import project.first.repository.OrderSearch;
import project.first.service.ItemService;
import project.first.service.MemberService;
import project.first.service.OrderService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;


    @PostMapping("items/detail/{itemId}/edit")
    public String order(@SessionAttribute(SessionConst.LOGIN_MEMBER) Member member,
                        @PathVariable Long itemId,
                        @RequestParam("count") int count){

        orderService.order(member.getId(), itemId, count);

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@SessionAttribute(SessionConst.LOGIN_MEMBER) Member member,
                           OrderSearch orderSearch,
                            Model model) {


        List<Order> orders = orderService.findOrders(member.getId(), orderSearch);

        //List<Order> order = orderService.findOrder();

        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@SessionAttribute(SessionConst.LOGIN_MEMBER) Member member,@PathVariable("orderId") Long orderId){

        orderService.cancelOrder(orderId, member);

        return "redirect:/orders";
    }
}
