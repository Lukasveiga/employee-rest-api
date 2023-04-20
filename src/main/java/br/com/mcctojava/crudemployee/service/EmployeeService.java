package br.com.mcctojava.crudemployee.service;

import br.com.mcctojava.crudemployee.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getEmployees();
    Employee getEmployee(int id);
    List<Employee> getEmployeeByFirstName(String firstName);
    Employee addEmployee(Employee employee);
    Employee update(int id, Employee employee);
    String deleteById(int id);
}
