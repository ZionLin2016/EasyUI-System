package lsd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lsd.dao.ResourceDaoI;
import lsd.dao.RoleDaoI;
import lsd.dao.UserDaoI;
import lsd.model.Tresource;
import lsd.model.Trole;
import lsd.model.Tuser;
import lsd.pageModel.Role;
import lsd.pageModel.SessionInfo;
import lsd.pageModel.Tree;
import lsd.service.RoleServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleServiceI {

	@Autowired
	private RoleDaoI roleDao;

	@Autowired
	private UserDaoI userDao;

	@Autowired
	private ResourceDaoI resourceDao;

	@Override
	public void grant(Role role) {
		Trole t = roleDao.get(Trole.class, role.getId());
		if (role.getResourceIds() != null && !role.getResourceIds().equalsIgnoreCase("")) {
			String ids = "";
			boolean b = false;
			for (String id : role.getResourceIds().split(",")) {
				if (b) {
					ids += ",";
				} else {
					b = true;
				}
				ids += "'" + id + "'";
			}
			t.setTresources(new HashSet<Tresource>(resourceDao.find("select distinct t from Tresource t where t.id in (" + ids + ")")));
		} else {
			t.setTresources(null);
		}
	}

	@Override
	public Role get(String id) {
		Role r = new Role();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Trole t = roleDao.get("select distinct t from Trole t left join fetch t.tresources resource where t.id = :id", params);
		if (t != null) {
			BeanUtils.copyProperties(t, r);
			if (t.getTrole() != null) {
				r.setPid(t.getTrole().getId());
				r.setPname(t.getTrole().getName());
			}
			Set<Tresource> s = t.getTresources();
			if (s != null && !s.isEmpty()) {
				boolean b = false;
				String ids = "";
				String names = "";
				for (Tresource tr : s) {
					if (b) {
						ids += ",";
						names += ",";
					} else {
						b = true;
					}
					ids += tr.getId();
					names += tr.getName();
				}
				r.setResourceIds(ids);
				r.setResourceNames(names);
			}
		}
		return r;
	}

	@Override
	public void delete(String id) {
		Trole t = roleDao.get(Trole.class, id);
		del(t);
	}

	private void del(Trole t) {
		if (t.getTroles() != null && t.getTroles().size() > 0) {
			for (Trole r : t.getTroles()) {
				del(r);
			}
		}
		roleDao.delete(t);
	}

	@Override
	public void add(Role role, SessionInfo sessionInfo) {
		Trole t = new Trole();
		BeanUtils.copyProperties(role, t);
		if (role.getPid() != null && !role.getPid().equalsIgnoreCase("")) {
			t.setTrole(roleDao.get(Trole.class, role.getPid()));
		}
		roleDao.save(t);

		// 角色赋予给当前的用户
		Tuser user = userDao.get(Tuser.class, sessionInfo.getId());
		user.getTroles().add(t);
	}

	@Override
	public void edit(Role role) {
		Trole t = roleDao.get(Trole.class, role.getId());
		if (t != null) {
			BeanUtils.copyProperties(role, t);
			if (role.getPid() != null && !role.getPid().equalsIgnoreCase("")) {
				t.setTrole(roleDao.get(Trole.class, role.getPid()));
			}
			if (role.getPid() != null && !role.getPid().equalsIgnoreCase("")) {// 说明前台选中了上级资源
				Trole pt = roleDao.get(Trole.class, role.getPid());
				isChildren(t, pt);// 说明要将当前资源修改到当前资源的子/孙子资源下
				t.setTrole(pt);
			} else {
				t.setTrole(null);// 前台没有选中上级资源，所以就置空
			}
		}
	}

	private boolean isChildren(Trole t, Trole pt) {
		if (pt != null && pt.getTrole() != null) {
			if (pt.getTrole().getId().equalsIgnoreCase(t.getId())) {
				pt.setTrole(null);
				return true;
			} else {
				return isChildren(t, pt.getTrole());
			}
		}
		return false;
	}

	@Override
	public List<Role> treeGrid(SessionInfo sessionInfo) {
		List<Role> rl = new ArrayList<Role>();
		List<Trole> tl = null;
		Map<String, Object> params = new HashMap<String, Object>();
		if (sessionInfo != null) {
			params.put("userId", sessionInfo.getId());// 查自己有权限的角色
			tl = roleDao
					.find("select distinct t from Trole t left join fetch t.tresources resource join fetch t.tusers user where user.id = :userId order by t.seq",
							params);
		} else {
			tl = roleDao.find("select distinct t from Trole t left join fetch t.tresources resource order by t.seq");
		}
		if (tl != null && tl.size() > 0) {
			for (Trole t : tl) {
				Role r = new Role();
				BeanUtils.copyProperties(t, r);
				r.setIconCls("status_online");
				if (t.getTrole() != null) {
					r.setPid(t.getTrole().getId());
					r.setPname(t.getTrole().getName());
				}
				Set<Tresource> s = t.getTresources();
				if (s != null && !s.isEmpty()) {
					boolean b = false;
					String ids = "";
					String names = "";
					for (Tresource tr : s) {
						if (b) {
							ids += ",";
							names += ",";
						} else {
							b = true;
						}
						ids += tr.getId();
						names += tr.getName();
					}
					r.setResourceIds(ids);
					r.setResourceNames(names);
				}
				rl.add(r);
			}
		}
		return rl;
	}

	@Override
	public List<Tree> tree(SessionInfo sessionInfo) {
		List<Trole> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		Map<String, Object> params = new HashMap<String, Object>();
		if (sessionInfo != null) {
			params.put("userId", sessionInfo.getId());// 查自己有权限的角色
			l = roleDao.find("select distinct t from Trole t join fetch t.tusers user where user.id = :userId order by t.seq", params);
		} else {
			l = roleDao.find("from Trole t order by t.seq");
		}

		if (l != null && l.size() > 0) {
			for (Trole t : l) {
				Tree tree = new Tree();
				BeanUtils.copyProperties(t, tree);
				tree.setText(t.getName());
				tree.setIconCls("status_online");
				if (t.getTrole() != null) {
					tree.setPid(t.getTrole().getId());
				}
				lt.add(tree);
			}
		}
		return lt;
	}

	@Override
	public List<Tree> allTree() {
		return this.tree(null);
	}

}
