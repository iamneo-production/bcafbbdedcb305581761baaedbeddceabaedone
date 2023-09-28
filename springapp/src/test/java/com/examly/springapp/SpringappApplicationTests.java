package com.examly.springapp;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringJUnit4ClassRunner.class) 
@SpringBootTest(classes = SpringappApplication.class)
@AutoConfigureMockMvc
class SpringappApplicationTests {

	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void testPostData() throws Exception {
		String st = "{\"employeeId\": 200, \"employeeName\": \"SampleEmployee\",\"age\":30, \"mobile\": 1233455}";
		mockMvc.perform(MockMvcRequestBuilders.post("/employee")
				.contentType(MediaType.APPLICATION_JSON)
				.content(st)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}
	
	@Test
	public void testPostPayrollData() throws Exception {
		String st = "{\"payrollId\": 200, \"amount\": 10000.0, \"noOfDaysWorked\": 10}";
		mockMvc.perform(MockMvcRequestBuilders.post("/employee/200/payroll")
				.contentType(MediaType.APPLICATION_JSON)
				.content(st)
				.accept(MediaType.APPLICATION_JSON))  
		         .andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();	
		}
	

    @Test
    public void testGetEmployeeByID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/200")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
    
    @Test
    public void testGetPayrollById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/200/payroll")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employee")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

    private void checkClassExists(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            fail("Class " + className + " does not exist.");
        }
    }

    private void checkFieldExists(String className, String fieldName) {
        try {
            Class<?> clazz = Class.forName(className);
            clazz.getDeclaredField(fieldName);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            fail("Field " + fieldName + " in class " + className + " does not exist.");
        }
    }

	private void checkAnnotationExists(String className, String annotationName) {
		try {
			Class<?> clazz = Class.forName(className);
			ClassLoader classLoader = clazz.getClassLoader();
			Class<?> annotationClass = Class.forName(annotationName, false, classLoader);
			assertNotNull(clazz.getAnnotation((Class) annotationClass)); // Use raw type
		} catch (ClassNotFoundException | NullPointerException e) {
			fail("Class " + className + " or annotation " + annotationName + " does not exist.");
		}
	}
	

	 @Test
   public void testControllerClassExists() {
       checkClassExists("com.examly.springapp.controller.ApiController");
   }

   @Test
   public void testPayrollRepoClassExists() {
       checkClassExists("com.examly.springapp.repository.PayrollRepo");
   }
   
   @Test
   public void testEmployeeRepoClassExists() {
       checkClassExists("com.examly.springapp.repository.EmployeeRepo");
   }

   @Test
   public void testServiceClassExists() {
       checkClassExists("com.examly.springapp.service.ApiService");
   }

   @Test
   public void testPayrollModelClassExists() {
       checkClassExists("com.examly.springapp.model.Payroll");
   }
   
   @Test
   public void testEmployeeModelClassExists() {
       checkClassExists("com.examly.springapp.model.Employee");
   }


   @Test
   public void testModelHasEmployeenameField() {
       checkFieldExists("com.examly.springapp.model.Employee", "employeeName");
   }

   @Test
   public void testModelHasPasswordForField() {
       checkFieldExists("com.examly.springapp.model.Employee", "age");
   }

   @Test
   public void testModelHasPayrollNumberForField() {
       checkFieldExists("com.examly.springapp.model.Payroll", "amount");
   }
   
   @Test
   public void testModelHasBalanceForField() {
       checkFieldExists("com.examly.springapp.model.Payroll", "noOfDaysWorked");
   }
   

   @Test
   public void testEmployeeModelHasEntityAnnotation() {
       checkAnnotationExists("com.examly.springapp.model.Employee", "javax.persistence.Entity");
   }
   
   @Test
   public void testPayrollModelHasEntityAnnotation() {
       checkAnnotationExists("com.examly.springapp.model.Payroll", "javax.persistence.Entity");
   }

   @Test
   public void testRepoHasRepositoryAnnotation() {
       checkAnnotationExists("com.examly.springapp.repository.EmployeeRepo", "org.springframework.stereotype.Repository");
   }
   
   @Test
   public void testRepo1HasRepositoryAnnotation() {
       checkAnnotationExists("com.examly.springapp.repository.PayrollRepo", "org.springframework.stereotype.Repository");
   }
   
   @Test
   public void testServiceHasServiceAnnotation() {
       checkAnnotationExists("com.examly.springapp.service.ApiService", "org.springframework.stereotype.Service");
   }
   
   @Test
   public void testControllerHasRestControllerAnnotation() {
       checkAnnotationExists("com.examly.springapp.controller.ApiController", "org.springframework.web.bind.annotation.RestController");
   }
   
   @Test 
   public void testControllerFolder() { 
       String directoryPath = "src/main/java/com/examly/springapp/controller"; // Replace with the path to your directory 
       File directory = new File(directoryPath); 
       assertTrue(directory.exists() && directory.isDirectory()); 
   }
   

	@Test 
   public void testModelFolder() { 
       String directoryPath = "src/main/java/com/examly/springapp/model"; // Replace with the path to your directory 
       File directory = new File(directoryPath); 
       assertTrue(directory.exists() && directory.isDirectory()); 
   }
   

	@Test 
   public void testRepositoryFolder() { 
       String directoryPath = "src/main/java/com/examly/springapp/repository"; // Replace with the path to your directory 
       File directory = new File(directoryPath); 
       assertTrue(directory.exists() && directory.isDirectory()); 
   }
   

	@Test 
   public void testServiceFolder() { 
       String directoryPath = "src/main/java/com/examly/springapp/service"; // Replace with the path to your directory 
       File directory = new File(directoryPath); 
       assertTrue(directory.exists() && directory.isDirectory()); 
   }

}
