package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDAOjdbc implements SellerDAO {

	private Connection conn;

	public SellerDAOjdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller dept) {

	}

	@Override
	public void update(Seller dept) {

	}

	@Override
	public void deleteById(Seller dept) {

	}

	@Override
	public Seller findById(Integer Id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select seller.*,department.Name as DeptName " + "from seller inner join department "
							+ "on seller.DepartmentId = department.Id " + "where seller.Id = ?;");

			st.setInt(1, Id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dept = instantiateDept(rs);
				Seller obj = instantiateSeller(rs, dept);
				return obj;

			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeConnection();
			DB.closeStatement(st);
		}

	}

	private Seller instantiateSeller(ResultSet rs, Department dept) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dept);
		return obj;
	}

	private Department instantiateDept(ResultSet rs) throws SQLException {
		Department dept = new Department();
		dept.setId(rs.getInt("DepartmentId"));
		dept.setName(rs.getString("DeptName"));
		return dept;
	}

	@Override
	public List<Seller> findAll() {
		return null;
	}

}
