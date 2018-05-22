package org.flow.controllers;

import org.flow.models.OrderLine;
import org.flow.models.Ordering;
import org.flow.models.Product;
import org.flow.repositories.OrderLineRepository;
import org.flow.repositories.OrderingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/ordering/")
public class OrderLineController {

    @Autowired
    private OrderLineRepository orderLineRepository;
    private OrderingRepository orderingRepository;

    //get all orderLines for the current ordering
    @GetMapping(path="/{id}/orderlines")
    public @ResponseBody List<OrderLine> findOrderLines (@PathVariable("id") Long id) {
        Iterable<OrderLine> allOrderLines = orderLineRepository.findAll();
        List<OrderLine> conditions = new ArrayList<>();
        for (OrderLine condition : allOrderLines) {
            if (condition.getOrdering().equals(orderingRepository.findById(id).get())) {
                conditions.add(condition);
            }
        }
        return conditions;
    }


    // get orderLine by ID
    @GetMapping(path="/{id}/orderlines/{id2}")
    public @ResponseBody OrderLine getOrderLineById (@PathVariable("id2") Long id) {
        Optional<OrderLine> orderLine = orderLineRepository.findById(id);
        return orderLine.get();
    }

    //create new orderLine
    @PostMapping(path="/{id}/orderlines/")
    public @ResponseBody OrderLine addNewOrderLine (@PathVariable("id") Long orderId, @RequestParam Product product_id, @RequestParam int quantity) {
        OrderLine newOrderLine = new OrderLine();
        Ordering parentOrdering = orderingRepository.findById(orderId).get();
        newOrderLine.setOrdering(parentOrdering);
        newOrderLine.setProduct(product_id);
        newOrderLine.setQuantity(quantity);
        List<OrderLine> parentOrderingsOrderLineList = parentOrdering.getOrderLineList();
        orderLineRepository.save(newOrderLine);
        parentOrderingsOrderLineList.add(newOrderLine);
        parentOrdering.setOrderLineList(parentOrderingsOrderLineList);
        return newOrderLine;
    }

    //update orderLine
    @PostMapping(path="/{id}/orderlines/{id2}")
    public @ResponseBody OrderLine updateOrderLine (@PathVariable("id2") Long id, @RequestParam Product product_id, @RequestParam int quantity) {
        OrderLine updatedOrderLine = orderLineRepository.findById(id).get();
        updatedOrderLine.setProduct(product_id);
        updatedOrderLine.setQuantity(quantity);
        orderLineRepository.save(updatedOrderLine);
        return updatedOrderLine;
    }


    //delete orderLine by ID
    @DeleteMapping(path = "/{id}/orderlines/{id2}")
    public @ResponseBody Iterable<OrderLine> deleteOrderLine (@PathVariable("id") Long orderId, @PathVariable("id2") Long id) {
        Ordering parentOrdering = orderingRepository.findById(id).get();
        List<OrderLine> parentOrderingsOrderLineList = parentOrdering.getOrderLineList();
        parentOrderingsOrderLineList.remove(orderLineRepository.findById(id));
        parentOrdering.setOrderLineList(parentOrderingsOrderLineList);
        orderLineRepository.deleteById(id);
        return orderLineRepository.findAll();
    }
}
