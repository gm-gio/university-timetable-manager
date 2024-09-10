package com.java.course.universitytimetablemanager.dao;

import com.java.course.universitytimetablemanager.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    Optional<Group> findGroupByGroupName(String groupName);

    @Query("SELECT g FROM Group g LEFT JOIN FETCH g.students WHERE g.groupId = :groupId")
    Optional<Group> findGroupWithStudentsByGroupId(@Param("groupId") Integer groupId);

}
