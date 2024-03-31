// User.java - Shayan Saberi-Nikou

// This class represents a user of the system. It stores user details and manages account status.

// Test Case 1: Create a new User account with valid credentials.
// Expected Result: The account is created with an active status, and appropriate details are stored.

// Test Case 2: Delete an existing User account.
// Expected Result: The account's status is set to inactive, and an account deletion message is displayed.

public class User {
    // Attributes to store the username, password, email, and account status
    private String username;
    private String password;
    private String email;
    private boolean isAccountActive;

    // Constructor to initialize a new User object with username, password, and email. Sets the account as active.
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAccountActive = true; // Account is active upon creation
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password. In a real application, consider enforcing strong password policies here.
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email. Could include validation for email format.
    public void setEmail(String email) {
        this.email = email;
    }

    // Check if the account is active
    public boolean isAccountActive() {
        return isAccountActive;
    }

    // Method to simulate account deletion. Sets the account status to inactive.
    public void deleteAccount() {
        this.isAccountActive = false;
        System.out.println("User account has been deleted.");
    }

    // Static method to simulate account creation. Returns a new User object.
    // In a real application, this would involve more checks (e.g., for existing username) and database interaction.
    public static User createAccount(String username, String password, String email) {
        // Here, additional checks for username uniqueness, password complexity, and email format can be implemented.
        return new User(username, password, email);
    }
}

//FilteringTransactions.java - Ritika Singh

//This class represents a user filtering their transaction history by keywords and categories. 

//Test Case 1: Filter Financial Transactions by Keyword
//Expected Result: The application will display each transaction associated to a unique keyword. 

//Test Case 2: Filter Financial Transactions by Category
//Expected Result: The application will display each transaction associated to a category (ex: essential/non-essential).

/******************************************************************************
class FilteringTransactions will read through a file of previous transactions 
and filter them by keywords... further implementation of succint categorization
will be revised for phase 5
*******************************************************************************/
import java.io.*;
import java.util.*;

class FilteringTransactions {
    BufferedReader inputFileReader;
    ArrayList<Element> keys;
    Element[] myArray = new Element[107];
    
    public boolean contains(String keyword) {
        for(int i = 0; i < myArray.length; i++) {
            if(myArray[i] != null && (myArray[i].getKeyword().equals(keyword))) {
                return true;
            }
        }
        return false;
    }
    
    public static void main(String [] args) {
        FilteringTransactions test = new FilteringTransactions("testFiltering.txt");

    }
    
    public FilteringTransactions(String filename) {
        try { // in try/catch block to catch FileNotFoundException
            // from java source code of FileReader(String) 
            // Java Def: public FileReader(String fileName) throws FileNotFoundException
            inputFileReader = new BufferedReader(new FileReader(filename));
            keys = new ArrayList<Element>(); 
            
            if (inputFileReader == null) {
                System.out.println("Error: you must open the file first!");
            }
            else {
                while (readNextRecord());
            } 
            
            //print
            for(int q = 0; q < myArray.length; q++) {
                if(myArray[q] != null) {
                String k = myArray[q].getKeyword();
                System.out.println(k + "\n\n" + myArray[q].print());
                }
            }

        }
        catch (FileNotFoundException e) {
            //e.printStackTrace();
        }
    } 
    
    public boolean readNextRecord() {
        
        try {
            String data = inputFileReader.readLine();
            if (data == null) return false;

            int date = Integer.parseInt(data);
            String price = inputFileReader.readLine();
            String buyer = inputFileReader.readLine();
            int numberOfKeys = Integer.parseInt(inputFileReader.readLine());

            
            Transaction t = new Transaction(date, price, buyer);

            String keyword; //this is a temporary empty string that will hold every keyword
            for (int i = 0; i < numberOfKeys; i++) {
                keyword = inputFileReader.readLine();
                keyword = keyword.trim();
                
                Element creation = new Element();
                creation.set(keyword);
                
                int probe = 1;
                int original_probe = 0;
                
                if(contains(keyword) == false) {
                    creation.addTransaction(t);
                    if(myArray[creation.getSum()] == null) {
                        myArray[creation.getSum()] = creation;
                    }
                    else {
                        
                        while(myArray[creation.getSum()] != null) {
                            creation.setSum((creation.getSum() + (probe*probe) - (original_probe*original_probe)) % 107);
                            probe++;
                            original_probe++;
                        }
                        myArray[creation.getSum()] = creation;
                    }
                }
                else { 
                    for(int k = 0; k < myArray.length; k++) {
                        if(myArray[k] != null && (myArray[k].getKeyword().equals(keyword))) {
                            myArray[k].addTransaction(t);
                        }
                    }
                }

            }
            
            inputFileReader.readLine();
            
        }
        catch (IOException e) {
            System.out.printf("%s\n", e);
        }
        return true;
    }

}

class Transaction {
    private int date;
    private String price;
    private String buyer;
    
    public Transaction(int d, String p, String b) {
        date = d;
        price = p;
        buyer = b;
    }
    
    @Override
    public String toString() {
        return String.format("\t %d | %s | %s \n", date, price, buyer);
    }
}

class TransactionsArray {
    protected MyLinkedList<Transaction> transactions;
    public TransactionsArray() {
        transactions = new MyLinkedList<Transaction>();
    }
    
    public boolean addTransactionsArray(Transaction t) {
        transactions.add(t);
        return true;
    }
    
    public void print() {
        System.out.printf("%s\n", transactions);
    }
}

class Purchase {
    String key;
    
    public Purchase(String k) {
        key = k;
    }
    
    @Override
    public String toString() {
        return String.format("\t %s | \n", key);
    }
}

class Element {
    private String keyword;
    private MyLinkedList<Transaction> next = new MyLinkedList<Transaction>();
    public Element() { }
    private int sum;
    
    public Element(String k) {
        keyword = k;
        
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    public void set(String keyTerm) {
        keyword = keyTerm;
        sum = 0;
        for(int z = 0; z < keyTerm.length(); z++) {
            sum += Character.getNumericValue(keyTerm.charAt(z));
        }
        sum = sum % 107;
        
    }
    
    public int getSum() {
        return sum;
    }
    
    public void setSum(int s) {
        sum = s;
    }
    
    public void addTransaction(Transaction t) {
        next.add(t);
    }
    
    public String print() {
        return next.toString();
    }
    
}

class MyLinkedList<E> extends MyAbstractList<E> {
    private Node<E> head, tail;

    /** Create a default list */
    public MyLinkedList() { }

    /** Create a list from an array of objects */
    public MyLinkedList(E[] objects) {
        super(objects);
    }

    /** Return the head element in the list */
    public E getFirst() {           /* O(1) */
        if (size == 0) { 
            return null;
        }
        else {
            return head.element;
        }
    }

    /** Return the last element in the list */
    public E getLast() {            /* O(1) */ 
        if (size == 0) {
            return null;
        }
        else {
            return tail.element;
        }
    }

    /** Add an element to the beginning of the list */
    public void addFirst(E e) {         /* O(1) */
        Node<E> newNode = new Node<>(e); // Create a new node
        newNode.next = head; // link the new node with the head
        head = newNode; // head points to the new node
        size++; // Increase list size

        if (tail == null) // The new node is the only node in list
            tail = head;
    }

    /** Add an element to the end of the list */
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e); // Create a new node for e

        if (tail == null) {
            head = tail = newNode; // The only node in list
        }
        else {
            tail.next = newNode; // Link the new node with the last node
            tail = tail.next; // tail now points to the last node
        }

        size++; // Increase size
    }

    @Override /** Add a new element at the specified index
                * in this list. The index of the head element is 0 */
    public void add(int index, E e) {
    // Implemented in Section 24.4.3.3, so omitted here
        if (index == 0) addFirst(e); // Insert first
        else if (index >= size) addLast(e); // Insert last
        else { // Insert in the middle
            Node < E > current = head;
            for (int i = 1; i < index; i++)
              current = current.next;
            
            Node < E > temp = current.next;
            current.next = new Node < E > (e);
            (current.next).next = temp;
            size++;
      }
    }  

    /** Remove the head node and
    * return the object that is contained in the removed node. */
    public E removeFirst() {
    // Implemented in Section 24.4.3.4, so omitted here
        if (size == 0) return null; // Nothing to delete
        else {
            Node<E> temp = head; // Keep the first node temporarily
            head = head.next; // Move head to point to next node
            size--; // Reduce size by 1
            if (head == null) tail = null; // List becomes empty
            return temp.element; // Return the deleted element
        }
    }

    /** Remove the last node and
    * return the object that is contained in the removed node. */
    public E removeLast() {
    // Implemented in Section 24.4.3.5, so omitted here
        if (size == 0) return null; // Nothing to remove
        else if (size == 1) { // Only one element in the list
            Node<E> temp = head;
            head = tail = null; // list becomes empty
            size = 0;
            return temp.element;
        }
        else {
            Node<E> current = head;

            for (int i = 0; i < size - 2; i++)
                current = current.next;

            Node<E> temp = tail;
            tail = current;
            tail.next = null;
            size--;
            return temp.element;
    }
    }

    @Override /** Remove the element at the specified position in this
            * list. Return the element that was removed from the list. */
    public E remove(int index) {
    // Implemented earlier in Section 24.4.3.6, so omitted here
        if (index < 0 || index >= size) return null; // Out of range
        else if (index == 0) return removeFirst(); // Remove first
        else if (index == size - 1) return removeLast(); // Remove last
        else {
            Node<E> previous = head;

            for (int i = 1; i < index; i++) {
                previous = previous.next;
            }

            Node<E> current = previous.next;
            previous.next = current.next;
            size--;
            return current.element;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");

        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            result.append(current.element);
            current = current.next;
            if (current != null) {
                result.append(""); // Separate two elements with a comma
            }
            else {
                result.append(""); // Insert the closing ] in the string
            }
        }

        return result.toString();
    }

    @Override /** Clear the list */
    public void clear() {
        size = 0;
        head = tail = null;
    }

    @Override /** Return true if this list contains the element e */
    public boolean contains(E e) {
        System.out.println("Implementation left as an exercise");
        return true;
    }

    @Override /** Return the element at the specified index */
    public E get(int index) {
        System.out.println("Implementation left as an exercise");
        return null;
    }

    @Override /** Return the index of the head matching element
            * in this list. Return -1 if no match. */
    public int indexOf(E e) {
        System.out.println("Implementation left as an exercise");
        return 0;
    }

    @Override /** Return the index of the last matching element
    * in this list. Return -1 if no match. */
    public int lastIndexOf(E e) {
        System.out.println("Implementation left as an exercise");
        return 0;
    }

    @Override /** Replace the element at the specified position
        * in this list with the specified element. */
    public E set(int index, E e) {
        System.out.println("Implementation left as an exercise");
        return null;
    }

    @Override /** Override iterator() defined in Iterable */
    public java.util.Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator
        implements java.util.Iterator<E> {
        private Node<E> current = head; // Current index

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public E next() {
            E e = current.element;
            current = current.next;
            return e;
        }

        @Override
        public void remove() {
            System.out.println("Implementation left as an exercise");
        }
    }

    // This class is only used in LinkedList, so it is private.
    // This class does not need to access any
    // instance members of LinkedList, so it is defined static.
    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element) {
            this.element = element;
        }
    }
 }

abstract class MyAbstractList<E> implements MyList<E> {
    protected int size = 0; // The size of the list

    /** Create a default list */
    protected MyAbstractList() { }

    /** Create a list from an array of objects */
    protected MyAbstractList(E[] objects) {
        for (int i = 0; i < objects.length; i++)
        add(objects[i]);
    }

    @Override /** Add a new element at the end of this list */
    public void add(E e) {
        add(size, e);
    }

    @Override /** Return true if this list doesn't contain any elements */
    public boolean isEmpty() {
        return size == 0;
    }

    @Override /** Return the number of elements in this list */
    public int size() {
        return size;
    }

    @Override /** Remove the first occurrence of the element e
                * from this list. Shift any subsequent elements to the left.
                * Return true if the element is removed. */
    public boolean remove(E e) {
        if (indexOf(e) >= 0) {
            remove(indexOf(e));
            return true;
        }
        else
        return false;
    }
}

interface MyList<E> extends Iterable<E> {
    public void add(E e);
    public void add(int index, E e);
    public void clear();
    public boolean contains(E e);
    public E get(int index);
    public int indexOf(E e);
    public boolean isEmpty();
    public int lastIndexOf(E e);
    public boolean remove(E e);
    public E remove(int index);
    public Object set(int index, E e);
    public int size();
}
