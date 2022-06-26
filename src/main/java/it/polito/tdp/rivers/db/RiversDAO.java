package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public List<River> getAllRivers(Map<Integer,River> idMap) {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				River r=new River(res.getInt("id"), res.getString("name"));
				rivers.add(r);
				idMap.put(r.getId(),r );
			}
			
			conn.close();
			return rivers;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
	
public List<Flow> getFlow(Map<Integer,River> idMap) {
		
		final String sql = "Select * "
				+ "From flow f ";

		List<Flow> result = new LinkedList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Date d=res.getDate("day");
				result.add(new Flow(d.toLocalDate(),res.getDouble("flow"),idMap.get(res.getInt("river"))));
			}

			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}

public List<Flow> getFlowRiver(River r) {
	
	final String sql = "Select * "
			+ "From flow f "
			+ "where river=? ";

	List<Flow> result = new LinkedList<Flow>();

	try {
		Connection conn = DBConnect.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, r.getId());
		ResultSet res = st.executeQuery();

		while (res.next()) {
			Date d=res.getDate("day");
			result.add(new Flow(d.toLocalDate(),res.getDouble("flow"),r));
		}

		conn.close();
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		throw new RuntimeException("SQL Error");
	}
}
}
