package net.guides.employeeservice.service.impl;

import net.guides.employeeservice.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import net.guides.employeeservice.entity.Employee;
import net.guides.employeeservice.mapper.EmployeeMapper;
import net.guides.employeeservice.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import net.guides.employeeservice.service.EmployeeService;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        Employee saveDEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(saveDEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).get();

        return EmployeeMapper.mapToEmployeeDto(employee);
    }
}
