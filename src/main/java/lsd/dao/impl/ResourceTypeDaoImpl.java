package lsd.dao.impl;

import lsd.dao.ResourceTypeDaoI;
import lsd.model.Tresourcetype;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class ResourceTypeDaoImpl extends BaseDaoImpl<Tresourcetype> implements ResourceTypeDaoI {

	@Override
	@Cacheable(value = "resourceTypeDaoCache", key = "#id")
	public Tresourcetype getById(String id) {
		return super.get(Tresourcetype.class, id);
	}

}
