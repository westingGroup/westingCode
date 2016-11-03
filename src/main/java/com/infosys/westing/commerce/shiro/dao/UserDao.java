package com.infosys.westing.commerce.shiro.dao;

import java.util.List;

import com.infosys.westing.commerce.shiro.entity.User;
/**
 * 
 * @author Anne_Yan
 * 2016年10月31日
 */
public interface UserDao {

    public User createUser(User user);
    public User updateUser(User user);
    public void deleteUser(Long userId);

    User findOne(Long userId);

    List<User> findAll();

    User findByUsername(String username);

}
