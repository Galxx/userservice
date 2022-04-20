package com.gorokhov.example.service;

import com.gorokhov.example.domain.Role;
import com.gorokhov.example.dto.RoleDTO;
import com.gorokhov.example.exceptions.DeleteRoleException;
import com.gorokhov.example.exceptions.NotFoundException;
import com.gorokhov.example.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;


    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name){

        return roleRepository.findByName(name).orElseThrow(() -> new NotFoundException(String.format("Роль с именем %s не найдена", name)));

    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role saveRole(RoleDTO roleDTO) {

        Role role = new Role();
        if(roleDTO.getId() != null){
            role.setId(roleDTO.getId());
        }
        role.setName(roleDTO.getName());

        return roleRepository.save(role);
    }

    public Role findById(Long roleId) {

        return roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(String.format("Роль с индентификатром %s не найдена", roleId)));

    }

    public void deleteById(Long roleId) {
        try {
            roleRepository.deleteById(roleId);
        }catch (RuntimeException e){
            throw new DeleteRoleException(String.format("Не удалось удалить роль с индентификатром %s ", roleId));
        }

    }
}
