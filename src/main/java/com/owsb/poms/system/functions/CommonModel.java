package com.owsb.poms.system.functions;

import java.util.*;

public interface CommonModel <T>{
    String generateID();
    void saveToFile(List<T> list);
    void add();
}