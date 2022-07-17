package com.demo.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import com.demo.dao.UserDao;
import com.demo.entity.User;


@ManagedBean
public class UserBean {

	private int id;
	private String name;
	private String gender;
	private String address;
	private UserDao userDao;
	private String editId;
	private int updateId;
	
	@PostConstruct
	private void init() {
		userDao = new UserDao();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

	public List<User> findAll() {
		return userDao.findAll();
	}
	
	public String add(UserBean bean) {
		return userDao.add(bean);		
	}
	
	public int getUpdateId() {
		return updateId;
	}

	public void setUpdateId(int updateId) {
		this.updateId = updateId;
	}

	public String editUserById() {
		editId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedId");		
		updateId = Integer.parseInt(editId);
		return "editUser.xhtml";
	}
	
	public String updateUser(UserBean bean) {
		return userDao.updateUser(bean);		
	}
	
	public String deleteUser(int id) {
		return userDao.deleteUser(id);
	}

	
	
	
}
