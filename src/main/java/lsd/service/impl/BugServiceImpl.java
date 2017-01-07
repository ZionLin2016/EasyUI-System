package lsd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lsd.dao.BugDaoI;
import lsd.dao.BugTypeDaoI;
import lsd.model.Tbug;
import lsd.pageModel.Bug;
import lsd.pageModel.DataGrid;
import lsd.pageModel.PageHelper;
import lsd.service.BugServiceI;
import lsd.util.ClobUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BugServiceImpl implements BugServiceI {

	@Autowired
	private BugDaoI bugDao;

	@Autowired
	private BugTypeDaoI bugTypeDao;

	@Override
	public DataGrid dataGrid(Bug bug, PageHelper ph) {
		DataGrid dg = new DataGrid();
		List<Bug> bl = new ArrayList<Bug>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tbug t ";
		String joinHql = " left join fetch t.tbugtype type ";
		List<Tbug> l = bugDao.find(hql + joinHql + whereHql(bug, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		if (l != null && l.size() > 0) {
			for (Tbug t : l) {
				Bug b = new Bug();
				BeanUtils.copyProperties(t, b, new String[] { "note" });
				b.setTypeId(t.getTbugtype().getId());
				b.setTypeName(t.getTbugtype().getName());
				bl.add(b);
			}
		}
		dg.setRows(bl);
		dg.setTotal(bugDao.count("select count(*) " + hql + " left join t.tbugtype type " + whereHql(bug, params), params));
		return dg;
	}

	private String orderHql(PageHelper ph) {
		String orderString = "";
		if (ph.getSort() != null && ph.getOrder() != null) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	private String whereHql(Bug bug, Map<String, Object> params) {
		String whereHql = "";
		if (bug != null) {
			whereHql += " where 1=1 ";
			if (bug.getName() != null) {
				whereHql += " and t.name like :name";
				params.put("name", "%%" + bug.getName() + "%%");
			}
			if (bug.getCreatedatetimeStart() != null) {
				whereHql += " and t.createdatetime >= :createdatetimeStart";
				params.put("createdatetimeStart", bug.getCreatedatetimeStart());
			}
			if (bug.getCreatedatetimeEnd() != null) {
				whereHql += " and t.createdatetime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", bug.getCreatedatetimeEnd());
			}
			if (bug.getModifydatetimeStart() != null) {
				whereHql += " and t.modifydatetime >= :modifydatetimeStart";
				params.put("modifydatetimeStart", bug.getModifydatetimeStart());
			}
			if (bug.getModifydatetimeEnd() != null) {
				whereHql += " and t.modifydatetime <= :modifydatetimeEnd";
				params.put("modifydatetimeEnd", bug.getModifydatetimeEnd());
			}
			if (bug.getTypeId() != null && !bug.getTypeId().trim().equals("")) {
				whereHql += " and type.id = :type ";
				params.put("type", bug.getTypeId());
			}
		}
		return whereHql;
	}

	@Override
	public void add(Bug bug) {
		Tbug t = new Tbug();
		BeanUtils.copyProperties(bug, t, new String[] { "note" });
		t.setTbugtype(bugTypeDao.getById(bug.getTypeId()));
		t.setNote(ClobUtil.getClob(bug.getNote()));
		t.setCreatedatetime(new Date());
		bugDao.save(t);
	}

	@Override
	public Bug get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tbug t = bugDao.get("from Tbug t join fetch t.tbugtype bugType where t.id = :id", params);
		Bug b = new Bug();
		BeanUtils.copyProperties(t, b, new String[] { "note" });
		b.setNote(ClobUtil.getString(t.getNote()));
		b.setTypeId(t.getTbugtype().getId());
		b.setTypeName(t.getTbugtype().getName());
		return b;
	}

	@Override
	public void edit(Bug bug) {
		Tbug t = bugDao.get(Tbug.class, bug.getId());
		if (t != null) {
			BeanUtils.copyProperties(bug, t, new String[] { "id", "note", "createdatetime" });
			t.setTbugtype(bugTypeDao.getById(bug.getTypeId()));
			t.setModifydatetime(new Date());
			t.setNote(ClobUtil.getClob(bug.getNote()));
		}
	}

	@Override
	public void delete(String id) {
		bugDao.delete(bugDao.get(Tbug.class, id));
	}

}
