package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository underTest;
    @AfterEach
    void erase(){
        underTest.deleteAll();
    }
    @Test
    void itShouldCheckIfStudentEmailExist() {
        //given
         String email ="dhoni@gmail.com";
        Student student = new Student("Dhoni",email,Gender.MALE);
        underTest.save(student);
        //when case
        Boolean expected = underTest.selectExistsEmail(email); //it will return boolean
        //then
        assertThat(expected).isTrue();
    }
    @Test
    void itShouldCheckIfStudentEmailDoesNotExist() {
        //given
        String email ="dhoni@gmail.com";

        //when case
        Boolean expected = underTest.selectExistsEmail(email); //it will return boolean
        //then
        assertThat(expected).isFalse();
    }
}