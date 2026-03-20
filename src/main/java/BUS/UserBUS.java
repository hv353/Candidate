package BUS;

import java.util.List;

import DAO.UserDAO;
import entity.Role;
import entity.user;

public class UserBUS {
	 private UserDAO userDAO = new UserDAO();

	    public user login(String username, String password) {

	        if(username == null || username.trim().isEmpty()){
	            System.out.println("Username không được rỗng");
	            return null;
	        }

	        if(password == null || password.trim().isEmpty()){
	            System.out.println("Password không được rỗng");
	            return null;
	        }

	        return userDAO.login(username, password);
	    }
	    public List<user> getAllUser(){
			List<user> listUser = userDAO.getAllUser();
			return listUser;
		}
	    public boolean insertUser(user u) {

	        if (u.getUsername() == null || u.getUsername().trim().isEmpty()) {
	            System.out.println("Username không hợp lệ");
	            return false;
	        }

	        if (u.getPassword() == null || u.getPassword().trim().isEmpty()) {
	            System.out.println("Password không hợp lệ");
	            return false;
	        }

	        if (u.getRole() == null) {
	            u.setRole(Role.USER); // default
	        }

	        return userDAO.insertUser(u);
	    }

	    // ===== UPDATE =====
	    public boolean updateUser(user u) {
	        if (u.getId() == null) return false;
	        return userDAO.updateUser(u);
	    }

	    // ===== DELETE =====
	    public boolean deleteUser(int id) {
	        if (id <= 0) return false;
	        return userDAO.deleteUser(id);
	    }

	    // ===== ENABLE / DISABLE =====
	    public boolean toggleStatus(int id) {
	        if (id <= 0) return false;
	        return userDAO.toggleStatus(id);
	    }

	    // ===== CHANGE ROLE =====
//	    public boolean updateRole(int id, String role) {
//	        try {
//	            Role r = Role.valueOf(role); // check hợp lệ
//	            return userDAO.updateRole(id, r);
//	        } catch (Exception e) {
//	            System.out.println("Role không hợp lệ");
//	            return false;
//	        }
//	    }

	    // ===== FIND BY ID =====
//	    public user findById(int id) {
//	        if (id <= 0) return null;
//	        return userDAO.findById(id);
//	    }
	    

}
