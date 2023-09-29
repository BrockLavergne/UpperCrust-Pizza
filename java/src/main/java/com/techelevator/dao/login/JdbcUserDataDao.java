package com.techelevator.dao.login;

import com.techelevator.model.login.UserData;
import com.techelevator.model.orders.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

@Component
public class JdbcUserDataDao implements UserDataDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDataDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserData getUserData(int userId){
        String sql = "SELECT * FROM user_data WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            return mapRowToUserData(results);
        } else {
            return null;
        }
    }

    @Override
    public UserData updateUserData(UserData updatedUserData) {
        String sql = "UPDATE user_data SET email = ?, phone = ?, credit_card = ?, address = ? WHERE user_id = ?;";
        int numOfRows = -1;

        try{
            numOfRows = jdbcTemplate.update(sql, updatedUserData.getEmail(), updatedUserData.getPhone(), updatedUserData.getCardNumber(),
                    updatedUserData.getAddress(), updatedUserData.getUserId());

            if(numOfRows == -1){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException e){
            System.out.println(e.getMessage());
        }
        return getUserData(updatedUserData.getUserId());
    }

    private UserData mapRowToUserData(SqlRowSet rowSet){
        UserData userData = new UserData();
        userData.setEmail(rowSet.getString("email"));
        userData.setPhone(rowSet.getString("phone"));
        userData.setCardNumber(rowSet.getString("credit_card"));
        userData.setAddress(rowSet.getString("address"));
        userData.setUserId(rowSet.getInt("user_id"));
        return userData;
    }

    public int createGuestData(Order order){
        int id = -1;
        try{
            String insertUserSql = "insert into user_data (email, address, phone) values (?,?,?) RETURNING data_id;";
            id = jdbcTemplate.queryForObject(insertUserSql, Integer.class, order.getEmail(), order.getAddress(), order.getPhone());
        } catch (ResourceAccessException | DataAccessException e){
            System.out.println(e.getMessage());
        }
        return id;
    }
}
