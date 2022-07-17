package com.demo.dao;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.demo.bean.UserBean;
import com.demo.entity.User;
import com.demo.util.LocalEntityManagerFactory;


public class UserDao {

	private EntityManager em;
	
	public UserDao() {
		try{
			em = LocalEntityManagerFactory.getEntityManager();
		} catch( RuntimeException ex){
			ex.printStackTrace(System.err);
			throw ex;
		}
	}
	public void close(){
		em.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		Query obj = em.createQuery("SELECT u FROM User u");
		List<User> list = obj.getResultList();
		if (list != null && list.size() > 0) {			
			return list;
		} else {
			return null;
		}
	}
	
	public String add(UserBean bean) {
		em.getTransaction().begin();
		User u = new User();
		u.setName(bean.getName());
		u.setGender(bean.getGender());
		u.setAddress(bean.getAddress());
		em.persist(u);
		em.getTransaction().commit();
		return "index.xhtml?faces-redirect=true";	
	}
	
	public String updateUser(UserBean bean) {
		em.getTransaction().begin();
		if(isUserIdPresent(bean.getUpdateId())) {
			Query queryObj = em.createQuery("UPDATE User u SET u.name=:name, u.gender=:gender, u.address=:address WHERE u.id=:id");			
				
			queryObj.setParameter("id", bean.getUpdateId());
			queryObj.setParameter("name", bean.getName());
			queryObj.setParameter("gender", bean.getGender());
			queryObj.setParameter("address", bean.getAddress());
			queryObj.executeUpdate();
			
		}
		em.getTransaction().commit();
		FacesContext.getCurrentInstance().addMessage("editUserForm:userId", new FacesMessage("User Record #" + bean.getUpdateId() + " Is Successfully Updated."));
		return "editUser.xhtml";
	}
	
	private boolean isUserIdPresent(int id) {
		
		boolean idResult = false;
		User u=em.find(User.class, id);
	
		if(u != null) {
			idResult = true;
		}
		return idResult;
	}
	
	public String deleteUser(int id) {
		em.getTransaction().begin();
		User u = em.find(User.class, id);
		em.remove(u);	
		em.getTransaction().commit();
		return "index.xhtml?faces-redirect=true";
	}
	
	
}
