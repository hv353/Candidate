package BUS;

import java.util.List;

import DAO.DiemCongXetTuyenDAO;
import entity.DiemCongXetTuyen;

public class DiemCongXetTuyenBUS {
	private DiemCongXetTuyenDAO bangquydoiDAO = new DiemCongXetTuyenDAO();
	public List<DiemCongXetTuyen> getAllBonusPoint(){
		List<DiemCongXetTuyen> listBonusPoint = bangquydoiDAO.getAllBonusPoint();
		return listBonusPoint;
		
	}
	public void updateBonusPoint(DiemCongXetTuyen c){
		bangquydoiDAO.updateBonusPoint(c);
	}
	public void deleteBonusPoint(int id){
		bangquydoiDAO.deleteBonusPoint(id);
	}
	

}
