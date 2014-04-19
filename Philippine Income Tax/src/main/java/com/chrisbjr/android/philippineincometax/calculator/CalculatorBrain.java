package com.chrisbjr.android.philippineincometax.calculator;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CalculatorBrain implements Serializable {

    /**
     * Version number associated with the serialization class
     */
    private static final long serialVersionUID = -4965616433338973442L;

    /**
     * List containing the operand involved in an operation
     */
    private List<Double> operandStack;

    /**
     * List containing the operations waiting to be resolved
     */
    private List<String> operationStack;


    /**
     * Return the list containing the operands involved in an operation
     *
     * @return The operand stack
     */
    public List<Double> getOperandStack() {
        return operandStack;
    }

    /**
     * Sets the operand stack involved in an operation
     *
     * @return The operand stack
     */
    public void setOperandStack(List<Double> operandStack) {
        this.operandStack = operandStack;
    }


    /**
     * Return the list containing the operations in the stack
     *
     * @return The operation stack
     */
    public List<String> getOperationStack() {
        return operationStack;
    }

    /**
     * Sets the operations stack to be resolved
     *
     * @return The operand stack
     */
    public void setOperationStack(List<String> operationStack) {
        this.operationStack = operationStack;
    }

    /**
     * Return a new instance of a CalculatorBrain
     *
     * @return A new calculator brain
     */
    public CalculatorBrain() {
        operandStack = new ArrayList<Double>();
        operationStack = new ArrayList<String>();
    }

    /**
     * Inserts a new number to the operand stack
     *
     * @param number the number to insert onto the stack
     */
    public void addOperand(double number) {
        operandStack.add(number);
    }

    /**
     * Inserts a new operation to the operation stack
     *
     * @param operation the operation to insert onto the stack
     */
    public void addOperation(String operation) {
        if (alreadyAnOperationWaiting()) {
            addOperand(performOperation());
        }
        operationStack.add(operation);
    }

    /**
     * Performs the last operation in the operation stack
     *
     * @return the operation result
     */
    public double performOperation() {
        String operation = popOperation();
        double result = 0;
        if (operation.equals("+")) {
            result = add(popOperand(), popOperand());
        } else if (operation.equals("-")) {
            result = subtract(popOperand(), popOperand());
        } else if (operation.equals("/")) {
            result = divide(popOperand(), popOperand());
        } else if (operation.equals("*")) {
            result = multiply(popOperand(), popOperand());
        } else {
            result = popOperand();
        }
        return result;
    }


    /**
     * Return the sum of the two numbers received as parameters
     *
     * @param firstOperand  the first operand in the operation
     * @param secondOperand the second operand in the operation
     * @return The sum result
     */
    private double add(double firstOperand, double secondOperand) {
        return firstOperand + secondOperand;
    }

    /**
     * Return the subtraction of the two numbers received as parameters
     *
     * @param firstOperand  the first operand in the operation
     * @param secondOperand the second operand in the operation
     * @return The subtraction result
     */
    private double subtract(double firstOperand, double secondOperand) {
        return firstOperand - secondOperand;
    }

    /**
     * Return the multiplication of the two numbers received as parameters
     *
     * @param firstOperand  the first operand in the operation
     * @param secondOperand the second operand in the operation
     * @return The multiplication result
     */
    private double multiply(double firstOperand, double secondOperand) {
        return firstOperand * secondOperand;
    }

    /**
     * Return the division of the two numbers received as parameters
     *
     * @param firstOperand  the first operand in the division
     * @param secondOperand the second operand in the division
     * @return The division result
     */
    private double divide(double firstOperand, double secondOperand) {
        if (secondOperand == 0) {
            return 0;
        } else {
            return firstOperand / secondOperand;
        }
    }

    /**
     * Return the last operation in the stack
     *
     * @return The last operation in the stack
     */
    private String popOperation() {
        String operation = "";
        try {
            operation = operationStack.get(0);
            operationStack.remove(0);
        } catch (Exception e) {
            operation = "";
            resetBrain();
        }
        return operation;
    }

    /**
     * Return the last operand in the stack
     *
     * @return The last operand in the stack
     */
    private double popOperand() {
        double operand = 0;
        try {
            operand = (double) operandStack.get(0);
            operandStack.remove(0);
        } catch (Exception e) {
            operand = 0;
            resetBrain();
        }
        return operand;
    }

    /**
     * Return true if there is already an operation in the stack
     *
     * @return true there is already an item in the stack
     */
    private boolean alreadyAnOperationWaiting() {
        return operationStack.size() == 1;
    }

    /**
     * Clears all the stacks in the calculator brain
     */
    private void resetBrain() {
        operandStack.clear();
        operationStack.clear();
    }

    /**
     * Return a byte array for an object
     * (Based on Jon Simon blog post http://www.jondev.net/articles/Android_Serialization_Example_(Java) )
     *
     * @return A byte array with the current object information
     */
    public static byte[] serializeObject(Object o) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(o);
            out.close();

            // Get the bytes of the serialized object
            byte[] buf = bos.toByteArray();

            return buf;
        } catch (IOException ioe) {
            Log.e("serializeObject", "error", ioe);

            return null;
        }
    }

    /**
     * Return the deserialized version of the current byte array
     * (Based on Jon Simon blog post http://www.jondev.net/articles/Android_Serialization_Example_(Java) )
     *
     * @return An instance of the object send to deserialized
     */
    public static Object deserializeObject(byte[] b) {
        try {
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b));
            Object object = in.readObject();
            in.close();

            return object;
        } catch (ClassNotFoundException cnfe) {
            Log.e("deserializeObject", "class not found error", cnfe);

            return null;
        } catch (IOException ioe) {
            Log.e("deserializeObject", "io error", ioe);

            return null;
        }
    }


}