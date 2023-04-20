package br.com.mcctojava.crudemployee.controller;

import br.com.mcctojava.crudemployee.entity.Employee;
import br.com.mcctojava.crudemployee.exception.ListEmployeesEmptyException;
import br.com.mcctojava.crudemployee.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {

    private final EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.service = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = service.getEmployees();
        if (employees.isEmpty()) {
            throw new ListEmployeesEmptyException("List of employees is empty");
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/first-name")
    public ResponseEntity<List<Employee>> getEmployeeByFirstName(@RequestParam("name") @NotEmpty String firstName) {
        List<Employee> employees = service.getEmployeeByFirstName(firstName);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee = service.getEmployee(id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody @Valid Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addEmployee(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody @Valid Employee employee) {
        return ResponseEntity.ok(service.update(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        String result = service.deleteById(id);
        return ResponseEntity.ok(result);
    }
}
