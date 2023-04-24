package br.com.mcctojava.crudemployee.controller;

import br.com.mcctojava.crudemployee.entity.Employee;
import br.com.mcctojava.crudemployee.exception.EmployeeNotFoundException;
import br.com.mcctojava.crudemployee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    ObjectMapper mapper;
    @MockBean
    private EmployeeService service;
    @Autowired
    private MockMvc mockMvc;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee("Lukas",
                                "Veiga",
                                "lukas@email.com");
    }

    @AfterEach
    void tearDown() {
        employee = null;
    }

    @Test
    void getEmployees_shouldReturnListOfEmployees() throws Exception {
        when(service.getEmployees())
                .thenReturn(List.of(employee));

        this.mockMvc
                .perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$[0].email").value(employee.getEmail()))
                .andDo(print());

    }

    @Test
    void getEmployees_shouldReturnNoContent204() throws Exception {
        when(service.getEmployees())
                .thenReturn(new ArrayList<>());

        this.mockMvc
                .perform(get("/api/v1/employees"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void getEmployeesByFirstName_shouldReturnEmployee() throws Exception {
        String firstName = "Lukas";
        when(service.getEmployeeByFirstName(firstName)).
                thenReturn(List.of(employee));

        this.mockMvc
                .perform(get("/api/v1/employees/first-name?name={firstName}", firstName))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$[0].email").value(employee.getEmail()))
                .andDo(print());
    }

    @Test
    void getEmployeesByFirstName_shouldReturnBadRequest400() throws Exception {
        when(service.getEmployeeByFirstName(""))
                .thenThrow(ConstraintViolationException.class);

        this.mockMvc
                .perform(get("/api/v1/employees/first-name?name="))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getEmployeesByFirstName_shouldReturnNoContent404() throws Exception {
        String firstName = "Lukas";
        when(service.getEmployeeByFirstName(firstName))
                .thenThrow(EmployeeNotFoundException.class);

        this.mockMvc
                .perform(get("/api/v1/employees/first-name?name={firstName}", firstName))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getEmployeeById_shouldReturnEmployee() throws Exception {
        int id = 0;
        when(service.getEmployee(id))
                .thenReturn(employee);

        this.mockMvc
                .perform(get("/api/v1/employees/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andDo(print());
    }

    @Test
    void getEmployeeById_shouldReturnContent404() throws Exception {
        when(service.getEmployee(any(Integer.class)))
                .thenThrow(EmployeeNotFoundException.class);

        this.mockMvc
                .perform(get("/api/v1/employees/{id}", 1))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void addEmployee_shouldReturnNewEmployee() throws Exception {
        String json = mapper.writeValueAsString(employee);

        when(service.addEmployee(employee)).thenReturn(employee);

        this.mockMvc
                .perform(post("/api/v1/employees")
                                 .contentType("application/json")
                                 .content(json))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void addEmployee_shouldReturnBadRequest400() throws Exception {
        employee.setFirstName("");
        String json = mapper.writeValueAsString(employee);

        when(service.addEmployee(employee))
                .thenReturn(employee);

        this.mockMvc
                .perform(post("/api/v1/employees")
                                 .contentType("application/json")
                                 .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void updateEmployee_shouldReturnUpdatedEmployee() throws Exception {
        String json = mapper.writeValueAsString(employee);

        when(service.update(employee.getId(), employee))
                .thenReturn(employee);

        this.mockMvc
                .perform(put("/api/v1/employees/{id}", employee.getId())
                                 .contentType("application/json")
                                 .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updateEmployee_shouldReturnBandRequest400() throws Exception {
        employee.setFirstName("");
        String json = mapper.writeValueAsString(employee);

        this.mockMvc
                .perform(put("/api/v1/employees/{id}", employee.getId())
                                 .contentType("application/json")
                                 .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void updateEmployee_shouldReturnNoFound404() throws Exception {
        int id = 1;
        String json = mapper.writeValueAsString(employee);

        when(service.update(id, employee))
                .thenThrow(EmployeeNotFoundException.class);

        this.mockMvc
                .perform(put("/api/v1/employees{id}", id)
                                 .contentType("application/json")
                                 .content(json))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deleteEmployee_shouldDeleteEmployee() throws Exception {
        when(service.deleteById(employee.getId()))
                .thenReturn("Employee %s %s was deleted.".formatted(employee.getFirstName(), employee.getLastName()));

        this.mockMvc
                .perform(delete("/api/v1/employees/{id}", employee.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee %s %s was deleted.".formatted(employee.getFirstName(), employee.getLastName())))
                .andDo(print());
    }

    @Test
    void deleteEmployee_shouldReturnNoFound404() throws Exception {
        int id = 1;

        when(service.deleteById(id)).thenThrow(EmployeeNotFoundException.class);

        this.mockMvc
                .perform(delete("/api/v1/employees/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}


