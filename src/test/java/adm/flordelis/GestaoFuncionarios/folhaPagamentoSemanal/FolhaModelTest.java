package adm.flordelis.GestaoFuncionarios.folhaPagamentoSemanal;

import adm.flordelis.GestaoFuncionarios.funcionarios.FuncionarioModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RelatorioSemanalFuncinonarioTest {

    private RelatorioSemanalFuncinonario relatorio;
    private FuncionarioModel funcionario;

    @BeforeEach
    void setUp() {
        relatorio = new RelatorioSemanalFuncinonario();
        funcionario = new FuncionarioModel("João Silva", LocalDate.of(2020,1,1));

    }

    // Inicialmente sem contabilizar o sábado

    // addDiaria
    @Test
    void ultrapassarLimitedeRegistros(){
        for (int i = 0; i < 6; i++) {
            relatorio.addDiaria(new RegistroResumo(
                    LocalDate.now().plusDays(i),
                    LocalTime.of(7, 30),
                    LocalTime.of(11, 30),
                    LocalTime.of(13, 0),
                    LocalTime.of(17, 15)
            ));
        }

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            relatorio.addDiaria(new RegistroResumo(
                    LocalDate.now().plusDays(6),
                    LocalTime.of(7, 30),
                    LocalTime.of(11, 30),
                    LocalTime.of(13, 0),
                    LocalTime.of(17, 15)
            ));
        });

        assertEquals("Limite semanal atingido: O relatório suporta no máximo 6 registros (Segunda a Sábado).",
                exception.getMessage());

    }


    // qtdExtras
    @Test
    void semanaCompletaSemExtras(){
        // 5 dias de trabalho, nenhuma hora extra
        relatorio.addDiaria(new RegistroResumo(
                LocalDate.now(),
                LocalTime.of(7, 30),
                LocalTime.of(11, 30),
                LocalTime.of(13, 0),
                LocalTime.of(17, 15)));
        for (int i = 1; i < 5; i++){
            relatorio.addDiaria(new RegistroResumo(
                    LocalDate.now().plusDays(i),
                    LocalTime.of(7, 30),
                    LocalTime.of(11, 30),
                    LocalTime.of(13, 0),
                    LocalTime.of(17, 15)));
        }

        double resultado = relatorio.qtdExtras();
        assertEquals(0, resultado, "A quantidade de horas extras deve ser 0");
    }


    @Test
    void deveCalcularQuantidadeDeHorasExtrasCorretamente() {
        // Cenário: 2 dias de trabalho
        // Dia 1: 7:30 às 17:15 (9 horas e 45min totais - 1 hora e meia de almoço - 15min de merenda = 0h extra)
        // Dia 2: 7:30 às 18:30 (11 horas totais - 1 hora e meia de almoço - 30min de merenda = 1h extras)
        // Total esperado: 1 horas extras

        relatorio.addDiaria(new RegistroResumo(LocalDate.now(),
                LocalTime.of(7, 30),
                LocalTime.of(11, 0),
                LocalTime.of(12, 30),
                LocalTime.of(17, 15)
        ));
        relatorio.addDiaria(new RegistroResumo(
                LocalDate.now().plusDays(1),
                LocalTime.of(7, 30),
                LocalTime.of(11, 0),
                LocalTime.of(12, 30),
                LocalTime.of(18, 30)
        ));

        double resultado = relatorio.qtdExtras();
        assertEquals(1, resultado, "A quantidade de horas extras deve ser 1");
    }


    @Test
    void deveCalcularValorAReceberComBaseNaFormula() {
        // Fórmula: 60 * dias + 10 * extras
        // Cenário: 3 dias trabalhados e 2 horas extras totais
        // Cálculo: (60 * 3) + (10 * 2) = 180 + 20 = 200.00

        relatorio.addDiaria(new RegistroResumo(
                LocalDate.now(),
                LocalTime.of(7, 30),
                LocalTime.of(12, 0),
                LocalTime.of(13, 30),
                LocalTime.of(18, 15)
        ));
        relatorio.addDiaria(new RegistroResumo(
                LocalDate.now().plusDays(1),
                LocalTime.of(7, 30),
                LocalTime.of(11, 30),
                LocalTime.of(13, 0),
                LocalTime.of(18, 15)
        ));
        relatorio.addDiaria(new RegistroResumo(
                LocalDate.now().plusDays(2),
                LocalTime.of(7, 30),
                LocalTime.of(12, 0),
                LocalTime.of(13, 30),
                LocalTime.of(17, 15)
        ));

        BigDecimal resultado = relatorio.aReceber();

        assertEquals(0, new BigDecimal("200.00").compareTo(resultado), "O valor a receber deve ser 200.00");
    }

    @Test
    void deveRetornarZeroQuandoNaoHaPresencas() {
        assertEquals(BigDecimal.ZERO, relatorio.aReceber());
        assertEquals(0.0, relatorio.qtdExtras());
    }
}