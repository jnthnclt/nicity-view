/*
 * LinLogLayout.java.java
 *
 * Created on 01-02-2010 10:44:42 AM
 *
 * Copyright 2010 Jonathan Colt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package linloglayout;

//Copyright (C) 2005 Andreas Noack
//
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//Lesser General Public License for more details.
//
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA 


import java.util.Map;
import java.util.HashMap;

/**
 * Simple program for computing graph layouts (positions of the nodes of a graph
 * in two- or three-dimensional space) for visualization and knowledge discovery.
 * Reads a graph from a file, computes a layout, writes the layout to a file,
 * and displays the layout in a dialog.
 * The program can be used to identify clusters (groups of densely connected 
 * nodes) in graphs, like groups of friends or collaborators in social networks,
 * related documents in hyperlink structures (e.g. web graphs),
 * cohesive subsystems in software models, etc.
 * With a change of a parameter in the <code>main</code> method,
 * it can also compute classical "nice" (i.e. readable) force-directed layouts.
 * The program is intended as a demo of the use of its core layouter class 
 * <code>MinimizerBarnesHut</code>.  See this class for details about layouts.
 * 
 * @author Andreas Noack (an@informatik.tu-cottbus.de)
 * @version 03.07.2006
 */
public class LinLogLayout {
	
	/**
	 * Returns a symmetric version of the given graph.
	 * A graph is symmetric if and only if for each pair of nodes,
	 * the weight of the edge from the first to the second node
	 * equals the weight of the edge from the second to the first node.
	 * Here the symmetric version is obtained by adding to each edge weight
	 * the weight of the inverse edge.
	 * 
	 * @param graph  possibly unsymmetric graph.
	 * @return symmetric version of the given graph.
	 */
	public static Map<Object,Map<Object,Float>> makeSymmetricGraph(
		Map<Object,Map<Object,Float>> graph
	) {
		Map<Object,Map<Object,Float>> result = new HashMap<Object,Map<Object,Float>>();
		for (Object source : graph.keySet()) {
			for (Object target : graph.get(source).keySet()) {
				float weight = graph.get(source).get(target);
				float revWeight = 0.0f;
				if (graph.get(target) != null && graph.get(target).get(source) != null) {
					revWeight = graph.get(target).get(source);
				}
				if (result.get(source) == null) result.put(source, new HashMap<Object,Float>());
				result.get(source).put(target, weight+revWeight);
				if (result.get(target) == null) result.put(target, new HashMap<Object,Float>());
				result.get(target).put(source, weight+revWeight);
			}
		}
		return result;
	}
	
	/**
	 * Returns a map from each node of the given graph 
	 * to a unique number from 0 to (number of nodes minus 1). 
	 * 
	 * @param graph the graph.
	 * @return map from each node of the given graph 
	 *         to a unique number from 0 to (number of nodes minus 1).
	 */
	public static Map<Object,Integer> makeIds(Map<Object,Map<Object,Float>> graph) {
		Map<Object,Integer> result = new HashMap<Object,Integer>();
		int cnt = 0;
		for (Object node : graph.keySet()) {
			result.put(node, cnt);
			cnt++;
		}
		return result;
	}
	
	/**
	 * Returns, for each node of the given graph,
	 * a random initial position in three-dimensional space. 
	 * 
	 * @param graph the graph.
         * @param _threeD 
         * @return random initial positions in three-dimensional space
	 *         for all nodes of the given graph.
	 */
	public static float[][] makeInitialPositions(Map<Object,Map<Object,Float>> graph,boolean _threeD) {
		float[][] result = new float[graph.size()][3];
		
		if (_threeD) {
			float c = (float)Math.pow(result.length,1d/3d);
			for (int i = 0; i < result.length; i++) {
				float y = (i/c);
				float x = i-(((int)y)*c);
				float z = i-(((int)x)*c);
				
				
				result[i][0] = (x/c) - 0.5f; 
				result[i][1] = (y/c) - 0.5f; 
				result[i][2] = (z/c) - 0.5f; 
			}
		}
		else {
			float w = (float)Math.sqrt(result.length);
			
			for (int i = 0; i < result.length; i++) {
				
				float y = (i/w);
				float x = i-(((int)y)*w);
				
				result[i][0] = (x/w) - 0.5f;
				result[i][1] = (y/w) - 0.5f;
				result[i][2] = 0.0f;// set to 0.0f for 2D layouts,
				
			}
		}
		return result;
	}
	
	/**
	 * Converts the edge weights of the given graph into the adjacency list 
	 * format expected by <code>MinimizerBarnesHut</code>.
	 * 
	 * @param graph    the graph.
	 * @param nodeToId unique ids of the graph nodes.
	 * @return array of adjacency lists for <code>MinimizerBarnesHut</code>.
	 */
	public static float[][] makeAttrWeights
			(Map<Object,Map<Object,Float>> graph, Map<Object,Integer> nodeToId) {
		float[][] result = new float[graph.size()][];
		for (Object source : graph.keySet()) {
			int sourceId = nodeToId.get(source);
			result[sourceId] = new float[graph.get(source).size()];
			int cnt = 0;
			// process all outgoing edges of the node source 
			for (Object target : graph.get(source).keySet()) {
				result[sourceId][cnt] = graph.get(source).get(target);
				cnt++;
			}
		}
		return result;
	}
	
	/**
	 * Converts the edges of the given graph into the adjacency list 
	 * format expected by <code>MinimizerBarnesHut</code>.
	 * 
	 * @param graph    the graph.
	 * @param nodeToId unique ids of the graph nodes.
	 * @return array of adjacency lists for <code>MinimizerBarnesHut</code>.
	 */
	public static int[][] makeAttrIndexes(
		Map<Object,Map<Object,Float>> graph,
		Map<Object,Integer> nodeToId
	) {
		int[][] result = new int[graph.size()][];
		for (Object source : graph.keySet()) {
			int sourceId = nodeToId.get(source);
			result[sourceId] = new int[graph.get(source).size()];
			int cnt = 0;
			// process all outgoing edges of the node source 
			for (Object target : graph.get(source).keySet()) {
				result[sourceId][cnt] = nodeToId.get(target);
				cnt++;
			}
		}
		return result;
	}

	/**
	 * Computes the repulsion weights of the nodes  
	 * for <code>MinimizerBarnesHut</code>.
	 * In the edge repulsion LinLog energy model, the repulsion weight
	 * of each node is its degree, i.e. the sum of the weights of its edges.
	 * 
	 * @param graph    the graph.
	 * @param nodeToId unique ids of the graph nodes.
	 * @return array of repulsion weights for <code>MinimizerBarnesHut</code>.
	 */
	public static float[] makeRepuWeights(Map<Object,Map<Object,Float>> graph, Map<Object,Integer> nodeToId) {
		float[] result = new float[graph.size()];
		for (Object source : graph.keySet()) {
			int sourceId = nodeToId.get(source);
			result[sourceId] = 0.0f;
			for (Float weight : graph.get(source).values()) {
				result[sourceId] += weight;
			}
		}
		return result;
	}

	/**
	 * Converts the array of node positions from <code>MinimizerBarnesHut</code>
	 * into a map from each node to its position.
	 * 
	 * @param positions array of node positions.
	 * @param nodeToId unique ids of the graph nodes.
	 * @return map from each node to its positions.
	 */
	private static void convertPositionsToMap(
		float[][] positions,
		Map<Object,Integer> nodeToId,
		Map<Object,float[]> result
	) {
		for (Object node : nodeToId.keySet()) {
			result.put(node, positions[nodeToId.get(node)]);
		}
	}
	
	/**
	 * Computes a map from each node to the diameter of the circle
	 * that represents the node in the visualization.
	 * Here the square root of the degree (the total weight of the edges)
	 * of the node is used as diameter, thus the area of the circle
	 * is proportional to the degree.  
	 * 
	 * @param graph the graph.
	 * @return map from each node to its diameter in the visualization.
	 */
	private static void computeDiameters(
		Map<Object,Map<Object,Float>> graph,
		Map<Object,Float> result
	) {
		for (Object source : graph.keySet()) {
			float degree = 0.0f;
			for (Float weight : graph.get(source).values()) degree += weight;
			result.put(source, (float)Math.sqrt(degree));
		}
	}

	
	
        /**
         * 
         * @param _
         * @param _graph
         * @param nodeToPosition
         * @param nodeToDiameter
         * @param _attraction
         * @param _repulsion
         * @param _iterations
         * @param _threeD
         */
        public static void layout(
		LinLogProgress _,
		Map<Object,Map<Object,Float>> _graph,
		Map<Object,float[]> nodeToPosition,
		Map<Object,Float> nodeToDiameter,
		double _attraction,double _repulsion,int _iterations,boolean _threeD
	) {
		_graph = makeSymmetricGraph(_graph);
		Map<Object,Integer> nodeToId = makeIds(_graph);
		float[][] positions = makeInitialPositions(_graph,_threeD);
		// see class MinimizerBarnesHut for a description of the parameters
		MinimizerBarnesHut minimizer = new MinimizerBarnesHut(
			makeAttrIndexes(_graph, nodeToId),
			makeAttrWeights(_graph, nodeToId), 
			makeRepuWeights(_graph, nodeToId),
			(float)_attraction,(float)_repulsion, 0.01f, positions,null
		);
		minimizer.minimizeEnergy(_,_iterations);
		
		convertPositionsToMap(positions, nodeToId, nodeToPosition);
		computeDiameters(_graph,nodeToDiameter);
	}



    
        /**
         * 
         * @param _
         * @param _symmetricGraph
         * @param nodeToPosition
         * @param nodeToDiameter
         * @param _attraction
         * @param _repulsion
         * @param _iterations
         * @param _positions
         */
        public static void layout(
		LinLogProgress _,
		Map<Object,Map<Object,Float>> _symmetricGraph,
		Map<Object,float[]> nodeToPosition,
		Map<Object,Float> nodeToDiameter,
		double _attraction,double _repulsion,int _iterations,
        float[][] _positions
	) {
		Map<Object,Integer> nodeToId = makeIds(_symmetricGraph);
		// see class MinimizerBarnesHut for a description of the parameters
		MinimizerBarnesHut minimizer = new MinimizerBarnesHut(
			makeAttrIndexes(_symmetricGraph, nodeToId),
			makeAttrWeights(_symmetricGraph, nodeToId), 
			makeRepuWeights(_symmetricGraph, nodeToId),
			(float)_attraction,(float)_repulsion, 0.01f,
            _positions,null
		);
		minimizer.minimizeEnergy(_,_iterations);
		
		convertPositionsToMap(_positions, nodeToId, nodeToPosition);
		computeDiameters(_symmetricGraph,nodeToDiameter);
	}
}
