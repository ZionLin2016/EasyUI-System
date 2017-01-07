package lsd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lsd.dao.ResourceDaoI;
import lsd.dao.ResourceTypeDaoI;
import lsd.dao.UserDaoI;
import lsd.model.Tresource;
import lsd.model.Trole;
import lsd.model.Tuser;
import lsd.pageModel.Resource;
import lsd.pageModel.SessionInfo;
import lsd.pageModel.Tree;
import lsd.service.ResourceServiceI;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class ResourceServiceImpl implements ResourceServiceI {

	public static Logger log = Logger.getLogger(ResourceServiceImpl.class);

	@Autowired
	private ResourceDaoI resourceDao;

	@Autowired
	private ResourceTypeDaoI resourceTypeDao;

	@Autowired
	private UserDaoI userDao;

	@Override
	public Resource get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tresource t = resourceDao.get(
				"from Tresource t left join fetch t.tresource resource left join fetch t.tresourcetype resourceType where t.id = :id", params);
		Resource r = new Resource();
		BeanUtils.copyProperties(t, r);
		if (t.getTresource() != null) {
			r.setPid(t.getTresource().getId());
			r.setPname(t.getTresource().getName());
		}
		r.setTypeId(t.getTresourcetype().getId());
		r.setTypeName(t.getTresourcetype().getName());
		if (t.getIcon() != null && !t.getIcon().equalsIgnoreCase("")) {
			r.setIconCls(t.getIcon());
		}
		return r;
	}

	@Override
	public List<Tree> tree(SessionInfo sessionInfo) {
		List<Tresource> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("resourceTypeId", "0");// 菜单类型的资源

		if (sessionInfo != null) {
			params.put("userId", sessionInfo.getId());// 自查自己有权限的资源
			l = resourceDao
					.find("select distinct t from Tresource t join fetch t.tresourcetype type join fetch t.troles role join role.tusers user where type.id = :resourceTypeId and user.id = :userId order by t.seq",
							params);
		} else {
			l = resourceDao.find("select distinct t from Tresource t join fetch t.tresourcetype type where type.id = :resourceTypeId order by t.seq",
					params);
		}

		if (l != null && l.size() > 0) {
			for (Tresource r : l) {
				Tree tree = new Tree();
				BeanUtils.copyProperties(r, tree);
				if (r.getTresource() != null) {
					tree.setPid(r.getTresource().getId());
				}
				tree.setText(r.getName());
				tree.setIconCls(r.getIcon());
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", r.getUrl()); // 设置url链接
				tree.setAttributes(attr);
				lt.add(tree);
			}
		}

		log.info("debug级别的日志输出----" + JSON.toJSONString(lt));
		return lt;
	}

	@Override
	public List<Tree> allTree(SessionInfo sessionInfo) {
		List<Tresource> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		Map<String, Object> params = new HashMap<String, Object>();
		if (sessionInfo != null) {
			params.put("userId", sessionInfo.getId());
			l = resourceDao
					.find("select distinct t from Tresource t join fetch t.tresourcetype type join fetch t.troles role join role.tusers user where user.id = :userId order by t.seq",
							params);
		} else {
			l = resourceDao.find("select distinct t from Tresource t join fetch t.tresourcetype type order by t.seq", params);
		}

		if (l != null && l.size() > 0) {
			for (Tresource r : l) {
				Tree tree = new Tree();
				BeanUtils.copyProperties(r, tree);
				if (r.getTresource() != null) {
					tree.setPid(r.getTresource().getId());
				}
				tree.setText(r.getName());
				tree.setIconCls(r.getIcon());
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", r.getUrl());
				tree.setAttributes(attr);
				lt.add(tree);
			}
		}
		return lt;
	}

	@Override
	public List<Resource> treeGrid(SessionInfo sessionInfo) {
		List<Tresource> l = null;
		List<Resource> lr = new ArrayList<Resource>();

		Map<String, Object> params = new HashMap<String, Object>();
		if (sessionInfo != null) {
			params.put("userId", sessionInfo.getId());
			l = resourceDao
					.find("select distinct t from Tresource t join fetch t.tresourcetype type join fetch t.troles role join role.tusers user where user.id = :userId order by t.seq",
							params);
		} else {
			l = resourceDao.find("select distinct t from Tresource t join fetch t.tresourcetype type order by t.seq", params);
		}

		if (l != null && l.size() > 0) {
			for (Tresource t : l) {
				Resource r = new Resource();
				BeanUtils.copyProperties(t, r);
				if (t.getTresource() != null) {
					r.setPid(t.getTresource().getId());
					r.setPname(t.getTresource().getName());
				}
				r.setTypeId(t.getTresourcetype().getId());
				r.setTypeName(t.getTresourcetype().getName());
				if (t.getIcon() != null && !t.getIcon().equalsIgnoreCase("")) {
					r.setIconCls(t.getIcon());
				}
				lr.add(r);
			}
		}
		log.info("debug级别的日志输出----" + JSON.toJSONString(lr));
		return lr;
	}

	@Override
	public void add(Resource resource, SessionInfo sessionInfo) {
		Tresource t = new Tresource();
		BeanUtils.copyProperties(resource, t);
		if (resource.getPid() != null && !resource.getPid().equalsIgnoreCase("")) {
			t.setTresource(resourceDao.get(Tresource.class, resource.getPid()));
		}
		if (resource.getTypeId() != null && !resource.getTypeId().equalsIgnoreCase("")) {
			t.setTresourcetype(resourceTypeDao.getById(resource.getTypeId()));
		}
		if (resource.getIconCls() != null && !resource.getIconCls().equalsIgnoreCase("")) {
			t.setIcon(resource.getIconCls());
		}
		resourceDao.save(t);

		// 由于当前用户所属的角色，没有访问新添加的资源的权限，所以在新添加资源的时候，将当前资源授权给当前用户的所有角色，以便添加资源后在资源列表中能找到
		Tuser user = userDao.get(Tuser.class, sessionInfo.getId());
		Set<Trole> roles = user.getTroles();
		for (Trole r : roles) {
			r.getTresources().add(t);
		}
	}

	@Override
	public void delete(String id) {
		Tresource t = resourceDao.get(Tresource.class, id);
		del(t);
	}

	private void del(Tresource t) {
		if (t.getTresources() != null && t.getTresources().size() > 0) {
			for (Tresource r : t.getTresources()) {
				del(r);
			}
		}
		resourceDao.delete(t);
	}

	@Override
	public void edit(Resource resource) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", resource.getId());
		Tresource t = resourceDao.get("select distinct t from Tresource t where t.id= :id", params);
		if (t != null) {
			BeanUtils.copyProperties(resource, t);
			if (resource.getTypeId() != null && !resource.getTypeId().equalsIgnoreCase("")) {
				t.setTresourcetype(resourceTypeDao.getById(resource.getTypeId()));// 赋值资源类型
			}
			if (resource.getIconCls() != null && !resource.getIconCls().equalsIgnoreCase("")) {
				t.setIcon(resource.getIconCls());
			}
			if (resource.getPid() != null && !resource.getPid().equalsIgnoreCase("")) {// 说明前台选中了上级资源
				Tresource pt = resourceDao.get(Tresource.class, resource.getPid());
				isChildren(t, pt);// 说明要将当前资源修改到当前资源的子/孙子资源下
				t.setTresource(pt);
			} else {
				t.setTresource(null);// 前台没有选中上级资源，所以就置空
			}
		}
	}

	/**
	 * 是否是将当前节点修改到当前节点的子节点
	 * 
	 * @param t
	 *            当前节点
	 * @param pt
	 *            要修改到的节点
	 */
	private boolean isChildren(Tresource t, Tresource pt) {
		if (pt != null && pt.getTresource() != null) {
			if (pt.getTresource().getId().equalsIgnoreCase(t.getId())) {
				pt.setTresource(null);
				return true;
			} else {
				return isChildren(t, pt.getTresource());
			}
		}
		return false;
	}

}
