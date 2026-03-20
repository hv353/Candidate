package importer;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Excel.ExcelReader;
import entity.Candidate;
import util.HibernateUtil;

public class CandidateImporter {

    public static void importCandidate(String filePath){

        List<List<String>> data = ExcelReader.readExcel(filePath);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        for(List<String> row : data){

            Candidate c = new Candidate();

            c.setCccd(row.get(1));
            c.setHo(row.get(2));
            c.setTen(row.get(2));
            c.setNgaySinh(row.get(3));
            c.setGioiTinh(row.get(4));
            c.setNoiSinh(row.get(35));

            session.persist(c);

            System.out.println("Inserted: " + c.getHo() + " " + c.getTen());
        }

        tx.commit();
        session.close();

        System.out.println("Import thành công!");
    }
}
