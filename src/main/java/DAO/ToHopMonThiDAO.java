package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import Excel.ExcelReader;
import entity.ToHopMonThi;
import util.HibernateUtil;

public class ToHopMonThiDAO {
	public List<ToHopMonThi> getAllSubjectGroups() {
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    List<ToHopMonThi> listSubjectGroups = null;

	    try {
	        listSubjectGroups = session
	                .createQuery("from ToHopMonThi", ToHopMonThi.class)
	                .getResultList();
	    } finally {
	        session.close();
	    }

	    return listSubjectGroups;
	}
	public void updateSubjectGroups(ToHopMonThi c) {
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
	public void deleteSubjectGroups(int id) {
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;

	    try {
	        tx = session.beginTransaction();

	        ToHopMonThi c = session.get(ToHopMonThi.class, id);

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
	public static void importSubjectGroups(String filePath) {

	    List<List<String>> data = ExcelReader.readExcel(filePath);

	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = session.beginTransaction();

	    try {

	        for (List<String> row : data) {

	        	ToHopMonThi c = new ToHopMonThi();

	           

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
