package org.flow.controllers;
import com.google.zxing.WriterException;
import org.flow.configuration.Validations;
import org.flow.models.OrderLine;
import org.flow.models.Ordering;
import org.flow.qr_code.QRCGenerator;
import org.flow.repositories.OrderLineRepository;
import org.flow.repositories.OrderingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/ordering")
public class OrderingController {

    @Autowired
    private OrderingRepository orderingRepository;
    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    Validations validations = new Validations();
    @Autowired
    QRCGenerator qrcGenerator = new QRCGenerator();

    //get all orderings
    @GetMapping
    public @ResponseBody ResponseEntity findAllProducts (@RequestHeader(value = "Authorization") String token) {
        if(validations.isAdmin(token)) {
            return ResponseEntity.ok(orderingRepository.findAll());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }
    }

    // get ordering by ID
    @GetMapping(path="/{id}")
    public @ResponseBody ResponseEntity getProductById (@PathVariable("id") Long id, @RequestHeader(value = "Authorization") String token) {
        if(validations.isAdmin(token)) {
            Optional<Ordering> ordering = orderingRepository.findById(id);
            return ResponseEntity.ok(ordering);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }

    }

    //create new ordering
    @PostMapping
    public @ResponseBody ResponseEntity addNewOrdering (@RequestHeader(value = "Authorization") String token) {
        //if(validations.isAdmin(token)) {
            Ordering newOrdering = new Ordering();
            newOrdering.setQrCodePath("qrCodePath");
            orderingRepository.save(newOrdering);
            return ResponseEntity.ok(newOrdering);
        /*} else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }*/
    }

    @GetMapping(path="/{id}/generate")
    public @ResponseBody ResponseEntity generateCode (@PathVariable("id") Long id, @RequestHeader(value = "Authorization") String token) {
        String data = id.toString();
        System.out.println(data);
        Ordering order = orderingRepository.findById(id).get();
        Iterable<OrderLine> allOrderlines = orderLineRepository.findAll();
        for (OrderLine orderLine : allOrderlines) {
            if (orderLine.getOrdering().getId().equals(order.getId())) {
                data += "." + orderLine.getProduct().getName();
                data += "." + orderLine.getQuantity().toString();
                System.out.println(data);
            }
        }
        try {
            qrcGenerator.generateQRCode(data.toString());
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(data);
        return ResponseEntity.ok(data);
    }

    //delete ordering by ID
    @DeleteMapping(path = "/{id}")
    public @ResponseBody ResponseEntity deleteOrdering (@PathVariable("id") Long id, @RequestHeader String token) {
        if(validations.isAdmin(token)) {
            orderingRepository.deleteById(id);
            return ResponseEntity.ok(orderingRepository.findAll());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }

    }
}
