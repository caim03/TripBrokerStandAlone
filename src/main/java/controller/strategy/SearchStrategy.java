package controller.strategy;

import model.entityDB.AbstractEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SearchStrategy<T extends Object> {

    public abstract List<? extends Object> search(T factor);

    public abstract class Node<T extends Object, N extends Number> {

        protected T acorn;
        protected Node parent;
        protected Map<Node, N> subTree = new HashMap<>();

        protected Node(T acorn) { this.acorn = acorn; }

        protected void setParent(Node parent) { this.parent = parent; }
        protected Node getParent() { return this.parent; }

        protected abstract void attach(Node son);

        abstract N getWeight() ;
        public String weightToString() { return getWeight().toString(); }

        protected void attach(Node... sons) { for (Node n : sons) attach(n); }

        public abstract List climbUp();

        public T getAcorn() {
            return acorn;
        }
    }
}
