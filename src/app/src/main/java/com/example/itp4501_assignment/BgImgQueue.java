package com.example.itp4501_assignment;

import android.widget.ImageView;

import java.util.LinkedList;
import java.util.Queue;

public class BgImgQueue {
    private Queue<ImageView> queue = new LinkedList<>();

    public void enQueue(ImageView element) {
        queue.add(element);
    }

    public ImageView deQueue() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public void clear() {
        queue.clear();
    }
}
