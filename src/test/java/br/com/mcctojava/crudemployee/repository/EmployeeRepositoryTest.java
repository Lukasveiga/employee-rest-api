package br.com.mcctojava.crudemployee.repository;

import br.com.mcctojava.crudemployee.entity.Employee;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
public class EmployeeRepositoryTest {

    private final EmployeeRepository repository;
    private Employee employee;

    @Autowired
    public EmployeeRepositoryTest(EmployeeRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp(){
       employee = new Employee("Lukas", "Veiga", "lukas@email.com");
       repository.save(employee);
    }

    @AfterEach
    void tearDown() {
        employee = null;
        repository.deleteAll();
    }

    @Test
    void findEmployeeByFirstName_ShouldReturnEmployees() {
        List<Employee> employeeList = repository.findByFirstName("Lukas");
        assertThat(employeeList.get(0))
                .usingRecursiveComparison()
                .isEqualTo(employeeList.get(0));

    }

    @Test
    void findEmployeeByFirstName_ShouldReturnEmptyListEmployees() {
        List<Employee> employeeList = repository.findByFirstName("John");
        assertThat(employeeList.isEmpty()).isTrue();
    }
}














