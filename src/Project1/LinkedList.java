package Project1;
/**
 * @author Prabhdeep Singh
 * @project 1
 * @class CMSC 341 M-W 5:30-6:45pm
 * @section 3
 * @email CW28656@umbc.edu
 */

import java.lang.reflect.Array;

/**
 * The minimal linked list is used solely
 * for this project. It brings the absolute
 * minimal requirements for a functioning
 * LinkedList.
 * The list utilizes a singly-linked
 * Node system, which will traverse
 * from the head to the tail.
 *
 * @param <E>   The data type for this
 *              LinkedList
 *              implementation.
 */

public class LinkedList<E> {

    private int size;

    private Node<E> head;
    private Node<E> tail;

    /**
     * Adds the a new Node to the list
     * based off the data value. This will
     * attempt to add the element to the tail,
     * if possible. Otherwise, the list would be
     * empty and the head would be set to the
     * new node.
     *
     * @param data  The value for this node to
     *              hold.
     * @return      true if the node
     *              was successfully added. This
     *              cannot return false, but
     *              rather an exception would
     *              be thrown.
     */

    public boolean add(final E data) {
        final Node<E> node = new Node<>(data); // creates a local Node variable, node, with the E type data 
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
        return true;
    }

    /**
     * Returns the data elements as an array.
     * The type T is used to create a
     * new array. 
     * It is not necessary for the list
     * to have a notable length. As long as the
     * array is not null, the size will
     * be modified accordingly.
     *
     * @param array The array who's type will be
     *              copied and returned.
     * @param <T>   The type that will be used to
     *              create a pseudo-generic array.
     * @return      The populated list of type
     *              T, with the elements
     *              of this LinkedList
     *              implementation.
     */

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] array) {
        // Creates a new instance of an array with its type and size
        if (array.length < size) {
            array = (T[]) Array.newInstance(array.getClass().getComponentType(), size);
        }
        int i = 0;
        Object[] result = array;
        for (Node<E> node = head; node != null; node = node.next){
            result[i++] = node.data;
        }

        if (array.length > size){
            array[size] = null;
        }

        return array;
    }

    /**
     * The Node wrapper for holding
     * data values of type E and for
     * the linked implementation through the
     * next value.
     *
     * @param <E>   The data type, specified
     *              in the 
     *              LinkedList
     *              creation.
     */

    private class Node<E> {

        private E data;
        private Node<E> next;

        /**
         * Constructs a new Node to hold
         * the specified data values.
         *
         * @param data  The value to hold.
         */

        public Node(final E data) {
            this.data = data;
        }

    }

}
