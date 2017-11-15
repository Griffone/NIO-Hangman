/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import java.util.function.Consumer;

/**
 * A combination of a Consumer<T> with the <T> parameter for storage in the blocking queue.
 *
 * @param <T> The type of the argument for consumer
 * @author Griffone
 */
public class Action<T> {
    
    public final Consumer<T> consumer;
    public final T argument;
    
    public Action(Consumer consumer, T argument) {
        this.consumer = consumer;
        this.argument = argument;
    }
}
