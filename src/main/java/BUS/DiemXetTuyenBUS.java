package BUS;

import java.util.List;

import DAO.DiemXetTuyenDAO;
import entity.DiemThiXetTuyen;

public class DiemXetTuyenBUS {
	private DiemXetTuyenDAO bangquydoiDAO = new DiemXetTuyenDAO();
	public List<DiemThiXetTuyen> getAllAdmissionScore(){
		List<DiemThiXetTuyen> listScoreConversion = bangquydoiDAO.getAllAdmissionScore();
		return listScoreConversion;
		
	}
	public void updateAdmissionScore(DiemThiXetTuyen c){
		bangquydoiDAO.updateAdmissionScore(c);
	}
	public void deleteAdmissionScore(int id){
		bangquydoiDAO.deleteAdmissionScore(id);
	}

}
