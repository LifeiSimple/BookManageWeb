package com.book.service.impl;

import com.book.dao.StudentMapper;
import com.book.entity.Student;
import com.book.service.StudentService;
import com.book.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    @Override
    public List<Student> getStudentList() {
        try (SqlSession session = MybatisUtil.getSession()) {
            StudentMapper studentMapper = session.getMapper(StudentMapper.class);
            return studentMapper.getStudentList();
        }
    }
}
