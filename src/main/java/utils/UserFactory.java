package utils;

import dto.User;
import net.datafaker.Faker;

public class UserFactory {
    static Faker faker = new Faker();
    public static void main(String[] args){
//        String firstName = faker.name().firstName();
//        String lastName = faker.name().lastName();
//        String email = faker.internet().emailAddress();
    }
    public static User positiveUser(){
        User user = new User(faker.internet().emailAddress(), "QWEasd123!");
        return user;
    }
}
