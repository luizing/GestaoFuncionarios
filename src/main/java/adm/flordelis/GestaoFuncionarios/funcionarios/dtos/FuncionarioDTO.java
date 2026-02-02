package adm.flordelis.GestaoFuncionarios.funcionarios.dtos;

import java.time.LocalDate;

public record FuncionarioDTO(Long id, String nome, LocalDate contratoInicial) {
}
