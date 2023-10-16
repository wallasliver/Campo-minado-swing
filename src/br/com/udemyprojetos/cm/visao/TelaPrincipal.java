package br.com.udemyprojetos.cm.visao;

import javax.swing.JFrame;

import br.com.udemyprojetos.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {
	
	public TelaPrincipal() {
		
		Tabuleiro tabuleiro = new Tabuleiro(16,30,45);
		
		add(new PaineTabuleiro(tabuleiro));
		
        setTitle("Campo Minado");
        setSize(690, 438);
        setLocationRelativeTo(null);;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

}
	
	public static void main(String[] args) {
		new TelaPrincipal();
		
		
	}
	
}
