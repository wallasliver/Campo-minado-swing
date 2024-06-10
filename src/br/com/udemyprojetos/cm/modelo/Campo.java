package br.com.udemyprojetos.cm.modelo;

import java.util.ArrayList;
import java.util.List;

//2ª opção: import java.util.function.BiConsumer;
//import br.com.udemyprojetos.cm.excecao.ExplosaoException;

public class Campo {

	private final int linha;
	private final int coluna;

	private boolean aberto;
	private boolean minado;

	private boolean marcado = false;

	private List<Campo> vizinhos = new ArrayList<>();
	private List<CampoObservador> observadores = new ArrayList<>();

//2ª opção:	private List<BiConsumer<Campo,CampoEvento>> 
//	observadores2 = new ArrayList<>();

	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	public void registrarObservador(CampoObservador observador) {
		observadores.add(observador);
	}

	private void notificarObservadores(CampoEvento evento) {
		observadores.stream().forEach(o -> o.eventoOcorreu(this, evento));
	}

	boolean adicionarVizinho(Campo vizinho) {

		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;

		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);

		int deltaGeral = deltaLinha + deltaColuna;

		if (deltaGeral == 1 && !diagonal) {

			vizinhos.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {

			return false;
		}

	}

	public void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;
			if (marcado) {
				notificarObservadores(CampoEvento.MARCAR);
			} else {
				notificarObservadores(CampoEvento.DESMARCAR);
			}
		}
	}

	public boolean abrir() {

		if (!aberto && !marcado) {
			aberto = true;

			if (minado) {
				// TODO Implementar nova versão
				notificarObservadores(CampoEvento.EXPLODIR);
				return true;
			}

			setAberto(true);

			if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;

		} else {
			return false;
		}
	}

	public boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
		// noneMatch --> nenhum deu match --> der true (entao vizinhança é segura)
	}

	void minar() {
		minado = true;
	}

	public boolean isMinado() {
		return minado;
	}

	public boolean isMarcado() {
		return marcado;
	}

	void setAberto(boolean aberto) {
		this.aberto = aberto;
		if (aberto) {
			notificarObservadores(CampoEvento.ABRIR);
		}
	}
//Wallas
	public boolean isAberto() {
		return aberto;
	}

	public boolean isFechado() {
		return !isAberto();
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegio = minado && marcado;
		return desvendado || protegio;
	}

	public int minasNaVizinhanca() {
		return (int) vizinhos.stream().filter(v -> v.minado).count(); 
		/// Motivos para explicitamente colocar (int), pe que o Count(), retorna tipo Long
	}

	void reiniciar() {
		minado = false;
		marcado = false;
		aberto = false;
		notificarObservadores(CampoEvento.REINICIAR);
	}

//	public String toString(){		
//		if(marcado) {
//		 return "x";	 
//		}else if(minado && aberto){
//			return "*";	
//		}else if(aberto && minasNaVizinhanca() > 0) {
//			return Long.toString(minasNaVizinhanca());
//		}else if(aberto) {
//			return " ";
//		}else {
//			return "?";	
//		}		
//	}

}
