package org.example.DAO;



import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.Model.Usuario;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@ApplicationScoped
public class UsuarioDAO {

    @Inject
    DataSource dataSource;
    public void create(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuario (id, nome, email, senha, telefone, data_cadastro, status, tipo_usuario, setor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, u.getId());
            ps.setString(2, u.getNome());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getSenha());
            ps.setString(5, u.getTelefone());
            ps.setString(6, u.getDataCadastro());
            ps.setString(7, u.getStatus());
            ps.setString(8, u.getTipoUsuario());
            ps.setString(9, u.getSetor());

            ps.executeUpdate();
        }
    }

    public List<Usuario> readAll() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("telefone"),
                        rs.getString("data_cadastro"),
                        rs.getString("status"),
                        rs.getString("tipo_usuario"),
                        rs.getString("setor")
                );
                lista.add(u);
            }
        }
        return lista;
    }

    public void update(Usuario u) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, telefone = ?, status = ?, tipo_usuario = ?, setor = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getNome());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getSenha());
            ps.setString(4, u.getTelefone());
            ps.setString(5, u.getStatus());
            ps.setString(6, u.getTipoUsuario());
            ps.setString(7, u.getSetor());
            ps.setInt(8, u.getId());

            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Usuario> buscarPorNome(String nome) throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE LOWER(nome) LIKE ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nome.toLowerCase() + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getString("telefone"),
                            rs.getString("data_cadastro"),
                            rs.getString("status"),
                            rs.getString("tipo_usuario"),
                            rs.getString("setor")
                    );
                    lista.add(u);
                }
            }
        }
        return lista;
    }
}
