package com.internousdev.orion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.orion.dto.MCategoryDTO;
import com.internousdev.orion.util.DBConnector;

public class MCategoryDAO {

	//商品カテゴリー
	public List<MCategoryDTO> getMCategoryList() {
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		List<MCategoryDTO> mCategoryDTOList = new ArrayList<MCategoryDTO>();
		String sql = "SELECT * FROM m_category";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				MCategoryDTO mCategorydto = new MCategoryDTO();
				mCategorydto.setId(resultSet.getInt("id"));
				mCategorydto.setCategoryId(resultSet.getInt("category_id"));
				mCategorydto.setCategoryName(resultSet.getString("category_name"));
				mCategorydto.setCategoryDescription(resultSet.getString("category_description"));
				mCategoryDTOList.add(mCategorydto);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
			connection.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		return mCategoryDTOList;
	}
}