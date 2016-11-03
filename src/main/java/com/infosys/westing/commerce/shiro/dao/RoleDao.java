package com.infosys.westing.commerce.shiro.dao;

import java.util.List;

import com.infosys.westing.commerce.shiro.entity.Role;
/**
 * 
 * @author Anne_Yan
 *
 */
public interface RoleDao {

    public Role createRole(Role role);
    public Role updateRole(Role role);
    public void deleteRole(Long roleId);

    public Role findOne(Long roleId);
    public List<Role> findAll();
}
