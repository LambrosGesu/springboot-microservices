package net.guides.employeeservice.service.impl;

import net.guides.employeeservice.dto.APIResponseDto;
import net.guides.employeeservice.dto.DepartmentDto;
import net.guides.employeeservice.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import net.guides.employeeservice.entity.Employee;
import net.guides.employeeservice.mapper.EmployeeMapper;
import net.guides.employeeservice.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import net.guides.employeeservice.service.EmployeeService;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private RestTemplate restTemplate;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        Employee saveDEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(saveDEmployee);
    }

    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).get();

        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/" + employee.getDepartmentCode(), DepartmentDto.class);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(EmployeeMapper.mapToEmployeeDto(employee));
        apiResponseDto.setDepartment(responseEntity.getBody());
        return apiResponseDto;
    }
}
