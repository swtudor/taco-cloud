package com.example.tacocloud.web;

import com.example.tacocloud.data.OrderRepository;
import com.example.tacocloud.data.UserRepository;
import com.example.tacocloud.domain.Order;
import com.example.tacocloud.domain.User;
import com.example.tacocloud.security.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("orders")
public class OrderController {
    private OrderRepository orderRepository;
    private UserRepository userRepo;

    @Autowired
    public OrderController(OrderRepository orderRepository, UserRepository userRepo){
        this.orderRepository = orderRepository;
        this.userRepo = userRepo;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        model.addAttribute("order", new Order());
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors error, SessionStatus sessionStatus, Session session){
        if(error.hasErrors()){
            return "orderForm";
        }
        Authentication fake = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails phake = (MyUserDetails) fake.getPrincipal();
        User heyItsMe = userRepo.findById( phake.getId()).orElse(null);
        order.setUser(heyItsMe);
        order.setCreatedAt(new Date());
        orderRepository.save(order);
        sessionStatus.setComplete();
        log.info("Order received: "+ order);
        return "redirect:/";
    }

}
