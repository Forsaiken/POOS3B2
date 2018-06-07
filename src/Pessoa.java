
abstract class Pessoa {

	private String Nome;
	private long RA;
	private long CPF;
	private Unidade unidade; 
	

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public long getRA() {
		return RA;
	}

	public void setRA(long rA) {
		RA = rA;
	}

	public long getCPF() {
		return CPF;
	}

	public void setCPF(long cPF) {
		CPF = cPF;
	}
}
