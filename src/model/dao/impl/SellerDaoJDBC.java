package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	private Connection conn;
	
	public  SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	
	//Método para inserir Seller pelo ID;
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	
	//Método para atualizar Seller pelo ID;
	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	//Método para deletar Seller pelo ID;
	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	//Método para buscar Seller pelo ID;
	@Override
	public Seller findById(Integer id) {
		
	//Inicializando as variáveis de acesso e resultado do banco;
	PreparedStatement st = null;
	ResultSet rs = null;
	
	//Tratamento de excessões;
	try {
		//Variável de acesso ao dado recebendo o valor da query que será executada no BD;
		st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
				+ "FROM seller INNER JOIN department on seller.DepartmentId = department.Id "
				+ "WHERE seller.Id = ?");
		
		//Aqui estou passando o paramêtro que será utilizado na query do BD e executando a query com a var rs;
		st.setInt(1, id);
		rs = st.executeQuery();
		
		//If usado para colocar a rs na próxima posição caso não tenha nada na posição atual;
		//Se as próximas posições também não estiverem nenhum valor, é usado o return nulo que está mais abaixo;
		if(rs.next()) {
		
			//Estou usando um método onde os valores capturados no rs, já estão sendo setados nos seus devidos atributos; 
			Department dep = instanciateDepartment(rs);
			
			Seller seller = instanciateSeller(rs, dep);
						return seller;

		}
	//Usado para retornar nulo caso não tenha nenhum valor no resultSet;
		return null;
	
	//Tratamento de excessões;
	}catch (SQLException e) {
		throw new DbException(e.getMessage());
		
	}finally {
	DB.closeStatement(st);
	DB.closeResultSet(rs);
	}
		
		
	}

	//Método usado para setar os valores do rs nos seus devidos atributos de cada entidade;
	private Seller instanciateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();
			seller.setId(rs.getInt("Id"));
			seller.setName(rs.getString("Name"));
			seller.setEmail(rs.getString("Email"));
			seller.setBaseSalary(rs.getDouble("BaseSalary"));
			seller.setBirthDate(rs.getDate("BirthDate"));
			seller.setDepartment(dep);
		return seller;
	}

	private Department instanciateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		
		return dep;
	}

	//Método para buscar uma lista Seller;
	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		//Tratamento de excessões;
		try {
			//Variável de acesso ao dado recebendo o valor da query que será executada no BD;
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name;");
			
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			//If usado para colocar a rs na próxima posição caso não tenha nada na posição atual;
			//Se as próximas posições também não estiverem nenhum valor, é usado o return nulo que está mais abaixo;
			while(rs.next()) {
			
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instanciateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				
				Seller seller = instanciateSeller(rs, dep);
				list.add(seller);		
				
				

			}
		//Usado para retornar nulo caso não tenha nenhum valor no resultSet;
			return list;
		
		//Tratamento de excessões;
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
			
		}finally {
		DB.closeStatement(st);
		DB.closeResultSet(rs);
		}
	}


	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		//Tratamento de excessões;
		try {
			//Variável de acesso ao dado recebendo o valor da query que será executada no BD;
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name;");
			
			//Aqui estou passando o paramêtro que será utilizado na query do BD e executando a query com a var rs;
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			//If usado para colocar a rs na próxima posição caso não tenha nada na posição atual;
			//Se as próximas posições também não estiverem nenhum valor, é usado o return nulo que está mais abaixo;
			while(rs.next()) {
			
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instanciateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				
				Seller seller = instanciateSeller(rs, dep);
				list.add(seller);		
				
				

			}
		//Usado para retornar nulo caso não tenha nenhum valor no resultSet;
			return list;
		
		//Tratamento de excessões;
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
			
		}finally {
		DB.closeStatement(st);
		DB.closeResultSet(rs);
		}
	}

}
