package org.flow.controllers;
import org.flow.models.Ordering;
import org.flow.repositories.OrderingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/ordering")
public class OrderingController {

    @Autowired
    private OrderingRepository orderingRepository;

    UserController userController = new UserController();

    //get all orderings
    @GetMapping
    public @ResponseBody ResponseEntity findAllProducts (@RequestHeader String token) {
        if(userController.isAdmin(token)) {
            return ResponseEntity.ok(orderingRepository.findAll());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }
    }

    // get ordering by ID
    @GetMapping(path="/{id}")
    public @ResponseBody ResponseEntity getProductById (@PathVariable("id") Long id, @RequestHeader String token) {
        if(userController.isAdmin(token)) {
            Optional<Ordering> ordering = orderingRepository.findById(id);
            return ResponseEntity.ok(ordering);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }

    }

    //create new ordering
    @PostMapping
    public @ResponseBody ResponseEntity addNewOrdering (@RequestHeader String token) {
        //if(userController.isAdmin(token)) {
            Ordering newOrdering = new Ordering();
            newOrdering.setQrCodePath("qrCodePath");
            orderingRepository.save(newOrdering);
            return ResponseEntity.ok(newOrdering);
        /*} else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }*/
    }

    //delete ordering by ID
    @DeleteMapping(path = "/{id}")
    public @ResponseBody ResponseEntity deleteOrdering (@PathVariable("id") Long id, @RequestHeader String token) {
        if(userController.isAdmin(token)) {
            orderingRepository.deleteById(id);
            return ResponseEntity.ok(orderingRepository.findAll());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }

    }
}
