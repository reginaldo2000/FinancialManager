package br.com.cary.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.cary.entity.Financeiro;
import br.com.cary.filter.FinanceiroFilter;
import br.com.cary.jpa.util.JPAUtil;

public class FinanceiroDAO {

	private EntityManager em;
	private static FinanceiroDAO instance;

	public static FinanceiroDAO getInstance() {
		if (instance == null) {
			instance = new FinanceiroDAO();
		}
		return instance;
	}

	public void salvarOuAtualizar(Financeiro f) {
		em = JPAUtil.createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(f);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public void deletar(Financeiro f) {
		em = JPAUtil.createEntityManager();
		try {
			f = em.find(Financeiro.class, f.getId());
			em.getTransaction().begin();
			em.remove(f);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public List<Financeiro> listar(FinanceiroFilter filtro) {
		List<Financeiro> temp = null;
		em = JPAUtil.createEntityManager();
		try {
			Session session = em.unwrap(Session.class);
			Criteria c = session.createCriteria(Financeiro.class);
			if (filtro.getDescricao() != null) {
				c.add(Restrictions.like("descricao", filtro.getDescricao() + "%"));
			} else if (filtro.getDataInicial() != null && filtro.getDataFinal() != null) {
				c.add(Restrictions.between("data", filtro.getDataInicial(), filtro.getDataFinal()));
			}
			c.addOrder(Order.desc("data"));
			c.setMaxResults(5);
			temp = c.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return temp;
	}

	public List<Financeiro> consultar(FinanceiroFilter filtro) {
		List<Financeiro> temp = null;
		em = JPAUtil.createEntityManager();
		try {
			Session session = em.unwrap(Session.class);
			Criteria c = session.createCriteria(Financeiro.class);
			if (filtro.getDataInicial() != null && filtro.getDataFinal() != null) {
				c.add(Restrictions.between("data", filtro.getDataInicial(), filtro.getDataFinal()));
			} else if (filtro.getCategoria() != null) {
				c.add(Restrictions.eq("categoria", filtro.getCategoria()));
			} else if (filtro.getDescricao() != null) {
				c.add(Restrictions.like("descricao", filtro.getDescricao() + "%"));
			}
			c.addOrder(Order.desc("data"));
			temp = c.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return temp;
	}

	public List<Financeiro> popularGrafico(FinanceiroFilter filtro) {
		List<Financeiro> temp = null;
		em = JPAUtil.createEntityManager();
		try {
			Session session = em.unwrap(Session.class);
			Criteria c = session.createCriteria(Financeiro.class);
			if (filtro.getDataInicial() != null && filtro.getDataFinal() != null) {
				c.add(Restrictions.between("data", filtro.getDataInicial(), filtro.getDataFinal()));
			}
			temp = c.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return temp;
	}

}
