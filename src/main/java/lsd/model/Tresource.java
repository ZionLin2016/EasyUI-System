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
@Table(name = "tresource")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tresource implements java.io.Serializable {

	private String id;
	private Tresourcetype tresourcetype;
	private Tresource tresource;
	private String name;
	private String remark;
	private Integer seq;
	private String url;
	private String icon;
	private Set<Trole> troles = new HashSet<Trole>(0);
	private Set<Tresource> tresources = new HashSet<Tresource>(0);

	public Tresource() {
	}

	public Tresource(String id, Tresourcetype tresourcetype, String name) {
		this.id = id;
		this.tresourcetype = tresourcetype;
		this.name = name;
	}

	public Tresource(String id, Tresourcetype tresourcetype, Tresource tresource, String name, String remark, Integer seq, String url, String icon, Set<Trole> troles, Set<Tresource> tresources) {
		this.id = id;
		this.tresourcetype = tresourcetype;
		this.tresource = tresource;
		this.name = name;
		this.remark = remark;
		this.seq = seq;
		this.url = url;
		this.icon = icon;
		this.troles = troles;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRESOURCETYPE_ID", nullable = false)
	public Tresourcetype getTresourcetype() {
		return this.tresourcetype;
	}

	public void setTresourcetype(Tresourcetype tresourcetype) {
		this.tresourcetype = tresourcetype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PID")
	public Tresource getTresource() {
		return this.tresource;
	}

	public void setTresource(Tresource tresource) {
		this.tresource = tresource;
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

	@Column(name = "URL", length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "ICON", length = 100)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "trole_tresource", joinColumns = { @JoinColumn(name = "TRESOURCE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TROLE_ID", nullable = false, updatable = false) })
	public Set<Trole> getTroles() {
		return this.troles;
	}

	public void setTroles(Set<Trole> troles) {
		this.troles = troles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tresource")
	public Set<Tresource> getTresources() {
		return this.tresources;
	}

	public void setTresources(Set<Tresource> tresources) {
		this.tresources = tresources;
	}

}
