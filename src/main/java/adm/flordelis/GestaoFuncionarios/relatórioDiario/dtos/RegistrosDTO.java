package adm.flordelis.GestaoFuncionarios.relatórioDiario.dtos;

import java.time.LocalTime;

public record RegistrosDTO(Long idFuncionario, LocalTime horarioChegada, LocalTime horarioSaida) {
}
