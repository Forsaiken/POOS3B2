package objects;
import java.util.ArrayList;

import global.Materias;

public class Aluno extends Pessoa implements Materias {

	private ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();
	
	public Aluno(String nome, long RA, Unidade unidade) {
		
		this.setNome(nome);
		this.setRA(RA);
		this.setUnidade(unidade);
		
	}
	
	public void setDisciplina(byte codigo) {
		
		Disciplina disciplina = new Disciplina(codigo);
		
		boolean exists = false;
		
		for (Disciplina i: disciplinas) {
			if (i.getCodigo() == codigo) {
				exists = true;
			}
		}
		
		if (exists != true) {
			disciplinas.add(disciplina);
			System.out.println(this.Nome + " " + disciplina.getCodigo() + " foi adicionada. Total: " + disciplinas.size());
		}
		
	}
	

	public Disciplina getDisciplina(byte codigo) {

		for (int i = 0; i < disciplinas.size(); i++) {
			if (disciplinas.get(i).getCodigo() == codigo) {
				return disciplinas.get(i);
			}
		}
		return null;
	}
	

	public Disciplina getDisciplina(int array) {
		return this.disciplinas.get(array);
	}
	
	public ArrayList<Disciplina> getDisciplinas() {
		return this.disciplinas;
	}
	
	public Unidade getUnidade() {
		return this.unidade;
	}
	
}


