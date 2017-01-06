package lsd.model;

import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tbug")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tbug implements java.io.Serializable {

	private String id;
	private Tbugtype tbugtype;
	private String name;
	private Clob note;
	private Date createdatetime;
	private Date modifydatetime;

	public Tbug() {
	}

	public Tbug(String id) {
		this.id = id;
	}

	public Tbug(String id, Tbugtype tbugtype, String name, Clob note, Date createdatetime, Date modifydatetime) {
		this.id = id;
		this.tbugtype = tbugtype;
		this.name = name;
		this.note = note;
		this.createdatetime = createdatetime;
		this.modifydatetime = modifydatetime;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BUGTYPE_ID")
	public Tbugtype getTbugtype() {
		return this.tbugtype;
	}

	public void setTbugtype(Tbugtype tbugtype) {
		this.tbugtype = tbugtype;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NOTE")
	public Clob getNote() {
		return this.note;
	}

	public void setNote(Clob note) {
		this.note = note;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATETIME", length = 19)
	public Date getCreatedatetime() {
		return this.createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYDATETIME", length = 19)
	public Date getModifydatetime() {
		return this.modifydatetime;
	}

	public void setModifydatetime(Date modifydatetime) {
		this.modifydatetime = modifydatetime;
	}

}
