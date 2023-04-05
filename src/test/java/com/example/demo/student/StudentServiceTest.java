package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    //  private AutoCloseable autoCloseable;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        //Initialize the mock created above here before setup
        //MockitoAnnotations.openMocks(this);  // this will return autocloseable type:
//        autoCloseable =MockitoAnnotations.openMocks(this);
        underTest = new StudentService(studentRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
//    autoCloseable.close();
    }

    @Test
    void canGetAllStudents() {
        //when

        underTest.getAllStudents();

        //then

        //then we have to verify this studentRepo is invoked with method findAll;

        verify(studentRepository).findAll();  // this will verify class studentRepo has findAll Method with getAllStudents.

    }

    @Test
    void addStudent() {
        // Introduce Argument Capture which will capture the student passed in save method of StudentServiceClass
        //And help to get the capture student value so that
        //At last we can verify the capture object with student object for validation.

        //1. given
        Student student = new Student("Dhoni","dhoni@gmail.com",Gender.MALE);

        //2. when
        underTest.addStudent(student);

        //3. then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());  //to verify student saga save vane method xa ki xaina.

        Student captureStudent = studentArgumentCaptor.getValue();

        assertThat(captureStudent).isEqualTo(student);

    }



    @Test
    void willThrowWhenEmailIsTaken () {
        //1. given
        Student student = new Student(
                "Dhoni",
                "dhoni@gmail.com",
                 Gender.MALE);
        //when

        given(studentRepository.selectExistsEmail(anyString())).willReturn(false);

        //then
        assertThatThrownBy(()-> underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken");

        verify(studentRepository,never()).save(any());


    }
    @Test
    void canDeleteStudent() {
        //given
        long id =10;

        given(studentRepository.existsById(id)).willReturn(true);

        //when
        underTest.deleteStudent(id);
        //then
        verify(studentRepository).deleteById(id);
    }
    @Test
    void WillThrowErrorWhenIdNotFound(){
        //given
        long id =10;
        given(studentRepository.existsById(id)).willReturn(false);

        //when
        //then
        assertThatThrownBy(()->underTest.deleteStudent(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " +id+ " does not exist");

        verify(studentRepository, never()).deleteById(any());

    }

}