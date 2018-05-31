package org.flow.controllers;
import org.flow.models.Ordering;
import org.flow.repositories.OrderingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/ordering")
public class OrderingController {

    @Autowired
    private OrderingRepository orderingRepository;

    //get all orderings
    @GetMapping
    public @ResponseBody ResponseEntity findAllProducts () {
        return ResponseEntity.ok(orderingRepository.findAll());
    }

    // get ordering by ID
    @GetMapping(path="/{id}")
    public @ResponseBody ResponseEntity getProductById (@PathVariable("id") Long id) {
        Optional<Ordering> ordering = orderingRepository.findById(id);
        return ResponseEntity.ok(ordering);
    }

    //create new ordering
    @PostMapping
    public @ResponseBody ResponseEntity addNewOrdering () {
        Ordering newOrdering = new Ordering();
        newOrdering.setQrCodePath("qrCodePath");
        orderingRepository.save(newOrdering);
        return ResponseEntity.ok(newOrdering);
    }

    //delete ordering by ID
    @DeleteMapping(path = "/{id}")
    public @ResponseBody ResponseEntity deleteORdering (@PathVariable("id") Long id) {
        orderingRepository.deleteById(id);
        return ResponseEntity.ok(orderingRepository.findAll());
    }
}
