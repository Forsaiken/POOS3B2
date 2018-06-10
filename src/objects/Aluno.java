package objects;
import java.util.ArrayList;

import global.Materias;

public class Aluno extends Pessoa implements Materias {

	private ArrayList<Disciplina> Disciplinas;
	
	public Aluno(String nome, long RA, Unidade unidade) {
		
		this.setNome(nome);
		this.setRA(RA);
		this.setUnidade(unidade);
		unidade.setAluno(this);
		
		Disciplinas = new ArrayList<Disciplina>();
		
	}
	
	public void setDisciplina(byte codigo, String nome) {
		
		Disciplina disciplina = new Disciplina(codigo, nome);
		Disciplinas.add(disciplina);
		
	}
	

	public Disciplina getDisciplina(byte codigo) {
		int index = 666;
		for (int i = 0; i < Disciplinas.size(); i++) {
			if (Disciplinas.get(i).getCodigo() == codigo) {
				index = i;
				break;
			}
		}
		return Disciplinas.get(index);
	}
	
}


