package objects;
import java.util.ArrayList;

import global.Materias;

public class Aluno extends Pessoa implements Materias {

	private ArrayList<Disciplina> disciplinas;
	
	public Aluno(String nome, long RA) {
		
		this.setNome(nome);
		this.setRA(RA);
		this.setUnidade(unidade);
		
		disciplinas = new ArrayList<Disciplina>();
		
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
			System.out.println(disciplina.getCodigo() + "foi adicionada. Total: " + disciplinas.size());
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
	
}


