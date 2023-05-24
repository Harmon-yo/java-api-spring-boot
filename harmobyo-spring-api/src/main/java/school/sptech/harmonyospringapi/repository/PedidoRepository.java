package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.harmonyospringapi.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
