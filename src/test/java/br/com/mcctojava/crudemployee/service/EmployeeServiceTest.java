package br.com.mcctojava.crudemployee.service;

import br.com.mcctojava.crudemployee.entity.Employee;
import br.com.mcctojava.crudemployee.exception.EmployeeNotFoundException;
import br.com.mcctojava.crudemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;
    private EmployeeService service;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee("Lukas",
                               "Veiga", "lukas@email.com");
        service = new EmployeeServiceImp(repository);
    }

    @AfterEach
    void tearDown() {
        employee = null;
    }

    @Test
    void getEmployees_ShouldReturnListOfEmployees() {
        when(repository.findAll()).thenReturn(List.of(employee));
        List<Employee> employeeList = service.getEmployees();
        assertThat(employeeList.size()).isGreaterThan(0);
    }

    @Test
    void getEmployee_ShouldReturnEmployeeById() {
        when(repository.findById(1)).thenReturn(Optional.of(employee));
        Employee savedEmployee = service.getEmployee(1);
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee)
                .usingRecursiveComparison()
                .isEqualTo(employee);
    }

    @Test
    void getEmployee_ShouldThrowEmployeeNotFoundException() {
        assertThrows(EmployeeNotFoundException.class, () -> service.getEmployee(1));
    }

    @Test
    void getEmployeeByFirstName_shouldReturnEmployeeByFirstName() {
        List<Employee> employeeList = List.of(employee);
        when(repository.findByFirstName("Lukas")).thenReturn(employeeList);
        assertThat(service.getEmployeeByFirstName("Lukas").get(0))
                .usingRecursiveComparison()
                .isEqualTo(employee);
    }

    @Test
    void getEmployeeByFirstName_ShouldThrowEmployeeNotFoundException() {
        when(repository.findByFirstName("Lukas")).thenReturn(new ArrayList<>());
        assertThrows(EmployeeNotFoundException.class, () -> service.getEmployeeByFirstName("Lukas"));
    }


    @Test
    void addEmployee_shouldAddAndReturnNewEmployee() {
        when(repository.save(Mockito.any(Employee.class))).thenReturn(employee);
        Employee savedEmployee = service.addEmployee(employee);
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    void update_shouldUpdateAndReturnEmployee() {
        int id = 1;
        when(repository.findById(id)).thenReturn(Optional.ofNullable(employee));
        when(repository.save(employee)).thenReturn(employee);

        Employee updatedEmployee = service.update(id, employee);
        assertThat(updatedEmployee).isNotNull();
    }

    @Test
    void update_ShouldThrowEmployeeNotFoundException() {
        int id = 1;
        when(repository.findById(id)).thenThrow(EmployeeNotFoundException.class);
        assertThrows(EmployeeNotFoundException.class, () -> service.update(id, employee));
    }

    @Test
    void deleteById_shouldDeleteEmployeeAndReturnSuccessMessage() {
        int id = 1;
        when(repository.findById(id)).thenReturn(Optional.ofNullable(employee));
        doNothing().when(repository).delete(employee);
        assertThat(service.deleteById(id)
                           .equalsIgnoreCase("Employee %s %s was deleted."
                                                     .formatted(employee.getFirstName(), employee.getLastName()))).isTrue();
    }

    @Test
    void deleteById_ShouldThrowEmployeeNotFoundException() {
        int id = 1;
        when(repository.findById(id)).thenThrow(EmployeeNotFoundException.class);
        assertThrows(EmployeeNotFoundException.class, () -> service.deleteById(id));
    }
}