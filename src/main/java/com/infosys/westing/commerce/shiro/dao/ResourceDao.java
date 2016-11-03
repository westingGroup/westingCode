package com.infosys.westing.commerce.shiro.dao;

import java.util.List;

import com.infosys.westing.commerce.shiro.entity.Resource;
/**
 * 
 * @author Anne_Yan
 *
 */
public interface ResourceDao {

    public Resource createResource(Resource resource);
    public Resource updateResource(Resource resource);
    public void deleteResource(Long resourceId);

    Resource findOne(Long resourceId);
    List<Resource> findAll();

}
