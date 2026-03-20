package DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import Excel.ExcelReader;
import entity.Candidate;
import util.HibernateUtil;

public class CandidateDAO {
	public List<Candidate> getAllCandidate() {
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    List<Candidate> listCandidate = null;

	    try {
	        listCandidate = session
	                .createQuery("from Candidate", Candidate.class)
	                .getResultList();
	    } finally {
	        session.close();
	    }

	    return listCandidate;
	}
	public static void importCandidate(String filePath) {

	    List<List<String>> data = ExcelReader.readExcel(filePath);

	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = session.beginTransaction();

	    try {

	        for (List<String> row : data) {

	            Candidate c = new Candidate();

	            c.setCccd(row.get(1));
	            c.setHo(row.get(2));
	            c.setTen(row.get(2));
	            c.setNgaySinh(row.get(3));
	            c.setGioiTinh(row.get(4));
	            c.setNoiSinh(row.get(35));

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
		public List<Candidate> findCandidate(String keyword){
			Session session = HibernateUtil.getSessionFactory().openSession();
		    Transaction tx = null;
		    List<Candidate> list = new ArrayList<>();

		    try {
		        tx = session.beginTransaction();
		        //tìm theo gần đúng
		        String hql = "from Candidate c where c.ten like :kw or c.cccd like :kw";
		        Query<Candidate> query = session.createQuery(hql, Candidate.class);
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
		public void updateCandidate(Candidate c) {
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
		public void deleteCandidate(int id) {
		    Session session = HibernateUtil.getSessionFactory().openSession();
		    Transaction tx = null;

		    try {
		        tx = session.beginTransaction();

		        Candidate c = session.get(Candidate.class, id);

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

}
