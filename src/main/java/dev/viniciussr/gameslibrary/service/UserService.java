package dev.viniciussr.gameslibrary.service;

import dev.viniciussr.gameslibrary.dto.UserDTO;
import dev.viniciussr.gameslibrary.enums.Plans;
import dev.viniciussr.gameslibrary.model.User;
import dev.viniciussr.gameslibrary.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Salvar/Criar USUÁRIO
    public UserDTO createUser(UserDTO dto) {

        User savedUser = new User(
                dto.name(),
                dto.email(),
                dto.plan(),
                dto.activeLoans()
        );
        return new UserDTO(userRepository.save(savedUser));
    }

    // Atualizar USUÁRIO
    public UserDTO updateUser(Long id, UserDTO dto) {

        User updatedUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no id: " + id));

        updatedUser.setName(dto.name());
        updatedUser.setEmail(dto.email());
        updatedUser.setPlan(dto.plan());
        updatedUser.setActiveLoans(dto.activeLoans());

        return new UserDTO(userRepository.save(updatedUser));
    }

    // Deletar USUÁRIO
    public void deleteUser(Long id) {

        User deletedUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no id: " + id));

        userRepository.delete(deletedUser);
    }

    // --------------- MÉTODOS DE BUSCA/LISTA ---------------

    // Buscar USUÁRIO por ID
    public UserDTO findUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDTO::new)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no id: " + id));
    }

    // Listar USUÁRIOS (Todos)
    public List<UserDTO> listUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserDTO::new)
                .toList();
    }

    // Listar USUÁRIOS por NOME
    public List<UserDTO> listUsersByName(String name) {

        return userRepository.findByName(name)
                .stream()
                .map(UserDTO::new)
                .toList();
    }

    // Listar USUÁRIOS por EMAIL
    public List<UserDTO> listUsersByEmail(String email) {

        return userRepository.findByEmail(email)
                .stream()
                .map(UserDTO::new)
                .toList();
    }

    // Listar USUÁRIOS por PLANO
    public List<UserDTO> listUsersByPlan(Plans plan) {

        return userRepository.findByPlan(plan)
                .stream()
                .map(UserDTO::new)
                .toList();
    }
}