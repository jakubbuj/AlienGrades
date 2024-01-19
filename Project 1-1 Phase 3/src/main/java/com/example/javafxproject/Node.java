package com.example.javafxproject;

public class Node {
    // For decision node
    private int attributeIndex;
    private String threshold;
    private Node left;
    private Node right;
    private double varRed;

    // For leaf node
    private double value;

    // Constructor for decision node
    public Node(int attributeIndex, String threshold, Node left, Node right, double varianceReduction) {
        this.attributeIndex = attributeIndex;
        this.threshold = threshold;
        this.left = left;
        this.right = right;
        this.varRed = varianceReduction;
        this.value = Double.NaN;  // Initialize value for decision node (NaN for non-leaf nodes)
    }

    // Constructor for leaf node
    public Node(Double value) {
        this.value = value;
        // Initialize other fields to default values for leaf nodes
        this.attributeIndex = -1;
        this.threshold = null;
        this.left = null;
        this.right = null;
        this.varRed = Double.NaN;
    }

    public String getAttributeName(){
    if (attributeIndex == 0){
        return "Suruna Value";
    } else if (attributeIndex==1) {
        return"Hurni Level";
    } else if (attributeIndex==2) {
        return "Lal Count";
    } else if (attributeIndex==3) {
        return "Volta";
    }else {
        return " attribute ? ";
    }

    }
    // Getters for the fields
    public int getAttributeIndex() {
        return attributeIndex;
    }
    public String getThreshold() {
        return threshold;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public double getVarRed() {
        return varRed;
    }

    public double getValue() {
        return value;
    }

    // Setters for the fields (if needed)
    public void setAttributeIndex(int featureIndex) {
        this.attributeIndex = featureIndex;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setVarRed(double varRed) {
        this.varRed = varRed;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
