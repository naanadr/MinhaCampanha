package br.ufrpe.minhacampanha.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import br.ufrpe.minhacampanha.domain.Instituicao_doadora;
import br.ufrpe.minhacampanha.util.ConnectionFactory;
/**
 * 
 * @author raiss
 *
 */
public class Instituicao_doadoraDAO {
	
	public void criar(Instituicao_doadora instituicao) throws SQLException{
		Connection connection = ConnectionFactory.getConnection();
		java.sql.PreparedStatement stmt = null;
		
		try{
			stmt = connection.prepareStatement("INSERT INTO Instituicao_doadora (id_doador,dt_ultima_doacao, "
					+ "num_doacoes_prod,  num_doacoes_fin)VALUES(?,?,?,?)");
		
		    stmt.setInt(1, instituicao.getId_doador());
			stmt.setDate(2,instituicao.getDt_ultima_doacao());
			stmt.setInt(3,instituicao.getNum_doacoes_prod());
			stmt.setInt(4,instituicao.getNum_doacoes_fin());
			

			
			//JOptionPane.showMessageDialog(null, "Donativo_instituicao, regustrado");
			
		}catch (SQLException ex){
			//JOptionPane.showMessageDialog(null, "Erro ao salvar - "+ex);
			
		}finally{
			ConnectionFactory.closeConnection(connection, stmt);
		}
	}
	
	public List<Instituicao_doadora> listar() throws SQLException{
		Connection connection = ConnectionFactory.getConnection();
		java.sql.PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		List<Instituicao_doadora> instituicaos = new ArrayList<Instituicao_doadora>();
		
		try{
		
			stmt = connection.prepareStatement("SELECT * FROM Instituicao_doadora");
			resultSet =stmt.executeQuery();
		
			while (resultSet.next()){
				
				
				Instituicao_doadora instituicao = new Instituicao_doadora();
				instituicao.setId_doador(resultSet.getInt("id_doador"));
				instituicao.setDt_ultima_doacao(resultSet.getDate("dt_ultima_doacao"));
				instituicao.setNum_doacoes_prod(resultSet.getInt("num_doacoes_prod"));
				instituicao.setNum_doacoes_fin(resultSet.getInt("num_doacoes_fin"));
			
				
				
				instituicaos.add(instituicao);
			 }
			
		}catch (SQLException ex){
			//JOptionPane.showMessageDialog(null, "Erro ao listar - "+ex);
		}finally{
			ConnectionFactory.closeConnection(connection, stmt, resultSet);
		}
		
		return instituicaos;
		
	}
	
	public void update(Instituicao_doadora instituicao) throws SQLException{
		Connection connection = ConnectionFactory.getConnection();
		java.sql.PreparedStatement stmt = null;
		
		try{
			stmt = connection.prepareStatement("UPDATE Instituicao_doadora SET   dt_ultima_doacao= ?,  num_doacoes_prod= ?, "
					+ "num_doacoes_fin  = ? WHERE cod = ?");
			stmt.setDate(1, instituicao.getDt_ultima_doacao());
			stmt.setInt(2, instituicao.getNum_doacoes_prod());
			stmt.setInt(3,instituicao.getNum_doacoes_fin());
			stmt.setLong(4, instituicao.getCodigo());	
			stmt.executeUpdate();
			
			//JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			
		}catch (SQLException ex){
			//JOptionPane.showMessageDialog(null, "Erro ao atualizar - "+ex);
			
		}finally{
			ConnectionFactory.closeConnection(connection, stmt);
		}
	}
	
	public void excluir(Instituicao_doadora instituicao) throws SQLException{
		Connection connection = ConnectionFactory.getConnection();
		java.sql.PreparedStatement stmt = null;
		
		try{
			stmt = connection.prepareStatement("DELETE FROM Instituicao_doadora WHERE id = ?");
			stmt.setLong(1, instituicao.getCodigo());
						
			stmt.executeUpdate();
			
			//JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso");
			
		}catch (SQLException ex){
			//JOptionPane.showMessageDialog(null, "Erro ao excluir - "+ex);
			
		}finally{
			ConnectionFactory.closeConnection(connection, stmt);
		}
		
	}
}
