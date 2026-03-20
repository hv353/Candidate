package BUS;

import java.util.List;

import entity.Candidate;
import DAO.CandidateDAO;
public class CandidateBUS {
	private CandidateDAO candidateDao = new CandidateDAO();
	public List<Candidate> getAllCandidate(){
		List<Candidate> listCandidate = candidateDao.getAllCandidate();
		return listCandidate;
		
	}
	public void updateCandidate(Candidate c){
	    candidateDao.updateCandidate(c);
	}
	public void deleteCandidate(int id){
	    candidateDao.deleteCandidate(id);
	}
	public List<Candidate> findCandidate(String keyword){
	    return candidateDao.findCandidate(keyword);
	}

}
