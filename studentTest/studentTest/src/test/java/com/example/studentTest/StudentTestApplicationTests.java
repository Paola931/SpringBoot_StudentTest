package com.example.studentTest;

import com.example.studentTest.Controllers.StudentController;
import com.example.studentTest.Entities.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles(value = "test")
class StudentTestApplicationTests {
	@Autowired
	private StudentController studentController;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void controllerExistTest() {
		assertThat(studentController).isNotNull();
	}
	private Student createStudent() throws Exception {
		Student student = new Student();
		student.setName("Mario");
		student.setSurname("Rossi");
		student.setWorking(false);
		return createStudent(student);
	}
	private Student createStudent(Student student) throws Exception {
		MvcResult result = createStudentRequest(student);
		Student studentMap = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
		assertThat(studentMap.getId()).isNotNull();
		assertThat(studentMap).isNotNull();
        return studentMap;
    }
	private MvcResult createStudentRequest(Student student) throws Exception {
		if(student == null) return null;
		String studentJSON = objectMapper.writeValueAsString(student);
		return this.mockMvc.perform(MockMvcRequestBuilders
				.post("/students/createStudent")
				.content(studentJSON)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}
	private Student getStudentFromID(Long id) throws Exception {
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
				.get("/students/getSingleStudent/" + id))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		try{
			String studentJSON = result.getResponse().getContentAsString();
			Student student = objectMapper.readValue(studentJSON, Student.class);
			assertThat(student.getId()).isNotNull();
			assertThat(student).isNotNull();
			return student;
		} catch (Exception e) {
            return null;
        }
    }
	@Test
	void createStudentTest() throws Exception {
		Student result = createStudent();
	}
	@Test
	void readStudentListTest() throws Exception {
		createStudent();
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
				.get("/students/getAllStudents"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		List<Student> students = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
		System.out.println("Students in list are: " + students.size());
		assertThat(students.size()).isNotZero();
	}
	@Test
	void readSingleStudentTest() throws Exception {
		Student student = createStudent();
		assertThat(student.getId()).isNotNull();
		Student studentID = getStudentFromID(student.getId());
		assertThat(studentID.getId()).isEqualTo(student.getId());
	}
    @Test
    void deleteSingleStudentTest() throws Exception {
        Student student = createStudent();
        assertThat(student.getId()).isNotNull();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/students/deleteSingleStudent/" + student.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Student studetID = getStudentFromID(student.getId());
        assertThat(studetID).isNull();
    }
}
