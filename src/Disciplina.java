
public class Disciplina {

	private byte codigo;
	private String nome;
	private float[] trabalhos;
	private float[] provas;

	public Disciplina(byte codigo, String nome) {
		this.nome = nome;
		this.codigo = codigo;
		trabalhos = new float[2];
		provas = new float[2];

	}

	public void setNotaProva(int bimestre, float nota) {

		if (bimestre <= 2 && nota <= 7) {

			this.trabalhos[bimestre - 1] = nota;

		} else {
			System.out.println("Valor de Bimestre ou nota de prova incorreto!");
		}

	}

	public void setNotaTrabalho(int bimestre, float nota) {

		if (bimestre <= 2 && nota <= 3) {

			this.trabalhos[bimestre - 1] = nota;

		} else {
			System.out.println("Valor de Bimestre ou nota de trabalho incorreto!");
		}

	}

	public float getNotaB1() {

		float notaB1 = 0;

		for (int i = 0; i < 1; i++) {
			notaB1 += (this.trabalhos[i] + this.provas[i]);
		}

		return notaB1;
	}

	public float getNotaB2() {

		float notaB2 = 0;

		for (int i = 1; i < 2; i++) {
			notaB2 += (this.trabalhos[i] + this.provas[i]);
		}

		return notaB2;
	}

	public float getNotaTotal() {

		float notaB1 = 0;

		for (int i = 0; i < 1; i++) {
			notaB1 += (this.trabalhos[i] + this.provas[i]) * 0.4f;
		}

		float notaB2 = 0;

		for (int i = 1; i < 2; i++) {
			notaB2 += (this.trabalhos[i] + this.provas[i]) * 0.6f;
		}

		return notaB1 + notaB2;
	}

	public byte getCodigo() {
		return this.codigo;
	}

	public String getNome() {
		return this.nome;
	}

}
