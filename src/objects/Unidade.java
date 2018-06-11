package objects;
import java.util.ArrayList;

public class Unidade {
	
	private ArrayList<Aluno> alunos = new ArrayList<Aluno>();
	private ArrayList<Professor> professores = new ArrayList<Professor>();
	
	public void setAluno(Aluno aluno) {
		alunos.add(aluno);
	}
	
	public ArrayList<Professor> getProfessores() {
		return professores;
	}
	
	public Professor getProfessor(long RA) {
		for (Professor professor: professores) {
			if (professor.getRA() == RA)
				return professor;
		}
		
		return null;
				
	}
	
	public ArrayList<Aluno> getAlunos() {
		return alunos;
	}
	
	public Aluno getAluno(long RA) {
		for (Aluno aluno: alunos) {
			if (aluno.getRA() == RA)
				return aluno;
		}
		
		return null;
				
	}
	
	public void setProfessor(Professor professor) {
		professores.add(professor);
	}

	
}
