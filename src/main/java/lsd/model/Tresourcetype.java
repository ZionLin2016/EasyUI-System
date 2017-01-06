package lsd.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tresourcetype")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tresourcetype implements java.io.Serializable {

	private String id;
	private String name;
	private Set<Tresource> tresources = new HashSet<Tresource>(0);

	public Tresourcetype() {
	}

	public Tresourcetype(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Tresourcetype(String id, String name, Set<Tresource> tresources) {
		this.id = id;
		this.name = name;
		this.tresources = tresources;
	}

	@Id
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tresourcetype")
	public Set<Tresource> getTresources() {
		return this.tresources;
	}

	public void setTresources(Set<Tresource> tresources) {
		this.tresources = tresources;
	}

}
