package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers() {

		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}

	public LocalDate getPrimaData(River fiume) {

		final String sql = "SELECT day FROM flow WHERE river=? ORDER BY DAY LIMIT 1";
		LocalDate data = null;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fiume.getId());
			ResultSet res = st.executeQuery();

			if (res.next()) {
				data = res.getDate(1).toLocalDate();
			}

			conn.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return data;
	}

	public LocalDate getUltimaData(River fiume) {

		final String sql = "SELECT day FROM flow WHERE river=? ORDER BY DAY DESC LIMIT 1";
		LocalDate data = null;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fiume.getId());
			ResultSet res = st.executeQuery();

			if (res.next()) {
				data = res.getDate(1).toLocalDate();
			}

			conn.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return data;
	}

	public int getNumeroMisurazioni(River fiume) {

		final String sql = "SELECT COUNT(*) FROM flow WHERE river=?";
		int misurazioni = -1;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fiume.getId());
			ResultSet res = st.executeQuery();

			if (res.next()) {
				misurazioni = res.getInt(1);
			}

			conn.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return misurazioni;
	}

	public double getMedia(River fiume) {

		final String sql = "SELECT AVG(flow) FROM flow WHERE river=?";
		double media = -1.0;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, fiume.getId());
			ResultSet res = st.executeQuery();

			if (res.next()) {
				media = res.getDouble(1);
			}

			conn.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return media;
	}

	public List<Flow> getFlows(River fiume) {

		final String sql = "SELECT DAY, flow FROM flow WHERE river=? ORDER BY day";

		List<Flow> flows = new LinkedList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fiume.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Flow f = new Flow(res.getDate(1).toLocalDate(), res.getDouble(2), fiume);
				flows.add(f);
			}

			conn.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return flows;
	}

}
