package application;

import java.util.Date;
import java.util.List;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	
	public static void main(String[] args) {
		
		


		SellerDAO sellerDao = DAOFactory.createSellerDAO();
		System.out.println("======TEST 1 : seller findById ======");
		Seller seller = sellerDao.findById(2);
		System.out.println(seller);

		
		System.out.println();
		System.out.println("======TEST 2 : seller findByDepartment ======");
		Department dep = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(dep);
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		
		System.out.println();
		System.out.println("======TEST 3 : seller findByAll ======");
		List<Seller> list2 = sellerDao.findAll();
		for (Seller obj : list2) {
			System.out.println(obj);
		}
		
		
		System.out.println();
		System.out.println("======TEST 3 : seller findByAll ======");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, dep);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New Id: " + newSeller.getId());

	}

}
