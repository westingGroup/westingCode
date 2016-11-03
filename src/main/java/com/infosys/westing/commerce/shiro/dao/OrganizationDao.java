package com.infosys.westing.commerce.shiro.dao;

import java.util.List;

import com.infosys.westing.commerce.shiro.entity.Organization;

/**
 * 
 * @author Anne_Yan
 *
 */
public interface OrganizationDao {

    public Organization createOrganization(Organization organization);
    public Organization updateOrganization(Organization organization);
    public void deleteOrganization(Long organizationId);

    Organization findOne(Long organizationId);
    List<Organization> findAll();

    List<Organization> findAllWithExclude(Organization excludeOraganization);

    void move(Organization source, Organization target);
}
