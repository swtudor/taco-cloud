package com.example.tacocloud.data;

import com.example.tacocloud.domain.Order;
import com.example.tacocloud.domain.Taco;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepositoryImpl implements OrderRepository{
    private SimpleJdbcInsert orderInsert;
    private SimpleJdbcInsert orderTacoInsert;
    private ObjectMapper objectMapper;

    @Autowired
    public OrderRepositoryImpl(JdbcTemplate jdbc){
        this.orderInsert = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order").usingGeneratedKeyColumns("id");
        this.orderTacoInsert = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order_Tacos");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setCreatedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        for(Taco taco: tacos){
            saveTacoOrder(taco, orderId);
        }
        return order;
    }

    private long saveOrderDetails(Order order){
        @SuppressWarnings("unchecked")
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        values.put("placedAt", order.getCreatedAt());
        return orderInsert.executeAndReturnKey(values).longValue();
    }

    private void saveTacoOrder(Taco taco, long orderId){
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInsert.execute(values);
    }
}
