package com.techelevator.controller;

import com.techelevator.dao.menu.PizzaDao;
import com.techelevator.dao.pizzaOptions.CrustDao;
import com.techelevator.dao.pizzaOptions.SauceDao;
import com.techelevator.dao.pizzaOptions.SizeDao;
import com.techelevator.dao.pizzaOptions.ToppingDao;
import com.techelevator.model.menu.Menu;
import com.techelevator.model.menu.Pizza;
import com.techelevator.model.pizzaOptions.Crust;
import com.techelevator.model.pizzaOptions.Sauce;
import com.techelevator.model.pizzaOptions.Size;
import com.techelevator.model.pizzaOptions.Topping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
//@PreAuthorize("isAuthenticated()")
@PreAuthorize("permitAll()")
@CrossOrigin
public class PizzaController {

    @Autowired
    private PizzaDao pizzaDao;

    @Autowired
    private ToppingDao toppingDao;

    @Autowired
    private SizeDao sizeDao;
    @Autowired
    private CrustDao crustDao;
    @Autowired
    private SauceDao sauceDao;

    @RequestMapping(path = "/pizzas", method = RequestMethod.POST)
    public int addPizza(@RequestBody Pizza pizza){
        return pizzaDao.create(pizza);
    }

    @RequestMapping(path = "/pizzas/{id}", method = RequestMethod.GET)
    public Pizza getPizzaById(@PathVariable int id){
        return pizzaDao.getPizzaById(id);
    }

    @RequestMapping(path = "/menu", method = RequestMethod.GET)
    public Menu getMenu(){
        Menu menu = new Menu();
        menu.setCrusts(crustDao.getAllCrusts());
        menu.setSauces(sauceDao.getAllSauces());
        menu.setSizes(sizeDao.getAllSizes());
        menu.setToppings(toppingDao.getAllToppings());
        return menu;
    }

    @RequestMapping(path = "/toppings/{id}", method = RequestMethod.GET)
    public Topping getToppingById(@PathVariable int id){
        return toppingDao.getToppingById(id);
    }

    @RequestMapping(path = "/toppings", method = RequestMethod.POST)
    public Topping addToppingToTable(@RequestBody Topping topping){
        Topping newTopping = toppingDao.addToppingToTable(topping);
        if(newTopping.getId() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topping already exists");
        }
        return newTopping;
    }

    @RequestMapping(path = "/toppings", method = RequestMethod.GET)
    public List<Topping> getAllToppings(){
        return toppingDao.getAllToppings();
    }

    @RequestMapping(path = "/sizes/{id}", method = RequestMethod.GET)
    public Size getSizeById(@PathVariable int id){
        return sizeDao.getSizeById(id);
    }

    @RequestMapping(path = "/sizes", method = RequestMethod.POST)
    public Size addSizeToTable(@RequestBody Size size){
        Size newSize = sizeDao.addSizeToTable(size);
        if(newSize.getId() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Size already exists");
        }
        return newSize;
    }

    @RequestMapping(path = "/sizes", method = RequestMethod.GET)
    public List<Size> getAllSizes(){
        return sizeDao.getAllSizes();
    }
    @RequestMapping(path = "/crusts/{id}", method = RequestMethod.GET)
    public Crust getCrustById(@PathVariable int id){
        return crustDao.getCrustById(id);
    }

    @RequestMapping(path = "/crusts", method = RequestMethod.POST)
    public Crust addCrustToTable(@RequestBody Crust crust){
        Crust newCrust = crustDao.addCrustToTable(crust);
        if(newCrust.getId() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Crust already exists");
        }
        return newCrust;
    }

    @RequestMapping(path = "/crusts", method = RequestMethod.GET)
    public List<Crust> getAllCrusts(){
        return crustDao.getAllCrusts();
    }
    @RequestMapping(path = "/sauces/{id}", method = RequestMethod.GET)
    public Sauce getSauceById(@PathVariable int id){
        return sauceDao.getSauceById(id);
    }

    @RequestMapping(path = "/sauces", method = RequestMethod.POST)
    public Sauce addSauceToTable(@RequestBody Sauce sauce){
        Sauce newSauce = sauceDao.addSauceToTable(sauce);
        if(newSauce.getId() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sauce already exists");
        }
        return newSauce;
    }

    @RequestMapping(path = "/sauces", method = RequestMethod.GET)
    public List<Sauce> getAllSauces(){
        return sauceDao.getAllSauces();
    }
}
