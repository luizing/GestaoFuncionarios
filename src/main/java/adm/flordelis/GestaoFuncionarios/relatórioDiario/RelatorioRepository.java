package adm.flordelis.GestaoFuncionarios.relatórioDiario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RelatorioRepository extends JpaRepository<RelatorioModel, Long> {
    Optional<RelatorioModel> findByData(LocalDate data);
}
