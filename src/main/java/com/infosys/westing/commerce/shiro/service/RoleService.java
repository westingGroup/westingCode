package com.infosys.westing.commerce.shiro.service;

import java.util.List;
import java.util.Set;

import com.infosys.westing.commerce.shiro.entity.Role;
/**
 * 
 * @author Anne_Yan
 * 2016年10月31日
 */
public interface RoleService {


    public Role createRole(Role role);
    public Role updateRole(Role role);
    public void deleteRole(Long roleId);

    public Role findOne(Long roleId);
    public List<Role> findAll();

    /**
     * 根据角色编号得到角色标识符列表
     * @param roleIds
     * @return
     */
    Set<String> findRoles(Long... roleIds);

    /**
     * 根据角色编号得到权限字符串列表
     * @param roleIds
     * @return
     */
    Set<String> findPermissions(Long[] roleIds);
}
