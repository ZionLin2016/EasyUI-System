package lsd.service;

import java.util.List;

import lsd.pageModel.Role;
import lsd.pageModel.SessionInfo;
import lsd.pageModel.Tree;

/**
 * 角色业务逻辑
 * 
 * @author LSD
 * 
 */
public interface RoleServiceI {

	/**
	 * 为角色授权
	 * 
	 * @param role
	 */
	public void grant(Role role);

	/**
	 * 获得角色
	 * 
	 * @param id
	 * @return
	 */
	public Role get(String id);

	/**
	 * 删除角色
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 添加角色
	 * 
	 * @param role
	 * @param sessionInfo
	 */
	public void add(Role role, SessionInfo sessionInfo);

	/**
	 * 编辑角色
	 * 
	 * @param role
	 */
	public void edit(Role role);

	/**
	 * 获得角色treeGrid
	 * 
	 * @param sessionInfo
	 * @return
	 */
	public List<Role> treeGrid(SessionInfo sessionInfo);

	/**
	 * 获得本角色拥有的角色树
	 * 
	 * @param sessionInfo
	 * @return
	 */
	public List<Tree> tree(SessionInfo sessionInfo);

	/**
	 * 获得角色树
	 * 
	 * @return
	 */
	public List<Tree> allTree();
}
