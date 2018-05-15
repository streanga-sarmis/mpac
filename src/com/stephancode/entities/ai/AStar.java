package com.stephancode.entities.ai;

import java.util.LinkedList;

import com.stephancode.levelMachine.LevelManager;

public class AStar {

	public LinkedList<ANode> getPathNodes(ANode start, ANode finish){
		LinkedList<ANode> opened = new LinkedList<ANode>();
		LinkedList<ANode> closed = new LinkedList<ANode>();
		
		opened.add(start);
		start.gCost = 0;
		start.hCost = heuristic(start, finish);
		
		ANode current = start;
		
		while(!opened.isEmpty()){
			if(opened.size() > 1){
				ANode min = opened.getFirst();
			
				for(int i = 0; i < opened.size(); i++){
					if(opened.get(i).gCost < min.gCost) min = opened.get(i);
				}
				current = min;
			}
			
			if(contains(closed, current)){
				System.out.println(current.x + ", " + current.y + ", " + current.neighbours.size());
				break; // don't let a closed node be processed
			}

			current.generateNeighbours(current.x, current.y, LevelManager.getCurrentLevel().getTiles());
			opened.remove(current);
			closed.add(current);
			
			if(current.equals(finish)){
				opened.clear();
				closed.clear();
				return makeList(current);
			}
			
			for(int i = 0; i < current.neighbours.size(); i++){
				ANode neigh = current.neighbours.get(i);
				neigh.fCost = 1;
				neigh.gCost = heuristic(neigh, finish);
				neigh.parent = current;
				
				if(!contains(closed, neigh) && !contains(opened, neigh) 
					&& !neigh.solid && !LevelManager.getCurrentLevel().mobOnTile(neigh.x, neigh.y))
					opened.add(neigh);
			}

		}
		
		opened.clear();
		closed.clear();
		
		return null;
	}
	
	private boolean contains(LinkedList<ANode> a, ANode node){
		for(int i = 0; i < a.size(); i++){
			if(a.get(i).equals(node)) return true;
		}
		return false;
	}
	
	private LinkedList<ANode> makeList(ANode current){
		LinkedList<ANode> path = new LinkedList<ANode>();
		path.add(current);
		while(current.parent != null){
			path.add(current.parent);
			current = current.parent;
		}
		
		return path;
	}
	
	private int heuristic(ANode a, ANode b){
		int xd = a.x - b.x;
		int yd = a.y - b.y;
		return (int)Math.sqrt(xd * xd + yd * yd);
	}
}
