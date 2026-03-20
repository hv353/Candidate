package BUS;

import java.util.List;

import DAO.NganhDAO;
import entity.Nganh;

public class NganhBUS {
	private NganhDAO bangquydoiDAO = new NganhDAO();
	public List<Nganh> getAllIndustry(){
		List<Nganh> listScoreConversion = bangquydoiDAO.getAllIndustry();
		return listScoreConversion;
		
	}
	public void updateIndustry(Nganh c){
		bangquydoiDAO.updateIndustry(c);
	}
	public void deleteIndustry(int id){
		bangquydoiDAO.deleteIndustry(id);
	}
	

}
