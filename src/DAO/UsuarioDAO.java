
package DAO;

import DTO.UsuarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class UsuarioDAO {
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<UsuarioDTO> lista = new ArrayList<>(); // Utilizado na pesquisa de usuario
    
    public ResultSet autenticacaoUsuario(UsuarioDTO objusuariodto){
      conn = new ConexaoDAO().conectarBD();
      
        try {
            //"select * from tbusuario where login_usuario = ? and senha_usuario = ? ";
            String sql = "select * from tbusuario where login_usuario = ? and senha_usuario = ? ";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, objusuariodto.getNome_usuario());
            pstm.setString(2, objusuariodto.getSenha_usuario());
            
            ResultSet rs = pstm.executeQuery();
            return rs;
                        
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "UsuarioDAO:" + e);
           return null;
        }
    } 
    
    
    //   Cadastrar Usuário
    public void cadastrarUsuario(UsuarioDTO objUsuarioDTO) {
        //Inserir dados no banco
        String sql = "insert into tbusuario (login_usuario, senha_usuario) values (?, ?)";

        //novo objeto de conexão
        conn = new ConexaoDAO().conectarBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objUsuarioDTO.getNome_usuario());
            pstm.setString(2, objUsuarioDTO.getSenha_usuario());

            pstm.execute();
            pstm.close();
            // Aviso para sucesso de cadastro

        } catch (SQLException e) {
            JOptionPane.showInternalMessageDialog(null, "NovoUsuarioDAO: " + e);
        }
    }

    
//Lista usuários
    public ArrayList<UsuarioDTO> PesquisarUsuario(){
        String sql = "Select * from tbusuario";// busca as informações do banco
        conn = new ConexaoDAO().conectarBD();
        
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                UsuarioDTO objusuarioDTO = new UsuarioDTO();
                objusuarioDTO.setId_usuario(rs.getInt("id_usuario"));//Atributo id_usuario tem que ser igual o do banco
                objusuarioDTO.setNome_usuario(rs.getString("login_usuario"));//Atributo login_usuario tem que ser igual o do banco
                objusuarioDTO.setSenha_usuario(rs.getString("senha_usuario"));//Atributo senha_usuario tem que ser igual o do banco
                
                lista.add(objusuarioDTO);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"UsuarioDAO: " + e);            
        }
        return lista;
    }
    
    
   
}
