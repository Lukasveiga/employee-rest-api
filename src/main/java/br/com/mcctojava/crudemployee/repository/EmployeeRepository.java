package br.com.mcctojava.crudemployee.repository;

import br.com.mcctojava.crudemployee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByFirstName(String FirstName);
}
