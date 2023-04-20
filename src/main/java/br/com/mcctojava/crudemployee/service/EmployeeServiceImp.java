package br.com.mcctojava.crudemployee.service;

import br.com.mcctojava.crudemployee.entity.Employee;
import br.com.mcctojava.crudemployee.exception.EmployeeNotFoundException;
import br.com.mcctojava.crudemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private final EmployeeRepository repository;

    @Autowired
    public EmployeeServiceImp(EmployeeRepository employeeDAO) {
        this.repository = employeeDAO;
    }

    public List<Employee> getEmployees() {
        return repository.findAll();
    }

    public Employee getEmployee(int id) {
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found: " + id));
    }

    public List<Employee> getEmployeeByFirstName(String firstName) {
        List<Employee> employees = repository.findByFirstName(firstName);

        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("Employee not found: " + firstName);
        }
        return employees;
    }

    @Transactional
    public Employee addEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Transactional
    public Employee update(int id, Employee employee) {
        Employee existing = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found: " + id));
        existing.setFirstName(employee.getFirstName());
        existing.setLastName(employee.getLastName());
        existing.setEmail(employee.getEmail());
        return repository.save(existing);
    }

    @Transactional
    public String deleteById(int id) {
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee was not found: " + id));
        repository.delete(employee);

        return "Employee %s %s was deleted.".formatted(employee.getFirstName(), employee.getLastName());
    }
}
