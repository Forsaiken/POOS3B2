import java.util.ArrayList;

public class Aluno implements Materias{ /// TESTE LINCOLN 
	
	private String Nome;
	private long RA;
	private ArrayList<Disciplina> Disciplinas;
	
	public Aluno(String nome, long RA) {
		
		nome = this.Nome;
		RA = this.RA;
		Disciplinas = new ArrayList<Disciplina>();
		
	}
	
	public void setDisciplina(byte codigo, String nome) {
		
		Disciplina disciplina = new Disciplina(codigo, nome);
		Disciplinas.add(disciplina);
		
	}
	
	public void setNotaProva(byte materia, int bimestre, float nota) {
		
		if (bimestre <= 2 && nota <= 7) {
		
			int index = this.getDisciplina(materia);
		
			if (index != 666) {
				this.Disciplinas.get(index).setNotaProva(bimestre, nota);
			} else {
				System.out.println("Matéria esta fora da grade de Disciplinas deste aluno!");
			}
		
		} else {
			System.out.println("Valor de Bimestre ou nota de prova incorreto!");
		}
	
	}
	
	public void setNotaTrabalho(byte materia, int bimestre, float nota) {
		
		if (bimestre <= 2 && nota <= 3) {
			
			int index = this.getDisciplina(materia);
		
			if (index != 666) {
				this.Disciplinas.get(index).setNotaTrabalho(bimestre, nota);
			} else {
				System.out.println("Matéria esta fora da grade de Disciplinas deste aluno!");
			}
		
		} else {
			System.out.println("Valor de Bimestre ou nota de trabalho incorreto!");
		}
	
	}
	
	public void getNotaDisciplina(byte materia) {
		int index =  this.getDisciplina(materia);
		if (index != 666) {
			Disciplina disciplina = Disciplinas.get(index);
			System.out.println("Disciplina: " + disciplina.getNome());
			System.out.println("Nota B1: " + disciplina.getNotaB1());
			System.out.println("Nota B2: " + disciplina.getNotaB2());
			System.out.println("Nota Final: " + disciplina.getNotaTotal());
		}
	}
	
	public int getDisciplina(byte codigo) {
		int index = 666;
		for (int i = 0; i < Disciplinas.size(); i++) {
			if (Disciplinas.get(i).getCodigo() == codigo) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public static void main(String[] args) {
		Aluno aluno = new Aluno("Vitor Mendes", 8799001598L);
		aluno.setDisciplina(Materias.POO1, "Programação Orientada a Objetos");
		aluno.setNotaProva	 (Materias.POO1, 1, 4);
		aluno.setNotaTrabalho(Materias.POO1, 1, 3);
		aluno.setNotaProva	 (Materias.POO1, 2, 5);
		aluno.setNotaTrabalho(Materias.POO1, 2, 1);
		aluno.getNotaDisciplina(Materias.POO1);
		
		
	}
}


