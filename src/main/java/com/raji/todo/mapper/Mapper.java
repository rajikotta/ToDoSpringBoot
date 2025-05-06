package com.raji.todo.mapper;

public interface Mapper<A, B> {
    B mapTo(A a);

    A mapFrom(B b);


}
