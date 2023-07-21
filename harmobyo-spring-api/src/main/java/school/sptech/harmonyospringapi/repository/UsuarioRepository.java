package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.sptech.harmonyospringapi.domain.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    @Query("SELECT min(a.valorAula) FROM Professor p JOIN Aula a ON p.id = a.professor.id")
    Double obterPrecoMinimo();

    @Query("SELECT max(a.valorAula) FROM Professor p JOIN Aula a ON p.id = a.professor.id")
    Double obterPrecoMaximo();
}
