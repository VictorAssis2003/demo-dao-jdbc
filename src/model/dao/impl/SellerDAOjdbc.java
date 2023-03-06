package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(""
					+ "INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, dept.getName());
			st.setString(2, dept.getEmail());
			st.setDate(3, new Date(dept.getBirthDate().getTime()));
			st.setDouble(4, dept.getBaseSalary());
			st.setInt(5, dept.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					dept.setId(id);
				}
				DB.closeResultSet(rs);

			}else {
				throw new DbException("Unexpected Error! No line affected");
			}			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}

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
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			
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
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name AS DeptName "
							+ "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id "
							+ "order by Name;");

			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDept(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	

	@Override
	public List<Seller> findByDepartment(Department dept) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name AS DeptName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "order by Name;");

			st.setInt(1, dept.getId());
			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDept(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	
	
	
}
