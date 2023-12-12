package net.guides.employeeservice.service;

import net.guides.employeeservice.dto.APIResponseDto;
import net.guides.employeeservice.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    APIResponseDto getEmployeeById(Long employeeId);
}
