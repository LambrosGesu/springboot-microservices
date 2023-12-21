package net.guides.organizationservice.service;

import net.guides.organizationservice.dto.OrganizationDto;


public interface OrganizationService {
    OrganizationDto saveOrganization(OrganizationDto organizationDto);
    OrganizationDto getOrganizationByCode(String organizationCode);
}
