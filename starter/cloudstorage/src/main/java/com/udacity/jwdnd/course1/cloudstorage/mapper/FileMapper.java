package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> findByUserId(Integer userId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} AND fileName = #{fileName}")
    File findByUserIdAndFileName(Integer userId, String fileName);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int save(File file);

    @Delete("DELETE FROM FILES WHERE filename = #{fileName} AND userId = #{userId}")
    void deleteFileByFileNameAndUserId(String fileName, Integer userId);

}
