package com.techelevator.dao.orders;

import com.techelevator.dao.menu.MenuItemDao;
import com.techelevator.dao.menu.PizzaDao;
import com.techelevator.model.menu.MenuItem;
import com.techelevator.model.menu.Pizza;
import com.techelevator.model.orders.Order;
import com.techelevator.model.orders.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcOrderDao implements OrderDao{

    @Autowired
    private PizzaDao pizzaDao;

    @Autowired
    private MenuItemDao menuItemDao;
    private final JdbcTemplate jdbcTemplate;
    public JdbcOrderDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public int create(Order order) {

        String sql = "INSERT INTO orders (status, data_id, delivery, total) VALUES (?, ?, ?, ?) RETURNING order_id;";
        String sql2 = "INSERT INTO orders_to_menu_items (order_id, item_id, quantity) VALUES (?, ?, ?);";
        int newId = -1;
        try{
            order.setStatus("pending");
            newId = jdbcTemplate.queryForObject(sql, Integer.class, order.getStatus(), order.getDataId(), order.isDelivery(), order.getTotal());
            if(newId == -1){throw new Exception();}
            System.out.println(order.getCustomPizzas());
            System.out.println(order.getMenuItems());
            for(Pizza pizza : order.getCustomPizzas()){
                int pizzaId = -1;
                pizzaId = pizzaDao.create(pizza);
                if(pizzaId == -1){throw new Exception();}
                if(jdbcTemplate.update(sql2, newId, pizzaId, pizza.getQuantity()) != 1){
                    throw new Exception();
                }
            }
            for(MenuItem menuItem : order.getMenuItems()){
                if(jdbcTemplate.update(sql2, newId, menuItem.getItemId(), menuItem.getQuantity()) != 1){
                    throw new Exception();
                }
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return newId;

    }

    @Override
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders JOIN user_data ON orders.data_id = user_data.data_id WHERE order_id = ?;";
        Order order = new Order();
        try {

            SqlRowSet row = jdbcTemplate.queryForRowSet(sql, orderId);
            while(row.next()){
                order = mapRowToOrder(row);
                List<Integer> itemIds = getItemsByOrderId(order.getOrderId());
                List<MenuItem> menuItems = new ArrayList<>();
                List<Pizza> pizzas = new ArrayList<>();
                for(int id : itemIds){
                    if(id >= 1001){
                        pizzas.add(pizzaDao.getPizzaById(id));
                    }else if(id > 0){
                        menuItems.add(menuItemDao.getMenuItemById(id));
                    }
                }
                order.setCustomPizzas(pizzas);
                order.setMenuItems(menuItems);
            }
        } catch (ResourceAccessException | DataAccessException e){
            System.out.println(e.getMessage());
        }
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders JOIN user_data ON orders.data_id = user_data.data_id;";
        List<Order> orderList = new ArrayList<>();
        try {
            Order order = new Order();
            SqlRowSet row = jdbcTemplate.queryForRowSet(sql);
            while(row.next()){
                order = mapRowToOrder(row);
                List<Integer> itemIds = getItemsByOrderId(order.getOrderId());
                List<MenuItem> menuItems = new ArrayList<>();
                List<Pizza> pizzas = new ArrayList<>();
                for(int id : itemIds){
                    if(id >= 1001){
                        pizzas.add(pizzaDao.getPizzaById(id));
                    }else if(id > 0){
                        menuItems.add(menuItemDao.getMenuItemById(id));
                    }
                }
                order.setCustomPizzas(pizzas);
                order.setMenuItems(menuItems);
                orderList.add(order);
            }
        } catch (ResourceAccessException | DataAccessException e){
            System.out.println(e.getMessage());
        }
        
        
        return orderList;
    }

    @Override
    public boolean updateStatus(OrderStatus orderStatus) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?;";
        int numOfRows = 0;
        try {
            numOfRows = jdbcTemplate.update(sql, orderStatus.getStatus(), orderStatus.getOrderId());
            if(numOfRows == 0){
                return false;
            }
        } catch (ResourceAccessException | DataAccessException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private Order mapRowToOrder(SqlRowSet row){
        Order order = new Order();

        order.setOrderId(row.getInt("order_id"));
        order.setDataId(row.getInt("data_id"));
        order.setStatus(row.getString("status"));
        order.setAddress(row.getString("address"));
        order.setPhone(row.getString("phone"));
        order.setTotal(row.getBigDecimal("total"));
        order.setEmail(row.getString("email"));
        order.setDelivery(row.getBoolean("delivery"));

        return order;
    }

    private List<Integer> getItemsByOrderId(int orderId){
        List<Integer> itemIds = new ArrayList<>();
        String sql = "SELECT item_id FROM orders_to_menu_items WHERE order_id = ?;";
        try {
            SqlRowSet row = jdbcTemplate.queryForRowSet(sql, orderId);
            while(row.next()){
                itemIds.add(row.getInt("item_id"));
            }
        }catch (ResourceAccessException | DataAccessException e){
            System.out.println(e.getMessage());
        }
        return itemIds;
    }

}
