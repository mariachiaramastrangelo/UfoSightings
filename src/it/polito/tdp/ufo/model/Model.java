package it.polito.tdp.ufo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.GrayFilter;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	private SightingsDAO dao;
	private SimpleDirectedGraph<StatiAvvistamenti, DefaultEdge> grafo;
	
	public Model() {
		dao= new SightingsDAO();
		grafo= new SimpleDirectedGraph<>(DefaultEdge.class);
	}

	public List<AnniAvvistamenti> getAnniAvvistamenti() {
		// TODO Auto-generated method stub
		return dao.anniAvvistamenti();
	}
	public List<StatiAvvistamenti> getStatiAvvistamenti(int anno) {
		// TODO Auto-generated method stub
		return dao.statiAvvistamenti(anno);
	}
	
	public void creaGrafo(int anno) {
		Graphs.addAllVertices(grafo, dao.statiAvvistamenti(anno));
		for(StatiAvvistamenti s1: grafo.vertexSet()) {
			for(StatiAvvistamenti s2: grafo.vertexSet()) {
				if (!s1.equals(s2)) {
					if(s1.haUnEventoPrecedente(s2)) {
						Graphs.addEdgeWithVertices(grafo, s1, s2);
					}
				}
				
			}
		}
		
	}
	public String grafoCreato() {
		return "Grafo greato:\nNumero di vertici: "+grafo.vertexSet().size()+"\nNumero di archi: "+grafo.edgeSet().size();
	}
	public String adiacentiEntrantiUscenti(StatiAvvistamenti sa) {
		String result= "stati prima: \n"+grafo.incomingEdgesOf(sa)+"\nstati dopo:"+"\n"+grafo.outgoingEdgesOf(sa);
		return result;
	}
	public List<StatiAvvistamenti> trovaRaggiungibili(StatiAvvistamenti stato){
		DepthFirstIterator<StatiAvvistamenti, DefaultEdge> dfi= new DepthFirstIterator<>(grafo, stato);
		
		List<StatiAvvistamenti> result= new ArrayList<>();
		while(dfi.hasNext()) {
				result.add(dfi.next());
			}
		return  result;
		}
}
