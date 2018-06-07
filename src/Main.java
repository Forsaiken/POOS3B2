
public class Main {
	public static void main(String[] args) {
		
		Unidade anhanguera = new Unidade();
		
		Aluno a1 = new Aluno("Lincoln Nunes", 47041926877L, anhanguera);
		Aluno a2 = new Aluno("Rodrigo Dias", 68526465464L, anhanguera);
		
		
		System.out.println(a1.getNome());
		System.out.println(a2.getNome());
		
		
		a1.setDisciplina(Materias.ALGORITMO, "Algoritmo");
		a1.getDisciplina(Materias.ALGORITMO).setNotaProva(2, 6);
		
		Professor p1 = new Professor("Cleiton", 6666666666L, anhanguera);
		System.out.println(p1.getNome());
		p1.getAluno(47041926877L).getDisciplina(codigo)
		
		
		
	}
}
