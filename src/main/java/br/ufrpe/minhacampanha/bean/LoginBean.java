package br.ufrpe.minhacampanha.bean;

import java.io.Serializable;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.omg.CORBA.PERSIST_STORE;
import org.omnifaces.util.Messages;

import br.ufrpe.minhacampanha.dao.InstituicaoDAO;
import br.ufrpe.minhacampanha.dao.LoginDAO;
import br.ufrpe.minhacampanha.dao.PessoaFisicaDAO;
import br.ufrpe.minhacampanha.domain.Instituicao;
import br.ufrpe.minhacampanha.domain.Login;
import br.ufrpe.minhacampanha.domain.PessoaFisica;
import br.ufrpe.minhacampanha.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean 
@SessionScoped
public class LoginBean implements Serializable{
	private Login login;
	
	public Login getLogin() {
		return login;
	}
	
	
	public void setLogin(Login loginExistente) {
		this.login = loginExistente;
	}
	
	@PostConstruct
	public void novoLogin(){
		login = new Login();
	}
	
	
	public String logar(){
		/**
		 * TODO quando clicar nesse botao vai levar para a tela referente
		 * ao tipo de usuario:
		 */		
		try {			
			LoginDAO loginDAO = new LoginDAO();
			Usuario verificado = loginDAO.pegaUser(login);
			
			FacesContext context = FacesContext.getCurrentInstance();
			
			if (verificado != null) {
				context.getExternalContext().getSessionMap().put("usuario", verificado);
				
				if (verificado.getInstituicao_vinculada() != 0L) {
					InstituicaoDAO instituicaoDAO = new InstituicaoDAO();
					Instituicao instituicao = instituicaoDAO.buscar(verificado.getInstituicao_vinculada());
					
					context.getExternalContext().getApplicationMap().put("instituicao", instituicao);
					return "/pages/menuInstituicao?faces-redirect=true";
				} else {
					PessoaFisicaDAO pessoaDAO = new PessoaFisicaDAO();
					PessoaFisica pessoa = pessoaDAO.buscar(verificado.getCodigo());
					
					context.getExternalContext().getApplicationMap().put("pessoa", pessoa);
					return "/pages/menuPessoa?faces-redirect=true";
				}
			} else {
				Messages.addGlobalError("Usuario ou senha incorretos!");
			}
		} catch (RuntimeException|SQLException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar logar no sistema.");
			erro.printStackTrace();
		}
		return null;
	}	
}
