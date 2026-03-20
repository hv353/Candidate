package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import Excel.ExcelReader;
import entity.Nganh;
import util.HibernateUtil;

public class NganhDAO {
	public List<Nganh> getAllIndustry() {
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    List<Nganh> listIndustry = null;

	    try {
	        listIndustry = session
	                .createQuery("from Nganh", Nganh.class)
	                .getResultList();
	    } finally {
	        session.close();
	    }

	    return listIndustry;
	}
	public void updateIndustry(Nganh c) {
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;

	    try {
	        tx = session.beginTransaction();

	        session.merge(c); 

	        tx.commit();
	      

	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
	public void deleteIndustry(int id) {
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;

	    try {
	        tx = session.beginTransaction();

	        Nganh c = session.get(Nganh.class, id);

	        if (c != null) {
	            session.remove(c);
	            
	        }

	        tx.commit();

	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
	public static void importIndustry(String filePath) {

	    List<List<String>> data = ExcelReader.readExcel(filePath);

	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = session.beginTransaction();

	    try {

	        for (List<String> row : data) {

	            Nganh c = new Nganh();

	           

	            session.persist(c);
	        }

	        tx.commit();
	        System.out.println("Import thành công!");

	    } catch (Exception e) {

	        tx.rollback();
	        e.printStackTrace();

	    } finally {

	        session.close();

	    }
	}
}
