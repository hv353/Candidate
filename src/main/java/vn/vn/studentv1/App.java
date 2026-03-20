package vn.vn.studentv1;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import BUS.CandidateBUS;
import BUS.UserBUS;
import GUI.QLThiSinhGUI;
import GUI.QLUserGUI;
import entity.Candidate;
import entity.Role;
import entity.user;
import importer.CandidateImporter;
import util.HibernateUtil;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
    	//đổ dữ liệu vào bảng QLuser
    	 new QLUserGUI().setVisible(true);
    	
    }  	
}
