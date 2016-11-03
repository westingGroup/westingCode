package com.infosys.westing.commerce.shiro.service;

import java.util.List;

import com.infosys.westing.commerce.shiro.entity.Organization;
/**
 * 
 * @author Anne_Yan
 * 2016年10月31日
 */
public interface OrganizationService {


    public Organization createOrganization(Organization organization);
    public Organization updateOrganization(Organization organization);
    public void deleteOrganization(Long organizationId);

    Organization findOne(Long organizationId);
    List<Organization> findAll();

    Object findAllWithExclude(Organization excludeOraganization);

    void move(Organization source, Organization target);
}
