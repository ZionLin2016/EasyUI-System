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
@Table(name = "tbugtype")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tbugtype implements java.io.Serializable {

	private String id;
	private String name;
	private Set<Tbug> tbugs = new HashSet<Tbug>(0);

	public Tbugtype() {
	}

	public Tbugtype(String id) {
		this.id = id;
	}

	public Tbugtype(String id, String name, Set<Tbug> tbugs) {
		this.id = id;
		this.name = name;
		this.tbugs = tbugs;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbugtype")
	public Set<Tbug> getTbugs() {
		return this.tbugs;
	}

	public void setTbugs(Set<Tbug> tbugs) {
		this.tbugs = tbugs;
	}

}
