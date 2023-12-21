package net.guides.employeeservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import net.guides.employeeservice.dto.APIResponseDto;
import net.guides.employeeservice.dto.DepartmentDto;
import net.guides.employeeservice.dto.EmployeeDto;
import net.guides.employeeservice.dto.OrganizationDto;
import net.guides.employeeservice.entity.Employee;
import net.guides.employeeservice.mapper.EmployeeMapper;
import net.guides.employeeservice.repository.EmployeeRepository;
import net.guides.employeeservice.service.APIClient;
import net.guides.employeeservice.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private WebClient webClient;
    private APIClient apiClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        Employee saveDEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(saveDEmployee);
    }

    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).get();

        DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();

        OrganizationDto organizationDto = webClient.get()
                .uri("http://localhost:8083/api/organizations/" + employee.getOrganizationCode())
                .retrieve()
                .bodyToMono(OrganizationDto.class)
                .block();

//        DepartmentDto departmentDto = apiClient.getDepartment(employee.getDepartmentCode());

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(EmployeeMapper.mapToEmployeeDto(employee));
        apiResponseDto.setDepartment(departmentDto);
        apiResponseDto.setOrganizationDto(organizationDto);
        return apiResponseDto;
    }

    public APIResponseDto getDefaultDepartment(Long employeeId, Exception exception) {
        Employee employee = employeeRepository.findById(employeeId).get();

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("R&D Department");
        departmentDto.setDepartmentCode("RD001");
        departmentDto.setDepartmentDescription("Research and Development Department");

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(EmployeeMapper.mapToEmployeeDto(employee));
        apiResponseDto.setDepartment(departmentDto);
        return apiResponseDto;
    }
}
