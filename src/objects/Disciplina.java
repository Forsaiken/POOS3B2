package objects;

import global.Materias;

public class Disciplina {

	private byte codigo;
	private double[] trabalhos;
	private double[] provas;
	private String nome;

	public Disciplina(byte codigo) {
		
		this.codigo = codigo;
		
		trabalhos = new double[2];
		provas = new double[2];
		
		if (codigo == Materias.ECONOMIA)
			this.nome = "Economia";
		else if (codigo == Materias.ENGENHARIA_SOFTWARE)
			this.nome = "Engenharia de Software";
		else if (codigo == Materias.POO1)
			this.nome = "Programação Orientada a Objetos";
		else if (codigo == Materias.SISTEMAS_COMPUTACAO)
			this.nome = "Sistemas de Computação";

	}

	public void setNotaProva(int bimestre, double nota) {

		if (bimestre <= 2 && bimestre > 0 && nota >= 0 && nota <= 7) {

			this.provas[bimestre - 1] = nota;

		} else {
			System.out.println("Valor de Bimestre ou nota de prova incorreto!");
		}

	}

	public void setNotaTrabalho(int bimestre, double nota) {

		if (bimestre <= 2 && bimestre > 0 && nota >= 0 && nota <= 3) {

			this.trabalhos[bimestre - 1] = nota;

		} else {
			System.out.println("Valor de Bimestre ou nota de trabalho incorreto!");
		}

	}
	
	public double getNotaProva(int bimestre) {
		return this.provas[bimestre - 1];
		
	}
	
	public double getNotaTrabalho(int bimestre) {
		return this.trabalhos[bimestre - 1];
		
	}
	
	public double getNotaBimestral(int bimestre) {
		if (bimestre == 1) {
			return (provas[0] + trabalhos[0]);
		} else if (bimestre == 2) {
			return (provas[1] + trabalhos[1]);
		} else {
			System.out.println("Valor bimestral deve ser 1 ou 2");
		}
		
		return 0;
	}

	public double getNotaTotal() {

		return (provas[0] + trabalhos[0]) * 0.4 + (provas[0] + trabalhos[0]) * 0.6;
	}

	public byte getCodigo() {
		return this.codigo;
	}
	
	public String getNome() {
		return this.nome;
	}

}
