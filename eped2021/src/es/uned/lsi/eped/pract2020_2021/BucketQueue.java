package es.uned.lsi.eped.pract2020_2021;

import es.uned.lsi.eped.DataStructures.*;

/*Representa una cola con prioridad implementada mediante una secuencia de SamePriorityQueue*/
public class BucketQueue<E> extends Collection<E> implements PriorityQueueIF<E> {
 
	private List<SamePriorityQueue<E>> list;
  
  /* Clase privada que implementa un iterador para la *
   * cola con prioridad basada en secuencia.          */
  public class PriorityQueueIterator implements IteratorIF<E> {

	Queue<E> auxQueue;
    IteratorIF<E> itAuxQueue;

    /*Constructor por defecto*/
    protected PriorityQueueIterator(){
    	auxQueue = new Queue<>();
    	IteratorIF<SamePriorityQueue<E>> itList = list.iterator();
    	while(itList.hasNext()) {
    		itAuxQueue = itList.getNext().iterator();
    		while (itAuxQueue.hasNext()) {
    			auxQueue.enqueue(itAuxQueue.getNext());
    		}
    	}
    	itAuxQueue = auxQueue.iterator();
    }

    /*Devuelve el siguiente elemento de la iteración*/
    public E getNext() {
    	E elem = itAuxQueue.getNext();
    	return elem;
    }
    
    /*Comprueba si queda algún elemento por iterar*/
    public boolean hasNext() { 
    	return itAuxQueue.hasNext();
    }
 
    /*Reinicia el iterador a la posición inicial*/
    public void reset() { 
    	itAuxQueue.reset();
    }
  }


  /* OPERACIONES PROPIAS DE ESTA CLASE */

  /*constructor por defecto: crea cola con prioridad vacía
   */
  BucketQueue(){ 
	  super();
	  list = new List<>();
  }

  /* OPERACIONES PROPIAS DE LA INTERFAZ PRIORITYQUEUEIF */

  /*Devuelve el elemento más prioritario de la cola y que
   *llegó en primer lugar
   * @Pre !isEmpty()
   */
  public E getFirst() { 
	  return list.get(1).getFirst();
  }
 
  /*Añade un elemento a la cola de acuerdo a su prioridad
   *y su orden de llegada
   */
  public void enqueue(E elem, int prior) {
	  boolean insert = false;
	  if (list.isEmpty()) {
		  SamePriorityQueue<E> auxQueueElem = new SamePriorityQueue<>(prior);
		  auxQueueElem.enqueue(elem);
		  list.insert(1, auxQueueElem);
		  insert = true;
	  } else {
		  SamePriorityQueue<E> auxQueueElem = new SamePriorityQueue<>(prior);
		  auxQueueElem.enqueue(elem);
		  IteratorIF<SamePriorityQueue<E>> itList = list.iterator();
		  SamePriorityQueue<E> auxQueueList;
		  int counter = 1;
		  while(itList.hasNext()) {
			  auxQueueList = itList.getNext();
			  if (auxQueueList.compareTo(auxQueueElem)==0 && !insert) {
				  auxQueueList.enqueue(elem);
				  insert = true;
			  } else {
				  if(auxQueueList.compareTo(auxQueueElem)==-1 && !insert) {
					  list.insert(counter, auxQueueElem);
					  insert = true;
				  }
			  }
			  counter++;
		  }
		  if(!insert) {
			  list.insert(list.size()+1, auxQueueElem);
			  insert = true;
		  }
	  }
  }

  /*Elimina el elemento más prioritario y que llegó a la cola
   *en primer lugar
   * @Pre !isEmpty()
   */
  public void dequeue() {
	  list.get(1).dequeue();
	  if (list.get(1).isEmpty()) {
		  list.remove(1);
	  }
  }

  /* OPERACIONES PROPIAS DE LA INTERFAZ SEQUENCEIF */

  /*Devuelve un iterador para la cola*/
  public IteratorIF<E> iterator() {
	  return new PriorityQueueIterator();
  }
 
  /* OPERACIONES PROPIAS DE LA INTERFAZ COLLECTIONIF */

  /*Devuelve el número de elementos de la cola*/
  public int size() {
	  IteratorIF<SamePriorityQueue<E>> itList = list.iterator();
	  size = 0;
	  while(itList.hasNext()) {
		  size += itList.getNext().size();
	  }
	  return size;
  }

  /*Decide si la cola está vacía*/
  public boolean isEmpty() { 
	  boolean emptyness = true;
	  IteratorIF<SamePriorityQueue<E>> itLista = list.iterator();
	  SamePriorityQueue<E> auxQueue;
	  while (itLista.hasNext()) {
		  auxQueue = itLista.getNext();
		  emptyness	= emptyness && auxQueue.isEmpty();
	  }
	  return emptyness;
  }
 
  /*Decide si la cola contiene el elemento dado por parÃ¡metro*/
  public boolean contains(E e) {
	  boolean cont = false;
	  IteratorIF<SamePriorityQueue<E>> it = list.iterator();
	  SamePriorityQueue<E> auxQueue;
	  while(it.hasNext()) {
		  auxQueue = it.getNext();
		  cont = cont || auxQueue.contains(e);
	  }
	  return cont;
  }
 
  /*Elimina todos los elementos de la cola*/
  public void clear() {
	  list.clear();
  }
 
}

