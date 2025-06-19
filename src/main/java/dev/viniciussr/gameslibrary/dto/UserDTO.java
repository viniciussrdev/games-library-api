package dev.viniciussr.gameslibrary.dto;

import dev.viniciussr.gameslibrary.enums.Plans;
import dev.viniciussr.gameslibrary.model.User;
import jakarta.validation.constraints.*;

public record UserDTO(

        Long idUser,

        @NotBlank(message = "NOME é campo obrigatório")
        String name,

        @NotBlank(message = "EMAIL é campo obrigatório")
        @Email(message = "Formato de EMAIL inválido")
        String email,

        @NotNull(message = "PLANO é campo obrigatório")
        @Pattern(regexp = "NOOB|PRO|LEGEND",
                message = "PLANO deve ser um entre NOOB - PRO - LEGEND")
        Plans plan,

        @NotNull(message = "EMPRÉSTIMOS ATIVOS é campo obrigatório")
        @Min(value = 0, message = "EMPRÉSTIMOS ATIVOS não pode ser menor que zero")
        Integer activeLoans
) {
    public UserDTO(User user) {
        this(
                user.getIdUser(),
                user.getName(),
                user.getEmail(),
                user.getPlan(),
                user.getActiveLoans()
        );
    }
}
