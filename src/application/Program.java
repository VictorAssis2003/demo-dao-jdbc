package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Program {
	
	public static void main(String[] args) {
		
		Department dp = new Department();
		dp.setId(1);
		dp.setName("Contabilidade");

		Seller seller = new Seller(21, "Victor", "victor@gmail", new Date(), 2000.00, dp);

		System.out.println(seller);

	}

}
