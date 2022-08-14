package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userId=#{userId}")
    List<Note> findNoteByUserId(Integer userId);

    @Insert("INSERT INTO NOTES(notetitle, notedescription, userId) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    int insert(Note note);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    void update(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteId}")
    boolean delete(Integer noteId);
}
