package be.abis.exb1b.ut;

import be.abis.exb1b.model.Address;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAddress {

    Address a;
    @BeforeEach
    public void setUp(){
        a = new Address("Diestsevest","32 bus 4B","3000","Leuven","Belgium","BE");
    }
    @Test
    public void testThatBelgianZipCodeIsNumeric() {
       boolean b = a.checkBelgianZipCode();
       assertTrue(b);
    }


    @Test
    public void appendingAddressToFileShouldReturnExtraLineInFile() throws IOException {
        Path path = Address.getFilePath();
        int countBeforeWrite=0;
        if (Files.exists(path)) {
            path.toFile().setWritable(true);
            countBeforeWrite = Files.readAllLines(path).size();
        }
        a.writeToFile();
        int countAfterWrite = Files.readAllLines(path).size();
        int linesAdded=countAfterWrite - countBeforeWrite;
        assertEquals(1,linesAdded);
    }

    @Test
    public void appendingAddressToExistingReadOnlyFileShouldThrowIOException() throws IOException {
        Path path = Address.getFilePath();
        File file = path.toFile();
        if (!Files.exists(path)) {
            file.createNewFile();
        }
        file.setReadOnly();

        assertThrows(IOException.class, () -> a.writeToFile());
    }

}
