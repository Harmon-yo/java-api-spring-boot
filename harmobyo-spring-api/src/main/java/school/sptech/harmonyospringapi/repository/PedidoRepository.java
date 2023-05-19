package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.harmonyospringapi.domain.Pedido;
import school.sptech.harmonyospringapi.domain.PedidoKey;

public interface PedidoRepository extends JpaRepository<Pedido, PedidoKey> {

}
