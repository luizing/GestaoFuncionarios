package adm.flordelis.GestaoFuncionarios.relatórioDiario.dtos;

import adm.flordelis.GestaoFuncionarios.relatórioDiario.RegistroPresenca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record RelatorioDTO(Long Id, LocalDate data, LocalTime horaInicio, LocalTime horaTermino, List<RegistroPresenca> registros) {
}
