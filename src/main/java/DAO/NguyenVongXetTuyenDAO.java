package DAO;

import entity.NguyenVongXetTuyen;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.Collections;
import java.util.List;

public class NguyenVongXetTuyenDAO {

    // Lấy toàn bộ danh sách (dùng cho xét tuyển hàng loạt)
    public List<NguyenVongXetTuyen> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from NguyenVongXetTuyen order by nnCccd, nvThuTu",
                    NguyenVongXetTuyen.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Phân trang – dùng cho GUI (20 row/page)
    public List<NguyenVongXetTuyen> getByPage(int page, int pageSize) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from NguyenVongXetTuyen order by nnCccd, nvThuTu",
                    NguyenVongXetTuyen.class)
                    .setFirstResult((page - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Tổng số bản ghi (tính số trang)
    public long countAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "select count(n) from NguyenVongXetTuyen n", Long.class).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Tất cả nguyện vọng của 1 thí sinh
    public List<NguyenVongXetTuyen> getByCccd(String cccd) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from NguyenVongXetTuyen where nnCccd = :cccd order by nvThuTu",
                    NguyenVongXetTuyen.class)
                    .setParameter("cccd", cccd)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Danh sách theo ngành – sort theo điểm xét tuyển giảm dần (dùng khi chốt kết
    // quả)
    public List<NguyenVongXetTuyen> getByManganh(String manganh) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from NguyenVongXetTuyen where nvManganh = :ma order by diemXetTuyen desc",
                    NguyenVongXetTuyen.class)
                    .setParameter("ma", manganh)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Tìm theo ID
    public NguyenVongXetTuyen getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(NguyenVongXetTuyen.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Tìm theo nv_keys (unique key tổng hợp)
    public NguyenVongXetTuyen getByKeys(String nvKeys) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from NguyenVongXetTuyen where nvKeys = :k",
                    NguyenVongXetTuyen.class)
                    .setParameter("k", nvKeys)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Thêm mới
    public boolean save(NguyenVongXetTuyen nv) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(nv);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật
    public boolean update(NguyenVongXetTuyen nv) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(nv);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // Xóa theo ID
    public boolean delete(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            NguyenVongXetTuyen nv = session.get(NguyenVongXetTuyen.class, id);
            if (nv != null)
                session.remove(nv);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // Batch update kết quả xét tuyển (flush mỗi 50 bản ghi)
    public void updateKetQuaBatch(List<NguyenVongXetTuyen> list) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            int count = 0;
            for (NguyenVongXetTuyen nv : list) {
                session.merge(nv);
                if (++count % 50 == 0) {
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        }
    }
}