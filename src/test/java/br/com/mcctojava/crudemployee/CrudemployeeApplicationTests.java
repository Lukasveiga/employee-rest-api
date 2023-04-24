package br.com.mcctojava.crudemployee;

import br.com.mcctojava.crudemployee.entity.Employee;
import br.com.mcctojava.crudemployee.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class CrudEmployeeApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private ObjectMapper mapper;
    private List<Employee> employees;

    @BeforeEach
    void setUp() {
        employees = List.of(new Employee("Lukas", "Veiga", "lukas@email.com"),
                            new Employee("John", "Carter", "john@email.com"));
        employees.forEach(employee -> repository.save(employee));
    }

    @AfterEach
    void tearDown() {
        employees = null;
        repository.deleteAll();
    }

    @Test
    void getEmployees_shouldReturnListOfEmployees() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].firstName").value(employees.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(employees.get(0).getLastName()))
                .andExpect(jsonPath("$[0].email").value(employees.get(0).getEmail()))
                .andDo(print());

    }

    @Test
    void getEmployeesByFirstName_shouldReturnEmployee() throws Exception {
        Employee employee = employees.get(0);
        this.mockMvc
                .perform(get("/api/v1/employees/first-name?name={firstName}", employee.getFirstName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$[0].email").value(employee.getEmail()))
                .andDo(print());
    }

    @Test
    void getEmployeesByFirstName_shouldReturnBadRequest400() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/employees/first-name?name="))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getEmployeesByFirstName_shouldReturnNoContent404() throws Exception {
        String firstName = "Carl";
        this.mockMvc
                .perform(get("/api/v1/employees/first-name?name={firstName}", firstName))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getEmployeeById_shouldReturnEmployee() throws Exception {
        Employee employee = employees.get(0);
        this.mockMvc
                .perform(get("/api/v1/employees/{id}", employee.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andDo(print());
    }

    @Test
    void getEmployeeById_shouldReturnContent404() throws Exception {
        int id = employees.size() + 1;
        this.mockMvc
                .perform(get("/api/v1/employees/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void addEmployee_shouldReturnNewEmployee() throws Exception {
        Employee employee = new Employee("Carl", "Sagan", "carl@email.com");
        String json = mapper.writeValueAsString(employee);
        this.mockMvc
                .perform(post("/api/v1/employees")
                                 .contentType("application/json")
                                 .content(json))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()));
    }

    @Test
    void addEmployee_shouldReturnBadRequest400() throws Exception {
        Employee employee = new Employee("", "Sagan", "carl@email.com");
        String json = mapper.writeValueAsString(employee);
        this.mockMvc
                .perform(post("/api/v1/employees")
                                 .contentType("application/json")
                                 .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void updateEmployee_shouldReturnUpdatedEmployee() throws Exception {
        int id = employees.get(0).getId();
        Employee employee = new Employee("Carl", "Sagan", "carl@email.com");
        String json = mapper.writeValueAsString(employee);
        this.mockMvc
                .perform(put("/api/v1/employees/{id}", id)
                                 .contentType("application/json")
                                 .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updateEmployee_shouldReturnBandRequest400() throws Exception {
        int id = 1;
        Employee employee = new Employee("Carl", "", "carl@email.com");
        String json = mapper.writeValueAsString(employee);
        this.mockMvc
                .perform(put("/api/v1/employees/{id}", id)
                                 .contentType("application/json")
                                 .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void updateEmployee_shouldReturnNoFound404() throws Exception {
        int id = employees.size() + 1;
        Employee employee = new Employee("Carl", "Sagan", "carl@email.com");
        String json = mapper.writeValueAsString(employee);
        this.mockMvc
                .perform(put("/api/v1/employees/{id}", id)
                                 .contentType("application/json")
                                 .content(json))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deleteEmployee_shouldDeleteEmployee() throws Exception {
        Employee employee = employees.get(0);
        this.mockMvc
                .perform(delete("/api/v1/employees/{id}", employee.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee %s %s was deleted.".formatted(employee.getFirstName(), employee.getLastName())))
                .andDo(print());
    }

    @Test
    void deleteEmployee_shouldReturnNoFound404() throws Exception {
        int id = employees.size() + 1;
        this.mockMvc
                .perform(delete("/api/v1/employees/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}