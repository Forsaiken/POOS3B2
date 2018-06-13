package objects;

public class Disciplina {

	private byte codigo;
	private double[] trabalhos;
	private double[] provas;

	public Disciplina(byte codigo) {
		
		this.codigo = codigo;
		trabalhos = new double[2];
		provas = new double[2];

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

	public double getNotaB1() {

		double notaB1 = 0;

		for (int i = 0; i < 1; i++) {
			notaB1 += (this.trabalhos[i] + this.provas[i]);
		}

		return notaB1;
	}

	public double getNotaB2() {

		double notaB2 = 0;

		for (int i = 1; i < 2; i++) {
			notaB2 += (this.trabalhos[i] + this.provas[i]);
		}

		return notaB2;
	}

	public double getNotaTotal() {

		double notaB1 = 0;

		for (int i = 0; i < 1; i++) {
			notaB1 += (this.trabalhos[i] + this.provas[i]) * 0.4f;
		}

		double notaB2 = 0;

		for (int i = 1; i < 2; i++) {
			notaB2 += (this.trabalhos[i] + this.provas[i]) * 0.6f;
		}

		return notaB1 + notaB2;
	}

	public byte getCodigo() {
		return this.codigo;
	}

}
