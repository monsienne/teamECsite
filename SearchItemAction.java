package com.internousdev.orion.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.orion.dao.MCategoryDAO;
import com.internousdev.orion.dao.ProductInfoDAO;
import com.internousdev.orion.dto.MCategoryDTO;
import com.internousdev.orion.dto.ProductInfoDTO;
import com.internousdev.orion.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

public class SearchItemAction extends ActionSupport implements SessionAware{
	private String categoryId;
	private String keywords;
	private List<String> keywordsErrorMessageList;
	private List<ProductInfoDTO> productInfoDTOList;
	private Map<String, Object> session;

	public String execute() {
		// カテゴリーの選択肢が存在しない場合は、すべてのカテゴリーを設定する
		if (categoryId == null) {
			categoryId = "1";
		}

		InputChecker inputChecker = new InputChecker();

		String tempKeywords = null;

		// 空欄チェック（空欄、スペース、Null→ true）
		if (StringUtils.isBlank(keywords)){
			tempKeywords = "";
		}else{

			/* .replaceAll（"全角スペース"を,"半角スペース")に置換、
			 * .replaceAll（"半角スペース２つ以上あったら","半角スペース")に置換
			 * \s→半角スペース、{2,}→2つ以上
			 */
			tempKeywords = keywords.replaceAll("　", " ").replaceAll("\\s{2,}", " ");
		}

		// 入力チェック用の変数(inputChecker)に値を入れる
		if(!(tempKeywords.equals(""))){

			keywordsErrorMessageList = inputChecker.doCheck("検索ワード", keywords,0,50, true, true, true, true, false, true, true);

			//入力にエラーがあればSUCCESSを返し、商品一覧画面にエラーメッセージが表示される
		if (keywordsErrorMessageList.size() > 0) {
			return SUCCESS;
		}
		}

		ProductInfoDAO productInfoDAO = new ProductInfoDAO();
		switch (categoryId) {
			// 検索ワードだけで検索→半角スペースがあれば文字列を分割する
		case "1":
			productInfoDTOList = productInfoDAO.getProductInfoListByKeyword(tempKeywords.split(" "));
			break;
			//カテゴリを選択かつ検索ワードで検索→半角スペースがあれば文字列を分割する
		default:
			productInfoDTOList = productInfoDAO.getProductInfoListByCategoryIdAndKeyword(tempKeywords.split(" "), categoryId);
			break;
		}
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
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public List<String> getKeywordsErrorMessageList() {
		return keywordsErrorMessageList;
	}
	public void setKeywordsErrorMessageList(List<String> keywordsErrorMessageList) {
		this.keywordsErrorMessageList = keywordsErrorMessageList;
	}
	public List<ProductInfoDTO> getProductInfoDTOList() {
		return productInfoDTOList;
	}
	public void setProductInfoDTOList(List<ProductInfoDTO> productInfoDTOList) {
		this.productInfoDTOList = productInfoDTOList;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}