package es.uned.lsi.eped.pract2020_2021;

import es.uned.lsi.eped.DataStructures.*;
import es.uned.lsi.eped.DataStructures.BSTreeIF.IteratorModes;
import es.uned.lsi.eped.DataStructures.BSTreeIF.Order;

/*Representa una cola con prioridad implementada mediante un �rbol binario de b�squeda de SamePriorityQueue*/
public class BSTPriorityQueue<E> extends Collection<E> implements PriorityQueueIF<E> {
 
  private BSTree<SamePriorityQueue<E>> tree;
  
  /* Clase privada que implementa un iterador para la *
   * cola con prioridad basada en secuencia.          */
  public class PriorityQueueIterator implements IteratorIF<E> {

    Queue<E> auxQueue;
    IteratorIF<E> itAuxQueue;;

    /*Constructor por defecto*/
    protected PriorityQueueIterator(){
    	IteratorIF<SamePriorityQueue<E>> itTree = tree.iterator(IteratorModes.REVERSEORDER);
    	auxQueue = new Queue<>();
    	while(itTree.hasNext()) {
    		itAuxQueue = itTree.getNext().iterator();
    		while(itAuxQueue.hasNext()) {
    			auxQueue.enqueue(itAuxQueue.getNext());
    		}
    	}
    	itAuxQueue = auxQueue.iterator();
    }

    /*Devuelve el siguiente elemento de la iteraci�n*/
    public E getNext() {
    	E elem = itAuxQueue.getNext();
    	return elem;
    }
    
    /*Comprueba si queda alg�n elemento por iterar*/
    public boolean hasNext() {
    	return itAuxQueue.hasNext();
    }
 
    /*Reinicia el iterador a la posición inicial*/
    public void reset() {
    	itAuxQueue.reset();
    }
  }



  /* OPERACIONES PROPIAS DE ESTA CLASE */

  /*constructor por defecto: crea cola con prioridad vac�a
   */
  BSTPriorityQueue(){ 
	  super();
	  tree = new BSTree<>(Order.ASCENDING);
  }

  /* OPERACIONES PROPIAS DE LA INTERFAZ PRIORITYQUEUEIF */

  /*Devuelve el elemento m�s prioritario de la cola y que
   *lleg� en primer lugar
   * @Pre !isEmpty()
   */
  public E getFirst() { 
	  IteratorIF<SamePriorityQueue<E>> itTree = tree.iterator(IteratorModes.REVERSEORDER);
	  return itTree.getNext().getFirst();
  }
 
  /*A�ade un elemento a la cola de acuerdo a su prioridad
   *y su orden de llegada
   */
  public void enqueue(E elem, int prior) {
	  SamePriorityQueue<E> auxQueueOutside = new SamePriorityQueue<>(prior);
	  IteratorIF<SamePriorityQueue<E>> itTree = tree.iterator(IteratorModes.REVERSEORDER);
	  auxQueueOutside.enqueue(elem);
	  boolean added = false;
	  if(tree.isEmpty()) {
		  tree.add(auxQueueOutside);
	  } else {
		  while (itTree.hasNext() && !added) {
			  SamePriorityQueue<E> AuxQueueInside = itTree.getNext();
			  if(auxQueueOutside.compareTo(AuxQueueInside)==0){
				  AuxQueueInside.enqueue(elem);
				  added = true;  
			  }
		  }
	  }
	  if(!added) {
		  tree.add(auxQueueOutside);
	  }
  }

  /*Elimina el elemento m�s prioritario y que lleg� a la cola
   *en primer lugar
   * @Pre !isEmpty()
   */
  public void dequeue() { 
	  if (!tree.isEmpty()) {
		  IteratorIF<SamePriorityQueue<E>> itTree = tree.iterator(IteratorModes.REVERSEORDER);
		  SamePriorityQueue<E> auxQueue = itTree.getNext();
		  auxQueue.dequeue();
		  if(auxQueue.isEmpty()) {
			  tree.remove(auxQueue);
		  }
	  }
  }

  /* OPERACIONES PROPIAS DE LA INTERFAZ SEQUENCEIF */

  /*Devuelve un iterador para la cola*/
  public IteratorIF<E> iterator() { 
	  return new PriorityQueueIterator();
  }
 
  /* OPERACIONES PROPIAS DE LA INTERFAZ COLLECTIONIF */

  /*Devuelve el n�mero de elementos de la cola*/
  public int size() {
	  IteratorIF<SamePriorityQueue<E>> itTree = tree.iterator(IteratorModes.REVERSEORDER);
	  size = 0;
	  while(itTree.hasNext()) {
		  SamePriorityQueue<E> auxQueue = itTree.getNext();
		  size += auxQueue.size();
	  }
	  return size;
  }

  /*Decide si la cola est� vac�a*/
  public boolean isEmpty() {
	  boolean emptyness = true;
	  IteratorIF<SamePriorityQueue<E>> itTree = tree.iterator(IteratorModes.REVERSEORDER);
	  while(itTree.hasNext()) {
		  SamePriorityQueue<E> auxQueue = itTree.getNext();
		  emptyness = emptyness && auxQueue.isEmpty();
	  }
	  return emptyness;
  }
 
  /*Decide si la cola contiene el elemento dado por parámetro*/
  public boolean contains(E e) {
	  boolean isThere = false;
	  IteratorIF<SamePriorityQueue<E>> itTree = tree.iterator(IteratorModes.REVERSEORDER);
	  while(itTree.hasNext()) {
		  isThere = isThere || itTree.getNext().contains(e);
	  }
	  return isThere;
  }
 
  /*Elimina todos los elementos de la cola*/
  public void clear() { 
	  tree.clear();
  }
 
}

