package com.viridiri.graph;

import java.io.Serializable;
import java.util.*;

public final class Graph implements Cloneable, Serializable {

    private final Map<String, Map<String, Object>> vertices;
    private final Map<String, Map<String, Map<String, Object>>> edges;

    public Graph() {
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
    }

    public void addVertex(String name) {
        vertices.put(name, new HashMap<>());
        edges.put(name, new HashMap<>());
    }

    public void removeVertex(String name) {
        for (String value : getParentVertices(name)) {
            removeEdge(value, name);
        }

        vertices.remove(name);
        edges.remove(name);
    }

    public boolean containsVertex(String name) {
        return vertices.containsKey(name);
    }

    public Set<String> getVertices() {
        return new ImmutableSet<>(vertices.keySet());
    }

    public Object getPropertyValue(String name, String key) {
        if (!containsVertex(name)) {
            throw new IllegalArgumentException(String.format("getPropertyValue: unknown vertex name '%s'", name));
        } else if (!vertices.get(name).containsKey(name)) {
            throw new IllegalArgumentException(String.format("getPropertyValue: unknown property name '%s'", key));
        }

        return vertices.get(name).get(key);
    }

    public void setProperty(String name, String key, Object value) {
        if (!containsVertex(name)) {
            throw new IllegalArgumentException(String.format("setPropertyValue: unknown vertex name '%s'", name));
        }

        vertices.get(name).put(key, value);
    }

    public void removePropertyValue(String name, String key) {
        if (!containsVertex(name)) {
            throw new IllegalArgumentException(String.format("removePropertyValue: unknown vertex name '%s'", name));
        } else if (!vertices.get(name).containsKey(key)) {
            throw new IllegalArgumentException(String.format("removePropertyValue: unknown property name '%s'", key));
        }

        vertices.get(name).remove(key);
    }

    public Set<String> getVertexKeys(String label) {
        return new ImmutableSet<>(vertices.get(label).keySet());
    }

    public void addEdge(String label1, String label2) {
        if (!containsVertex(label1)) {
            throw new IllegalArgumentException(String.format("addEdge: unknown vertex name '%s'", label1));
        } else if (!containsVertex(label2)) {
            throw new IllegalArgumentException(String.format("addEdge: unknown vertex name '%s'", label2));
        }

        edges.get(label1).put(label2, new HashMap<>());
    }

    public void removeEdge(String label1, String label2) {
        if (!containsVertex(label1)) {
            throw new IllegalArgumentException(String.format("removeEdge: unknown vertex name '%s'", label1));
        } else if (!containsVertex(label2)) {
            throw new IllegalArgumentException(String.format("removeEdge: unknown vertex name '%s'", label2));
        }

        edges.get(label1).remove(label2);
    }

    public boolean containsEdge(String label1, String label2) {
        if (!containsVertex(label1)) {
            throw new IllegalArgumentException(String.format("containsEdge: unknown vertex name '%s'", label1));
        } else if (!containsVertex(label2)) {
            throw new IllegalArgumentException(String.format("containsEdge: unknown vertex name '%s'", label2));
        }

        return edges.get(label1).containsKey(label1);
    }

    public Set<String> getAdjVertices(String label) {
        if (!containsVertex(label)) {
            throw new IllegalArgumentException(String.format("getAdjVertices: unknown vertex name '%s'", label));
        }

        return new ImmutableSet<>(edges.get(label).keySet());
    }

    public Object getEdgePropertyValue(String label1, String label2, String key) {
        if (!containsVertex(label1)) {
            throw new IllegalArgumentException(String.format("getEdgePropertyValue: unknown vertex name '%s'", label1));
        } else if (!containsVertex(label2)) {
            throw new IllegalArgumentException(String.format("getEdgePropertyValue: unknown vertex name '%s'", label2));
        } else if (!edges.get(label1).containsKey(label2)) {
            throw new IllegalArgumentException(String.format("getEdgePropertyValue: an edge between vertices %s and %s are not existing", label1, label2));
        } else if (!edges.get(label1).get(label2).containsKey(key)) {
            throw new IllegalArgumentException(String.format("getEdgePropertyValue: unknown property name '%s'", key));
        }

        return edges.get(label1).get(label2).get(key);
    }

    public void setEdgePropertyValue(String label1, String label2, String key, Object value) {
        if (!containsVertex(label1)) {
            throw new IllegalArgumentException(String.format("setEdgePropertyValue: unknown vertex name '%s'", label1));
        } else if (!containsVertex(label2)) {
            throw new IllegalArgumentException(String.format("setEdgePropertyValue: unknown vertex name '%s'", label2));
        } else if (!edges.get(label1).containsKey(label2)) {
            throw new IllegalArgumentException(String.format("setEdgePropertyValue: an edge between vertices %s and %s are not existing", label1, label2));
        }

        edges.get(label1).get(label2).put(key, value);
    }

    public void removeEdgePropertyValue(String label1, String label2, String key) {
        if (!containsVertex(label1)) {
            throw new IllegalArgumentException(String.format("removeEdgePropertyValue: unknown vertex name '%s'", label1));
        } else if (!containsVertex(label2)) {
            throw new IllegalArgumentException(String.format("removeEdgePropertyValue: unknown vertex name '%s'", label2));
        } else if (!edges.get(label1).containsKey(label2)) {
            throw new IllegalArgumentException(String.format("removeEdgePropertyValue: an edge between vertices %s and %s are not existing", label1, label2));
        } else if (!edges.get(label1).get(label2).containsKey(key)) {
            throw new IllegalArgumentException(String.format("removeEdgePropertyValue: unknown property name '%s'", key));
        }

        edges.get(label1).get(label2).remove(key);
    }

    public Set<String> getEdgeProperties(String label1, String label2) {
        if (!containsVertex(label1)) {
            throw new IllegalArgumentException(String.format("getEdgeProperties: unknown vertex name '%s'", label1));
        } else if (!containsVertex(label2)) {
            throw new IllegalArgumentException(String.format("getEdgeProperties: unknown vertex name '%s'", label2));
        }

        return new ImmutableSet<>(edges.get(label1).get(label2).keySet());
    }

    public Set<String> getParentVertices(String label) {
        Set<String> set = new HashSet<>();

        for (String key : edges.keySet()) {
            if (edges.get(key).containsKey(label)) {
                set.add(key);
            }
        }

        return new ImmutableSet<>(set);
    }

    public boolean isVertexLeaf(String label) {
        return !getAdjVertices(label).iterator().hasNext();
    }

    public boolean isVertexRoot(String label) {
        if (!containsVertex(label)) {
            throw new IllegalArgumentException();
        }

        for (String vertex : getVertices()) {
            if (containsEdge(vertex, label)) {
                return false;
            }
        }

        return true;
    }

    public boolean isOriented() {
        for (String vertex : getVertices()) {
            for (String adjVertex : getAdjVertices(vertex)) {
                for (String adjAdjVertex : getAdjVertices(adjVertex)) {
                    if (vertex.equals(adjAdjVertex)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean hasCycles() {
        return GraphCycleService.hasCycles(Graph.this);
    }

    public LinkedList<String> trace(String source, String destination) {
        return GraphTraceService.trace(Graph.this, source, destination);
    }

    public void compress() {
        GraphCompressor.compress(this);
    }

    @Override
    public Object clone() {
        return GraphCopyService.copy(this);
    }

    @Override
    public int hashCode() {
        return this.vertices.hashCode() * this.edges.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return this == obj
                || obj instanceof Graph
                && this.hashCode() == obj.hashCode();
    }
}
