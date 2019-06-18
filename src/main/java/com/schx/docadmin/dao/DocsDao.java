package com.schx.docadmin.dao;

import com.schx.docadmin.model.Doc;
import com.schx.docadmin.utils.MyBlobTypeHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DocsDao {
        @Insert("INSERT INTO docs (file)  VALUES ( #{file,typeHandler=com.schx.docadmin.utils.MyBlobTypeHandler})")
        @SelectKey(statement = "SELECT seq FROM sqlite_sequence WHERE (name = 'docs')", before = false, keyProperty = "id", resultType = Integer.class)
        int insert(Doc doc);

        @Select("select * from docs where id=#{id}")
        @Results({ @Result(property = "id", column = "id"),
                @Result(property = "file", column = "file",jdbcType = JdbcType.BLOB,typeHandler = MyBlobTypeHandler.class) })
        Doc findByid(int id);

}
