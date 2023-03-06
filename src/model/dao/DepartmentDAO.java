package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface DepartmentDAO {
	
	
	void insert(Department dept);
	void update(Department dept);
	void deleteById(Department dept);
	Department findById(Integer Id);
	List<Department> findAll();
	
		
	

}
