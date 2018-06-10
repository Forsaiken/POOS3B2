package objects;

abstract class Pessoa {

	protected String Nome;
	protected long RA;
	protected long CPF;
	protected Unidade unidade; 
	
	// SETS
	
	public void setNome(String nome) {
		Nome = nome;
	}

	public void setRA(long rA) {
		RA = rA;
	}
	
	public void setCPF(long cPF) {
		CPF = cPF;
	}
	
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	// GETS
	
	public String getNome() {
		return Nome;
	}

	public long getRA() {
		return RA;
	}

	public long getCPF() {
		return CPF;
	}
	
	public Unidade getUnidade() {
		return unidade;
	}

}
