package DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import Excel.ExcelReader;
import entity.BangQuyDoi;
import util.HibernateUtil;

public class BangQuyDoiDAO {
	public List<BangQuyDoi> getAllScoreConversion() {
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    List<BangQuyDoi> listScoreConversion = null;

	    try {
	        listScoreConversion = session
	                .createQuery("from BangQuyDoi", BangQuyDoi.class)
	                .getResultList();
	    } finally {
	        session.close();
	    }

	    return listScoreConversion;
	}
	public void updateScoreConversion(BangQuyDoi c) {
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
	public void deleteScoreConversion(int id) {
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;

	    try {
	        tx = session.beginTransaction();

	        BangQuyDoi c = session.get(BangQuyDoi.class, id);

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
	public static void importScoreConversion(String filePath) {

	    List<List<String>> data = ExcelReader.readExcel(filePath);

	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = session.beginTransaction();

	    try {

	        for (List<String> row : data) {

	        	BangQuyDoi c = new BangQuyDoi();

	           

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
	public List<BangQuyDoi> findCandidate(String keyword){
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    List<BangQuyDoi> list = new ArrayList<>();

	    try {
	        tx = session.beginTransaction();
	        //tìm theo gần đúng maquydoi
	        String hql = "from BangQuyDoi c where c.dMaQuyDoi like :kw ";
	        Query<BangQuyDoi> query = session.createQuery(hql, BangQuyDoi.class);
	        query.setParameter("kw", "%" + keyword + "%");

	        list = query.getResultList();

	        tx.commit();
	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
	    }

	    session.close();
	    return list;
	}

}
