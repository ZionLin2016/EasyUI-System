package lsd.service;

import java.util.List;

import lsd.model.Tbugtype;

/**
 * BUG类型服务
 * 
 * @author LSD
 * 
 */
public interface BugTypeServiceI {

	/**
	 * 获得BUG类型列表
	 * 
	 * @return
	 */
	public List<Tbugtype> getBugTypeList();

}
