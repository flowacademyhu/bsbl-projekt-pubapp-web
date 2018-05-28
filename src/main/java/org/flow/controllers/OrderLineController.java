package org.flow.controllers;

import org.flow.models.OrderLine;
import org.flow.models.Ordering;
import org.flow.models.Product;
import org.flow.repositories.OrderLineRepository;
import org.flow.repositories.OrderingRepository;
import org.flow.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/ordering")
public class OrderLineController {

    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private OrderingRepository orderingRepository;
    @Autowired
    private ProductRepository productRepository;

    //get all orderLines for the current ordering
    @GetMapping(path="/{id}/orderlines")
    public @ResponseBody Iterable<OrderLine> findOrderLines (@PathVariable("id") Long id) {
        Iterable<OrderLine> allOrderLines = orderLineRepository.findAll();
        List<OrderLine> orderLineList = new ArrayList<>();
        for (OrderLine orderLine : allOrderLines) {
            if (orderLine.getOrdering().getId().equals(orderingRepository.findById(id).get().getId())) {
                orderLineList.add(orderLine);
            }
        }
        Iterable<OrderLine> lines = orderLineList;
        return lines;
    }


    //get orderLine by ID
    @GetMapping(path="/{id}/orderlines/{id2}")
    public @ResponseBody OrderLine getOrderLineById (@PathVariable("id") Long id, @PathVariable("id2") Long id2) {
        Optional<OrderLine> orderLine = orderLineRepository.findById(id2);
        return orderLine.get();
    }

    //create new orderLine
    @PostMapping(path="/{id}/orderlines/")
    public @ResponseBody OrderLine addNewOrderLine (@PathVariable("id") Long orderId, @RequestParam String product_id, @RequestParam int quantity) {
        OrderLine newOrderLine = new OrderLine();
        newOrderLine.setOrdering(orderingRepository.findById(orderId).get());
        newOrderLine.setProduct(productRepository.findByName(product_id));
        newOrderLine.setQuantity(quantity);
        orderLineRepository.save(newOrderLine);
        return newOrderLine;
    }

    //update orderLine
    @PutMapping(path="/{id}/orderlines/{id2}")
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
        orderLineRepository.deleteById(id);
        return orderLineRepository.findAll();
    }
}
