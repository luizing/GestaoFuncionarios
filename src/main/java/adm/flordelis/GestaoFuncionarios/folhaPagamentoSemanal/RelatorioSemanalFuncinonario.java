package adm.flordelis.GestaoFuncionarios.folhaPagamentoSemanal;

import adm.flordelis.GestaoFuncionarios.funcionarios.FuncionarioModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RelatorioSemanalFuncinonario {

    // Adicionar .env

    private static final int JORNADA_PADRAO_MINUTOS = 480; //8 horas
    private static final int DESCONTO_MINUTOS_DIARIOS_VARIAVEL = 30; //caso hora extra, 15min da merenda da tarde + 15min da manhã fixa
    private static final int DIARIA_PADRAO = 60;

    private FuncionarioModel funcionario;
    private List<RegistroResumo> presencas = new ArrayList<>();

    public void addDiaria(RegistroResumo dia){
        if (presencas.size() >= 6){
            throw new IllegalStateException("Limite semanal atingido: O relatório suporta no máximo 6 registros (Segunda a Sábado).");
        }else{
            presencas.add(dia);
        }
    }

    // Não pode ser hardcoded, existem parametros variáveis -> numero de merendas no dia por exemplo.
    public double qtdExtras() {
        if (presencas == null) return 0.0;

        long totalMinutosExtras = presencas.stream()
                .mapToLong(p -> {
                    long minutosTrabalhados =
                            Duration.between(p.horaChegada(), p.horaSaidaAlmoco()).toMinutes() + Duration.between(p.horaChegadaAlmoco(), p.horaSaida()).toMinutes();
                    long extrasNoDia = minutosTrabalhados - JORNADA_PADRAO_MINUTOS;
                    if (extrasNoDia > 0){
                        extrasNoDia -= DESCONTO_MINUTOS_DIARIOS_VARIAVEL;
                    }
                    return Math.max(0, extrasNoDia);
                })
                .sum();

        return Math.ceil((double)totalMinutosExtras / 60);
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

