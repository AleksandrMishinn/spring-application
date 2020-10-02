package com.andersen.service.impl;

import com.andersen.domain.Role;
import com.andersen.repository.RoleRepository;
import com.andersen.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleDao;

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> getRole(long id) {
        return roleDao.getRole(id);
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleDao.saveRole(role);
    }

    @Override
    @Transactional
    public void deleteRole(long id) {
        roleDao.deleteRole(id);
    }

}
