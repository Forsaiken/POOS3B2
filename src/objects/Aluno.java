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
		
		disciplinas.add(disciplina);
		
	}
	

	public Disciplina getDisciplina(byte codigo) {
		int index = 666;
		for (int i = 0; i < disciplinas.size(); i++) {
			if (disciplinas.get(i).getCodigo() == codigo) {
				index = i;
				break;
			}
		}
		return disciplinas.get(index);
	}
	
}


