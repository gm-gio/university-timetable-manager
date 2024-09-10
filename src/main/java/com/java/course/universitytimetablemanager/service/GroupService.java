package com.java.course.universitytimetablemanager.service;

import com.java.course.universitytimetablemanager.dto.GroupDTO;
import com.java.course.universitytimetablemanager.model.Group;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupService {

    @Transactional
    Group save(GroupDTO groupDTO);

    Group findById(Integer groupId);

    @Transactional
    void updateGroup(GroupDTO updatedGroup);

    void deleteGroup(Group group);

    List<Group> getAllGroups();

    @Transactional
    void deleteById(Integer id);

    @Transactional
    void addStudentToGroup(Integer studentId, Integer groupId);

    @Transactional
    void removeStudentFromGroup(Integer studentId, Integer groupId);

    @Transactional
    Group findGroupByStudentsByGroupId(Integer groupId);

    @Transactional
    void transferStudentBetweenGroups(Integer userId, Integer sourceGroupId, Integer targetGroupId);

}
