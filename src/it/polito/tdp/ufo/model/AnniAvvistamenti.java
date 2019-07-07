package it.polito.tdp.ufo.model;

public class AnniAvvistamenti {
	private int anno;
	private int numAvvistamenti;
	
	
	public AnniAvvistamenti(int anno, int numAvvistamenti) {
		super();
		this.anno = anno;
		this.numAvvistamenti = numAvvistamenti;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public int getNumAvvistamenti() {
		return numAvvistamenti;
	}
	public void setNumAvvistamenti(int numAvvistamenti) {
		this.numAvvistamenti = numAvvistamenti;
	}
	@Override
	public String toString() {
		return anno + " " + numAvvistamenti;
	}
	
	

}
