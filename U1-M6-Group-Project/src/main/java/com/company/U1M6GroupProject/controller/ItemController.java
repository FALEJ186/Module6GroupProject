package com.company.U1M6GroupProject.controller;

import com.company.U1M6GroupProject.model.Item;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemController {

    @RequestMapping(value = "/items", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Item postANewItem(@RequestBody  Item itemToCreate) {

    }
}
