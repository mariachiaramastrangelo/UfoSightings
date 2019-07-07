package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.cj.exceptions.RSAException;

import it.polito.tdp.ufo.model.AnniAvvistamenti;
import it.polito.tdp.ufo.model.Sighting;
import it.polito.tdp.ufo.model.StatiAvvistamenti;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Sighting(res.getInt("id"),
							res.getTimestamp("datetime").toLocalDateTime(),
							res.getString("city"), 
							res.getString("state"), 
							res.getString("country"),
							res.getString("shape"),
							res.getInt("duration"),
							res.getString("duration_hm"),
							res.getString("comments"),
							res.getDate("date_posted").toLocalDate(),
							res.getDouble("latitude"), 
							res.getDouble("longitude"))) ;
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<AnniAvvistamenti> anniAvvistamenti() {
		String sql="SELECT YEAR(datetime) as anno , COUNT(*) as avvistamenti " + 
				"From sighting " + 
				"WHERE country='us' " + 
				"GROUP BY(YEAR(datetime)) " + 
				"ORDER BY anno ";
		List<AnniAvvistamenti> result= new ArrayList<>();
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			while(rs.next()) {
				result.add(new AnniAvvistamenti(rs.getInt("anno"), rs.getInt("avvistamenti")));
			}
			conn.close();
			return result;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<StatiAvvistamenti> statiAvvistamenti(int anno){
		String sql="SELECT DISTINCT(state) as stato " + 
				"FROM sighting  " + 
				"WHERE YEAR(datetime)=? && country='us' ";
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs= st.executeQuery();
			List<StatiAvvistamenti> result= new ArrayList<>();
			while (rs.next()) {
				StatiAvvistamenti sa= new StatiAvvistamenti(rs.getString("stato"));
				this.aggiungiAvvistamenti(sa, anno);
				result.add(sa);
				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void aggiungiAvvistamenti(StatiAvvistamenti statoAvvistamento, int anno) {
		String sql="SELECT DATE(datetime) as data, TIME(datetime) as tempo " + 
				"FROM sighting " + 
				"WHERE YEAR(datetime)=? && country='us' && state=? ";
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setString(2, statoAvvistamento.getStato());
			ResultSet rs= st.executeQuery();
			while (rs.next()) {
				LocalDateTime data= LocalDateTime.of(rs.getDate("data").toLocalDate(), rs.getTime("tempo").toLocalTime());
				
				statoAvvistamento.add(data);
			}
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
