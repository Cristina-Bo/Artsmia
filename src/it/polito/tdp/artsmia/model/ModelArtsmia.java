package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class ModelArtsmia {
	
	private Graph<ArtObject, DefaultWeightedEdge> grafo;
	private Map<Integer, ArtObject> idMap;
	
	public ModelArtsmia() {
		idMap = new HashMap<Integer, ArtObject>();
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	}
	
	public void creaGrafo() {
		ArtsmiaDAO dao = new ArtsmiaDAO();
		List<ArtObject> oggetti = dao.listObjects(idMap);
		
		// aggiungo i vertici
		Graphs.addAllVertices(grafo, idMap.values());
		
		
		// aggiungo gli archi
		List<Adiacenza> adj = dao.listAdiacenze();
		for(Adiacenza a: adj) {
			ArtObject source = idMap.get(a.getO1());
			ArtObject dest = idMap.get(a.getO2());
			Graphs.addEdge(grafo, source, dest, a.getPeso());
		}
		
		
	}

	public int getVertexSize() {
		return grafo.vertexSet().size();
	}
	
	public int getEdgeSize() {
		return grafo.edgeSet().size();
	}
	
	public boolean verificaId(int idInserito) {
		ArtsmiaDAO dao = new ArtsmiaDAO();
		return dao.verificaId(idInserito);
	}

	public List<ArtObject> getComponenteConnessa(int idInserito) {
		ArtObject verticeInserito = idMap.get(idInserito);
		System.out.println("Stampo il vertice di partenza "+verticeInserito);
		
		List<ArtObject> result = new ArrayList<ArtObject>();

		GraphIterator<ArtObject, DefaultWeightedEdge> it = new BreadthFirstIterator<>(this.grafo, verticeInserito);
		while (it.hasNext()) {
			result.add(it.next());
		}

		return result;

	}

	public Graph getGrafo() {
		return this.grafo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
