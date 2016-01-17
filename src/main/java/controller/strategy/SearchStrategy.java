package controller.strategy;

import model.entityDB.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class SearchStrategy<T extends Object> {

    protected abstract List<? extends AbstractEntity> search(T factor);

    protected abstract class Node<T extends Object> {

        protected T acorn;
        private Node parent;
        private List<Node> subTree = new ArrayList<>();

        protected Node(T acorn) { this.acorn = acorn; }

        protected void setParent(Node parent) { this.parent = parent; }
        protected Node getParent() { return this.parent; }

        private void attach(Node son) {
            subTree.add(son);
            son.setParent(this);
        }
        protected void attach(Node... sons) { for (Node n : sons) attach(n); }

        public abstract List climbUp();

        public T getAcorn() {
            return acorn;
        }
    }
}
