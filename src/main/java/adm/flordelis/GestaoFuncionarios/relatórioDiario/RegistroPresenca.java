package adm.flordelis.GestaoFuncionarios.relatórioDiario;

import adm.flordelis.GestaoFuncionarios.funcionarios.FuncionarioModel;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import java.time.LocalTime;

@Embeddable
public class RegistroPresenca {
    @ManyToOne
    private FuncionarioModel funcionario;
    private LocalTime horarioChegada;
    private LocalTime horarioSaida;
}

