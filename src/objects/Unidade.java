package objects;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Unidade {
	
	private ArrayList<Aluno> alunos = new ArrayList<Aluno>();
	private ArrayList<Professor> professores = new ArrayList<Professor>();
	private String name;
	
	public Unidade(String name) {
		this.name = name;
	}
	
	public void setAluno(Aluno aluno) {
		alunos.add(aluno);
	}
	
	public void setProfessor(Professor professor) {
		professores.add(professor);
	}

	public boolean removerAluno(long number) {
		boolean ok = false;
		for (int i = 0; i < alunos.size(); i++) {
			if (alunos.get(i).getCPF() == number || alunos.get(i).getRA() == number) {
				JOptionPane.showMessageDialog(null,"O Aluno " + alunos.get(i).getNome() + " foi removido com sucesso!","Sucesso!", JOptionPane.INFORMATION_MESSAGE);
				alunos.remove(i);
				ok = true;
			}
		}
		return ok;
	}
	
	public boolean removerProfessor(long number) {
		boolean ok = false;
		for (int i = 0; i < professores.size(); i++) {
			if (professores.get(i).getCPF() == number || professores.get(i).getRA() == number) {
				JOptionPane.showMessageDialog(null,"O Professor " + professores.get(i).getNome() + " foi removido com sucesso!","Sucesso!", JOptionPane.INFORMATION_MESSAGE);
				professores.remove(i);
				ok = true;
			}
		}
		return ok;
	}
	
	public Professor getProfessor(long RA) {
		for (Professor professor: professores) {
			if (professor.getRA() == RA)
				return professor;
		}
		
		return null;
				
	}
	
	public ArrayList<Professor> getProfessores() {
		return professores;
	}

	public Aluno getAluno(long number) {
		for (Aluno aluno: alunos) {
			if (aluno.getRA() == number || aluno.getCPF() == number)
				return aluno;
		}
		
		return null;
				
	}

	public ArrayList<Aluno> getAlunos() {
		return alunos;
	}
	
	public String getName() {
		return this.name;
	}

}
