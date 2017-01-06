package lsd.service;

import java.util.List;

import lsd.pageModel.Resource;
import lsd.pageModel.SessionInfo;
import lsd.pageModel.Tree;

/**
 * 资源service
 * 
 * @author LSD
 * 
 */
public interface ResourceServiceI {
	/**
	 * 获得一个资源
	 * 
	 * @param id
	 * @return
	 */
	public Resource get(String id);

	/**
	 * 获得资源树(菜单类型) 通过用户ID判断，能看到的资源
	 * 
	 * @param sessionInfo
	 * @return
	 */
	public List<Tree> tree(SessionInfo sessionInfo);

	/**
	 * 获得资源树(所有资源类型) 通过用户ID判断,能看到资源
	 * 
	 * @param sessionInfo
	 * @return
	 */
	public List<Tree> allTree(SessionInfo sessionInfo);

	/**
	 * 获得资源列表
	 * 
	 * @param sessionInfo
	 * @return
	 */
	public List<Resource> treeGrid(SessionInfo sessionInfo);

	/**
	 * 添加资源
	 * 
	 * @param resource
	 * @param sessionInfo
	 */
	public void add(Resource resource, SessionInfo sessionInfo);

	/**
	 * 删除资源
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 修改资源
	 * 
	 * @param resource
	 */
	public void edit(Resource resource);
}
