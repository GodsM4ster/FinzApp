package edu.udea.FinzAPP.repositories;

import edu.udea.FinzAPP.entities.Empleado;
import edu.udea.FinzAPP.entities.MovimientoDinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoDineroRepository extends JpaRepository<MovimientoDinero, Integer> {
    public List<MovimientoDinero> findByEmpleadoEmpresaNit(int nit);

    public List<MovimientoDinero> findByEmpleadoNumDocumento(int numDocumento);

}
