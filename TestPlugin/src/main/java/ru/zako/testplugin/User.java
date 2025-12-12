package ru.zako.testplugin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class User {
    private String name;
    private int age;
}
