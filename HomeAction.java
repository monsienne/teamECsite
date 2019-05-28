package com.internousdev.orion.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.orion.dao.MCategoryDAO;
import com.internousdev.orion.dto.MCategoryDTO;
import com.internousdev.orion.util.CommonUtility;
import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport implements SessionAware{
	public Map<String, Object> session;
	public String execute() {
		// tempId(仮ユーザーID)がなければ仮ユーザーID作成
		if(!(session.containsKey("tempId"))){
			CommonUtility commonUtility = new CommonUtility();
			session.put("tempId", commonUtility.getRamdomValue());
		}
		// ログインしてなければ0を入れる
		if(!session.containsKey("loggedIn")){
			session.put("loggedIn", 0);
		}
		// カテゴリリストがなければカテゴリリストを入れる
		if(!session.containsKey("mCategoryDTOList")){
			List<MCategoryDTO> mCategoryDTOList = new ArrayList<MCategoryDTO>();
			MCategoryDAO mCategoryDAO = new MCategoryDAO();
			try{
				mCategoryDTOList = mCategoryDAO.getMCategoryList();
				}catch(NullPointerException e){
					mCategoryDTOList = null;
				}
				session.put("mCategoryDTOList", mCategoryDTOList);
			}
		return SUCCESS;
		}
	public Map<String, Object> getSession(){
		return session;
	}
	public void setSession(Map<String, Object> session){
		this.session = session;
	}
}