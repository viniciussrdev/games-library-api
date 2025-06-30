package dev.viniciussr.gameslibrary.model;

import dev.viniciussr.gameslibrary.enums.Plans;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser; // ID

    private String name; // Nome

    private String email; // Email

    @Enumerated(EnumType.STRING)
    private Plans plan; // Plano Assinatura

    private Integer activeLoans; // Empréstimos Ativos

    public User(
            String name,
            String email,
            Plans plan,
            Integer activeLoans
    ) {
        this.name = name;
        this.email = email;
        this.plan = plan;
        this.activeLoans = activeLoans;
    }
}
