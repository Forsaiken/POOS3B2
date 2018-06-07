import java.util.ArrayList;

public class Unidade {

	
	ArrayList<Aluno> alunos = new ArrayList<Aluno>();
	ArrayList<Professor> professores = new ArrayList<Professor>();
	
	public void setAluno(Aluno aluno) {
		alunos.add(aluno);
	}
	
	public ArrayList<Professor> getProfessores() {
		return professores;
	}
	
	public ArrayList<Aluno> getAlunos() {
		return alunos;
	}
	
	public void setProfessor(Professor professor) {
		professores.add(professor);
	}

	
}
