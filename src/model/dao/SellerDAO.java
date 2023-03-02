package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDAO {

	void insert(Seller dept);
	void update(Seller dept);
	void deleteById(Seller dept);
	Seller findById(Integer Id);
	List<Seller> findAll();
	
}
