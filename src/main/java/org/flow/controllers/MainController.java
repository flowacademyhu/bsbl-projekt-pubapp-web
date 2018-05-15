package org.flow.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/pubapp")
public class MainController {

    @GetMapping(path="/")
    public String render() {
        return "Hello there!";
    }
}
