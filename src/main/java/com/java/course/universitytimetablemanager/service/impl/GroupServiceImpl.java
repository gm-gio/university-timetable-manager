package com.java.course.universitytimetablemanager.service.impl;

import com.java.course.universitytimetablemanager.dao.GroupRepository;
import com.java.course.universitytimetablemanager.dao.StudentRepository;
import com.java.course.universitytimetablemanager.dao.UserRepository;
import com.java.course.universitytimetablemanager.dto.GroupDTO;
import com.java.course.universitytimetablemanager.model.Group;
import com.java.course.universitytimetablemanager.model.Student;
import com.java.course.universitytimetablemanager.model.User;
import com.java.course.universitytimetablemanager.service.GroupService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    private boolean groupNameExists(String groupName) {
        return groupRepository.findGroupByGroupName(groupName).isPresent();
    }

    @Override
    @Transactional
    public Group save(GroupDTO groupDTO) {
        if (groupNameExists(groupDTO.getGroupName())) {
            throw new RuntimeException("Group already exists " + groupDTO.getGroupName());
        }

        Group group = new Group();
        group.setGroupName(groupDTO.getGroupName());

        return groupRepository.save(group);
    }

    @Override
    public Group findById(Integer groupId) {
        return groupRepository.findById(groupId).orElse(null);
    }

    @Override
    @Transactional
    public void updateGroup(GroupDTO updatedGroup) {
        Group existingCourse = groupRepository.findById(updatedGroup.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + updatedGroup.getGroupId()));
        existingCourse.setGroupName(updatedGroup.getGroupName());
        groupRepository.save(existingCourse);
    }


    @Override
    public void deleteGroup(Group group) {
        groupRepository.delete(group);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Integer groupId) {
        groupRepository.deleteById(groupId);
    }

    @Override
    @Transactional
    public void addStudentToGroup(Integer studentId, Integer groupId) {

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found"));

        if (student != null & group != null) {
            group.addStudent(student);
            groupRepository.save(group);
        }
    }


    @Override
    @Transactional
    public void removeStudentFromGroup(Integer studentId, Integer groupId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found"));

        if (student != null & group != null) {
            group.removeStudent(student);
        }
    }

    @Override
    @Transactional
    public Group findGroupByStudentsByGroupId(Integer groupId) {
        return groupRepository.findGroupWithStudentsByGroupId(groupId).orElseThrow(() -> new EntityNotFoundException("Group with students not found"));
    }

    @Override
    @Transactional
    public void transferStudentBetweenGroups(Integer userId, Integer sourceGroupId, Integer targetGroupId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Student student = (Student) user;

        Group sourceGroup = groupRepository.findGroupWithStudentsByGroupId(sourceGroupId)
                .orElseThrow(() -> new EntityNotFoundException("Source group not found"));

        Group targetGroup = groupRepository.findGroupWithStudentsByGroupId(targetGroupId)
                .orElseThrow(() -> new EntityNotFoundException("Target group not found"));

        sourceGroup.getStudents().remove(student);
        targetGroup.getStudents().add(student);

        groupRepository.saveAll(Arrays.asList(sourceGroup, targetGroup));

        student.getGroups().remove(sourceGroup);
        student.getGroups().add(targetGroup);
        studentRepository.save(student);
    }
}
