package com.mygdx.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConexaoBD {
	
	
	public static Connection connect() {
		
		Connection con = null;
		
		try {
			System.out.println("Tentando conectar...");
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:RankingJogadores.db");
			//conexão com database
			System.out.println("Banco conectado!");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e+"");
		}
		return con;
	}
	
	
	
	public static void inserirDados(String nome, String pontuacao, String data) {
		Connection con = connect();
		
		PreparedStatement ps = null;
		
		try {
			System.out.println("Salvando dados...");
			String sql = "INSERT INTO Jogadores(Nome, Pontuacao, Data) VALUES(?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, nome);
			ps.setString(2, pontuacao);
			ps.setString(3, data);
			
			ps.execute();
			
			System.out.println("Dados Salvos!");
			
		}
		catch(SQLException e){
			System.out.println(e.toString());
		}
	}
	public static void alteraPontuacao(String nome, int pontuacao, String data,String nomeSub, int pontuacaosub) {
		String sql = "UPDATE Jogadores SET Nome= ?, Pontuacao = ?, Data = ? WHERE nome = ? and Pontuacao = ?";
		
		try(Connection con = connect();) {
			PreparedStatement ps = con.prepareStatement(sql);
			System.out.println("alterando dados...");
			ps = con.prepareStatement(sql);
			ps.setString(1, nome);
			ps.setInt(2, pontuacao);
			ps.setString(3, data);
			ps.setString(4, nomeSub);
			ps.setInt(5, pontuacaosub);
			
			ps.execute();
			con.close();
			ps.close();
		}
		catch(SQLException e){
			System.out.println(e.toString());
		}
	}
	public static void substituiMaiorPontuacao(String nome,int pontuacao, String data) {
		String sql="SELECT * FROM Jogadores WHERE Pontuacao <= ? ORDER BY Pontuacao DESC";
		try(Connection con = connect();
			PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, pontuacao);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String nomeSub = rs.getString(1);
			int pontuacaoSub = rs.getInt(2);
			//System.out.println(a);
			con.close();
			ps.close();
			alteraPontuacao(nome, pontuacao, data,nomeSub,pontuacaoSub);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void leituraDados() {
		Connection con = connect();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try {
			System.out.println("Lendo dados...");
			
			String sql = "SELECT * FROM Jogadores";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String nome = rs.getString("Nome");
				int pontos = rs.getInt("Pontuacao");
				String data = rs.getString("Data");
								
				Jogador jogador = new Jogador(nome, pontos, data);
				RankingScreen.listaJogadores.add(jogador);
			}
			
		}
		catch (SQLException e){
			System.out.println(e.toString());
		}
		finally {
			try {
				ps.close();
				rs.close();
				con.close();
				System.out.println("Dados obtidos!");
			}
			catch (SQLException e){
				System.out.println(e.toString());
			}
		}
	}

}
