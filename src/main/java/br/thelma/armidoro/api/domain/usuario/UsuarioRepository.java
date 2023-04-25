package br.thelma.armidoro.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByLogin(String login);

    @Query("""
            select u
            from Usuario u
            where
            u.login = :login
            """)
    List<Usuario> findUsuariosPorNome(String login);

    @Query("""
            select u
            from Usuario u
            where
            u.login = :login
            """)
    Usuario findOneUsuariosPorNome(String login);
}
