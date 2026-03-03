package adm.flordelis.GestaoFuncionarios.folhaPagamentoSemanal;

import adm.flordelis.GestaoFuncionarios.funcionarios.FuncionarioModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RelatorioSemanalFuncinonario {
    private static final int JORNADA_PADRAO_MINUTOS = 480;
    private static final int DIARIA_PADRAO = 60;

    private FuncionarioModel funcionario;
    private List<RegistroResumo> presencas;

    public double qtdExtras() {
        if (presencas == null) return 0.0;

        long totalMinutosExtras = presencas.stream()
                .mapToLong(p -> {
                    long minutosTrabalhados = Duration.between(p.horaChegada(), p.horaSaida()).toMinutes();
                    long extrasNoDia = minutosTrabalhados - JORNADA_PADRAO_MINUTOS;
                    return Math.max(0, extrasNoDia);
                })
                .sum();

        return (double) totalMinutosExtras / 60;
    }

    public BigDecimal aReceber(){
        if (presencas == null || presencas.isEmpty()) {
            return BigDecimal.ZERO;
        }
        long diasTrabalhados = presencas.size();
        BigDecimal componenteDias = new BigDecimal(diasTrabalhados).multiply(new BigDecimal(DIARIA_PADRAO));

        BigDecimal horasExtras = BigDecimal.valueOf(qtdExtras());
        BigDecimal componenteExtras = horasExtras.multiply(new BigDecimal("10"));

        return componenteDias.add(componenteExtras).setScale(2, RoundingMode.HALF_UP);
    }
}

