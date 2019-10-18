package com.testP.dao;

import com.testP.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface CommentDAO {

    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id,content,created_date,entity_id,entity_type,status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into " , TABLE_NAME , "(" , INSERT_FIELDS , ")" ,
            "values (#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select " , SELECT_FIELDS, " from ",TABLE_NAME, " where entity_id = #{entityId} and entity_type = #{entityType} " +
            " order by created_date desc "})
    List<Comment> selectCommentsByEntity(@Param("entityId") int entityId,
                                         @Param("entityType") int entityType);


    @Select({"select count(id) from ",TABLE_NAME," where entity_id = #{entityId} and entity_type = #{entityType} "})
    int getCommentCount(@Param("entityId") int entityId,
                                         @Param("entityType") int entityType);

    @Update({"update " ,TABLE_NAME ," set status = #{status}","where comment_id = #{commentId}"})
    int updateStatus(int commentId,int status);

    @Select({"select " , SELECT_FIELDS, " from ",TABLE_NAME, " where id = #{id}"})
    Comment getCommentById(@Param("id") int id);

    @Select({"select count(id) " ,  " from ",TABLE_NAME, " where id = #{id}"})
    int getUserCommentCount(@Param("id") int id);

}
