package school.sptech.harmobyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.harmobyospringapi.domain.Usuario;
import school.sptech.harmobyospringapi.lista.ListaGenericaObj;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByCategoriaEquals(String categoria);


}
