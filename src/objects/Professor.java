package objects;
import java.util.ArrayList;

public class Professor extends Pessoa{
	
	private ArrayList<Byte> materias;
	private Unidade unidade;
	
	public Professor(String nome, long RA, Unidade unidade) {
		this.setNome(nome);
		this.setRA(RA);
		this.unidade = unidade;
		materias = new ArrayList<Byte>();
		
	}
	
	public Aluno getAluno(long RA) {

		ArrayList<Aluno> alunos = unidade.getAlunos();
		for (int i = 0; i <alunos.size(); i++) {
			if (alunos.get(i).getRA() == RA) {
				System.out.println("Aluno " + alunos.get(i).getNome() + " encontrado!");
				for (byte materiaProf : materias) {
					if (alunos.get(i).getDisciplina(materiaProf) != null) {
						return alunos.get(i);
					}
				}
			}
		}
		
		return null;
	}
	
	public void setDisciplina(byte materia) {
		
		boolean exists = false;
		
		for (int i = 0; i < materias.size(); i++) {
			if (materias.get(i).equals(materias))
				exists = true;
		}
		
		if (exists == false) {
			materias.add(materia);
			System.out.println(materia + "foi adicionada. Total: " + materias.size());
		}
		
			
	}
}

