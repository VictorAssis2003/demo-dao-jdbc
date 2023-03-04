package application;

import java.util.Date;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	
	public static void main(String[] args) {
		
		Department dp = new Department();
		dp.setId(1);
		dp.setName("Contabilidade");



		SellerDAO sellerDao = DAOFactory.createSellerDAO();
		
		Seller seller = sellerDao.findById(3);
		
		System.out.println(seller);
		
		
		 
	}

}
