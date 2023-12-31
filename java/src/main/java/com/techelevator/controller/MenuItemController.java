package com.techelevator.controller;

import com.techelevator.dao.menu.MenuItemDao;
import com.techelevator.model.menu.Menu;
import com.techelevator.model.menu.MenuItem;
import com.techelevator.model.pizzaOptions.Topping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
//@PreAuthorize("isAuthenticated()")
//@PreAuthorize("permitAll()")
@CrossOrigin
public class MenuItemController {

    @Autowired
    MenuItemDao menuItemDao;

    @RequestMapping(path = "/menuitems", method = RequestMethod.GET)
    public List<MenuItem> getAllMenuItems(){
        return menuItemDao.getAllMenuItems();
    }

    @Secured({"ROLE_EMPLOYEE", "ROLE_ADMIN" })
    @RequestMapping(path = "/menuitems/other", method = RequestMethod.POST)
    public int addMenuItemToTable(@RequestBody MenuItem menuItem) {
        int menuId = menuItemDao.addMenuItem(menuItem);
        if(menuId == -1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Menu Item already exists");
        }
        return menuId;
    }

    @Secured({"ROLE_EMPLOYEE", "ROLE_ADMIN" })
    @RequestMapping(path = "/menuitems/specialty", method = RequestMethod.POST)
    public int addSpecialtyPizzaToTable(@RequestBody MenuItem menuItem) {
        int menuId = menuItemDao.addSpecialtyPizza(menuItem);
        if(menuId == -1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Menu Item already exists");
        }
        return menuId;
    }

    @Secured({"ROLE_EMPLOYEE", "ROLE_ADMIN" })
    @RequestMapping(path = "/menuitems", method = RequestMethod.PUT)
    public void updateMenuItems(@RequestBody MenuItem menuItem){
        boolean updated = menuItemDao.updateMenuItem(menuItem);
        if(!updated){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update");
        }
    }

}
