package com.company.U1M6GroupProject.controller;

import com.company.U1M6GroupProject.model.Customer;
import com.company.U1M6GroupProject.model.Item;
import com.company.U1M6GroupProject.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    ServiceLayer serviceLayer;

    @Autowired
    public ItemController(ServiceLayer serviceLayer) {
        this.serviceLayer = serviceLayer;
    }


    @RequestMapping(value = "/item", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Item> getAllItems() {
        return serviceLayer.findAllItems();
    }

    @RequestMapping(value ="/items/{id}", method = RequestMethod.GET)
    @ResponseStatus (HttpStatus.OK)
    public Item getItemById (@PathVariable int id) {
        return serviceLayer.findItem(id);
    }


    @RequestMapping(value = "/item", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Item postANewItem(@RequestBody  Item itemToCreate) {
        return serviceLayer.saveItem(itemToCreate);

    }

    @RequestMapping (value = "/item/{id}", method = RequestMethod.PUT)
    @ResponseStatus (value = HttpStatus.OK)
    public void updateAItem(@PathVariable int id, @RequestBody Item item) {
        serviceLayer.updateItem(item);

    }

    @RequestMapping (value = "/item/{id}", method = RequestMethod.DELETE)
    @ResponseStatus (value = HttpStatus.OK)
    public void deleteAItem(@PathVariable int id) {
        serviceLayer.removeItem(id);

    }


}
