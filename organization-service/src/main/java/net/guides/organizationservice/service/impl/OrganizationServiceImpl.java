package net.guides.organizationservice.service.impl;

import lombok.AllArgsConstructor;
import net.guides.organizationservice.dto.OrganizationDto;
import net.guides.organizationservice.entity.Organization;
import net.guides.organizationservice.mapper.OrganizationMapper;
import net.guides.organizationservice.repository.OrganizationRepository;
import net.guides.organizationservice.service.OrganizationService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;

    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {

        Organization organization = OrganizationMapper.mapToOrganization(organizationDto);
        Organization savedOrganization = organizationRepository.save(organization);

        return OrganizationMapper.mapToOrganizationDto(savedOrganization);
    }

    @Override
    public OrganizationDto getOrganizationByCode(String organizationCode) {
        Organization organization = organizationRepository.findByOrganizationCode(organizationCode);
        return OrganizationMapper.mapToOrganizationDto(organization);
    }

}
