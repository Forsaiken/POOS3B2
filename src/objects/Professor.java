package objects;
import java.util.ArrayList;

public class Professor extends Pessoa{
	private byte materia;
	private Unidade unidade;
	
	public Professor(String nome, long RA) {
		this.setNome(nome);
		this.setRA(RA);
		
	}
	
	public Aluno getAluno(long RA) {
		int index = 666;
		ArrayList<Aluno> alunos = unidade.getAlunos();
		for (int i = 0; i <alunos.size(); i++) {
			if (alunos.get(i).getRA() == RA) {
				index = i;
				break;
			}
		}
		return alunos.get(index);
	}
}

