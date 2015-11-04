package controller;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import model.User;

@ManagedBean(name="userController")
@SessionScoped
public class UserController implements Serializable{

	private static final long serialVersionUID = -3770573459254222700L;
	
	private User user;
	
	private List<User> users;
	
	private String message;
	
	
	private boolean createUserPanel;

	public UserController() {
		System.out.println("*** Construtor!!");
		this.user = new User();
		this.users = new ArrayList<User>();
		this.message = null;
		this.createUserPanel = true;
	}
	
	public String addUser(){

		Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String dateStr = formatter.format(new Date());
		this.user.setDateOfLogin(dateStr);
		if (!this.users.contains(this.user)){
			this.users.add(this.user);
			this.createUserPanel = false;
		}else{
			System.out.println("Ja existe usuario com esse apelido");
		}
		// Enviar para todos os clientes o nickname do usuario
		return null;
	}
	
	// GET AND SET
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isCreateUserPanel() {
		return createUserPanel;
	}

	public void setCreateUserPanel(boolean createUserPanel) {
		this.createUserPanel = createUserPanel;
	}

}
