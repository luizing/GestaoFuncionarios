package adm.flordelis.GestaoFuncionarios.funcionarios;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_funcionarios")
public class FuncionarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate contratoInicial;

    public FuncionarioModel(String nome, LocalDate contratoInicial){
        this.nome = nome;
        this.contratoInicial = contratoInicial;
    }

    @Override
    public String toString(){
        return String.format("%s. %s",id, nome);
    }
}


