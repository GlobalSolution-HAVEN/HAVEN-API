package org.example.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.DAO.HumorDAO;
import org.example.Model.Humor;

import java.util.List;
@ApplicationScoped

public class HumorService {
@Inject
     HumorDAO dao;

    public List<Humor> listarTodos() throws Exception {
        return dao.readAll();
    }

    public Humor buscarPorId(int id) throws Exception {
        for (Humor h : dao.readAll()) {
            if (h.getId() == id) return h;
        }
        return null;
    }

    public void cadastrar(Humor humor) throws Exception {
        dao.create(humor);
    }

    public void atualizar(Humor humor) throws Exception {
        dao.update(humor);
    }

    public void excluir(int id) throws Exception {
        dao.delete(id);
    }
}
