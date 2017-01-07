package lsd.service.impl;

import java.util.List;

import lsd.dao.BugTypeDaoI;
import lsd.model.Tbugtype;
import lsd.service.BugTypeServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BugTypeServiceImpl implements BugTypeServiceI {

	@Autowired
	private BugTypeDaoI bugType;

	@Override
	@Cacheable(value = "bugTypeServiceCache", key = "'bugTypeList'")
	public List<Tbugtype> getBugTypeList() {
		return bugType.find("from Tbugtype t");
	}

}
