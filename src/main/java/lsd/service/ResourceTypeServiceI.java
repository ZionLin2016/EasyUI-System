package lsd.service;

import java.util.List;

import lsd.pageModel.ResourceType;

/**
 * 资源类型service
 * 
 * @author LSD
 * 
 */
public interface ResourceTypeServiceI {

	/**
	 * 获取资源类型
	 * 
	 * @return
	 */
	public List<ResourceType> getResourceTypeList();

}
