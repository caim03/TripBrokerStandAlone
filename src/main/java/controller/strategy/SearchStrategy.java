package controller.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for Strategy pattern. Subclasses implement an algorithm from the search algorithms family.
 * This class only defines the "search" abstract method; arguments for this method are specified by the
 * implementing subclass via diamond operator.
 * In order to achieve its results, the Strategy class relies on a tree structure, whose nodes are the utility
 * Node inner class.
 * @param <T>: Diamond operator, represents the search method argument class
 */

public abstract class SearchStrategy<T> {

    /**
     * Algorithm interface method.
     * @param factor T: algorithm argument for actual research. The object is passed to a Node instance
     *                  to build up a research tree.
     * @return List: a List of object, containing the search results. Objects class is specified by
     *               Strategy subclasses.
     */
    public abstract List<?> search(T factor);

    /**
     * Utility inner class for building a search tree.SearchStrategy subclasses implements abstract methods
     * according to their needs.
     * @param <T> search subject.
     * @param <N> a Number instance representing the weight value of an arc between node and son.
     */
    public abstract class Node<T, N extends Number> {

        protected T acorn; //Composition attribute; has its role during tree creation
        protected Node parent; //Node parent; root has @null parent
        protected N weight; //weight cache
        protected Map<Node, N> subTree = new HashMap<>();
        //Map of children, indexed by actual Node child and containing a weight value

        /**
         * Main constructor.
         * @param acorn T: composed attribute
         */
        protected Node(T acorn) { this.acorn = acorn; }

        /**
         * acorn attribute Getter.
         * @return T: this Node composite object
         */
        public T getAcorn() { return acorn; }

        /**
         * Getter/Setter for parent attribute.
         * @param parent Node: this Node parent.
         */
        protected void setParent(Node parent) { this.parent = parent; }
        protected Node getParent() { return this.parent; }

        /**
         * Abstract method for child-parent bond creation.
         * @param son Node: this Node new child to be attached
         */
        protected abstract void attach(Node son);

        /**
         * weight attribute Getter. First request triggers weight calculation;
         * later requests are satisfied giving back the cached value.
         * @return N: weight value
         */
        N getWeight() {
            if (weight == null) weight = retrieveWeight();
            return weight;
        }

        /**
         * Weight retrieving routine. It varies according to the Node implementation.
         * @return N: freshly-calculated weight value.
         */
        abstract N retrieveWeight() ;

        /**
         * @return String: a message associated to weight value
         */
        public String weightToString() { return getWeight().toString(); }

        protected void attach(Node... sons) { for (Node n : sons) attach(n); }

        /**
         * Leaf-to-root Nodes recollection.
         * @return List: a List containing something, collected by crossed Nodes.
         */
        public abstract List climbUp();
    }
}
