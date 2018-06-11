package objects;
import java.util.ArrayList;

public class Professor extends Pessoa{
	
	private ArrayList<Byte> materias;
	private Unidade unidade;
	
	public Professor(String nome, long RA) {
		this.setNome(nome);
		this.setRA(RA);
		materias = new ArrayList<Byte>();
		
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
	
	public void setDisciplina(byte materia) {
		
		boolean exists = false;
		
		for (int i = 0; i < materias.size(); i++) {
			if (materias.get(i).equals(materia))
				exists = true;
		}
		
		if (exists == false)
			materias.add(materia);
			
	}
}

