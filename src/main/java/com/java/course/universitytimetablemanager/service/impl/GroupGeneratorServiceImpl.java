package com.java.course.universitytimetablemanager.service.impl;

import com.java.course.universitytimetablemanager.dao.GroupRepository;
import com.java.course.universitytimetablemanager.model.Group;
import com.java.course.universitytimetablemanager.service.GroupGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GroupGeneratorServiceImpl implements GroupGeneratorService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupGeneratorServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    private final String[] groupNames = {"A-01", "B-02", "C-03", "D-04", "E-05", "F-06", "G-07", "H-08", "I-09", "J-10"};

    @Override
    public void generateGroupsIfNeed() {
        if (groupRepository.count() == 0) {
            List<Group> groups = new ArrayList<>();
            for (String groupName : groupNames) {
                Group group = new Group(groupName);
                groups.add(group);
            }
            groupRepository.saveAll(groups);
        }
    }
}
