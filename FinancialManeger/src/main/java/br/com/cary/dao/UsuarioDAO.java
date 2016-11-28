package br.com.cary.dao;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.cary.entity.Usuario;
import br.com.cary.jpa.util.JPAUtil;

public class UsuarioDAO {

	private static UsuarioDAO instance;
	private EntityManager em;

	public static UsuarioDAO getInstance() {
		if (instance == null) {
			instance = new UsuarioDAO();
		}
		return instance;
	}

	public Usuario getUsuario(Usuario us) {
		em = JPAUtil.createEntityManager();
		try {
			Session s = em.unwrap(Session.class);
			Criteria c = s.createCriteria(Usuario.class);
			c.add(Restrictions.eq("usuario", us.getUsuario())).add(Restrictions.eq("senha", us.getSenha()));
			if (c.list().size() > 0) {
				return (Usuario) c.uniqueResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return null;
	}

}
