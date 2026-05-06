package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import Excel.ExcelReader;
import entity.DiemThiXetTuyen;
import util.HibernateUtil;

public class DiemXetTuyenDAO {
	public List<DiemThiXetTuyen> getAllAdmissionScore() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<DiemThiXetTuyen> listIndustry = null;

		try {
			listIndustry = session
					.createQuery("from DiemThiXetTuyen", DiemThiXetTuyen.class)
					.getResultList();
		} finally {
			session.close();
		}

		return listIndustry;
	}

	public void updateAdmissionScore(DiemThiXetTuyen c) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			session.merge(c);

			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteAdmissionScore(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			DiemThiXetTuyen c = session.get(DiemThiXetTuyen.class, id);

			if (c != null) {
				session.remove(c);

			}

			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static void importAdmissionScore(String filePath) {

		List<List<String>> data = ExcelReader.readExcel(filePath);

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		try {

			for (List<String> row : data) {

				DiemThiXetTuyen c = new DiemThiXetTuyen();

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

	// Tìm điểm thi theo CCCD + phương thức – dùng khi tính điểm nguyện vọng
	public DiemThiXetTuyen getByCccdAndPhuongThuc(String cccd, String phuongThuc) {
		org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			return session.createQuery(
					"from DiemThiXetTuyen where cccd = :cccd and phuongthuc = :pt",
					DiemThiXetTuyen.class)
					.setParameter("cccd", cccd)
					.setParameter("pt", phuongThuc)
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
}