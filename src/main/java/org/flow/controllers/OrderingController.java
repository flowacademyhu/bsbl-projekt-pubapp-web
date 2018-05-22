package org.flow.controllers;
import org.flow.models.Ordering;
import org.flow.repositories.OrderingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/ordering")
public class OrderingController {

    @Autowired
    private OrderingRepository orderingRepository;

    //get all orderings
    @GetMapping(path="/")
    public @ResponseBody Iterable<Ordering> findAllProducts () {
        return orderingRepository.findAll();
    }

    // get ordering by ID
    @GetMapping(path="/{id}")
    public @ResponseBody Ordering getProductById (@PathVariable("id") Long id) {
        Optional<Ordering> ordering = orderingRepository.findById(id);
        return ordering.get();
    }

    //create new ordering
    @PostMapping(path="/")
    public @ResponseBody Ordering addNewOrdering (@RequestParam String qrCodePath) {
        Ordering newOrdering = new Ordering();
        newOrdering.setQrCodePath(qrCodePath);
        orderingRepository.save(newOrdering);
        return newOrdering;
    }

    //update ordering
    @PutMapping(path="/{id}")
    public @ResponseBody Ordering updateOrdering (@PathVariable("id") Long id) {
        Ordering updatedOrdering = orderingRepository.findById(id).get();
        orderingRepository.save(updatedOrdering);
        return updatedOrdering;
    }


    //delete ordering by ID
    @DeleteMapping(path = "/{id}")
    public @ResponseBody Iterable<Ordering> deleteORdering (@PathVariable("id") Long id) {
        orderingRepository.deleteById(id);
        return orderingRepository.findAll();
    }

}
