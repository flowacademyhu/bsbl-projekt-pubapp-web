package org.flow.controllers;

import org.flow.models.OrderLine;
import org.flow.models.Ordering;
import org.flow.models.Product;
import org.flow.repositories.OrderLineRepository;
import org.flow.repositories.OrderingRepository;
import org.flow.repositories.ProductRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    UserController userController = new UserController();

    //get all orderLines for the current ordering
    @GetMapping(path="/{id}/orderlines")
    public @ResponseBody ResponseEntity findOrderLines (@PathVariable("id") Long id, @RequestHeader String token) {
        //if(userController.isAdmin(token)) {
            Iterable<OrderLine> allOrderLines = orderLineRepository.findAll();
            List<OrderLine> orderLineList = new ArrayList<>();
            for (OrderLine orderLine : allOrderLines) {
                if (orderLine.getOrdering().getId().equals(orderingRepository.findById(id).get().getId())) {
                    orderLineList.add(orderLine);
                }
            }
            Iterable<OrderLine> lines = orderLineList;
            return ResponseEntity.ok(lines);
        /*} else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }*/

    }


    //get orderLine by ID
    @GetMapping(path="/{id}/orderlines/{id2}")
    public @ResponseBody ResponseEntity getOrderLineById (@PathVariable("id") Long id, @PathVariable("id2") Long id2, @RequestHeader String token) {
        //if(userController.isAdmin(token)) {
            Optional<OrderLine> orderLine = orderLineRepository.findById(id2);
            return ResponseEntity.ok(orderLine);
        /*} else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }*/

    }

    //create new orderLine
    @PostMapping(path="/{id}/orderlines")
    public @ResponseBody ResponseEntity addNewOrderLine (@PathVariable("id") Long orderId, @RequestBody String orderLine, @RequestHeader String token) {
        //*if(userController.isAdmin(token)) {
            OrderLine newOrderLine = new OrderLine();
            JSONObject jsonObject = new JSONObject(orderLine);
            newOrderLine.setOrdering(orderingRepository.findById(orderId).get());
            newOrderLine.setProduct(productRepository.findByName(jsonObject.getString("productName")));
            newOrderLine.setQuantity(jsonObject.getInt("quantity"));
            orderLineRepository.save(newOrderLine);
            return ResponseEntity.ok(newOrderLine);
        /*} else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }*/
    }

    //update orderLine
    @PutMapping(path="/{id}/orderlines/{id2}")
    public @ResponseBody ResponseEntity updateOrderLine (@PathVariable("id2") Long id, @RequestBody String orderLine, @RequestHeader String token) {
        //if(userController.isAdmin(token)) {
            OrderLine updatedOrderLine = orderLineRepository.findById(id).get();
            JSONObject jsonObject = new JSONObject(orderLine);
            updatedOrderLine.setProduct(productRepository.findByName(jsonObject.getString("productName")));
            updatedOrderLine.setQuantity(jsonObject.getInt("quantity"));
            orderLineRepository.save(updatedOrderLine);
            return ResponseEntity.ok(updatedOrderLine);
        /*} else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }*/
    }


    //delete orderLine by ID
    @DeleteMapping(path = "/{id}/orderlines/{id2}")
    public @ResponseBody ResponseEntity deleteOrderLine (@PathVariable("id") Long orderId, @PathVariable("id2") Long id, @RequestHeader String token) {
        if(userController.isAdmin(token)) {
            orderLineRepository.deleteById(id);
            return ResponseEntity.ok(orderLineRepository.findAll());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }

    }
}
