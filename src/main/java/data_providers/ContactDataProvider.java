package data_providers;

import dto.Contact;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static utils.ContactFactory.positiveContact;

public class ContactDataProvider {
    @DataProvider
    public Iterator<Contact> dataProviderFromFile_Positive() {
        return dataProviderFromFile("src/test/resources/data_csv/data_contacts.csv");
    }

    @DataProvider
    public Iterator<Contact> dataProviderFromFile_Negative_RequiredTextFields_Empty() {
        return dataProviderFromFile("src/test/resources/data_csv/required_text_fields_negative_data.csv");
    }

    @DataProvider
    public Iterator<Contact> dataProviderFromFile_Negative_RequiredTextFields_Security() {
        return dataProviderFromFile("src/test/resources/data_csv/regular_text_fields_negative_security_data.csv");
    }

    @DataProvider
    public Iterator<Contact> dataProviderFromFile_Negative_PhoneNumber() {
        return dataProviderFromFile("src/test/resources/data_csv/phone_negative_data.csv");
    }

    @DataProvider
    public Iterator<Contact> dataProviderFromFile_Negative_Email() {
        return dataProviderFromFile("src/test/resources/data_csv/email_negative_data.csv");
    }


    public Iterator<Contact> dataProviderFromFile(String filename) {
        List<Contact> contactList = new ArrayList<>();
        try (BufferedReader bufferReader = new BufferedReader(new FileReader
                (filename))) {
            String line = bufferReader.readLine();
            while (line != null) {
                String[] splitArray = line.split(",");
                contactList.add(Contact.builder()
                        .name(splitArray[0])
                        .lastName(splitArray[1])
                        .email(splitArray[2])
                        .phone(splitArray[3])
                        .address(splitArray[4])
                        .description(splitArray.length > 5 && !splitArray[5].isBlank()
                                ? splitArray[5] : "")
                        .build());
                line = bufferReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FileReader exception");
        }
        return contactList.listIterator();
    }

    @DataProvider
    public Iterator<Contact> dataProviderFromFile_WrongPhone() {
        List<Contact> list = new ArrayList<>();
        Contact contact = positiveContact();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader
                             ("src/test/resources/data_csv/dp_wrong_phone.csv"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                list.add(Contact.builder()
                        .name(contact.getName())
                        .lastName(contact.getLastName())
                        .email(contact.getEmail())
                        .phone(line)
                        .address(contact.getAddress())
                        .description(contact.getDescription())
                        .build());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO exception");
        }
        return list.listIterator();
    }
}
