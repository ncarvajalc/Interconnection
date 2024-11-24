package model.data_structures;

public class Edge<K extends Comparable<K>> implements Comparable<Edge<K>>
{
	private final K source;
	private final K destination;
	private float weight;
	
	public Edge(K source, K destination, float weight)
	{
		this.source= source;
		this.destination= destination;
		this.weight= weight;
	}
	
	public K getSource()
	{
		return source;
	}
	
	public K getDestination()
	{
		return destination;
	}
	
	public float weight()
	{
		return weight;
	}
	
	public void setWeight(float weight)
	{
		this.weight= weight;
	}
	
	public float getWeight()
	{
		return weight;
	}

	@Override
	public int compareTo(Edge o) 
	{
		return 0;
	}
}

