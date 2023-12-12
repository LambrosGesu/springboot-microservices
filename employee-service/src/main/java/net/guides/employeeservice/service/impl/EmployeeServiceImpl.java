package net.guides.employeeservice.service.impl;

import lombok.AllArgsConstructor;
import net.guides.employeeservice.dto.APIResponseDto;
import net.guides.employeeservice.dto.DepartmentDto;
import net.guides.employeeservice.dto.EmployeeDto;
import net.guides.employeeservice.entity.Employee;
import net.guides.employeeservice.mapper.EmployeeMapper;
import net.guides.employeeservice.repository.EmployeeRepository;
import net.guides.employeeservice.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private WebClient webClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        Employee saveDEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(saveDEmployee);
    }

    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).get();

        DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(EmployeeMapper.mapToEmployeeDto(employee));
        apiResponseDto.setDepartment(departmentDto);
        return apiResponseDto;
    }
}
