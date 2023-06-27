package org.example.spring;

import org.example.model.Dog;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;
import java.util.function.Predicate;

public class Runner {
    public static void main(String[] args) {
        final var sharik = Dog.builder()
                .name("Sharik")
                .build();

        final var sharik2 = Dog.builder()
                .name("Sharik")
                .build();

        System.out.println(sharik2 == sharik);


        // Context
        Map<String, Dog> map = new HashMap<>();
        map.put("shar", sharik);

        map.get("sharik");

        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("application.xml");

        DogSpring dogSpring = applicationContext.getBean("dog", DogSpring.class);
        DogSpring dogSpring2 = applicationContext.getBean("dog", DogSpring.class);
        HomeSpring homeSpring = applicationContext.getBean("home", HomeSpring.class);
        HumanSpring humanSpring = applicationContext.getBean("human", HumanSpring.class);

//        dogSpring.name = "max2";
//        System.out.println(dogSpring);
//        System.out.println(humanSpring);
//        System.out.println(homeSpring);

        AccountingSpring accountingSpring =
                applicationContext.getBean("accountingSpring", AccountingSpring.class);

//        System.out.println(accountingSpring);

        List<DogSpring> dogSpringSpringList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            dogSpringSpringList.add(applicationContext.getBean("dog", DogSpring.class));
        }

        dogSpringSpringList.add(new DogSpring("Sobaka", 5));

//        System.out.println(dogSpringSpringList.size());

//        for (DogSpring dog : dogSpringSpringList) {
//            if (dog.getAge() == 5) {
//                System.out.println(dog.getName().toUpperCase());
//            }
//        }

        dogSpringSpringList.stream()
                .filter(sobaka -> sobaka.getAge() == 5)
                .forEach(sobaka -> System.out.println(sobaka));
        // [a, a, a, a, a, b] -> | | | | | | | -> [b]
    }
}
