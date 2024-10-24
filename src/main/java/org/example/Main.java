package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        File input = new File("books.json");

        Gson gson = new Gson();

        List<Person> people;
        try {
            people = gson.fromJson(new FileReader(input), new TypeToken<>() {
            });
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Task 1
        people.forEach(System.out::println);

        System.out.println("Total people: " + people.size());

        // Task 2
        Set<Book> uniqueBooks = people.stream()
                .map(Person::getFavoriteBooks)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        // Не стал делать вывод сета здесь, так как в 3 задании вывод повторится
        System.out.println("Total books: " + uniqueBooks.size());

        // Task 3
        List<Book> sortedBooks = new ArrayList<Book>(uniqueBooks);
        sortedBooks.sort(Comparator.comparing(Book::getPublishingYear));

        System.out.println("Sorted books:");
        sortedBooks.forEach(System.out::println);

        // Task 4
        boolean isExist = sortedBooks.stream().anyMatch(book -> "Jane Austen".equals(book.getAuthor()));
        System.out.println("Книга издателя Jane Austen " + (isExist ? "" : "не ") + "существует.");

        // Task 5
        int maxBooks = 0;
        int currentSize;

        for(Person p : people)
            if((currentSize = p.getFavoriteBooks().size()) > maxBooks)
                maxBooks = currentSize;

        System.out.println("Максимум избранных книг: " + maxBooks);

        // Task 6
        List<Person> subscribers = people.stream()
                .filter(Person::isSubscribed)
                .toList();

        //Из задания не совсем понятно, нужно ли округлять среднее число книг,
        //но логично было бы округлить вверх (зачем вообще ceil возвращает double?)

        int avgBooks = (int) Math.ceil(people.stream()
                .mapToInt(p -> p.getFavoriteBooks().size())
                .average()
                .orElse(0));

        System.out.println("Среднее число избранных книг: " + avgBooks);

        String[] messages = {"read more", "fine", "you are a bookworm"};

        List<SMS> SMSs = subscribers.stream()
                .map(p ->
                        new SMS(p, messages[(int) Math.signum(p.getFavoriteBooks().size() - avgBooks) + 1]))
                .toList();

        SMSs.forEach(System.out::println);
    }
}