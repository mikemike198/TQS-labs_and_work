package ua;

import java.util.Stack;
import java.lang.IllegalStateException;

public class TQS_Stack<T> {
    Stack<T> stack = new Stack<T>();
    Boolean bounded = false;
    int max;

    public boolean push(T element) throws IllegalStateException{
        try{
            if (!bounded){
                stack.push(element);
            } else {
                if (stack.size() + 1 > max){
                    throw new IllegalStateException();
                } else {
                    stack.push(element);
                }
            }
            return true;
        } catch(ArrayStoreException e){
            return false;
        } catch (IllegalStateException f) {
            throw f;
        }
    }

    public boolean  isEmpty() {
        return stack.isEmpty();
    }

    public T pop() {
        // try {
        //     return stack.pop();
        // } catch (NoSuchElementException e) {
        //     return null;
        // }
        return stack.pop();
    }

    public T peek() {
        return stack.peek();
    }

    public int size() {
        return stack.size();
    }

    public void turnBouded(int size) {
        this.bounded = true;
        this.max = size;
    }

    

}