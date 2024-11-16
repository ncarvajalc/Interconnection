package model.data_structures;

import java.util.Comparator;

public class Vertex<K extends Comparable<K>,V  extends Comparable <V>> implements Comparable<Vertex<K, V>>
{
	private K key;
	private V value;
	private ILista<Edge<K>> arcos;
	private boolean marked;
	
	public Vertex(K id, V value)
	{
		this.key=id;
		this.value=value;
		this.arcos= new ArregloDinamico<>(1);
	}

	
	public K getId()
	{
		return key;
	}
	
	public V getInfo()
	{
		return value;
	}
	
	public boolean getMark()
	{
		return marked;
	}
	
	public void addEdge( Edge<K> edge )
	{
		try {
			arcos.insertElement(edge, arcos.size() +1);
		} catch (PosException | NullException e) {
			e.printStackTrace();
		}
	}
	
	public void mark()
	{
		marked=true;
	}
	
	public void unmark()
	{
		marked=false;
	}
	
	public int outdegree()
	{
		return arcos.size();
	}
	
	public int indegree() 
	{
		return arcos.size();
	}
	
	public Edge<K> getEdge(K vertex)
	{
		Edge<K> retorno=null;
		for(int i=1; i<=arcos.size(); i++)
		{
			try 
			{
				Vertex<K,V> destino= (Vertex<K, V>) arcos.getElement(i).getDestination();
				
				if(destino.getId().compareTo(vertex)==0)
				{
					retorno= arcos.getElement(i);
				}
			} 
			catch (PosException | VacioException e) 
			{
				e.printStackTrace();
			}
		}
		
		return retorno;
	
	}
	
	public ILista<Vertex<K,V>> vertices()
	{
		ILista<Vertex<K,V>> retorno=new ArregloDinamico<>(1);
		for(int i=1; i<=arcos.size(); i++)
		{
			try {
				retorno.insertElement((Vertex<K, V>) arcos.getElement(i).getDestination(), retorno.size()+1);
			} catch (PosException | NullException | VacioException e) {
				e.printStackTrace();
			}
		}
		
		return retorno; 
	}
	
	public ILista<Edge<K>> edges()
	{
		return arcos;
	}
	
	public void bfs()
	{
		ColaEncadenada<Vertex<K, V>> cola= new ColaEncadenada<>();
		mark();
		cola.enqueue(this);
		while(cola.peek() !=null)
		{
			Vertex<K, V> actual= cola.dequeue();
			for(int i=1; i<=actual.arcos.size(); i++)
			{
				Vertex<K, V> dest;
				try 
				{
					dest = (Vertex<K, V>) actual.edges().getElement(i).getDestination();
					if(dest.marked)
					{
						mark();
						cola.enqueue(dest);
					}
				} 
				catch (PosException | VacioException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void dfs(Edge<K> edgeTo)
	{
		mark();
		for(int i=1; i<=arcos.size(); i++)
		{
			Vertex<K, V> dest;
			try 
			{
				dest = (Vertex<K, V>) arcos.getElement(i).getDestination();
				if(!dest.marked)
				{
					dest.dfs(arcos.getElement(i));
				}
			} 
			catch (PosException | VacioException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void topologicalOrder( ColaEncadenada<Vertex<K, V>> pre, ColaEncadenada<Vertex<K, V>> post, PilaEncadenada<Vertex<K, V>> reversePost )
	{
		mark();
		pre.enqueue(this);
		
		for(int i=1; i<= arcos.size(); i++ )
		{
			Vertex<K, V> destino;
			try {
				destino = (Vertex<K, V>) arcos.getElement(i).getDestination();
				if(!destino.getMark())
				{
					destino.topologicalOrder(pre, post, reversePost);
				}
			} catch (PosException | VacioException e) {
				
				e.printStackTrace();
			}

		}
		
		post.enqueue(this);
		reversePost.push(this);
		
	}



	@Override
	public int compareTo(Vertex<K, V> o) 
	{
		return key.compareTo(o.getId());
	}
	
	public void getSCC(ITablaSimbolos<K, Integer> tabla, int idComponente)
	{
		mark();
		tabla.put(key, idComponente);
		for(int i=1; i<= arcos.size(); i++)
		{
			Vertex<K, V> actual;
			try 
			{
				actual = (Vertex<K, V>) arcos.getElement(i).getDestination();
				if(!actual.getMark())
				{
					actual.getSCC(tabla, idComponente);
				}
			} 
			catch (PosException | VacioException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	public ILista<Edge<K>> mstPrimLazy()
	{
		ILista<Edge<K>> mst= new ArregloDinamico<>(1);
		MinPQ<Float, Edge<K>> cola= new MinPQ<>(1);
		
		addEdgesToMinPQ(cola, this);
		
		while(!cola.isEmpty())
		{
			Edge<K> actual= cola.delMin(). getValue();
			Vertex<K, V> dest= (Vertex<K, V>) actual.getDestination();
			if(!dest.marked)
			{
				try {
					mst.insertElement(actual, mst.size()+1);
				} catch (PosException | NullException e) {
					
					e.printStackTrace();
				}
				addEdgesToMinPQ(cola, dest);
			}
		}
		return mst;
		
	}
	
	private void addEdgesToMinPQ(MinPQ<Float, Edge<K>> cola, Vertex<K, V> inicio)
	{
		inicio.mark();
		
		for(int i=1; i<= inicio.edges().size(); i++)
		{
			Edge<K> actual=null;
			try {
				actual = inicio.edges().getElement(i);
			} catch (PosException | VacioException e) {
				
				e.printStackTrace();
			}
			cola.insert(actual.getWeight(), actual);
		}
	}
	
	 public static class ComparadorXKey implements Comparator<Vertex<String, Landing>>
	 {

		/** Comparador alterno de acuerdo al número de likes
		* @return valor 0 si video1 y video2 tiene los mismos likes.
		 valor negativo si video1 tiene menos likes que video2.
		 valor positivo si video1 tiene más likes que video2. */
		 public int compare(Vertex<String, Landing> vertice1, Vertex<String, Landing> vertice2) 
		 {
			 return (vertice1.getId()).compareToIgnoreCase(vertice2.getId());
		 }

	}
	 
	public ITablaSimbolos<K, NodoTS<Float, Edge<K>>> minPathTree()
	{
		 ITablaSimbolos<K, NodoTS<Float, Edge<K>>> tablaResultado= new TablaHashLinearProbing<>(2);
		 MinPQIndexada<Float, K, Edge<K>> colaIndexada= new MinPQIndexada<>(20);
		 
		 tablaResultado.put(this.key, new NodoTS<>(0f, null));
		 
		 relaxDijkstra(tablaResultado, colaIndexada, this, 0);
		 
		 while(!colaIndexada.isEmpty())
		 {
			 NodoTS<Float, Edge<K>> actual= colaIndexada.delMin();
			 Edge<K> arcoActual= actual.getValue();
			 float pesoActual= actual.getKey();
			 relaxDijkstra(tablaResultado, colaIndexada, (Vertex<K, V>) arcoActual.getDestination(), pesoActual);
		 }
		 
		 return tablaResultado;
	}
	
	public void relaxDijkstra(ITablaSimbolos<K, NodoTS<Float, Edge<K>>> tablaResultado, MinPQIndexada<Float, K, Edge<K>> colaIndexada, Vertex<K, V> actual, float pesoAcumulado)
	{
		actual.mark();
		for(int i=1; i<=actual.edges().size(); i++)
		{
			Edge<K> arcoActual;
			try 
			{
				arcoActual = actual.edges().getElement(i);
				Vertex<K, V> destino= (Vertex<K, V>) arcoActual.getDestination();
				float peso= arcoActual.getWeight();
				if(!destino.getMark())
				{
					NodoTS<Float, Edge<K>>llegadaDestino= tablaResultado.get(destino.getId());
					
					if(llegadaDestino== null)
					{
						tablaResultado.put(destino.getId(), new NodoTS<>(pesoAcumulado + peso, arcoActual));
						colaIndexada.insert(peso+ pesoAcumulado, destino.getId(), arcoActual);
						
					}
					else if(llegadaDestino.getKey()>(pesoAcumulado + peso))
					{
						llegadaDestino.setKey(pesoAcumulado + peso);
						llegadaDestino.setValue(arcoActual);
						colaIndexada.changePriority(destino.getId(), pesoAcumulado + peso, arcoActual);
						
					}
				}
			} 
			catch (PosException | VacioException e) 
			{
				e.printStackTrace();
			}
			
		}
	}
	
	
	
}
