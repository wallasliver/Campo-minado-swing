package br.com.udemyprojetos.cm.visao;

//import javax.swing.JButton;


import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.udemyprojetos.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PaineTabuleiro extends JPanel {
	
	public PaineTabuleiro(Tabuleiro tabuleiro) {
		
    setLayout(new GridLayout(
    		tabuleiro.getLinhas(),tabuleiro.getColunas()));
    
    tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));
    
    tabuleiro.registrarObservador(e -> {
    	
SwingUtilities.invokeLater(() -> {
    if(e.isGanhou()) {
	    JOptionPane.showMessageDialog(this, "Ganhou: )");
    }else {
	    JOptionPane.showMessageDialog(this,"Perdeu :( ");
    }
    
    tabuleiro.reiniciar();
    	});
    });
    
       
//    int total = tabuleiro.getLinhas() * tabuleiro.getColunas();
//    for (int i = 0; i < total; i++) {
//    	add(new BotaoCampo(null)); }
    
	}
}                                 