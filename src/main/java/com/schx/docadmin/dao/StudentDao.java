package com.schx.docadmin.dao;

import com.schx.docadmin.model.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: xutao
 * @create: 2019-06-13 10:20
 **/
@Repository
@Mapper
public interface StudentDao {

    @Insert("insert into student (name,sex,age,department) values (#{name},#{sex},#{age},#{department})")
    @SelectKey(statement = "SELECT seq FROM sqlite_sequence WHERE (name = 'student')", before = false, keyProperty = "id", resultType = Integer.class)
    int insert(Student student);

    // 根据 ID 查询
    @Select("SELECT * FROM student WHERE id=#{id}")
    Student select(int id);

    // 查询全部
    @Select("SELECT * FROM student")
    List<Student> selectAll();

    // 更新 value
    @Update("UPDATE student SET name=#{name},sex=#{sex} WHERE id=#{id}")
    int updateValue(Student model);

    // 根据 ID 删除
    @Delete("DELETE FROM student WHERE id=#{id}")
    int delete(Integer id);
}
