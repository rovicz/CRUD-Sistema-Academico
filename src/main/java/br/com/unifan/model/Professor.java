package br.com.unifan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String formacao;

    @ManyToMany(mappedBy = "professores")
    private List<Disciplina> disciplinas;

    @Override
    public String toString() {
        return nome;
    }
}