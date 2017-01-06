package lsd.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "trole")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Trole implements java.io.Serializable {

	private String id;
	private Trole trole;
	private String name;
	private String remark;
	private Integer seq;
	private Set<Trole> troles = new HashSet<Trole>(0);
	private Set<Tresource> tresources = new HashSet<Tresource>(0);
	private Set<Tuser> tusers = new HashSet<Tuser>(0);

	public Trole() {
	}

	public Trole(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Trole(String id, Trole trole, String name, String remark, Integer seq, Set<Trole> troles, Set<Tresource> tresources, Set<Tuser> tusers) {
		this.id = id;
		this.trole = trole;
		this.name = name;
		this.remark = remark;
		this.seq = seq;
		this.troles = troles;
		this.tresources = tresources;
		this.tusers = tusers;
	}

	@Id
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PID")
	public Trole getTrole() {
		return this.trole;
	}

	public void setTrole(Trole trole) {
		this.trole = trole;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "SEQ")
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trole")
	public Set<Trole> getTroles() {
		return this.troles;
	}

	public void setTroles(Set<Trole> troles) {
		this.troles = troles;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "trole_tresource", joinColumns = { @JoinColumn(name = "TROLE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TRESOURCE_ID", nullable = false, updatable = false) })
	public Set<Tresource> getTresources() {
		return this.tresources;
	}

	public void setTresources(Set<Tresource> tresources) {
		this.tresources = tresources;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tuser_trole", joinColumns = { @JoinColumn(name = "TROLE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TUSER_ID", nullable = false, updatable = false) })
	public Set<Tuser> getTusers() {
		return this.tusers;
	}

	public void setTusers(Set<Tuser> tusers) {
		this.tusers = tusers;
	}

}
