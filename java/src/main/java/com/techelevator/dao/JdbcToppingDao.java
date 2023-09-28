package com.techelevator.dao;


import com.techelevator.model.Topping;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class JdbcToppingDao implements ToppingDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcToppingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Topping getToppingById(int id) {
        String sql = "SELECT * FROM toppings WHERE topping_id = ?;";
        Topping topping = new Topping();
        try{
            SqlRowSet row = jdbcTemplate.queryForRowSet(sql, id);
            if(row.next()){
                topping = mapRowToTopping(row);
            }
        } catch (ResourceAccessException | DataAccessException e){
            System.out.println(e.getMessage());
        }
        return topping;
    }

    public List<Topping> getAllToppings(){
        String sql = "SELECT * FROM toppings;";
        List<Topping> toppingList = new ArrayList<>();
        try {
            Topping topping = new Topping();
            SqlRowSet row = jdbcTemplate.queryForRowSet(sql);
            while(row.next()){
                topping = mapRowToTopping(row);
                toppingList.add(topping);
            }
        } catch (ResourceAccessException | DataAccessException e){
            System.out.println(e.getMessage());
        }

        return toppingList;
    }

    public Topping addToppingToTable(Topping topping){
        String sql = "INSERT INTO toppings (name, available, price) VALUES (?, ?, ?) RETURNING topping_id;";
        int newId = -1;
        Topping newTopping = new Topping();
        try{
            newId = jdbcTemplate.queryForObject(sql, Integer.class, topping.getName(), topping.isAvailable(), topping.getPrice());
            newTopping = getToppingById(newId);
        } catch (ResourceAccessException | DataAccessException e){
            System.out.println(e.getMessage());
        }

        return newTopping;
    }

    private Topping mapRowToTopping(SqlRowSet rs) {
        Topping topping = new Topping();
        topping.setId(rs.getInt("topping_id"));
        topping.setName(rs.getString("name"));
        topping.setAvailable(rs.getBoolean("available"));
        topping.setPrice(rs.getBigDecimal("price"));
        return topping;
    }
}
