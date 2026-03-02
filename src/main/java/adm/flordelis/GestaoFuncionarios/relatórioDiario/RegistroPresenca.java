package adm.flordelis.GestaoFuncionarios.relatórioDiario;

import adm.flordelis.GestaoFuncionarios.funcionarios.FuncionarioModel;
import adm.flordelis.GestaoFuncionarios.funcionarios.FuncionarioService;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import java.time.LocalTime;

@Embeddable
public class RegistroPresenca {
    @ManyToOne
    private FuncionarioModel funcionario;
    private LocalTime horarioChegada;
    private LocalTime horarioSaida;

    public RegistroPresenca(FuncionarioModel funcionario, LocalTime horarioChegada, LocalTime horarioSaida){
        this.funcionario = funcionario;
        this.horarioChegada = horarioChegada;
        this.horarioSaida = horarioSaida;
    }
}

