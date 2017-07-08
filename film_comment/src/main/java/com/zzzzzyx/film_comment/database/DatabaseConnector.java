package com.zzzzzyx.film_comment.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import com.zzzzzyx.film_comment.model.Frequency;


public class DatabaseConnector{

	private static Util db_public = new Util();
	DataSource ds;

	public static List<Frequency> getFrequencyByFilmName(String filmName) {
		ArrayList<Frequency> commentList = new ArrayList<Frequency>();
		ResultSet rs = null;
		try {
			rs = db_public.executeSql("select * from frequency where film=\""+ filmName +"\"");
			while(rs.next()){
				String frequency = rs.getString("frequency");
				String word = rs.getString("word");
				commentList.add(new Frequency(frequency,word));	
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return commentList;
	}
	public static void saveFrequency(HashMap<String, Integer> map, String filmName){
		Set<Entry<String, Integer>> set = map.entrySet();
		
		String sql = "INSERT frequency(word,frequency,film) VALUES(?,?,?)";
		Util db = new Util_Batch();
		Connection conn = db.getConnection();
		PreparedStatement ps;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			for(Entry<String, Integer> entry : set){
				ps.setString(1, entry.getKey());   
				ps.setInt(2, entry.getValue());   
				ps.setString(3, filmName);
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static List<String> getCommentByFilmName(String filmName) {
		ArrayList<String> commentList = new ArrayList<String>();
		ResultSet rs = null;
		try {
			rs = db_public.executeSql("select * from comment where film=\""+ filmName +"\"");
			while(rs.next()){
				String comment = rs.getString("comment");
				commentList.add(comment);	
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return commentList;
	}
	public static void saveComment(List<String> map, String filmName){		
		String sql = "INSERT comment(comment,film) VALUES(?,?)";
		Util db = new Util_Batch();
		Connection conn = db.getConnection();
		PreparedStatement ps;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			for(String entry : map){
				ps.setString(1, entry);    
				ps.setString(2, filmName);
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}

