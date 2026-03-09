package adm.flordelis.GestaoFuncionarios.folhaPagamentoSemanal;

import java.time.LocalDate;
import java.time.LocalTime;

public record RegistroResumo(
        LocalDate data,
        LocalTime horaChegada,
        LocalTime horaSaidaAlmoco,
        LocalTime horaChegadaAlmoco,
        LocalTime horaSaida
) {}
