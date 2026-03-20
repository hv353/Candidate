package BUS;

import java.util.List;

import DAO.BangQuyDoiDAO;
import entity.BangQuyDoi;


public class BangQuyDoiBUS {
	private BangQuyDoiDAO bangquydoiDAO = new BangQuyDoiDAO();
	public List<BangQuyDoi> getAllScoreConversion(){
		List<BangQuyDoi> listScoreConversion = bangquydoiDAO.getAllScoreConversion();
		return listScoreConversion;
		
	}
	public void updateScoreConversion(BangQuyDoi c){
		bangquydoiDAO.updateScoreConversion(c);
	}
	public void deleteScoreConversion(int id){
		bangquydoiDAO.deleteScoreConversion(id);
	}
	

}
