package BUS;

import java.util.List;

import DAO.ToHopMonThiDAO;
import entity.ToHopMonThi;

public class ToHopMonThiBUS {
	private ToHopMonThiDAO bangquydoiDAO = new ToHopMonThiDAO();
	public List<ToHopMonThi> getAllScoreConversion(){
		List<ToHopMonThi> listScoreConversion = bangquydoiDAO.getAllSubjectGroups();
		return listScoreConversion;
		
	}
	public void updateScoreConversion(ToHopMonThi c){
		bangquydoiDAO.updateSubjectGroups(c);
	}
	public void deleteScoreConversion(int id){
		bangquydoiDAO.deleteSubjectGroups(id);
	}


}
