package dev.viniciussr.gameslibrary.service;

import dev.viniciussr.gameslibrary.dto.UserDTO;
import dev.viniciussr.gameslibrary.enums.Plans;
import dev.viniciussr.gameslibrary.exception.loan.LoanLimitExceededException;
import dev.viniciussr.gameslibrary.exception.user.UserNotFoundException;
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

    // -------------------- CRUD BÁSICO --------------------

    // Criar USUÁRIO
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
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado no id: " + id));

        updatedUser.setName(dto.name());
        updatedUser.setEmail(dto.email());
        updatedUser.setPlan(dto.plan());
        updatedUser.setActiveLoans(dto.activeLoans());

        return new UserDTO(userRepository.save(updatedUser));
    }

    // Deletar USUÁRIO
    public void deleteUser(Long id) {

        User deletedUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado no id: " + id));

        userRepository.delete(deletedUser);
    }

    // --------------- MÉTODOS COMPLEMENTARES ---------------

    // Validar limite de EMPRÉSTIMOS do USUÁRIO
    void validateUserLoanLimit(User user) {

        int maxLoans = switch (user.getPlan()) {
            case NOOB -> 1;
            case PRO -> 3;
            case LEGEND -> 5;
        };

        if (user.getActiveLoans() >= maxLoans) {
            throw new LoanLimitExceededException(user);
        }
    }

    // Atualizar contagem de EMPRÉSTIMOS ATIVOS do USUÁRIO
    void updateUserLoanCount(User user, int x) {

        user.setActiveLoans(user.getActiveLoans() + x);
        userRepository.save(user);
    }

    // --------------- FILTROS ---------------

    // Buscar USUÁRIO por ID
    public UserDTO findUserById(Long id) {

        return userRepository.findById(id)
                .map(UserDTO::new)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado no id: " + id));

    }

    // Listar USUÁRIOS (Todos)
    public List<UserDTO> listUsers() {

        List<UserDTO> users = userRepository.findAll()
                .stream()
                .map(UserDTO::new)
                .toList();

        if (users.isEmpty()) {
            throw new UserNotFoundException("Nenhum Usuário encontrado");
        }
        return users;
    }

    // Listar USUÁRIOS por NOME
    public List<UserDTO> listUsersByName(String name) {

        List<UserDTO> users = userRepository.findByName(name)
                .stream()
                .map(UserDTO::new)
                .toList();

        if (users.isEmpty()) {
            throw new UserNotFoundException("Nenhum Usuário encontrado com o Nome: " + name);
        }
        return users;
    }

    // Listar USUÁRIOS por EMAIL
    public List<UserDTO> listUsersByEmail(String email) {

        List<UserDTO> users = userRepository.findByEmail(email)
                .stream()
                .map(UserDTO::new)
                .toList();

        if (users.isEmpty()) {
            throw new UserNotFoundException("Nenhum Usuário encontrado com o Email: " + email);
        }
        return users;
    }

    // Listar USUÁRIOS por PLANO
    public List<UserDTO> listUsersByPlan(Plans plan) {

        List<UserDTO> users = userRepository.findByPlan(plan)
                .stream()
                .map(UserDTO::new)
                .toList();

        if (users.isEmpty()) {
            throw new UserNotFoundException("Nenhum Usuário encontrado com o Plano: " + plan.name());
        }
        return users;
    }
}