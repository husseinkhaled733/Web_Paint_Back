package com.example.paintbe.Util;

public class Pair<A, B> {

    A first;
    B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public Pair() {
    }


    public A getFirst() {
        return first;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public B getSecond() {
        return second;
    }

    public void setSecond(B second) {
        this.second = second;
    }
}
