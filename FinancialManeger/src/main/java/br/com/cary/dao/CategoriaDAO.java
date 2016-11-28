package br.com.cary.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.cary.entity.Categoria;
import br.com.cary.entity.Financeiro;
import br.com.cary.jpa.util.JPAUtil;

public class CategoriaDAO {

	private EntityManager em;
	private static CategoriaDAO instance;

	public static CategoriaDAO getInstance() {
		if (instance == null) {
			instance = new CategoriaDAO();
		}
		return instance;
	}

	public void salvar(Categoria cat) {
		em = JPAUtil.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(cat);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public void atualizar(Categoria cat) {
		em = JPAUtil.createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(cat);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public List<Categoria> listar(String consulta) {
		em = JPAUtil.createEntityManager();
		List<Categoria> temp = new ArrayList<>();
		try {
			Session s = em.unwrap(Session.class);
			Criteria c = s.createCriteria(Categoria.class);
			if (!consulta.equals("") || consulta != null) {
				c.add(Restrictions.like("nomeCategoria", consulta + "%"));
			}
			c.addOrder(Order.asc("nomeCategoria"));
			temp = c.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return temp;
	}
	
	public Categoria porId(Long id) {
		em = JPAUtil.createEntityManager();
		try {
			return em.find(Categoria.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return null;
	}

}
