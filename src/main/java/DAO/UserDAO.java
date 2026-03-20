package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import entity.Candidate;
import entity.user;
import util.HibernateUtil;

public class UserDAO {
	 public user login(String username, String password) {

	        Session session = HibernateUtil.getSessionFactory().openSession();

	        Query<user> q = session.createQuery(
	            "from user where username = :u and password = :p", user.class
	        );

	        q.setParameter("u", username);
	        q.setParameter("p", password);

	        user user = q.uniqueResult();

	        session.close();
	        return user;
	    }
	 public List<user> getAllUser() {
		    Session session = HibernateUtil.getSessionFactory().openSession();
		    List<user> listUser = null;

		    try {
		    	listUser = session
		                .createQuery("from user", user.class)
		                .getResultList();
		    } finally {
		        session.close();
		    }

		    return listUser;
		}
	 public boolean insertUser(user u) {
		    Session session = HibernateUtil.getSessionFactory().openSession();
		    Transaction tx = session.beginTransaction();

		    try {
		        session.persist(u);
		        tx.commit();
		        return true; // ✅ thành công
		    } catch (Exception e) {
		        tx.rollback();
		        e.printStackTrace();
		        return false; // ❌ thất bại
		    } finally {
		        session.close();
		    }
		}
	 public boolean updateUser(user u) {
		    Session session = HibernateUtil.getSessionFactory().openSession();
		    Transaction tx = session.beginTransaction();

		    try {
		        session.merge(u);
		        tx.commit();
		        return true;
		    } catch (Exception e) {
		        tx.rollback();
		        e.printStackTrace();
		        return false;
		    } finally {
		        session.close();
		    }
		}
	 public boolean deleteUser(int id) {
		    Session session = HibernateUtil.getSessionFactory().openSession();
		    Transaction tx = session.beginTransaction();

		    try {
		        user u = session.get(user.class, id);
		        if (u != null) {
		        	session.remove(u);
		        }
		        tx.commit();
		        return true;
		    } catch (Exception e) {
		        tx.rollback();
		        e.printStackTrace();
		        return false;
		    } finally {
		        session.close();
		    }
		}
	 public boolean toggleStatus(int id) {
		    Session session = HibernateUtil.getSessionFactory().openSession();
		    Transaction tx = session.beginTransaction();

		    try {
		        user u = session.get(user.class, id);
		        if (u != null) {
		            u.setIsStatus(u.getIsStatus() == 1 ? 0 : 1);
		            session.merge(u);
		        }
		        tx.commit();
		        return true;
		    } catch (Exception e) {
		        tx.rollback();
		        e.printStackTrace();
		        return false;
		    } finally {
		        session.close();
		    }
		}
}
