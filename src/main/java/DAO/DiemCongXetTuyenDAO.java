package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import Excel.ExcelReader;
import entity.DiemCongXetTuyen;
import util.HibernateUtil;

public class DiemCongXetTuyenDAO {
	public List<DiemCongXetTuyen> getAllBonusPoint() {
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    List<DiemCongXetTuyen> listBonusPoint = null;

	    try {
	        listBonusPoint = session
	                .createQuery("from DiemCongXetTuyen", DiemCongXetTuyen.class)
	                .getResultList();
	    } finally {
	        session.close();
	    }

	    return listBonusPoint;
	}
	public void updateBonusPoint(DiemCongXetTuyen c) {
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
	public void deleteBonusPoint(int id) {
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;

	    try {
	        tx = session.beginTransaction();

	        DiemCongXetTuyen c = session.get(DiemCongXetTuyen.class, id);

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
	public static void importBonusPoint(String filePath) {

	    List<List<String>> data = ExcelReader.readExcel(filePath);

	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = session.beginTransaction();

	    try {

	        for (List<String> row : data) {

	        	DiemCongXetTuyen c = new DiemCongXetTuyen();

	           

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
